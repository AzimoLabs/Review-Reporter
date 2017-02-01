package com.azimo.tool.task.uploader;

import com.azimo.tool.firebase.FirebaseServiceManager;
import com.azimo.tool.firebase.collection.CreatedIssueCollection;
import com.azimo.tool.firebase.response.DefaultFirebaseResponse;
import com.azimo.tool.task.interfaces.Uploader;

/**
 * Created by F1sherKK on 27/01/17.
 */
public class FirebaseIssuesUploader implements Uploader<CreatedIssueCollection, DefaultFirebaseResponse> {

    private FirebaseServiceManager firebaseServiceManager;

    public FirebaseIssuesUploader(FirebaseServiceManager firebaseServiceManager) {
        this.firebaseServiceManager = firebaseServiceManager;
    }

    @Override
    public DefaultFirebaseResponse upload(CreatedIssueCollection createdIssues) {
        return firebaseServiceManager.updateCreatedIssues(createdIssues);
    }
}
