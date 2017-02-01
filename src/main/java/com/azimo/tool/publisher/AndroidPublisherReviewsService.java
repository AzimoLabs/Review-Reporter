package com.azimo.tool.publisher;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.publisher.mapper.ReviewMapper;
import com.azimo.tool.publisher.model.AppReview;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.ReviewsListResponse;

import java.io.IOException;
import java.util.List;

/**
 * Created by F1sherKK on 10/01/17.
 */
public class AndroidPublisherReviewsService {

    public static final int MAX_REVIEWS = 500;

    private AndroidPublisher.Reviews reviews;
    private ReviewMapper reviewMapper;
    private AppConfig appConfig;

    public AndroidPublisherReviewsService(AndroidPublisher.Reviews reviews,
                                          ReviewMapper reviewMapper,
                                          AppConfig appConfig) {
        this.reviews = reviews;
        this.reviewMapper = reviewMapper;
        this.appConfig = appConfig;
    }

    public ReviewCollection getReviews(int maxResults) throws IOException {
        ReviewCollection reviewCollection = new ReviewCollection();

        ReviewsListResponse response = reviews
            .list(appConfig.get(AppConfigKey.ANDROID_PACKAGE_NAME))
            .setMaxResults((long) maxResults)
            .execute();

        List<AppReview> appReviewList = reviewMapper.toAppReviewList(response.getReviews());
        reviewCollection.addAll(appReviewList);

        return reviewCollection;
    }
}
