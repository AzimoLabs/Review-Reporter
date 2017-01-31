package com.azimo.tool.publisher;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.utils.file.FilePathParser;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class AndroidPublisherAuthTest {

    AndroidPublisherAuth androidPublisherAuth;

    @Mock
    AppConfig appConfigMock;
    @Mock
    FilePathParser filePathParserMock;
    @Mock
    AndroidPublisherBuilder androidPublisherBuilderMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        androidPublisherAuth = new AndroidPublisherAuth(
            androidPublisherBuilderMock,
            appConfigMock,
            filePathParserMock);
    }

    @Test
    public void testWhenInitialised_ShouldAttemptToRetrieveAndroidPublisher() throws Exception {
        String mock = "mock";
        String parsedMock = "parsedMock";
        HttpTransport httpTransportMock = mock(HttpTransport.class);
        JsonFactory jsonFactoryMock = mock(JsonFactory.class);
        Credential credential = mock(Credential.class);

        when(appConfigMock.get(AppConfigKey.APPLICATION_NAME)).thenReturn(mock);
        when(appConfigMock.get(AppConfigKey.GOOGLE_PLAY_SERVICE_ACCOUNT_EMAIL)).thenReturn(mock);
        when(appConfigMock.get(AppConfigKey.GOOGLE_PLAY_SERVICE_CREDENTIALS_PATH)).thenReturn(mock);
        when(filePathParserMock.parsePath(anyString())).thenReturn(parsedMock);
        when(androidPublisherBuilderMock.newJacksonFactory()).thenReturn(jsonFactoryMock);
        when(androidPublisherBuilderMock.newTrustedTransport()).thenReturn(httpTransportMock);
        when(androidPublisherBuilderMock.authorizeWithServiceAccount(
            anyString(),
            anyString(),
            any(HttpTransport.class),
            any(JsonFactory.class))).thenReturn(credential);

        androidPublisherAuth.init();
        verify(androidPublisherBuilderMock, times(1))
            .build(httpTransportMock, jsonFactoryMock, credential, mock);
    }
}
