package com.azimo.tool.firebase;

import com.azimo.tool.firebase.model.CreatedIssuesList;
import com.azimo.tool.firebase.model.ReportedReviewsList;
import com.azimo.tool.firebase.response.DefaultFirebaseResponse;
import com.azimo.tool.firebase.response.GetCreatedIssuesResponse;
import com.azimo.tool.firebase.response.GetReportedReviewsResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import rx.Observable;

/**
 * Created by F1sherKK on 17/01/17.
 */
public interface FirebaseService {

    @PATCH("createdIssuesCollection.json")
    Observable<DefaultFirebaseResponse> updateCreatedIssues(
        @Body CreatedIssuesList createdIssuesList
    );

    @PATCH("reportedReviewCollection.json")
    Observable<DefaultFirebaseResponse> updateReportedReviews(
        @Body ReportedReviewsList reportedReviewsList
    );

    @GET("createdIssuesCollection.json")
    Observable<GetCreatedIssuesResponse> getCreatedIssues();

    @GET("reportedReviewCollection.json")
    Observable<GetReportedReviewsResponse> getReportedReviews();
}
