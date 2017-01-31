package com.azimo.tool.firebase.interceptor;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.firebase.FirebaseAuth;
import com.azimo.tool.utils.file.FilePathParser;
import okhttp3.Request;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import helper.MockHelper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class FirebaseCredentialInterceptorTest {

    FirebaseCredentialInterceptor firebaseCredentialInterceptor;
    Request request;

    @Mock
    FirebaseAuth firebaseAuth;
    @Mock
    AppConfig appConfig;
    @Mock
    FilePathParser filePathParser;
    @Mock
    okhttp3.Interceptor.Chain chainMock;
    @Captor
    ArgumentCaptor<Request> requestCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        request = MockHelper.getRequest();
        firebaseCredentialInterceptor = new FirebaseCredentialInterceptor(firebaseAuth, appConfig, filePathParser);
    }

    @Test
    public void whenAppliedInterceptor_ShouldAddFirebaseContentTypeHeader() throws Exception {
        String mockCredentials = "mockCredentials";

        when(firebaseAuth.requestAuthToken(anyString())).thenReturn(mockCredentials);
        when(filePathParser.parsePath(anyString())).thenReturn(mockCredentials);
        when(chainMock.request()).thenReturn(request);
        when(appConfig.get(AppConfigKey.FIREBASE_AUTH_CREDENTIALS_PATH)).thenReturn(mockCredentials);

        firebaseCredentialInterceptor.intercept(chainMock);

        verify(chainMock).proceed(requestCaptor.capture());

        assertEquals(requestCaptor.getValue().header(
            FirebaseCredentialInterceptor.AUTHORISATION_HEADER),
            FirebaseCredentialInterceptor.AUTHORISATION_VALUE + " " + mockCredentials);
    }
}
