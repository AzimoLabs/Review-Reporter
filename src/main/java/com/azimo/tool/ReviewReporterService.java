package com.azimo.tool;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.di.AppComponent;
import com.azimo.tool.di.DaggerAppComponent;
import com.azimo.tool.task.CreateJiraIssuesTask;
import com.azimo.tool.task.ReportToSlackTask;

import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.azimo.tool.task.base.ReviewReporterTask.RUN_VARIANT_LOOP;
import static com.azimo.tool.task.base.ReviewReporterTask.RUN_VARIANT_SINGLE;

/**
 * Created by F1sherKK on 14/12/16.
 */
public class ReviewReporterService {

    private static final String TAG = "ReviewReporterService:";

    @Inject
    CreateJiraIssuesTask createJiraIssuesTask;
    @Inject
    ReportToSlackTask reportToSlackTask;
    @Inject
    AppConfig appConfig;

    private static final int THREADS_AMOUNT = 1;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(THREADS_AMOUNT);

    public static void main(String[] args) {
        ReviewReporterService reviewReporterService = new ReviewReporterService();
        reviewReporterService.init(args);
    }

    private ReviewReporterService() {
        System.out.println(TAG + " starts!");
    }

    private void init(String[] args) {
        initAppComponent();

        String runVariant = args[1];
        System.out.println(TAG + " received runVariant: " + runVariant);
        switch (runVariant) {
            case RUN_VARIANT_SINGLE:
                reportIssuesWithThreeStarsOrLessOnce();
                reportReviewsToSlackOnce();
                break;

            case RUN_VARIANT_LOOP:
                reportIssuesWithThreeStarsOrLessPeriodically();
                reportNewReviewsToSlackPeriodically();
                break;

            default:
                System.out.println(
                    "Unknown run variant. Please use: '" + RUN_VARIANT_LOOP + "','" + RUN_VARIANT_SINGLE + "'.");
        }
    }

    private void initAppComponent() {
        AppComponent appComponent = DaggerAppComponent.create();
        appComponent.inject(this);
    }

    private void reportIssuesWithThreeStarsOrLessPeriodically() {
        final int timeInterval = Integer.parseInt(appConfig.get(AppConfigKey.REPORT_TO_JIRA_SCAN_INTERVAL_MILLIS));

        Runnable runnable = this::reportIssuesWithThreeStarsOrLessOnce;
        scheduler.scheduleAtFixedRate(runnable, 0, timeInterval, TimeUnit.MILLISECONDS);
    }

    private void reportIssuesWithThreeStarsOrLessOnce() {
        try {
            createJiraIssuesTask.run();
        } catch (Exception e) {
            System.out.println("Error has occurred: " + e.getMessage());
        }
    }

    private void reportNewReviewsToSlackPeriodically() {
        final int timeInterval = Integer.parseInt(appConfig.get(AppConfigKey.REPORT_TO_SLACK_SCAN_INTERVAL_MILLIS));

        Runnable runnable = this::reportReviewsToSlackOnce;
        scheduler.scheduleAtFixedRate(runnable, 0, timeInterval, TimeUnit.MILLISECONDS);
    }

    private void reportReviewsToSlackOnce() {
        try {
            reportToSlackTask.run();
        } catch (Exception e) {
            System.out.println("Error has occurred: " + e.getMessage());
        }
    }
}
