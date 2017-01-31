package com.azimo.tool.jira.interceptor;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import okhttp3.Request;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import helper.MockHelper;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class JavaCredentialInterceptorTest {

    JiraCredentialInterceptor jiraCredentialInterceptor;
    Request requestMock;

    @Mock
    AppConfig appConfig;
    @Mock
    okhttp3.Interceptor.Chain chainMock;
    @Captor
    ArgumentCaptor<Request> requestCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        requestMock = MockHelper.getRequest();
        jiraCredentialInterceptor = new JiraCredentialInterceptor(appConfig);
    }

    @Test
    public void whenAppliedInterceptor_ShouldAddJiraCredentials() throws IOException {
        String mockCredentials = "mockCredentials";

        when(chainMock.request()).thenReturn(requestMock);
        when(appConfig.get(AppConfigKey.JIRA_AUTH_CREDENTIALS)).thenReturn(mockCredentials);

        jiraCredentialInterceptor.intercept(chainMock);

        verify(chainMock).proceed(requestCaptor.capture());

        assertEquals(requestCaptor.getValue().header(
            JiraCredentialInterceptor.AUTHORISATION_HEADER),
            JiraCredentialInterceptor.AUTHORISATION_VALUE + " " + mockCredentials);
    }
}
