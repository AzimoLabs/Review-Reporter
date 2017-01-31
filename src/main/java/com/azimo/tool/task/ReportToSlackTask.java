package com.azimo.tool.task;

import com.azimo.tool.firebase.FirebaseServiceManager;
import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.task.base.ReviewReporterTask;
import com.azimo.tool.task.provider.UnreportedReviewsProvider;
import com.azimo.tool.task.uploader.FirebaseReviewsUploader;
import com.azimo.tool.task.uploader.SlackUploader;

import javax.inject.Inject;

/**
 * Created by F1sherKK on 25/01/17.
 */
public class ReportToSlackTask extends ReviewReporterTask {

    private static final String TAG = "ReportToSlackTask:";
    private FirebaseServiceManager firebaseServiceManager;
    private UnreportedReviewsProvider unreportedReviewsProvider;
    private SlackUploader slackUploader;
    private FirebaseReviewsUploader firebaseReviewsUploader;


    @Inject
    public ReportToSlackTask(UnreportedReviewsProvider unreportedReviewsProvider,
                             SlackUploader slackUploader,
                             FirebaseServiceManager firebaseServiceManager,
                             FirebaseReviewsUploader firebaseReviewsUploader) {
        this.unreportedReviewsProvider = unreportedReviewsProvider;
        this.slackUploader = slackUploader;
        this.firebaseServiceManager = firebaseServiceManager;
        this.firebaseReviewsUploader = firebaseReviewsUploader;

        System.out.println("ReportToSlackTask runs!");
    }

    @Override
    public void run() throws  Exception {
        attemptToSendReviewsToSlack();
    }

    private void attemptToSendReviewsToSlack() throws Exception {
        System.out.println(TAG + "Attempts to report reviews to Slack.");

        ReviewCollection unreportedReviews = unreportedReviewsProvider.fetch();
        System.out.println(TAG + "There are " + unreportedReviews.size() + " unreported reviews.");

        ReportedReviewsCollection reportedToSlackReviewsRecently = slackUploader.upload(unreportedReviews);
        System.out.println(TAG + "Sent " + reportedToSlackReviewsRecently.size() + " reviews as Slack messages.");

        ReportedReviewsCollection reportedToSlackReviewsAllTime = firebaseServiceManager.getReportedReviews();
        reportedToSlackReviewsAllTime.addAll(reportedToSlackReviewsRecently);

        firebaseReviewsUploader.upload(reportedToSlackReviewsAllTime);
        System.out.println(TAG + "Updated Firebase with reviews ids.");
        System.out.println(TAG + "Finished work.\n");
    }
}
