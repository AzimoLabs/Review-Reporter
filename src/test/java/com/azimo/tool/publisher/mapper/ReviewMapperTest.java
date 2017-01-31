package com.azimo.tool.publisher.mapper;

import com.azimo.tool.publisher.model.AppReview;
import com.google.api.services.androidpublisher.model.Review;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class ReviewMapperTest {

    ReviewMapper reviewMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        reviewMapper = new ReviewMapper();
    }

    @Test
    public void testWhenInsertedListOfReviews_shouldReturnListOfAppReviews() {
        Review review1 = new Review();
        Review review2 = new Review();
        Review review3 = new Review();

        List<Review> reviewList = Arrays.asList(review1, review2, review3);
        List<AppReview> appReviewList = reviewMapper.toAppReviewList(reviewList);

        assertThat(reviewList.size() == appReviewList.size(), is(true));
        assertThat(appReviewList.get(0).getReview() == review1, is(true));
        assertThat(appReviewList.get(1).getReview() == review2, is(true));
        assertThat(appReviewList.get(2).getReview() == review3, is(true));
    }
}