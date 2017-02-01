package com.azimo.tool.jira.interceptor;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by F1sherKK on 10/01/17.
 */
public class JiraCredentialInterceptor implements Interceptor {

    public static final String AUTHORISATION_HEADER = "Authorization";
    public static final String AUTHORISATION_VALUE = "Basic";

    private AppConfig config;

    public JiraCredentialInterceptor(AppConfig config) {
        this.config = config;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String credentials = AUTHORISATION_VALUE + " " + config.get(AppConfigKey.JIRA_AUTH_CREDENTIALS);

        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder()
            .addHeader(AUTHORISATION_HEADER, credentials);

        return chain.proceed(requestBuilder.build());
    }
}
