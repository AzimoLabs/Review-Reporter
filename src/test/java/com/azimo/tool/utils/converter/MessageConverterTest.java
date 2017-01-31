package com.azimo.tool.utils.converter;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.publisher.model.AppReview;
import com.azimo.tool.slack.model.SlackMessage;
import com.azimo.tool.utils.ColorFormatter;
import com.google.api.services.androidpublisher.model.Comment;
import com.google.api.services.androidpublisher.model.Timestamp;
import com.google.api.services.androidpublisher.model.UserComment;
import helper.StringHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 26/01/17.
 */
public class MessageConverterTest {

    MessageConverter messageConverter;

    @Mock
    AppConfig appConfigMock;
    @Mock
    TimeConverter timeConverter;
    @Mock
    ColorFormatter colorFormatter;

    @Mock
    AppReview appReview;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        messageConverter = new MessageConverter(appConfigMock, timeConverter, colorFormatter);
    }

    @Test
    public void testWhenInsertingEmptyAppReview_ShouldReturnMessageObject() {
        SlackMessage slackMessage = messageConverter.slackMessageFromAppReview(appReview);
        assertThat(slackMessage != null, is(true));
    }

    @Test
    public void testWhenInsertingAppReviewWithEmptyComment_ShouldReturnMessageObject() {
        when(appReview.getComments()).thenReturn(Collections.singletonList(new Comment()));

        SlackMessage slackMessage = messageConverter.slackMessageFromAppReview(appReview);
        assertThat(slackMessage != null, is(true));
    }

    @Test
    public void testWhenCommentIsNotNull_ShouldFillTime() {
        long seconds = 12345;
        int starRating = 5;
        String fullMessage = "";
        String someDate = "someDate";

        Comment comment = new Comment();
        Timestamp timestamp = new Timestamp();
        timestamp.setSeconds(seconds);

        UserComment userComment = new UserComment();
        userComment.setLastModified(timestamp);
        userComment.setStarRating(starRating);
        userComment.setText(fullMessage);

        when(timeConverter.millisToTimestamp(anyInt())).thenReturn(someDate);
        when(appReview.getComments()).thenReturn(Collections.singletonList(comment));
        when(appReview.getFirstUserComment()).thenReturn(userComment);

        SlackMessage slackMessage = messageConverter.slackMessageFromAppReview(appReview);

        assertThat(slackMessage.attachments[0].author_name.contains(someDate), is(true));
    }


    @Test
    public void testWhenCommentIsNotNull_ShouldAddStarEmojiAccordingToStarRating() {
        final String starEmojiString = ":star:";
        final Pattern starEmojiStringPattern = Pattern.compile(starEmojiString);
        final String squareEmojiString = ":white_small_square:";
        final Pattern squareEmojiStringPattern = Pattern.compile(squareEmojiString);
        final int maxStars = 5;

        long seconds = 12345;
        String fullMessage = "";
        String someDate = "someDate";

        int starRating = 4;
        int expectedSquares = maxStars - starRating;

        Comment comment = new Comment();
        Timestamp timestamp = new Timestamp();
        timestamp.setSeconds(seconds);

        UserComment userComment = new UserComment();
        userComment.setLastModified(timestamp);
        userComment.setStarRating(starRating);
        userComment.setText(fullMessage);

        when(timeConverter.millisToTimestamp(anyInt())).thenReturn(someDate);
        when(appReview.getComments()).thenReturn(Collections.singletonList(comment));
        when(appReview.getFirstUserComment()).thenReturn(userComment);

        SlackMessage slackMessage = messageConverter.slackMessageFromAppReview(appReview);

        assertThat(StringHelper.countMatches(
            starEmojiStringPattern, slackMessage.attachments[1].text) == starRating, is(true));
        assertThat(StringHelper.countMatches(
            squareEmojiStringPattern, slackMessage.attachments[1].text) == expectedSquares, is(true));
    }

    @Test
    public void testWhenCommentIsNotNull_ShouldAddTitleIfExists() {
        long seconds = 12345;
        int starRating = 4;
        String newLineCharacter = "\t";
        String title = "mockTitle";
        String message = "Some text content";
        String fullMessage = title + newLineCharacter + "Some text content";
        String someDate = "someDate";

        Comment comment = new Comment();
        Timestamp timestamp = new Timestamp();
        timestamp.setSeconds(seconds);

        UserComment userComment = new UserComment();
        userComment.setLastModified(timestamp);
        userComment.setStarRating(starRating);
        userComment.setText(fullMessage);

        when(timeConverter.millisToTimestamp(anyInt())).thenReturn(someDate);
        when(appReview.getComments()).thenReturn(Collections.singletonList(comment));
        when(appReview.getFirstUserComment()).thenReturn(userComment);

        SlackMessage slackMessage = messageConverter.slackMessageFromAppReview(appReview);

        assertThat(slackMessage.attachments[0].title.equals(title), is(true));
        assertThat(slackMessage.attachments[0].text.equals(message), is(true));
    }

    @Test
    public void testGoogleAccIsInConfig_ShouldAddItReviewUrl() {
        String google_dev_acc = "1234567";
        when(appConfigMock.get(AppConfigKey.GOOGLE_DEV_CONSOLE_ACC)).thenReturn(google_dev_acc);
        when(appConfigMock.contains(AppConfigKey.GOOGLE_DEV_CONSOLE_ACC)).thenReturn(true);

        SlackMessage slackMessage = messageConverter.slackMessageFromAppReview(appReview);

        assertThat(slackMessage.attachments[2].title_link.contains(google_dev_acc), is(true));
    }
}
