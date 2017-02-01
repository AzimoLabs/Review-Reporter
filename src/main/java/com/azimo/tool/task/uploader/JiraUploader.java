package com.azimo.tool.task.uploader;

import com.azimo.tool.firebase.collection.CreatedIssueCollection;
import com.azimo.tool.firebase.model.CreatedIssue;
import com.azimo.tool.jira.JiraIssueServiceManager;
import com.azimo.tool.jira.model.Issue;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.publisher.model.AppReview;
import com.azimo.tool.task.interfaces.Uploader;
import com.azimo.tool.utils.converter.IssueConverter;

/**
 * Created by F1sherKK on 27/01/17.
 */
public class JiraUploader implements Uploader<ReviewCollection, CreatedIssueCollection> {

    private IssueConverter issueConverter;
    private JiraIssueServiceManager jiraIssueServiceManager;

    public JiraUploader(IssueConverter issueConverter, JiraIssueServiceManager jiraIssueServiceManager) {
        this.issueConverter = issueConverter;
        this.jiraIssueServiceManager = jiraIssueServiceManager;
    }

    @Override
    public CreatedIssueCollection upload(ReviewCollection notReportedReviewsWithThreeOrLessStars) {
        CreatedIssueCollection createdIssueCollection = new CreatedIssueCollection();
        for (AppReview review : notReportedReviewsWithThreeOrLessStars) {
            try {
                Issue issue = issueConverter.issueFromAppReview(review);
                jiraIssueServiceManager.createJiraIssue(issue);
                CreatedIssue createdIssue = issueConverter.createdIssueFrom(review);
                createdIssueCollection.add(createdIssue);
            } catch (Exception ignored) {
            }
        }
        return createdIssueCollection;
    }
}
