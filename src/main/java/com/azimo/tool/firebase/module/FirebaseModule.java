package com.azimo.tool.firebase.module;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.firebase.FirebaseAuth;
import com.azimo.tool.firebase.FirebaseService;
import com.azimo.tool.firebase.FirebaseServiceManager;
import com.azimo.tool.firebase.interceptor.FirebaseContentTypeInterceptor;
import com.azimo.tool.firebase.interceptor.FirebaseCredentialInterceptor;
import com.azimo.tool.firebase.mapper.CreatedIssueMapper;
import com.azimo.tool.firebase.mapper.ReportedReviewMapper;
import com.azimo.tool.utils.file.FilePathParser;
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
public class FirebaseModule {

    @Singleton
    @Provides
    @Named("FirebaseApi")
    public OkHttpClient provideFirebaseOkHttpClient(AppConfig appConfig,
                                                    FilePathParser pathParser,
                                                    FirebaseAuth firebaseAuth) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new FirebaseContentTypeInterceptor());
        clientBuilder.addInterceptor(new FirebaseCredentialInterceptor(firebaseAuth, appConfig, pathParser));
        clientBuilder.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        clientBuilder.readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        clientBuilder.writeTimeout(60 * 1000, TimeUnit.MILLISECONDS);

        return clientBuilder.build();
    }

    @Singleton
    @Provides
    @Named("FirebaseApi")
    public Retrofit provideFirebaseRetrofit(AppConfig appConfig,
                                            @Named("FirebaseApi") OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(appConfig.get(AppConfigKey.FIREBASE_BASEPATH))
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        return builder.build();
    }

    @Singleton
    @Provides
    public FirebaseService provideFirebaseIssueService(@Named("FirebaseApi") Retrofit firebaseRetrofit) {
        return firebaseRetrofit.create(FirebaseService.class);
    }

    @Singleton
    @Provides
    public FirebaseServiceManager provideFirebaseIssueServiceManager(FirebaseService issueService,
                                                                     CreatedIssueMapper createdIssueMapper,
                                                                     ReportedReviewMapper reportedReviewMapper) {
        return new FirebaseServiceManager(issueService, createdIssueMapper, reportedReviewMapper);
    }

    @Singleton
    @Provides
    public CreatedIssueMapper provideCreatedIssueMapper() {
        return new CreatedIssueMapper();
    }


    @Singleton
    @Provides
    public ReportedReviewMapper provideReportedReviewMapper() {
        return new ReportedReviewMapper();
    }

    @Singleton
    @Provides
    public FirebaseAuth provideFirebaseAuth() {
        return new FirebaseAuth();
    }
}
