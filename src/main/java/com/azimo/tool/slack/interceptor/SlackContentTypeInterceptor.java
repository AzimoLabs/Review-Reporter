package com.azimo.tool.slack.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by F1sherKK on 17/01/17.
 */
public class SlackContentTypeInterceptor implements Interceptor {

    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "application/json";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder()
            .addHeader(CONTENT_TYPE_HEADER, CONTENT_TYPE_VALUE);

        return chain.proceed(requestBuilder.build());
    }
}
