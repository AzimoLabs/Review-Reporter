package com.azimo.tool.task;

import com.azimo.tool.firebase.FirebaseServiceManager;
import com.azimo.tool.firebase.collection.CreatedIssueCollection;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.task.base.ReviewReporterTask;
import com.azimo.tool.task.provider.UncreatedIssuesProvider;
import com.azimo.tool.task.uploader.FirebaseIssuesUploader;
import com.azimo.tool.task.uploader.JiraUploader;

import javax.inject.Inject;

/**
 * Created by F1sherKK on 25/01/17.
 */
public class CreateJiraIssuesTask extends ReviewReporterTask {

    private static final String TAG = "CreateJiraIssuesTask:";

    private FirebaseServiceManager firebaseServiceManager;
    private UncreatedIssuesProvider uncreatedIssuesProvider;
    private JiraUploader jiraUploader;
    private FirebaseIssuesUploader firebaseIssuesUploader;

    @Inject
    public CreateJiraIssuesTask(UncreatedIssuesProvider uncreatedIssuesProvider,
                                JiraUploader jiraUploader,
                                FirebaseServiceManager firebaseServiceManager,
                                FirebaseIssuesUploader firebaseIssuesUploader) {
        this.uncreatedIssuesProvider = uncreatedIssuesProvider;
        this.jiraUploader = jiraUploader;
        this.firebaseServiceManager = firebaseServiceManager;
        this.firebaseIssuesUploader = firebaseIssuesUploader;

        System.out.println("CreateJiraIssuesTask runs!");
    }

    @Override
    public void run() throws Exception {
        attemptToCreateJiraIssues();
    }

    private void attemptToCreateJiraIssues() throws Exception {
        System.out.println(TAG + "Attempts to create Jira issues.");

        ReviewCollection reviewsWithoutIssuesCreated = uncreatedIssuesProvider.fetch();

        ReviewCollection notReportedReviewsWithThreeOrLessStars = reviewsWithoutIssuesCreated.getWithMinThreeStars();
        System.out.println(TAG + "There are " + notReportedReviewsWithThreeOrLessStars.size()
            + " reviews, with three stars or less, without Jira issue created.");

        CreatedIssueCollection createdIssuesRecently = jiraUploader.upload(notReportedReviewsWithThreeOrLessStars);
        System.out.println(TAG + "Created " + createdIssuesRecently.size() + " Jira issues.");

        CreatedIssueCollection createdIssuesAllTime = firebaseServiceManager.getCreatedIssues();
        createdIssuesAllTime.addAll(createdIssuesRecently);

        firebaseIssuesUploader.upload(createdIssuesAllTime);
        System.out.println(TAG + "Updated Firebase with reviews ids.");
        System.out.println(TAG + "Finished work.\n");
    }
}
