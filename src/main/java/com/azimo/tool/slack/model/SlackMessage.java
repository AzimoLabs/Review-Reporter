package com.azimo.tool.slack.model;

/**
 * Created by F1sherKK on 24/01/17.
 */
public class SlackMessage {

    public String text;
    public boolean mrkdwn;
    public Attachment[] attachments;

    public static class Attachment {
        public String author_name;
        public String title_link;
        public String thumb_url;
        public String title;
        public String text;
        public String color;
    }
}
