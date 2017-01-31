package com.azimo.tool.firebase.response;

import com.azimo.tool.firebase.model.ReportedReview;

import java.util.List;

/**
 * Created by F1sherKK on 17/01/17.
 */
public class GetReportedReviewsResponse {

    private List<ReportedReview> reportedReviews;

    public GetReportedReviewsResponse(List<ReportedReview> reportedReview) {
        this.reportedReviews = reportedReview;
    }

    public ReportedReview get(int index) {
        return reportedReviews.get(index);
    }

    public void add(ReportedReview reportedReview) {
        reportedReviews.add(reportedReview);
    }

    public List<ReportedReview> getReportedReview() {
        return reportedReviews;
    }
}
