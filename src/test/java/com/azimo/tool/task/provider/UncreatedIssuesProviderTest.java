package com.azimo.tool.task.provider;

import com.azimo.tool.firebase.FirebaseServiceManager;
import com.azimo.tool.firebase.collection.CreatedIssueCollection;
import com.azimo.tool.publisher.AndroidPublisherReviewsService;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.publisher.model.AppReview;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 27/01/17.
 */
public class UncreatedIssuesProviderTest {

    UncreatedIssuesProvider uncreatedIssuesProvider;

    @Mock
    AndroidPublisherReviewsService publisherReviewsService;
    @Mock
    FirebaseServiceManager firebaseIssueServiceManager;
    @Mock
    CreatedIssueCollection createdIssueCollection;
    @Mock
    AppReview appReview1;
    @Mock
    AppReview appReview2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        uncreatedIssuesProvider = new UncreatedIssuesProvider(
            publisherReviewsService, firebaseIssueServiceManager);
    }

    @Test
    public void testOnFetch_shouldReturnReviewsWithoutCreatedIssues() throws Exception {
        String reviewId1 = "id1";
        String reviewId2 = "id2";

        ReviewCollection reviewCollection = new ReviewCollection();
        reviewCollection.add(appReview1);
        reviewCollection.add(appReview2);

        when(appReview1.getReviewId()).thenReturn(reviewId1);
        when(appReview2.getReviewId()).thenReturn(reviewId2);
        when(createdIssueCollection.containsReviewId(reviewId1)).thenReturn(true);
        when(createdIssueCollection.containsReviewId(reviewId2)).thenReturn(false);
        when(publisherReviewsService.getReviews(AndroidPublisherReviewsService.MAX_REVIEWS))
            .thenReturn(reviewCollection);
        when(firebaseIssueServiceManager.getCreatedIssues()).thenReturn(createdIssueCollection);

        ReviewCollection reviewsWithoutCreatedIssues = uncreatedIssuesProvider.fetch();

        assertThat(reviewsWithoutCreatedIssues.size() == 1, is(true));
        assertThat(reviewsWithoutCreatedIssues.get(0).getReviewId().equals(reviewId2), is(true));
    }
}
