package com.azimo.tool.task.uploader;

import com.azimo.tool.firebase.FirebaseServiceManager;
import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
import com.azimo.tool.firebase.response.DefaultFirebaseResponse;
import com.azimo.tool.task.interfaces.Uploader;

import javax.inject.Inject;

/**
 * Created by F1sherKK on 27/01/17.
 */
public class FirebaseReviewsUploader implements Uploader<ReportedReviewsCollection, DefaultFirebaseResponse> {

    private FirebaseServiceManager firebaseServiceManager;

    @Inject
    public FirebaseReviewsUploader(FirebaseServiceManager firebaseServiceManager) {
        this.firebaseServiceManager = firebaseServiceManager;
    }

    @Override
    public DefaultFirebaseResponse upload(ReportedReviewsCollection reportedToSlackReviews) {
        return firebaseServiceManager.updateReportedReviews(reportedToSlackReviews);
    }
}
