package com.azimo.tool.firebase.response;

/**
 * Created by F1sherKK on 17/01/17.
 */
public class DefaultFirebaseResponse {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DefaultFirebaseResponse{" +
            "name='" + name + '\'' +
            '}';
    }
}
