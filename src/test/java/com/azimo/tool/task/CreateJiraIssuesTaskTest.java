package com.azimo.tool.task;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.firebase.FirebaseServiceManager;
import com.azimo.tool.firebase.collection.CreatedIssueCollection;
import com.azimo.tool.firebase.response.DefaultFirebaseResponse;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.task.provider.UncreatedIssuesProvider;
import com.azimo.tool.task.uploader.FirebaseIssuesUploader;
import com.azimo.tool.task.uploader.JiraUploader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 26/01/17.
 */
public class CreateJiraIssuesTaskTest {

    CreateJiraIssuesTask createJiraIssuesTask;

    @Mock
    AppConfig appConfig;
    @Mock
    UncreatedIssuesProvider uncreatedIssuesProvider;
    @Mock
    JiraUploader jiraUploader;
    @Mock
    FirebaseIssuesUploader firebaseIssuesUploader;
    @Mock
    ReviewCollection unreportedToJiraReviews;
    @Mock
    CreatedIssueCollection createdIssues;
    @Mock
    FirebaseServiceManager firebaseServiceManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        createJiraIssuesTask = new CreateJiraIssuesTask(
            uncreatedIssuesProvider, jiraUploader, firebaseServiceManager, firebaseIssuesUploader);
    }

    @Test
    public void testWhenPerformingRun_shouldFetchUncreatedIssuesAndSendThemToJiraAndFirebase() throws Exception {
        when(uncreatedIssuesProvider.fetch()).thenReturn(unreportedToJiraReviews);
        when(unreportedToJiraReviews.getWithMinThreeStars()).thenReturn(unreportedToJiraReviews);
        when(firebaseServiceManager.getCreatedIssues()).thenReturn(createdIssues);
        when(jiraUploader.upload(unreportedToJiraReviews)).thenReturn(createdIssues);
        when(firebaseIssuesUploader.upload(createdIssues)).thenReturn(new DefaultFirebaseResponse());

        createJiraIssuesTask.run();

        verify(uncreatedIssuesProvider).fetch();
        verify(jiraUploader).upload(unreportedToJiraReviews);
        verify(firebaseServiceManager).getCreatedIssues();
        verify(firebaseIssuesUploader).upload(createdIssues);
    }
}
