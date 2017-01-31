package com.azimo.tool.jira.response;

/**
 * Created by F1sherKK on 11/01/17.
 */
public class CreateNewIssueResponse {

    public String id;
    public String key;
    public String self;

    @Override
    public String toString() {
        return "CreateNewIssueResponse{" +
            "id='" + id + '\'' +
            ", key='" + key + '\'' +
            ", self='" + self + '\'' +
            '}';
    }


}
