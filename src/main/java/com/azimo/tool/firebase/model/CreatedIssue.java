package com.azimo.tool.firebase.model;

/**
 * Created by F1sherKK on 17/01/17.
 */
public class CreatedIssue {

    private String createdReviewId;
    private long createdReviewTime;

    public String getCreatedReviewId() {
        return createdReviewId;
    }

    public void setCreatedReviewId(String createdReviewId) {
        this.createdReviewId = createdReviewId;
    }

    public long getCreatedReviewTime() {
        return createdReviewTime;
    }

    public void setCreatedReviewTime(long createdReviewTime) {
        this.createdReviewTime = createdReviewTime;
    }

    @Override
    public String toString() {
        return "CreatedIssue{" +
            "createdReviewId='" + createdReviewId + '\'' +
            ", createdReviewTime=" + createdReviewTime +
            '}';
    }
}
