package com.azimo.tool.publisher.module;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.publisher.AndroidPublisherAuth;
import com.azimo.tool.publisher.AndroidPublisherBuilder;
import com.azimo.tool.publisher.AndroidPublisherReviewsService;
import com.azimo.tool.publisher.mapper.ReviewMapper;
import com.azimo.tool.utils.file.FilePathParser;
import com.google.api.services.androidpublisher.AndroidPublisher;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by F1sherKK on 16/01/17.
 */
@Module
public class PublisherModule {

    @Singleton
    @Provides
    public AndroidPublisherAuth provideAndroidPublisherAuth(AndroidPublisherBuilder credentials,
                                                            AppConfig appConfig,
                                                            FilePathParser filePathParser) {
        return new AndroidPublisherAuth(credentials, appConfig, filePathParser);
    }

    @Singleton
    @Provides
    public AndroidPublisher provideAndroidPublisher(AndroidPublisherAuth androidPublisherAuth) {
        return androidPublisherAuth.init();
    }

    @Singleton
    @Provides
    public AndroidPublisher.Reviews providePublisherReviews(AndroidPublisher androidPublisher) {
        return androidPublisher.reviews();
    }

    @Singleton
    @Provides
    public AndroidPublisherReviewsService provideAndroidPublisherReviewsService(AndroidPublisher.Reviews reviews,
                                                                                ReviewMapper reviewMapper,
                                                                                AppConfig appConfig) {
        return new AndroidPublisherReviewsService(reviews, reviewMapper, appConfig);
    }

    @Singleton
    @Provides
    public ReviewMapper provideReviewMapper() {
        return new ReviewMapper();
    }

    @Singleton
    @Provides
    public AndroidPublisherBuilder provideAndroidPublisherCredentials() {
        return new AndroidPublisherBuilder();
    }
}
