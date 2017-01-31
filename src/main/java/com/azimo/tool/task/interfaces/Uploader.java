package com.azimo.tool.task.interfaces;

/**
 * Created by F1sherKK on 27/01/17.
 */
public interface Uploader<T, R> {
    R upload(T model);
}
