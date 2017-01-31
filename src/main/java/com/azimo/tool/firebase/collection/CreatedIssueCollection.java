package com.azimo.tool.firebase.collection;

import com.azimo.tool.firebase.model.CreatedIssue;

import java.util.ArrayList;

/**
 * Created by F1sherKK on 17/01/17.
 */
public class CreatedIssueCollection extends ArrayList<CreatedIssue> {

    public boolean containsReviewId(String reviewId) {
        boolean containsId = false;
        for (CreatedIssue createdIssue : this) {
            if (createdIssue.getCreatedReviewId().equals(reviewId)) {
                containsId = true;
                break;
            }
        }
        return containsId;
    }
}
