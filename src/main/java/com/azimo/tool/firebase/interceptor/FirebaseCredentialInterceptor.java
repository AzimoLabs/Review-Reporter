package com.azimo.tool.firebase.interceptor;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.firebase.FirebaseAuth;
import com.azimo.tool.utils.file.FilePathParser;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by F1sherKK on 10/01/17.
 */
public class FirebaseCredentialInterceptor implements Interceptor {

    public static final String AUTHORISATION_HEADER = "Authorization";
    public static final String AUTHORISATION_VALUE = "Bearer";

    private AppConfig config;
    private FilePathParser parser;
    private FirebaseAuth firebaseAuth;

    public FirebaseCredentialInterceptor(FirebaseAuth firebaseAuth,
                                         AppConfig config,
                                         FilePathParser parser) {
        this.firebaseAuth = firebaseAuth;
        this.config = config;
        this.parser = parser;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String credentialsPath = config.get(AppConfigKey.FIREBASE_AUTH_CREDENTIALS_PATH);
        String parsedCredentialsPath = parser.parsePath(credentialsPath);

        String credentials = AUTHORISATION_VALUE + " ";
        try {
            credentials += firebaseAuth.requestAuthToken(parsedCredentialsPath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder()
            .addHeader(AUTHORISATION_HEADER, credentials);

        return chain.proceed(requestBuilder.build());
    }
}
