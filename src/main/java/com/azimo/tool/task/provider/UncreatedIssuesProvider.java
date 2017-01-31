package com.azimo.tool.task.provider;

import com.azimo.tool.firebase.FirebaseServiceManager;
import com.azimo.tool.firebase.collection.CreatedIssueCollection;
import com.azimo.tool.publisher.AndroidPublisherReviewsService;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.publisher.model.AppReview;
import com.azimo.tool.task.interfaces.Provider;

import javax.inject.Inject;

/**
 * Created by F1sherKK on 27/01/17.
 */
public class UncreatedIssuesProvider implements Provider<ReviewCollection> {

    private AndroidPublisherReviewsService publisherReviewsService;
    private FirebaseServiceManager firebaseIssueServiceManager;

    @Inject
    public UncreatedIssuesProvider(AndroidPublisherReviewsService publisherReviewsService,
                                   FirebaseServiceManager firebaseIssueServiceManager) {
        this.publisherReviewsService = publisherReviewsService;
        this.firebaseIssueServiceManager = firebaseIssueServiceManager;
    }

    @Override
    public ReviewCollection fetch() throws Exception {
        ReviewCollection reviews = publisherReviewsService.getReviews(AndroidPublisherReviewsService.MAX_REVIEWS);
        CreatedIssueCollection createdIssues = firebaseIssueServiceManager.getCreatedIssues();

        ReviewCollection reviewsWithoutIssuesCreated = new ReviewCollection();
        for (AppReview review : reviews) {
            if (!createdIssues.containsReviewId(review.getReviewId())) {
                reviewsWithoutIssuesCreated.add(review);
            }
        }
        return reviewsWithoutIssuesCreated;
    }
}
