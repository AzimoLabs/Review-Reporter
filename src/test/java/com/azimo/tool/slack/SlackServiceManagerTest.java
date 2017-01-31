package com.azimo.tool.slack;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.slack.model.SlackMessage;
import com.azimo.tool.slack.response.SlackPostMessageResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 26/01/17.
 */
public class SlackServiceManagerTest {

    SlackServiceManager slackServiceManager;

    @Mock
    SlackService slackService;
    @Mock
    AppConfig appConfig;
    @Mock
    SlackPostMessageResponse responseMock;
    @Mock
    SlackMessage message;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        slackServiceManager = new SlackServiceManager(slackService, appConfig);
    }

    @Test
    public void whenSendingMessageToSlack_ShouldReturnResponse() {
        String mockWebHook = "mockedWebHook";
        String mockedRawMessage = "ok";

        when(appConfig.get(AppConfigKey.SLACK_WEB_HOOK)).thenReturn(mockWebHook);
        when(responseMock.getRawMessage()).thenReturn(mockedRawMessage);
        when(slackService.postMessage(mockWebHook, message)).thenReturn(Observable.just(responseMock));

        SlackPostMessageResponse response = slackServiceManager.sendMessage(message);

        assertThat(response.equals(responseMock), is(true));
        assertThat(response.getRawMessage().equals(responseMock.getRawMessage()), is(true));
    }
}
