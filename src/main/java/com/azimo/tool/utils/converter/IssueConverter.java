package com.azimo.tool.utils.converter;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.firebase.model.CreatedIssue;
import com.azimo.tool.jira.model.Issue;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.publisher.model.AppReview;
import com.google.api.services.androidpublisher.model.UserComment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by F1sherKK on 11/01/17.
 */
public class IssueConverter {

    private static final String GOOGLE_PLAY_REVIEW_BASE_PATH = "https://play.google.com/apps/publish/";
    private static final String DEV_ACC_PART = "?dev_acc=%s";
    private static final String REVIEW_DETAILS_PLACE = "#ReviewDetailsPlace:p=%s";
    private static final String REVIEW_ID = "&reviewid=%s";

    private AppConfig config;
    private TimeConverter timeConverter;

    public IssueConverter(AppConfig config, TimeConverter timeConverter) {
        this.config = config;
        this.timeConverter = timeConverter;
    }

    public List<Issue> listFromReviewCollection(ReviewCollection reviewCollection) {
        List<Issue> issues = new ArrayList<>();
        for (int i = 0; i < reviewCollection.size(); i++) {
            issues.add(issueFromAppReview(reviewCollection.get(i)));
        }
        return issues;
    }

    public Issue issueFromAppReview(AppReview review) {
        final String summaryLabel = "[Google Play Store]";
        final String timeLabel = "*Date:*";
        final String titleLabel = "*Title:*";
        final String messageLabel = "*Message:*";
        final String authorLabel = "*Author:*";
        final String languageLabel = "*Reviewer language:*";
        final String appVersionLabel = "*App version:*";
        final String starRatingLabel = "*Stars:*";
        final String linkLabel = "*Link to review:*";

        String author = "";
        String reviewId = "";
        String time = "";
        String starRating = "";
        String language = "";
        String appVersion = "";
        String title = "";
        String message = "";
        String fullMessage = "";
        String jira_summary = "";

        Issue issue = new Issue();
        issue.setProject(config.get(AppConfigKey.JIRA_PROJECT_NAME));
        issue.setIssueType(config.get(AppConfigKey.JIRA_ISSUE_TYPE));
        issue.setAssignee(config.get(AppConfigKey.JIRA_ASSIGNEE));

        author = review.getAuthorName();
        reviewId = review.getReviewId();

        UserComment comment = review.getFirstUserComment();
        if (comment != null) {
            time = timeConverter.millisToTimestamp(comment.getLastModified().getSeconds() * 1000);
            starRating = comment.getStarRating().toString();
            language = comment.getReviewerLanguage();
            appVersion = comment.getAppVersionCode() != null ? comment.getAppVersionCode().toString() : "not specified";

            final String newLineCharacter = "\t";
            fullMessage = comment.getText();
            if (fullMessage.contains(newLineCharacter)) {
                int titleIndex = fullMessage.indexOf(newLineCharacter);
                title = fullMessage.substring(0, titleIndex);
                message = fullMessage.substring(titleIndex, fullMessage.length()).replaceAll(newLineCharacter, "");
            } else {
                title = "";
                message = fullMessage;
            }
        }

        jira_summary = summaryLabel + " " + starRating + "-star review from " + author;
        issue.setSummary(jira_summary);

        String jira_description = "";
        jira_description += authorLabel + " " + author + "\n";
        jira_description += timeLabel + " " + time + "\n";
        jira_description += starRatingLabel + " " + starRating + "\n";
        jira_description += "\n";
        jira_description += languageLabel + " " + language + "\n";
        jira_description += appVersionLabel + " " + appVersion + "\n";
        jira_description += "\n";
        jira_description += titleLabel + " " + title + "\n";
        jira_description += messageLabel + " " + message + "\n";
        jira_description += "\n";

        String reviewUrl = GOOGLE_PLAY_REVIEW_BASE_PATH;
        if (config.contains(AppConfigKey.GOOGLE_DEV_CONSOLE_ACC)) {
            reviewUrl += String.format(DEV_ACC_PART, config.get(AppConfigKey.GOOGLE_DEV_CONSOLE_ACC));
        }
        reviewUrl += String.format(REVIEW_DETAILS_PLACE, config.get(AppConfigKey.ANDROID_PACKAGE_NAME));
        reviewUrl += String.format(REVIEW_ID, reviewId);

        jira_description += linkLabel + " " + reviewUrl;
        issue.setDescription(jira_description);

        return issue;
    }

    public CreatedIssue createdIssueFrom(AppReview review) {
        CreatedIssue createdIssue = new CreatedIssue();
        createdIssue.setCreatedReviewId(review.getReviewId());
        if (review.getFirstUserComment() != null) {
            createdIssue.setCreatedReviewTime(review.getFirstUserComment().getLastModified().getSeconds() * 1000);
        }
        return createdIssue;
    }
}
