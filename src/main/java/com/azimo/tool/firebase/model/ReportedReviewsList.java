package com.azimo.tool.firebase.model;

import com.azimo.tool.firebase.collection.ReportedReviewsCollection;

import java.util.List;

/**
 * Created by F1sherKK on 17/01/17.
 */
public class ReportedReviewsList {

    private List<ReportedReview> reportedReviews;

    public ReportedReviewsList(ReportedReviewsCollection collection) {
        this.reportedReviews = collection;
    }

    public ReportedReview get(int index) {
        return reportedReviews.get(index);
    }

    public void add(ReportedReview reportedReview) {
        reportedReviews.add(reportedReview);
    }

    public List<ReportedReview> getReportedReviews() {
        return reportedReviews;
    }

    public int size() {
        return reportedReviews.size();
    }
}
