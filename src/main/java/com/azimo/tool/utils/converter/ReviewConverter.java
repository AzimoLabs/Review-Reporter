package com.azimo.tool.utils.converter;

import com.azimo.tool.firebase.model.ReportedReview;
import com.azimo.tool.publisher.model.AppReview;

/**
 * Created by F1sherKK on 26/01/17.
 */
public class ReviewConverter {

    public ReportedReview reportedReviewFromAppReview(AppReview review) {
        ReportedReview reportedReview = new ReportedReview();
        reportedReview.setReportedReviewId(review.getReviewId());
        if (review.getFirstUserComment() != null) {
            reportedReview.setReportedReviewTime(review.getFirstUserComment().getLastModified().getSeconds() * 1000);
        }
        return reportedReview;
    }
}
