package com.azimo.tool.di;

import com.azimo.tool.ReviewReporterService;
import com.azimo.tool.config.module.ConfigModule;
import com.azimo.tool.firebase.module.FirebaseModule;
import com.azimo.tool.jira.module.JiraModule;
import com.azimo.tool.publisher.module.PublisherModule;
import com.azimo.tool.slack.module.SlackModule;
import com.azimo.tool.task.module.LaunchTaskModule;
import com.azimo.tool.utils.module.UtilsModule;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by F1sherKK on 16/01/17.
 */
@Singleton
@Component(
    modules = {
        LaunchTaskModule.class,
        ConfigModule.class,
        FirebaseModule.class,
        PublisherModule.class,
        JiraModule.class,
        SlackModule.class,
        UtilsModule.class
    }
)
public interface AppComponent {

    void inject(ReviewReporterService reviewReporterService);

    @Component.Builder
    interface Builder {
        AppComponent build();
    }
}
