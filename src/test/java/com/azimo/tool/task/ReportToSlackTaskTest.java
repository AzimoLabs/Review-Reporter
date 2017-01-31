package com.azimo.tool.task;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.firebase.FirebaseServiceManager;
import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
import com.azimo.tool.firebase.response.DefaultFirebaseResponse;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.task.provider.UnreportedReviewsProvider;
import com.azimo.tool.task.uploader.FirebaseReviewsUploader;
import com.azimo.tool.task.uploader.SlackUploader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 26/01/17.
 */
public class ReportToSlackTaskTest {

    ReportToSlackTask reportToSlackTask;

    @Mock
    AppConfig appConfig;
    @Mock
    UnreportedReviewsProvider unreportedReviewsProvider;
    @Mock
    SlackUploader slackUploader;
    @Mock
    FirebaseReviewsUploader firebaseReviewsUploader;

    @Mock
    ReviewCollection unreportedReviews;
    @Mock
    ReportedReviewsCollection reportedReviews;
    @Mock
    FirebaseServiceManager firebaseServiceManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        reportToSlackTask = new ReportToSlackTask(
            unreportedReviewsProvider, slackUploader, firebaseServiceManager, firebaseReviewsUploader);
    }

    @Test
    public void testWhenPerformingRun_shouldFetchUnreportedReviewsAndSendThemToSlackAndFirebase() throws Exception {
        when(unreportedReviewsProvider.fetch()).thenReturn(unreportedReviews);
        when(slackUploader.upload(unreportedReviews)).thenReturn(reportedReviews);
        when(firebaseServiceManager.getReportedReviews()).thenReturn(reportedReviews);
        when(firebaseReviewsUploader.upload(reportedReviews)).thenReturn(new DefaultFirebaseResponse());

        reportToSlackTask.run();

        verify(unreportedReviewsProvider).fetch();
        verify(slackUploader).upload(unreportedReviews);
        verify(firebaseServiceManager).getReportedReviews();
        verify(firebaseReviewsUploader).upload(reportedReviews);
    }
}


