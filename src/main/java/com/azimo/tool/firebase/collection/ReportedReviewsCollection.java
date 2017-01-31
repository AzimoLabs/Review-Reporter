package com.azimo.tool.firebase.collection;

import com.azimo.tool.firebase.model.ReportedReview;

import java.util.ArrayList;

/**
 * Created by F1sherKK on 25/01/17.
 */
public class ReportedReviewsCollection extends ArrayList<ReportedReview> {

    public boolean containsReviewId(String reviewId) {
        boolean containsId = false;
        for (ReportedReview reportedReview : this) {
            if (reportedReview.getReportedReviewId().equals(reviewId)) {
                containsId = true;
                break;
            }
        }
        return containsId;
    }
}