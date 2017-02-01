package com.azimo.tool.slack;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.slack.model.SlackMessage;
import com.azimo.tool.slack.response.SlackPostMessageResponse;

/**
 * Created by F1sherKK on 24/01/17.
 */
public class SlackServiceManager {

    private SlackService slackService;
    private AppConfig appConfig;

    public SlackServiceManager(SlackService slackService, AppConfig appConfig) {
        this.slackService = slackService;
        this.appConfig = appConfig;
    }

    public SlackPostMessageResponse sendMessage(SlackMessage message) {
        return slackService.postMessage(appConfig.get(AppConfigKey.SLACK_WEB_HOOK), message).toBlocking().first();
    }
}
