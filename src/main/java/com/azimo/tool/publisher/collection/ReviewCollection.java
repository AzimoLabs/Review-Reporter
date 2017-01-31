package com.azimo.tool.publisher.collection;

import com.azimo.tool.publisher.model.AppReview;
import com.google.api.services.androidpublisher.model.UserComment;

import java.util.ArrayList;

/**
 * Created by F1sherKK on 10/01/17.
 */
public class ReviewCollection extends ArrayList<AppReview> {

    public ReviewCollection getFromLastTimeOffset(int minutes) {
        final long intervalInMillis = minutes * 60 * 1000;

        ReviewCollection filteredReviewCollection = new ReviewCollection();
        long now = System.currentTimeMillis();

        for (AppReview appReview : this) {
            UserComment userComment = appReview.getFirstUserComment();
            long commentTimeInMillis = userComment.getLastModified().getSeconds() * 1000;
            if (commentTimeInMillis > (now - intervalInMillis)) {
                filteredReviewCollection.add(appReview);
            }
        }
        return filteredReviewCollection;
    }

    public ReviewCollection getWithMinThreeStars() {
        final int minStartRating = 3;

        ReviewCollection filteredReviewCollection = new ReviewCollection();

        for (AppReview appReview : this) {
            UserComment userComment = appReview.getFirstUserComment();
            if (userComment.getStarRating() <= minStartRating) {
                filteredReviewCollection.add(appReview);
            }
        }
        return filteredReviewCollection;
    }

    public ReviewCollection sortAscendingByCreatedTime() {
        ReviewCollection filteredReviewCollection = new ReviewCollection();
        filteredReviewCollection.addAll(this);

        filteredReviewCollection.sort((o1, o2) -> {
            long o1CreationTime = o1.getFirstUserComment().getLastModified().getSeconds();
            long o2CreationTime = o2.getFirstUserComment().getLastModified().getSeconds();
            if (o1CreationTime > o2CreationTime) {
                return 1;
            } else {
                return -1;
            }
        });
        return filteredReviewCollection;
    }
}
