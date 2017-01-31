package com.azimo.tool.task.uploader;

import com.azimo.tool.firebase.collection.CreatedIssueCollection;
import com.azimo.tool.firebase.model.CreatedIssue;
import com.azimo.tool.jira.JiraIssueServiceManager;
import com.azimo.tool.jira.model.Issue;
import com.azimo.tool.jira.response.CreateNewIssueResponse;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.publisher.model.AppReview;
import com.azimo.tool.utils.converter.IssueConverter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 27/01/17.
 */
public class JiraUploaderTest {

    JiraUploader jiraUploader;

    @Mock
    IssueConverter issueConverter;
    @Mock
    JiraIssueServiceManager jiraIssueServiceManager;

    @Mock
    AppReview appReview1;
    @Mock
    AppReview appReview2;
    @Mock
    Issue issue1;
    @Mock
    Issue issue2;
    @Mock
    CreatedIssue createdIssue1;
    @Mock
    CreatedIssue createdIssue2;
    @Mock
    CreateNewIssueResponse createNewIssueResponse;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jiraUploader = new JiraUploader(issueConverter, jiraIssueServiceManager);
    }

    @Test
    public void testWhenUploadingReviews_shouldReturnListOfCreatedIssues() {
        String reviewId1 = "id1";
        String reviewId2 = "id2";

        when(appReview1.getReviewId()).thenReturn(reviewId1);
        when(appReview2.getReviewId()).thenReturn(reviewId2);

        ReviewCollection reviewCollection = new ReviewCollection();
        reviewCollection.add(appReview1);
        reviewCollection.add(appReview2);

        when(createdIssue1.getCreatedReviewId()).thenReturn(reviewId1);
        when(createdIssue2.getCreatedReviewId()).thenReturn(reviewId2);

        when(issueConverter.issueFromAppReview(appReview1)).thenReturn(issue1);
        when(issueConverter.issueFromAppReview(appReview2)).thenReturn(issue2);
        when(issueConverter.createdIssueFrom(appReview1)).thenReturn(createdIssue1);
        when(issueConverter.createdIssueFrom(appReview2)).thenReturn(createdIssue2);
        when(jiraIssueServiceManager.createJiraIssue(issue1)).thenReturn(createNewIssueResponse);

        CreatedIssueCollection createdIssueCollection = jiraUploader.upload(reviewCollection);

        assertThat(createdIssueCollection.size() == 2, is(true));
        assertThat(createdIssueCollection.get(0).getCreatedReviewId().equals(reviewId1), is(true));
        assertThat(createdIssueCollection.get(1).getCreatedReviewId().equals(reviewId2), is(true));
    }
}
