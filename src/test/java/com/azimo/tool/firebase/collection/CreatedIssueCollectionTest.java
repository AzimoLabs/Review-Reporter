package com.azimo.tool.firebase.collection;

import com.azimo.tool.firebase.model.CreatedIssue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class CreatedIssueCollectionTest {

    CreatedIssueCollection createdIssueCollection;

    @Mock
    CreatedIssue createdIssue;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        createdIssueCollection = new CreatedIssueCollection();
    }

    @Test
    public void testWhenAskedIfContainsReviewId_shouldReturnProperValue() {
        String reviewId1 = "mock1";
        String reviewId2 = "mock2";

        when(createdIssue.getCreatedReviewId()).thenReturn(reviewId1);

        createdIssueCollection.add(createdIssue);
        boolean containsReview1 = createdIssueCollection.containsReviewId(reviewId1);
        boolean containsReview2 = createdIssueCollection.containsReviewId(reviewId2);

        assertThat(containsReview1, is(true));
        assertThat(containsReview2, is(false));
    }
}
