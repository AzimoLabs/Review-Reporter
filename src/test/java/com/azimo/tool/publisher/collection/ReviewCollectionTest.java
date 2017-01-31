package com.azimo.tool.publisher.collection;

import com.azimo.tool.publisher.model.AppReview;
import com.google.api.services.androidpublisher.model.Comment;
import com.google.api.services.androidpublisher.model.Review;
import com.google.api.services.androidpublisher.model.Timestamp;
import com.google.api.services.androidpublisher.model.UserComment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class ReviewCollectionTest {

    ReviewCollection reviewCollection;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        reviewCollection = new ReviewCollection();
    }

    @Test
    public void testWhenAskedForReviewsFromLastHour_shouldReturnFilteredCollection() {
        final long minuteInSeconds = 60;
        final long hourInSeconds = 60 * minuteInSeconds;
        final long dayInSeconds = 24 * hourInSeconds;

        final int hourInMinutes = 60;

        long nowInSeconds = System.currentTimeMillis() / 1000;

        Review review_twoDaysOld = new Review();
        Review review_twoHoursOld = new Review();
        Review review_twoMinutesOld = new Review();

        Comment comment_twoDaysOld = new Comment();
        Comment comment_twoHoursOld = new Comment();
        Comment comment_twoMinutesOld = new Comment();

        UserComment userComment_twoDaysOld = new UserComment();
        UserComment userComment_twoHoursOld = new UserComment();
        UserComment userComment_twoMinutesOld = new UserComment();

        Timestamp timestamp_twoDaysOld = new Timestamp();
        Timestamp timestamp_twoHoursOld = new Timestamp();
        Timestamp timestamp_twoMinutesOld = new Timestamp();

        timestamp_twoDaysOld.setSeconds(nowInSeconds - 2 * dayInSeconds);
        timestamp_twoDaysOld.setNanos(0);

        timestamp_twoHoursOld.setSeconds(nowInSeconds - 2 * hourInSeconds);
        timestamp_twoHoursOld.setNanos(0);

        timestamp_twoMinutesOld.setSeconds(nowInSeconds - 2 * minuteInSeconds);
        timestamp_twoMinutesOld.setNanos(0);

        userComment_twoDaysOld.setLastModified(timestamp_twoDaysOld);
        userComment_twoHoursOld.setLastModified(timestamp_twoHoursOld);
        userComment_twoMinutesOld.setLastModified(timestamp_twoMinutesOld);

        comment_twoDaysOld.setUserComment(userComment_twoDaysOld);
        comment_twoHoursOld.setUserComment(userComment_twoHoursOld);
        comment_twoMinutesOld.setUserComment(userComment_twoMinutesOld);

        review_twoDaysOld.setComments(Collections.singletonList(comment_twoDaysOld));
        review_twoHoursOld.setComments(Collections.singletonList(comment_twoHoursOld));
        review_twoMinutesOld.setComments(Collections.singletonList(comment_twoMinutesOld));

        reviewCollection.addAll(Arrays.asList(
            new AppReview(review_twoDaysOld),
            new AppReview(review_twoHoursOld),
            new AppReview(review_twoMinutesOld)));

        ReviewCollection filteredReviewCollection = reviewCollection.getFromLastTimeOffset(hourInMinutes);

        assertThat(filteredReviewCollection.size() == 1, is(true));
        assertThat(filteredReviewCollection.get(0).getReview() == review_twoMinutesOld, is(true));
    }

    @Test
    public void testWhenAskedForReviewsWithThreeOrLessStars_shouldReturnFilteredCollection() {
        Review review_five_star = new Review();
        Review review_four_star = new Review();
        Review review_three_star = new Review();
        Review review_two_star = new Review();
        Review review_one_star = new Review();

        Comment comment_five_star = new Comment();
        Comment comment_four_star = new Comment();
        Comment comment_three_star = new Comment();
        Comment comment_two_star = new Comment();
        Comment comment_one_star = new Comment();

        UserComment userComment_five_star = new UserComment();
        UserComment userComment_four_star = new UserComment();
        UserComment userComment_three_star = new UserComment();
        UserComment userComment_two_star = new UserComment();
        UserComment userComment_one_star = new UserComment();

        userComment_five_star.setStarRating(5);
        userComment_four_star.setStarRating(4);
        userComment_three_star.setStarRating(3);
        userComment_two_star.setStarRating(2);
        userComment_one_star.setStarRating(1);

        comment_five_star.setUserComment(userComment_five_star);
        comment_four_star.setUserComment(userComment_four_star);
        comment_three_star.setUserComment(userComment_three_star);
        comment_two_star.setUserComment(userComment_two_star);
        comment_one_star.setUserComment(userComment_one_star);

        review_five_star.setComments(Collections.singletonList(comment_five_star));
        review_four_star.setComments(Collections.singletonList(comment_four_star));
        review_three_star.setComments(Collections.singletonList(comment_three_star));
        review_two_star.setComments(Collections.singletonList(comment_two_star));
        review_one_star.setComments(Collections.singletonList(comment_one_star));

        reviewCollection.addAll(Arrays.asList(
            new AppReview(review_five_star),
            new AppReview(review_four_star),
            new AppReview(review_three_star),
            new AppReview(review_two_star),
            new AppReview(review_one_star)));

        ReviewCollection filteredReviewCollection = reviewCollection.getWithMinThreeStars();

        assertThat(reviewCollection.size() == 5, is(true));
        assertThat(filteredReviewCollection.size() == 3, is(true));
        assertThat(filteredReviewCollection.get(0).getFirstUserComment().getStarRating() <= 3, is(true));
        assertThat(filteredReviewCollection.get(1).getFirstUserComment().getStarRating() <= 3, is(true));
        assertThat(filteredReviewCollection.get(2).getFirstUserComment().getStarRating() <= 3, is(true));
    }


    @Test
    public void testWhenAskedToSortReviewsAscendingByCreatedTime_shouldReturnFilteredCollection() {
        final long minuteInSeconds = 60;
        final long hourInSeconds = 60 * minuteInSeconds;
        final long dayInSeconds = 24 * hourInSeconds;

        long nowInSeconds = System.currentTimeMillis() / 1000;

        Review review_sevenDaysOld = new Review();
        Review review_oneDayOld = new Review();
        Review review_now = new Review();

        Comment comment_sevenDaysOld = new Comment();
        Comment comment_oneDayOld = new Comment();
        Comment comment_now = new Comment();

        UserComment userComment_sevenDaysOld = new UserComment();
        UserComment userComment_oneDayOld = new UserComment();
        UserComment userComment_now = new UserComment();

        Timestamp timestamp_sevenDaysOld = new Timestamp();
        Timestamp timestamp_oneDayOld = new Timestamp();
        Timestamp timestamp_now = new Timestamp();

        timestamp_sevenDaysOld.setSeconds(nowInSeconds - 7 * dayInSeconds);
        timestamp_sevenDaysOld.setNanos(0);

        timestamp_oneDayOld.setSeconds(nowInSeconds - dayInSeconds);
        timestamp_oneDayOld.setNanos(0);

        timestamp_now.setSeconds(nowInSeconds);
        timestamp_now.setNanos(0);

        userComment_sevenDaysOld.setLastModified(timestamp_sevenDaysOld);
        userComment_oneDayOld.setLastModified(timestamp_oneDayOld);
        userComment_now.setLastModified(timestamp_now);

        comment_sevenDaysOld.setUserComment(userComment_sevenDaysOld);
        comment_oneDayOld.setUserComment(userComment_oneDayOld);
        comment_now.setUserComment(userComment_now);

        review_sevenDaysOld.setComments(Collections.singletonList(comment_sevenDaysOld));
        review_oneDayOld.setComments(Collections.singletonList(comment_oneDayOld));
        review_now.setComments(Collections.singletonList(comment_now));

        reviewCollection.add(new AppReview(review_oneDayOld));
        reviewCollection.add(new AppReview(review_now));
        reviewCollection.add(new AppReview(review_sevenDaysOld));

        ReviewCollection filteredReviewCollection = reviewCollection.sortAscendingByCreatedTime();

        assertThat(reviewCollection.get(0).getReview().equals(review_oneDayOld), is(true));
        assertThat(reviewCollection.get(1).getReview().equals(review_now), is(true));
        assertThat(reviewCollection.get(2).getReview().equals(review_sevenDaysOld), is(true));
        assertThat(filteredReviewCollection.get(0).getReview().equals(review_sevenDaysOld), is(true));
        assertThat(filteredReviewCollection.get(1).getReview().equals(review_oneDayOld), is(true));
        assertThat(filteredReviewCollection.get(2).getReview().equals(review_now), is(true));
    }
}
