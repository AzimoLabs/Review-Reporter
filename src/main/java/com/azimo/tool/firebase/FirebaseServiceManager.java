package com.azimo.tool.firebase;

import com.azimo.tool.firebase.collection.CreatedIssueCollection;
import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
import com.azimo.tool.firebase.mapper.CreatedIssueMapper;
import com.azimo.tool.firebase.mapper.ReportedReviewMapper;
import com.azimo.tool.firebase.model.CreatedIssuesList;
import com.azimo.tool.firebase.model.ReportedReviewsList;
import com.azimo.tool.firebase.response.DefaultFirebaseResponse;
import com.azimo.tool.firebase.response.GetCreatedIssuesResponse;
import com.azimo.tool.firebase.response.GetReportedReviewsResponse;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by F1sherKK on 17/01/17.
 */
public class FirebaseServiceManager {

    private final FirebaseService firebaseService;
    private final CreatedIssueMapper createdIssueMapper;
    private final ReportedReviewMapper reportedReviewMapper;

    @Inject
    public FirebaseServiceManager(FirebaseService firebaseService,
                                  CreatedIssueMapper createdIssueMapper,
                                  ReportedReviewMapper reportedReviewMapper) {
        this.firebaseService = firebaseService;
        this.createdIssueMapper = createdIssueMapper;
        this.reportedReviewMapper = reportedReviewMapper;
    }

    public DefaultFirebaseResponse updateCreatedIssues(CreatedIssueCollection collection) {
        CreatedIssuesList createdIssuesList = createdIssueMapper.createdIssuesCollectionToList(collection);
        return firebaseService.updateCreatedIssues(createdIssuesList).toBlocking().first();
    }

    public CreatedIssueCollection getCreatedIssues() throws IOException {
        GetCreatedIssuesResponse response = firebaseService.getCreatedIssues().toBlocking().first();
        return createdIssueMapper.getCreatedIssuesResponseToCollection(response);
    }

    public DefaultFirebaseResponse updateReportedReviews(ReportedReviewsCollection collection) {
        ReportedReviewsList reportedReviewsList = reportedReviewMapper.reportedReviewsCollectionToList(collection);
        return firebaseService.updateReportedReviews(reportedReviewsList).toBlocking().first();
    }

    public ReportedReviewsCollection getReportedReviews() throws IOException {
        GetReportedReviewsResponse response = firebaseService.getReportedReviews().toBlocking().first();
        return reportedReviewMapper.getReportedReviewsResponseToCollection(response);
    }
}
