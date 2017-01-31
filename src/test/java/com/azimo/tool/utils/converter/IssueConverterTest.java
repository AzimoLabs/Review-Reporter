package com.azimo.tool.utils.converter;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.firebase.model.CreatedIssue;
import com.azimo.tool.jira.model.Issue;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.publisher.model.AppReview;
import com.google.api.services.androidpublisher.model.Comment;
import com.google.api.services.androidpublisher.model.Timestamp;
import com.google.api.services.androidpublisher.model.UserComment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class IssueConverterTest {

    IssueConverter issueConverter;

    @Mock
    AppConfig appConfigMock;
    @Mock
    TimeConverter timeConverter;

    @Mock
    AppReview appReview1;
    @Mock
    AppReview appReview2;
    @Mock
    AppReview appReview3;

    @Mock
    ReviewCollection reviewCollection;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        issueConverter = new IssueConverter(appConfigMock, timeConverter);
    }

    @Test
    public void testWhenInsertingEmptyAppReview_ShouldReturnIssueObject() {
        Issue issue = issueConverter.issueFromAppReview(appReview1);
        assertThat(issue != null, is(true));
    }

    @Test
    public void testWhenInsertingAppReviewWithEmptyComment_ShouldReturnIssueObject() {
        when(appReview1.getComments()).thenReturn(Collections.singletonList(new Comment()));

        Issue issue = issueConverter.issueFromAppReview(appReview1);
        assertThat(issue != null, is(true));
    }

    @Test
    public void testWhenInsertingApp_Review_ShouldTransformItToCreatedIssue() {
        String reviewId = "123";
        long seconds = 456;

        Timestamp timestamp = new Timestamp();
        timestamp.setSeconds(seconds);

        UserComment userComment = new UserComment();
        userComment.setLastModified(timestamp);

        when(appReview1.getReviewId()).thenReturn(reviewId);
        when(appReview1.getFirstUserComment()).thenReturn(userComment);

        CreatedIssue createdIssue = issueConverter.createdIssueFrom(appReview1);
        assertThat(createdIssue != null, is(true));
    }

    @Test
    public void testWhenInsertingAppReview_ShouldCreatedIssueWithTimeInMillis() {
        long timeInSeconds = 123;
        long expectedCreatedTimeInMillis = timeInSeconds * 1000;

        Timestamp timeStamp = new Timestamp();
        timeStamp.setSeconds(timeInSeconds);

        UserComment userComment = new UserComment();
        userComment.setLastModified(timeStamp);

        when(appReview1.getFirstUserComment()).thenReturn(userComment);

        CreatedIssue createdIssue = issueConverter.createdIssueFrom(appReview1);

        assertThat(createdIssue.getCreatedReviewTime() == expectedCreatedTimeInMillis, is(true));
    }

    @Test
    public void testWhenInsertingAppReview_ShouldCopyReviewIdProperly() {
        String expectedReviewId = "mockedReviewId";
        when(appReview1.getReviewId()).thenReturn(expectedReviewId);

        CreatedIssue createdIssue = issueConverter.createdIssueFrom(appReview1);

        assertThat(createdIssue.getCreatedReviewId().equals(expectedReviewId), is(true));
    }

    @Test
    public void testWhenInsertingReviewCollection_ShouldReturnListOfIssues() {
        when(reviewCollection.get(0)).thenReturn(appReview1);
        when(reviewCollection.get(1)).thenReturn(appReview2);
        when(reviewCollection.get(2)).thenReturn(appReview3);
        when(reviewCollection.size()).thenReturn(3);

        List<Issue> issues = issueConverter.listFromReviewCollection(reviewCollection);

        assertThat(issues.size() == reviewCollection.size(), is(true));
    }

    @Test
    public void testWhenCommentIsNotNull_ShouldFillTime() {
        long seconds = 12345;
        int starRating = 5;
        String fullMessage = "";
        String someDate = "someDate";

        Comment comment = new Comment();
        Timestamp timestamp = new Timestamp();
        timestamp.setSeconds(seconds);

        UserComment userComment = new UserComment();
        userComment.setLastModified(timestamp);
        userComment.setStarRating(starRating);
        userComment.setText(fullMessage);

        when(timeConverter.millisToTimestamp(anyInt())).thenReturn(someDate);
        when(appReview1.getComments()).thenReturn(Collections.singletonList(comment));
        when(appReview1.getFirstUserComment()).thenReturn(userComment);

        Issue issue = issueConverter.issueFromAppReview(appReview1);

        assertThat(issue.fields.description.contains(someDate), is(true));
    }

    @Test
    public void testWhenCommentIsNotNull_ShouldAddTitleIfExists() {
        long seconds = 12345;
        int starRating = 4;
        String newLineCharacter = "\t";
        String title = "mockTitle";
        String fullMessage = title + newLineCharacter + "Some text content";
        String someDate = "someDate";

        Comment comment = new Comment();
        Timestamp timestamp = new Timestamp();
        timestamp.setSeconds(seconds);

        UserComment userComment = new UserComment();
        userComment.setLastModified(timestamp);
        userComment.setStarRating(starRating);
        userComment.setText(fullMessage);

        when(timeConverter.millisToTimestamp(anyInt())).thenReturn(someDate);
        when(appReview1.getComments()).thenReturn(Collections.singletonList(comment));
        when(appReview1.getFirstUserComment()).thenReturn(userComment);

        Issue issue = issueConverter.issueFromAppReview(appReview1);

        assertThat(issue.fields.description.contains(title), is(true));
    }


    @Test
    public void testGoogleAccIsInConfig_ShouldAddItReviewUrl() {
        String google_dev_acc = "1234567";
        when(appConfigMock.get(AppConfigKey.GOOGLE_DEV_CONSOLE_ACC)).thenReturn(google_dev_acc);
        when(appConfigMock.contains(AppConfigKey.GOOGLE_DEV_CONSOLE_ACC)).thenReturn(true);

        Issue issue = issueConverter.issueFromAppReview(appReview1);

        assertThat(issue.fields.description.contains(google_dev_acc), is(true));
    }
}
