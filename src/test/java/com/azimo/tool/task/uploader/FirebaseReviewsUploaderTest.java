package com.azimo.tool.task.uploader;

import com.azimo.tool.firebase.FirebaseServiceManager;
import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
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
public class FirebaseReviewsUploaderTest {

    FirebaseReviewsUploader firebaseReviewsUploader;

    @Mock
    FirebaseServiceManager firebaseServiceManager;
    @Mock
    DefaultFirebaseResponse defaultFirebaseResponse;
    @Mock
    ReportedReviewsCollection reportedReviewsCollection;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        firebaseReviewsUploader = new FirebaseReviewsUploader(firebaseServiceManager);
    }

    @Test
    public void testWhenUploading_shouldReturnResponse() {
        when(firebaseServiceManager.updateReportedReviews(reportedReviewsCollection))
            .thenReturn(defaultFirebaseResponse);

        DefaultFirebaseResponse response = firebaseReviewsUploader.upload(reportedReviewsCollection);

        assertThat(response == defaultFirebaseResponse, is(true));
    }
}
