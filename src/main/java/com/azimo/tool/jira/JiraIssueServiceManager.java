package com.azimo.tool.jira;

import com.azimo.tool.jira.model.Issue;
import com.azimo.tool.jira.response.CreateNewIssueResponse;

import javax.inject.Inject;

/**
 * Created by F1sherKK on 17/01/17.
 */
public class JiraIssueServiceManager {

    private JiraIssueService jiraIssueService;

    @Inject
    public JiraIssueServiceManager(JiraIssueService jiraIssueService) {
        this.jiraIssueService = jiraIssueService;
    }

    public CreateNewIssueResponse createJiraIssue(Issue issue) {
        return jiraIssueService.createIssue(issue).toBlocking().first();
    }
}
