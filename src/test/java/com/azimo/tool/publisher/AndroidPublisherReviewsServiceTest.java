package com.azimo.tool.publisher;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.publisher.mapper.ReviewMapper;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.Review;
import com.google.api.services.androidpublisher.model.ReviewsListResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class AndroidPublisherReviewsServiceTest {

    AndroidPublisherReviewsService androidPublisherReviewsService;

    @Mock
    AndroidPublisher.Reviews reviewsClientMock;
    @Mock
    ReviewMapper reviewMapperMock;
    @Mock
    AppConfig appConfigMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        androidPublisherReviewsService = new AndroidPublisherReviewsService(
            reviewsClientMock, reviewMapperMock, appConfigMock);
    }

    @Test
    public void testWhenAskedForReviews_ShouldReturnReviewsCollection() throws Exception {
        ReviewsListResponse reviewsListResponse = new ReviewsListResponse();
        reviewsListResponse.setReviews(new ArrayList<>());

        AndroidPublisher.Reviews.List list = mock(AndroidPublisher.Reviews.List.class);
        when(appConfigMock.get(AppConfigKey.APPLICATION_NAME)).thenReturn("mock");
        when(reviewsClientMock.list(anyString())).thenReturn(list);
        when(list.setMaxResults(anyLong())).thenReturn(list);
        when(list.execute()).thenReturn(reviewsListResponse);

        when(reviewMapperMock.toAppReviewList(anyListOf(Review.class))).thenReturn(new ReviewCollection());

        ReviewCollection collection =
            androidPublisherReviewsService.getReviews(AndroidPublisherReviewsService.MAX_REVIEWS);

        assertThat(collection != null, is(true));
    }
}
