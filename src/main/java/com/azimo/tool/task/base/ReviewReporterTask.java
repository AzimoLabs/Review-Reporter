package com.azimo.tool.task.base;

/**
 * Created by F1sherKK on 25/01/17.
 */
public abstract class ReviewReporterTask {

    public static final String RUN_VARIANT_LOOP = "loopRun";
    public static final String RUN_VARIANT_SINGLE = "singleRun";

    public abstract void run() throws Exception;
}
