package com.azimo.tool.firebase.mapper;

import com.azimo.tool.firebase.collection.CreatedIssueCollection;
import com.azimo.tool.firebase.model.CreatedIssue;
import com.azimo.tool.firebase.model.CreatedIssuesList;
import com.azimo.tool.firebase.response.GetCreatedIssuesResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
public class CreatedIssueMapperTest {

    CreatedIssueMapper createdIssueMapper;

    @Mock
    CreatedIssue createdIssue1;
    @Mock
    CreatedIssue createdIssue2;
    @Mock
    CreatedIssue createdIssue3;
    @Mock
    GetCreatedIssuesResponse response;
    @Mock
    CreatedIssueCollection createdIssueCollection;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        createdIssueMapper = new CreatedIssueMapper();
    }

    @Test
    public void whenInsertedGetCreatedIssuesResponse_ShouldReturnCreatedIssueCollection() {
        when(response.getCreatedIssues()).thenReturn(new ArrayList<>());

        CreatedIssueCollection createdIssueCollection =
            createdIssueMapper.getCreatedIssuesResponseToCollection(response);

        assertThat(createdIssueCollection != null, is(true));
    }

    @Test
    public void whenInsertedGetCreatedIssuesResponse_ShouldFillCreatedIssueCollectionWithIssues() {
        List<CreatedIssue> createdIssuesList = Arrays.asList(createdIssue1, createdIssue2, createdIssue3);
        when(response.getCreatedIssues()).thenReturn(createdIssuesList);

        CreatedIssueCollection createdIssueCollection =
            createdIssueMapper.getCreatedIssuesResponseToCollection(response);

        assertThat(createdIssueCollection.size() == createdIssuesList.size(), is(true));
    }

    @Test
    public void whenInsertedCreatedIssueCollection_ShouldReturnCreatedIssueList() {
        when(createdIssueCollection.get(0)).thenReturn(createdIssue1);
        when(createdIssueCollection.get(1)).thenReturn(createdIssue2);
        when(createdIssueCollection.get(2)).thenReturn(createdIssue3);

        CreatedIssuesList createdIssuesList =
            createdIssueMapper.createdIssuesCollectionToList(createdIssueCollection);

        assertThat(createdIssuesList.size() == createdIssueCollection.size(), is(true));
    }
}