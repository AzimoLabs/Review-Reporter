package com.azimo.tool.utils.file;

/**
 * Created by F1sherKK on 11/01/17.
 */
public class FilePathParser {

    public String parsePath(String path) {
        return path.replace("~", System.getProperty("user.home"));
    }
}
