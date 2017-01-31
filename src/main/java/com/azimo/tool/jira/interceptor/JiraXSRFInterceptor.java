package com.azimo.tool.jira.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by F1sherKK on 10/01/17.
 */
public class JiraXSRFInterceptor implements Interceptor {

    public static final String ATLASSIAN_TOKEN_HEADER = "X-Atlassian-Token";
    public static final String ATLASSIAN_TOKEN_HEADER_VALUE = "no-check";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder()
            .addHeader(ATLASSIAN_TOKEN_HEADER, ATLASSIAN_TOKEN_HEADER_VALUE);

        return chain.proceed(requestBuilder.build());
    }
}
