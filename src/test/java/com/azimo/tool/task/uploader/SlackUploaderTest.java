package com.azimo.tool.task.uploader;

import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
import com.azimo.tool.firebase.model.ReportedReview;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.publisher.model.AppReview;
import com.azimo.tool.slack.SlackServiceManager;
import com.azimo.tool.slack.model.SlackMessage;
import com.azimo.tool.slack.response.SlackPostMessageResponse;
import com.azimo.tool.utils.converter.MessageConverter;
import com.azimo.tool.utils.converter.ReviewConverter;
import com.google.api.services.androidpublisher.model.Timestamp;
import com.google.api.services.androidpublisher.model.UserComment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 27/01/17.
 */
public class SlackUploaderTest {

    SlackUploader slackUploader;

    @Mock
    MessageConverter messageConverter;
    @Mock
    ReviewConverter reviewConverter;
    @Mock
    SlackServiceManager slackServiceManager;

    @Mock
    AppReview appReview1;
    @Mock
    AppReview appReview2;
    @Mock
    SlackMessage slackMessage1;
    @Mock
    SlackMessage slackMessage2;
    @Mock
    SlackPostMessageResponse slackPostMessageResponse1;
    @Mock
    SlackPostMessageResponse slackPostMessageResponse2;
    @Mock
    ReportedReview reportedReview1;
    @Mock
    ReportedReview reportedReview2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        slackUploader = new SlackUploader(messageConverter, reviewConverter, slackServiceManager);
    }

    @Test
    public void whenUploadingReviews_shouldReturnReportedReviewList() {
        final long minuteInSeconds = 60;
        final long hourInSeconds = 60 * minuteInSeconds;
        final long dayInSeconds = 24 * hourInSeconds;

        long nowInSeconds = System.currentTimeMillis() / 1000;

        UserComment userComment_sevenDaysOld = new UserComment();
        UserComment userComment_oneDayOld = new UserComment();

        Timestamp timestamp_sevenDaysOld = new Timestamp();
        Timestamp timestamp_oneDayOld = new Timestamp();

        timestamp_sevenDaysOld.setSeconds(nowInSeconds - 7 * dayInSeconds);
        timestamp_sevenDaysOld.setNanos(0);

        timestamp_oneDayOld.setSeconds(nowInSeconds - dayInSeconds);
        timestamp_oneDayOld.setNanos(0);

        userComment_sevenDaysOld.setLastModified(timestamp_sevenDaysOld);
        userComment_oneDayOld.setLastModified(timestamp_oneDayOld);

        String reviewId1 = "id1";
        String reviewId2 = "id2";

        when(appReview1.getReviewId()).thenReturn(reviewId1);
        when(appReview2.getReviewId()).thenReturn(reviewId2);
        when(appReview1.getFirstUserComment()).thenReturn(userComment_oneDayOld);
        when(appReview2.getFirstUserComment()).thenReturn(userComment_sevenDaysOld);

        ReviewCollection reviewCollection = new ReviewCollection();
        reviewCollection.add(appReview1);
        reviewCollection.add(appReview2);

        when(messageConverter.slackMessageFromAppReview(appReview1)).thenReturn(slackMessage1);
        when(messageConverter.slackMessageFromAppReview(appReview2)).thenReturn(slackMessage2);

        when(slackServiceManager.sendMessage(slackMessage1)).thenReturn(slackPostMessageResponse1);
        when(slackServiceManager.sendMessage(slackMessage2)).thenReturn(slackPostMessageResponse2);

        when(slackPostMessageResponse1.wasSuccess()).thenReturn(true);
        when(slackPostMessageResponse2.wasSuccess()).thenReturn(true);

        when(reportedReview1.getReportedReviewId()).thenReturn(reviewId1);
        when(reportedReview2.getReportedReviewId()).thenReturn(reviewId2);

        when(reviewConverter.reportedReviewFromAppReview(appReview1)).thenReturn(reportedReview1);
        when(reviewConverter.reportedReviewFromAppReview(appReview2)).thenReturn(reportedReview2);

        ReportedReviewsCollection reportedReviewsCollection = slackUploader.upload(reviewCollection);

        assertThat(reportedReviewsCollection.size() == 2, is(true));
        assertThat(reportedReviewsCollection.get(0).getReportedReviewId().equals(reviewId2), is(true));
        assertThat(reportedReviewsCollection.get(1).getReportedReviewId().equals(reviewId1), is(true));
    }
}
