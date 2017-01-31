package com.azimo.tool.utils.converter;

import com.azimo.tool.firebase.model.ReportedReview;
import com.azimo.tool.publisher.model.AppReview;
import com.google.api.services.androidpublisher.model.Review;
import com.google.api.services.androidpublisher.model.Timestamp;
import com.google.api.services.androidpublisher.model.UserComment;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 26/01/17.
 */
public class ReviewConverterTest {

    ReviewConverter reviewConverter;

    @Mock
    AppReview appReview;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        reviewConverter = new ReviewConverter();
    }

    @Test
    public void whenInsertedAppReview_ShouldReturnReportedReview() {
        Review review = new Review();
        AppReview appReview = new AppReview(review);

        ReportedReview reportedReview = reviewConverter.reportedReviewFromAppReview(appReview);

        assertThat(reportedReview != null, is(true));
    }

    @Test
    public void whenInsertedAppReview_ShouldReturnReportedReviewWithCopiedTimestamp() {
        long timeInSeconds = 123;
        long expectedCreatedTimeInMillis = timeInSeconds * 1000;

        Timestamp timeStamp = new Timestamp();
        timeStamp.setNanos(0);
        timeStamp.setSeconds(timeInSeconds);

        UserComment userComment = new UserComment();
        userComment.setLastModified(timeStamp);

        when(appReview.getFirstUserComment()).thenReturn(userComment);

        ReportedReview reportedReview = reviewConverter.reportedReviewFromAppReview(appReview);

        assertThat(reportedReview.getReportedReviewTime() == expectedCreatedTimeInMillis, Matchers.is(true));
    }

    @Test
    public void whenInsertedAppReview_ShouldReturnReportedReviewWithCopiedId() {
        String expectedReviewId = "mockedReviewId";
        when(appReview.getReviewId()).thenReturn(expectedReviewId);

        ReportedReview reportedReview = reviewConverter.reportedReviewFromAppReview(appReview);

        assertThat(reportedReview.getReportedReviewId().equals(expectedReviewId), Matchers.is(true));
    }
}
