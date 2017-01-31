package com.azimo.tool.firebase.mapper;

import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
import com.azimo.tool.firebase.model.ReportedReviewsList;
import com.azimo.tool.firebase.response.GetReportedReviewsResponse;

/**
 * Created by F1sherKK on 17/01/17.
 */
public class ReportedReviewMapper {

    public ReportedReviewsCollection getReportedReviewsResponseToCollection(GetReportedReviewsResponse response) {
        ReportedReviewsCollection collection = new ReportedReviewsCollection();
        if (response != null && response.getReportedReview() != null) {
            collection.addAll(response.getReportedReview());
        }
        return collection;
    }

    public ReportedReviewsList reportedReviewsCollectionToList(ReportedReviewsCollection reportedReviews) {
        return new ReportedReviewsList(reportedReviews);
    }
}
