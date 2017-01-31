package com.azimo.tool.firebase;

import com.azimo.tool.firebase.collection.CreatedIssueCollection;
import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
import com.azimo.tool.firebase.mapper.CreatedIssueMapper;
import com.azimo.tool.firebase.mapper.ReportedReviewMapper;
import com.azimo.tool.firebase.model.CreatedIssue;
import com.azimo.tool.firebase.model.CreatedIssuesList;
import com.azimo.tool.firebase.model.ReportedReview;
import com.azimo.tool.firebase.model.ReportedReviewsList;
import com.azimo.tool.firebase.response.DefaultFirebaseResponse;
import com.azimo.tool.firebase.response.GetCreatedIssuesResponse;
import com.azimo.tool.firebase.response.GetReportedReviewsResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 26/01/17.
 */
public class FirebaseServiceManagerTest {

    FirebaseServiceManager firebaseIssueServiceManager;

    @Mock
    FirebaseService firebaseService;
    @Mock
    CreatedIssueMapper createdIssueMapper;
    @Mock
    ReportedReviewMapper reportedReviewMapper;

    @Mock
    ReportedReview reportedReview1;
    @Mock
    ReportedReview reportedReview2;
    @Mock
    ReportedReview reportedReview3;
    @Mock
    CreatedIssue createdIssue1;
    @Mock
    CreatedIssue createdIssue2;
    @Mock
    CreatedIssue createdIssue3;
    @Mock
    GetReportedReviewsResponse reviewResponse;
    @Mock
    GetCreatedIssuesResponse issuesResponse;
    @Mock
    DefaultFirebaseResponse defaultFirebaseResponse;
    @Mock
    ReportedReviewsCollection reportedReviewsCollection;
    @Mock
    CreatedIssueCollection createdIssueCollection;
    @Mock
    CreatedIssuesList createdIssuesList;
    @Mock
    ReportedReviewsList reportedReviewsList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        firebaseIssueServiceManager =
            new FirebaseServiceManager(firebaseService, createdIssueMapper, reportedReviewMapper);
    }

    @Test
    public void testWhenUpdatingReportedReviewsToFireBase_ShouldReturnResponse() throws IOException {
        when(reportedReviewMapper.reportedReviewsCollectionToList(reportedReviewsCollection))
            .thenReturn(reportedReviewsList);
        when(firebaseService.updateReportedReviews(reportedReviewsList))
            .thenReturn(Observable.just(defaultFirebaseResponse));

        DefaultFirebaseResponse response = firebaseIssueServiceManager.updateReportedReviews(reportedReviewsCollection);

        assertThat(response.equals(defaultFirebaseResponse), is(true));
    }

    @Test
    public void testWhenGettingReportedReviewsFromFireBase_ShouldReturnCollection() throws IOException {
        when(reportedReviewsCollection.get(0)).thenReturn(reportedReview1);
        when(reportedReviewsCollection.get(1)).thenReturn(reportedReview2);
        when(reportedReviewsCollection.get(2)).thenReturn(reportedReview3);
        when(reportedReviewMapper.getReportedReviewsResponseToCollection(reviewResponse))
            .thenReturn(reportedReviewsCollection);
        when(firebaseService.getReportedReviews()).thenReturn(Observable.just(reviewResponse));

        ReportedReviewsCollection responseCollection = firebaseIssueServiceManager.getReportedReviews();

        assertThat(reportedReviewsCollection.equals(responseCollection), is(true));
        assertThat(reportedReviewsCollection.get(0).equals(responseCollection.get(0)), is(true));
        assertThat(reportedReviewsCollection.get(0).equals(responseCollection.get(0)), is(true));
        assertThat(reportedReviewsCollection.get(0).equals(responseCollection.get(0)), is(true));
    }

    @Test
    public void testWhenUpdatingCreatedIssuesToFireBase_ShouldReturnResponse() throws IOException {
        when(createdIssueMapper.createdIssuesCollectionToList(createdIssueCollection))
            .thenReturn(createdIssuesList);
        when(firebaseService.updateCreatedIssues(createdIssuesList))
            .thenReturn(Observable.just(defaultFirebaseResponse));

        DefaultFirebaseResponse response = firebaseIssueServiceManager.updateCreatedIssues(createdIssueCollection);

        assertThat(response.equals(defaultFirebaseResponse), is(true));
    }

    @Test
    public void testWhenGettingCreatedIssuesFromFireBase_ShouldReturnCollection() throws IOException {
        when(createdIssueCollection.get(0)).thenReturn(createdIssue1);
        when(createdIssueCollection.get(1)).thenReturn(createdIssue2);
        when(createdIssueCollection.get(2)).thenReturn(createdIssue3);
        when(createdIssueMapper.getCreatedIssuesResponseToCollection(issuesResponse))
            .thenReturn(createdIssueCollection);
        when(firebaseService.getCreatedIssues()).thenReturn(Observable.just(issuesResponse));

        CreatedIssueCollection responseCollection = firebaseIssueServiceManager.getCreatedIssues();

        assertThat(createdIssueCollection.equals(responseCollection), is(true));
        assertThat(createdIssueCollection.get(0).equals(responseCollection.get(0)), is(true));
        assertThat(createdIssueCollection.get(0).equals(responseCollection.get(0)), is(true));
        assertThat(createdIssueCollection.get(0).equals(responseCollection.get(0)), is(true));
    }
}
