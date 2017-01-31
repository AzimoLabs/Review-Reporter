package com.azimo.tool.jira;

import com.azimo.tool.jira.model.Issue;
import com.azimo.tool.jira.response.CreateNewIssueResponse;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by F1sherKK on 10/01/17.
 */
public interface JiraIssueService {

    @POST("issue")
    Observable<CreateNewIssueResponse> createIssue(
        @Body Issue issue
    );
}
