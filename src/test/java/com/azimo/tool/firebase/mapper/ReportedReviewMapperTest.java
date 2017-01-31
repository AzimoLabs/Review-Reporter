package com.azimo.tool.firebase.mapper;

import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
import com.azimo.tool.firebase.model.ReportedReview;
import com.azimo.tool.firebase.model.ReportedReviewsList;
import com.azimo.tool.firebase.response.GetReportedReviewsResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class ReportedReviewMapperTest {

    ReportedReviewMapper reporetedReviewMapper;

    @Mock
    ReportedReview reportedReview1;
    @Mock
    ReportedReview reportedReview2;
    @Mock
    ReportedReview reportedReview3;
    @Mock
    GetReportedReviewsResponse response;
    @Mock
    ReportedReviewsCollection reportedReviewsCollection;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        reporetedReviewMapper = new ReportedReviewMapper();
    }

    @Test
    public void whenInsertedGetReportedReviewsResponse_ShouldReturnReportedReviewsCollection() {
        when(response.getReportedReview()).thenReturn(new ArrayList<>());

        ReportedReviewsCollection reportedReviewsCollection =
            reporetedReviewMapper.getReportedReviewsResponseToCollection(response);

        assertThat(reportedReviewsCollection != null, is(true));
    }

    @Test
    public void whenInsertedGetReportedReviewsResponse_ShouldFillReportedReviewsCollectionWithReviews() {
        List<ReportedReview> reportedReviewsList = Arrays.asList(reportedReview1, reportedReview2, reportedReview3);
        when(response.getReportedReview()).thenReturn(reportedReviewsList);


        ReportedReviewsCollection reportedReviewsCollection =
            reporetedReviewMapper.getReportedReviewsResponseToCollection(response);

        assertThat(reportedReviewsCollection.size() == reportedReviewsList.size(), is(true));
    }

    @Test
    public void whenInsertedReportedReviewsCollection_ShouldReturnReportedReviewsList() {
        when(reportedReviewsCollection.get(0)).thenReturn(reportedReview1);
        when(reportedReviewsCollection.get(1)).thenReturn(reportedReview2);
        when(reportedReviewsCollection.get(2)).thenReturn(reportedReview3);

        ReportedReviewsList reportedReviewsList =
            reporetedReviewMapper.reportedReviewsCollectionToList(reportedReviewsCollection);

        assertThat(reportedReviewsList.size() == reportedReviewsCollection.size(), is(true));
    }
}