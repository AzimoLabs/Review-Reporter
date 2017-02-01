package com.azimo.tool.publisher;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.utils.file.FilePathParser;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;

/**
 * Created by F1sherKK on 14/12/16.
 */
public class AndroidPublisherAuth {

    private static JsonFactory JSON_FACTORY;
    private static HttpTransport HTTP_TRANSPORT;

    private AppConfig appConfig;
    private FilePathParser filePathParser;
    private AndroidPublisherBuilder androidPublisherBuilder;

    public AndroidPublisherAuth(AndroidPublisherBuilder credentials,
                                AppConfig appConfig,
                                FilePathParser filePathParser) {
        this.appConfig = appConfig;
        this.filePathParser = filePathParser;
        this.androidPublisherBuilder = credentials;
    }

    public AndroidPublisher init() {
        String applicationName = appConfig.get(AppConfigKey.APPLICATION_NAME);
        String serviceAccountEmail = appConfig.get(AppConfigKey.GOOGLE_PLAY_SERVICE_ACCOUNT_EMAIL);
        String googlePlayCredentialsFilePath =
            filePathParser.parsePath(appConfig.get(AppConfigKey.GOOGLE_PLAY_SERVICE_CREDENTIALS_PATH));

        Credential credential = null;
        try {

            if (HTTP_TRANSPORT == null) {
                HTTP_TRANSPORT = androidPublisherBuilder.newTrustedTransport();
            }

            if (JSON_FACTORY == null) {
                JSON_FACTORY = androidPublisherBuilder.newJacksonFactory();
            }

            credential = androidPublisherBuilder.authorizeWithServiceAccount(
                serviceAccountEmail,
                googlePlayCredentialsFilePath,
                HTTP_TRANSPORT,
                JSON_FACTORY);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return androidPublisherBuilder.build(HTTP_TRANSPORT, JSON_FACTORY, credential, applicationName);
    }
}
