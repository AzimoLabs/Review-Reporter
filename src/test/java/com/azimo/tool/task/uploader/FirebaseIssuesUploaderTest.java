package com.azimo.tool.task.uploader;

import com.azimo.tool.firebase.FirebaseServiceManager;
import com.azimo.tool.firebase.collection.CreatedIssueCollection;
import com.azimo.tool.firebase.response.DefaultFirebaseResponse;
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
public class FirebaseIssuesUploaderTest {

    FirebaseIssuesUploader firebaseIssuesUploader;

    @Mock
    FirebaseServiceManager firebaseServiceManager;
    @Mock
    DefaultFirebaseResponse defaultFirebaseResponse;
    @Mock
    CreatedIssueCollection createdIssueCollection;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        firebaseIssuesUploader = new FirebaseIssuesUploader(firebaseServiceManager);
    }

    @Test
    public void testWhenUploading_shouldReturnResponse() {
        when(firebaseServiceManager.updateCreatedIssues(createdIssueCollection))
            .thenReturn(defaultFirebaseResponse);

        DefaultFirebaseResponse response = firebaseIssuesUploader.upload(createdIssueCollection);

        assertThat(response == defaultFirebaseResponse, is(true));
    }
}
