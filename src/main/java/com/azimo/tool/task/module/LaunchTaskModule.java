package com.azimo.tool.task.module;

import com.azimo.tool.firebase.FirebaseServiceManager;
import com.azimo.tool.jira.JiraIssueServiceManager;
import com.azimo.tool.publisher.AndroidPublisherReviewsService;
import com.azimo.tool.slack.SlackServiceManager;
import com.azimo.tool.task.CreateJiraIssuesTask;
import com.azimo.tool.task.ReportToSlackTask;
import com.azimo.tool.task.provider.UncreatedIssuesProvider;
import com.azimo.tool.task.provider.UnreportedReviewsProvider;
import com.azimo.tool.task.uploader.FirebaseIssuesUploader;
import com.azimo.tool.task.uploader.FirebaseReviewsUploader;
import com.azimo.tool.task.uploader.JiraUploader;
import com.azimo.tool.task.uploader.SlackUploader;
import com.azimo.tool.utils.converter.IssueConverter;
import com.azimo.tool.utils.converter.MessageConverter;
import com.azimo.tool.utils.converter.ReviewConverter;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by F1sherKK on 25/01/17.
 */
@Module
public class LaunchTaskModule {

    @Singleton
    @Provides
    public CreateJiraIssuesTask providesCreateJiraIssuesTask(UncreatedIssuesProvider uncreatedIssuesProvider,
                                                             JiraUploader jiraUploader,
                                                             FirebaseServiceManager firebaseServiceManager,
                                                             FirebaseIssuesUploader firebaseIssuesUploader) {
        return new CreateJiraIssuesTask(
            uncreatedIssuesProvider,
            jiraUploader,
            firebaseServiceManager,
            firebaseIssuesUploader);
    }

    @Singleton
    @Provides
    public ReportToSlackTask providesReportToSlackTask(UnreportedReviewsProvider unreportedReviewsProvider,
                                                       SlackUploader slackUploader,
                                                       FirebaseServiceManager firebaseServiceManager,
                                                       FirebaseReviewsUploader firebaseReviewsUploader) {
        return new ReportToSlackTask(
            unreportedReviewsProvider,
            slackUploader,
            firebaseServiceManager,
            firebaseReviewsUploader);
    }

    @Singleton
    @Provides
    public FirebaseIssuesUploader providesFirebaseIssuesUploader(FirebaseServiceManager firebaseServiceManager) {
        return new FirebaseIssuesUploader(firebaseServiceManager);
    }

    @Singleton
    @Provides
    public FirebaseReviewsUploader providesFirebaseReviewsUploader(FirebaseServiceManager firebaseServiceManager) {
        return new FirebaseReviewsUploader(firebaseServiceManager);
    }

    @Singleton
    @Provides
    public JiraUploader providesJiraUploader(IssueConverter issueConverter, JiraIssueServiceManager serviceManager) {
        return new JiraUploader(issueConverter, serviceManager);
    }

    @Singleton
    @Provides
    public SlackUploader providesSlackUploader(MessageConverter messageConverter,
                                               ReviewConverter reviewConverter,
                                               SlackServiceManager slackServiceManager) {
        return new SlackUploader(messageConverter, reviewConverter, slackServiceManager);
    }

    @Singleton
    @Provides
    public UncreatedIssuesProvider providesUncreatedIssuesProvider(AndroidPublisherReviewsService service,
                                                                   FirebaseServiceManager firebaseServiceManager) {
        return new UncreatedIssuesProvider(service, firebaseServiceManager);
    }

    @Singleton
    @Provides
    public UnreportedReviewsProvider unreportedReviewsProvider(AndroidPublisherReviewsService service,
                                                               FirebaseServiceManager firebaseServiceManager) {
        return new UnreportedReviewsProvider(service, firebaseServiceManager);
    }
}
