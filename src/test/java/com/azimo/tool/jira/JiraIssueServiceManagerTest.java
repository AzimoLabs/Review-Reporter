package com.azimo.tool.jira;

import com.azimo.tool.jira.model.Issue;
import com.azimo.tool.jira.response.CreateNewIssueResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 17/01/17.
 */
public class JiraIssueServiceManagerTest {

    JiraIssueServiceManager jiraIssueServiceManager;

    @Mock
    JiraIssueService jiraIssueServiceMock;
    @Mock
    CreateNewIssueResponse createNewIssueResponse;
    @Mock
    Issue issue;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jiraIssueServiceManager = new JiraIssueServiceManager(jiraIssueServiceMock);
    }

    @Test
    public void testWhenSendingIssueToJira_ShouldReturnResponse() {
        when(jiraIssueServiceMock.createIssue(issue)).thenReturn(Observable.just(createNewIssueResponse));

        CreateNewIssueResponse response = jiraIssueServiceManager.createJiraIssue(issue);

        assertThat(createNewIssueResponse.equals(response), is(true));
    }
}
