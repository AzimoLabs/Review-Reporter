package com.azimo.tool.firebase.collection;

import com.azimo.tool.firebase.model.ReportedReview;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class ReportedReviewsCollectionTest {

    ReportedReviewsCollection reportedReviewsCollection;

    @Mock
    ReportedReview reportedReview;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        reportedReviewsCollection = new ReportedReviewsCollection();
    }

    @Test
    public void testWhenAskedIfContainsReviewId_shouldReturnProperValue() {
        String reviewId1 = "mock1";
        String reviewId2 = "mock2";

        when(reportedReview.getReportedReviewId()).thenReturn(reviewId1);

        reportedReviewsCollection.add(reportedReview);
        boolean containsReview1 = reportedReviewsCollection.containsReviewId(reviewId1);
        boolean containsReview2 = reportedReviewsCollection.containsReviewId(reviewId2);

        assertThat(containsReview1, is(true));
        assertThat(containsReview2, is(false));
    }
}
