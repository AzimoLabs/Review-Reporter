package com.azimo.tool.task.interfaces;

/**
 * Created by F1sherKK on 27/01/17.
 */
public interface Provider<R> {
    R fetch() throws Exception;
}
