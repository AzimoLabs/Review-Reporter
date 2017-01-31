package com.azimo.tool.firebase;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import java.io.FileInputStream;
import java.util.Arrays;

/**
 * Created by F1sherKK on 16/01/17.
 */
public class FirebaseAuth {

    private static final String FIREBASE_SCOPE_DATABASE = "https://www.googleapis.com/auth/firebase.database";
    private static final String FIREBASE_SCOPE_EMAIL = "https://www.googleapis.com/auth/userinfo.email";

    public String requestAuthToken(String credentialsPath) throws Exception {
        GoogleCredential credentials = GoogleCredential.fromStream(new FileInputStream(credentialsPath));
        GoogleCredential scoped = credentials.createScoped(
            Arrays.asList(
                FIREBASE_SCOPE_DATABASE,
                FIREBASE_SCOPE_EMAIL
            )
        );
        scoped.refreshToken();
        return scoped.getAccessToken();
    }
}
