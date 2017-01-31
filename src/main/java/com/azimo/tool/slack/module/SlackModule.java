package com.azimo.tool.slack.module;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.slack.SlackService;
import com.azimo.tool.slack.SlackServiceManager;
import com.azimo.tool.slack.interceptor.SlackContentTypeInterceptor;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

/**
 * Created by F1sherKK on 24/01/17.
 */
@Module
public class SlackModule {

    @Singleton
    @Provides
    @Named("SlackApi")
    public OkHttpClient provideSlackOkHttpClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new SlackContentTypeInterceptor());
        clientBuilder.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        clientBuilder.readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        clientBuilder.writeTimeout(60 * 1000, TimeUnit.MILLISECONDS);

        return clientBuilder.build();
    }

    @Singleton
    @Provides
    public SlackService provideSlackIssueService(@Named("SlackApi") OkHttpClient slackOkHttp, Gson gson) {
        return new SlackService(slackOkHttp, gson);
    }

    @Singleton
    @Provides
    public SlackServiceManager provideSlackServiceManager(SlackService slackService, AppConfig appConfig) {
        return new SlackServiceManager(slackService, appConfig);
    }
}
