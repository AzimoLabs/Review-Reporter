package com.azimo.tool.utils.converter;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.publisher.model.AppReview;
import com.azimo.tool.slack.model.SlackMessage;
import com.azimo.tool.utils.ColorFormatter;
import com.google.api.services.androidpublisher.model.UserComment;

import javax.inject.Inject;

/**
 * Created by F1sherKK on 25/01/17.
 */
public class MessageConverter {

    private static final String GOOGLE_PLAY_REVIEW_BASE_PATH = "https://play.google.com/apps/publish/";
    private static final String DEV_ACC_PART = "?dev_acc=%s";
    private static final String REVIEW_DETAILS_PLACE = "#ReviewDetailsPlace:p=%s";
    private static final String REVIEW_ID = "&reviewid=%s";

    private static final String GOOGLE_ICON_URL = "http://upthetree.com/wp-content/uploads/2013/01/GooglePlay-Icon.png";

    private AppConfig config;
    private TimeConverter timeConverter;
    private ColorFormatter colorFormatter;

    @Inject
    public MessageConverter(AppConfig config, TimeConverter timeConverter, ColorFormatter colorFormatter) {
        this.config = config;
        this.timeConverter = timeConverter;
        this.colorFormatter = colorFormatter;
    }

    public SlackMessage slackMessageFromAppReview(AppReview review) {
        String author = "";
        String reviewId = "";
        String time = "";
        String title = "";
        String message = "";
        String fullMessage = "";
        int starRatingVal = -1;

        author = review.getAuthorName();
        reviewId = review.getReviewId();

        UserComment comment = review.getFirstUserComment();
        if (comment != null) {
            time = timeConverter.millisToTimestamp(comment.getLastModified().getSeconds() * 1000);
            starRatingVal = comment.getStarRating();

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

        String mainText = "New review was added to Google Play Store!";
        String messageAttachmentAuthor = "by %s - on %s";
        String ratingAttachmentText = "Rating: %s";
        String replyAttachmentText = "Reply to review";

        String reviewUrl = GOOGLE_PLAY_REVIEW_BASE_PATH;
        if (config.contains(AppConfigKey.GOOGLE_DEV_CONSOLE_ACC)) {
            reviewUrl += String.format(DEV_ACC_PART, config.get(AppConfigKey.GOOGLE_DEV_CONSOLE_ACC));
        }
        reviewUrl += String.format(REVIEW_DETAILS_PLACE, config.get(AppConfigKey.ANDROID_PACKAGE_NAME));
        reviewUrl += String.format(REVIEW_ID, reviewId);

        SlackMessage slackMessage = new SlackMessage();
        slackMessage.mrkdwn = true;
        slackMessage.text = String.format(mainText, author, time);

        SlackMessage.Attachment messageAttachment = new SlackMessage.Attachment();
        messageAttachment.color = colorFormatter.getColorFromStarRating(starRatingVal);
        messageAttachment.author_name = String.format(messageAttachmentAuthor, author, time);
        messageAttachment.thumb_url = GOOGLE_ICON_URL;
        if (!title.equals("")) {
            messageAttachment.title = title;
        }
        messageAttachment.text = message;

        SlackMessage.Attachment ratingAttachment = new SlackMessage.Attachment();
        ratingAttachment.color = ColorFormatter.RATING_SECTION;
        ratingAttachment.text = String.format(ratingAttachmentText, addStars(starRatingVal));

        SlackMessage.Attachment replyAttachment = new SlackMessage.Attachment();
        replyAttachment.color = ColorFormatter.REPLY_SECTION;
        replyAttachment.title = replyAttachmentText;
        replyAttachment.title_link = reviewUrl;

        SlackMessage.Attachment[] attachmentsArray = new SlackMessage.Attachment[3];
        attachmentsArray[0] = messageAttachment;
        attachmentsArray[1] = ratingAttachment;
        attachmentsArray[2] = replyAttachment;

        slackMessage.attachments = attachmentsArray;

        return slackMessage;
    }

    private String addStars(int starRatingVal) {
        final int maxStars = 5;
        final String slack_star_emoji = ":star:";
        final String slack_small_square =":white_small_square:";
        String starsString = "";
        for (int i = 0; i < starRatingVal; i++) {
            starsString += slack_star_emoji;
        }
        for (int j = 0; j < maxStars - starRatingVal; j++) {
            starsString += slack_small_square;
        }
        return starsString;
    }

}
