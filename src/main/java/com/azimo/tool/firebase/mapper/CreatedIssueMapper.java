package com.azimo.tool.firebase.mapper;

import com.azimo.tool.firebase.collection.CreatedIssueCollection;
import com.azimo.tool.firebase.model.CreatedIssuesList;
import com.azimo.tool.firebase.response.GetCreatedIssuesResponse;

/**
 * Created by F1sherKK on 17/01/17.
 */
public class CreatedIssueMapper {

    public CreatedIssueCollection getCreatedIssuesResponseToCollection(GetCreatedIssuesResponse response) {
        CreatedIssueCollection collection = new CreatedIssueCollection();
        if (response != null && response.getCreatedIssues() != null) {
            collection.addAll(response.getCreatedIssues());
        }
        return collection;
    }

    public CreatedIssuesList createdIssuesCollectionToList(CreatedIssueCollection collection) {
        return new CreatedIssuesList(collection);
    }
}
