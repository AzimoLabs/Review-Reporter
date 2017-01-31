package com.azimo.tool.slack.response;

/**
 * Created by F1sherKK on 24/01/17.
 */
public class SlackPostMessageResponse {

    public static final String OK_MESSAGE = "ok";

    public String rawMessage;

    public SlackPostMessageResponse(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    public boolean wasSuccess() {
        return rawMessage.equals(OK_MESSAGE);
    }

    @Override
    public String toString() {
        return "SlackPostMessageResponse{" +
            "rawMessage='" + rawMessage + '\'' +
            '}';
    }
}
