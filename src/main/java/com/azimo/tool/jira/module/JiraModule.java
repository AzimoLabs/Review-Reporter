package com.azimo.tool.jira.module;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.jira.JiraIssueService;
import com.azimo.tool.jira.JiraIssueServiceManager;
import com.azimo.tool.jira.interceptor.JiraContentTypeInterceptor;
import com.azimo.tool.jira.interceptor.JiraCredentialInterceptor;
import com.azimo.tool.jira.interceptor.JiraXSRFInterceptor;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

/**
 * Created by F1sherKK on 16/01/17.
 */
@Module
public class JiraModule {

    @Singleton
    @Provides
    @Named("JiraApi")
    public OkHttpClient provideJiraOkHttpClient(AppConfig appConfig) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new JiraCredentialInterceptor(appConfig));
        clientBuilder.addInterceptor(new JiraContentTypeInterceptor());
        clientBuilder.addInterceptor(new JiraXSRFInterceptor());
        clientBuilder.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        clientBuilder.readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        clientBuilder.writeTimeout(60 * 1000, TimeUnit.MILLISECONDS);

        return clientBuilder.build();
    }

    @Singleton
    @Provides
    @Named("JiraApi")
    public Retrofit provideJiraRetrofit(AppConfig appConfig,
                                        @Named("JiraApi") OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(appConfig.get(AppConfigKey.JIRA_BASEPATH))
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        return builder.build();
    }

    @Singleton
    @Provides
    public JiraIssueService provideJiraIssueService(@Named("JiraApi") Retrofit jiraRetrofit) {
        return jiraRetrofit.create(JiraIssueService.class);
    }

    @Singleton
    @Provides
    public JiraIssueServiceManager provideJiraIssueServiceManager(JiraIssueService issueService) {
        return new JiraIssueServiceManager(issueService);
    }
}
