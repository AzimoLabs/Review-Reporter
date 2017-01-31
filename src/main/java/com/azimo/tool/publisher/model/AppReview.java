package com.azimo.tool.publisher.model;

import com.google.api.services.androidpublisher.model.Comment;
import com.google.api.services.androidpublisher.model.Review;
import com.google.api.services.androidpublisher.model.UserComment;

import java.util.List;

/**
 * Created by F1sherKK on 10/01/17.
 */
public class AppReview {

    private Review review;

    public AppReview(Review review) {
        this.review = review;
    }

    public List<Comment> getComments() {
        return review.getComments();
    }

    public String getAuthorName() {
        return review.getAuthorName();
    }

    public String getReviewId() {
        return review.getReviewId();
    }

    public void setComments(List<Comment> comments) {
        review.setComments(comments);
    }

    public void setAuthorName(String authorName) {
        review.setAuthorName(authorName);
    }

    public void setReviewId(String reviewId) {
        review.setReviewId(reviewId);
    }

    public Review getReview() {
        return review;
    }

    public UserComment getFirstUserComment() {
        UserComment firstUserComment = null;
        if (review.getComments() != null) {
            for (Comment comment : review.getComments()) {
                if (comment.getUserComment() != null) {
                    if (firstUserComment == null) {
                        firstUserComment = comment.getUserComment();
                    } else {
                        long currentFirstCommentTimestamp = firstUserComment.getLastModified().getSeconds();
                        long commentTimestamp = comment.getUserComment().getLastModified().getSeconds();

                        if (commentTimestamp < currentFirstCommentTimestamp) {
                            firstUserComment = comment.getUserComment();
                        }
                    }
                }
            }
        }
        return firstUserComment;
    }

    @Override
    public String toString() {
        return "AppReview{" +
            "review=" + review +
            '}';
    }
}
