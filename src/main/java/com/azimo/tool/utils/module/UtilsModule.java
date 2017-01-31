package com.azimo.tool.utils.module;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.utils.ColorFormatter;
import com.azimo.tool.utils.converter.IssueConverter;
import com.azimo.tool.utils.converter.MessageConverter;
import com.azimo.tool.utils.converter.ReviewConverter;
import com.azimo.tool.utils.converter.TimeConverter;
import com.azimo.tool.utils.file.FilePathParser;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by F1sherKK on 16/01/17.
 */
@Module
public class UtilsModule {

    @Singleton
    @Provides
    public IssueConverter provideIssueConverter(AppConfig appConfig, TimeConverter timeConverter) {
        return new IssueConverter(appConfig, timeConverter);
    }

    @Singleton
    @Provides
    public MessageConverter provideMessageConverter(AppConfig appConfig,
                                                    TimeConverter timeConverter,
                                                    ColorFormatter colorFormatter) {
        return new MessageConverter(appConfig, timeConverter, colorFormatter);
    }

    @Singleton
    @Provides
    public ReviewConverter provideReviewConverter() {
        return new ReviewConverter();
    }

    @Singleton
    @Provides
    public ColorFormatter provideColorFormatter() {
        return new ColorFormatter();
    }

    @Singleton
    @Provides
    public TimeConverter provideTimeConverter() {
        return new TimeConverter();
    }

    @Singleton
    @Provides
    public FilePathParser provideFilePathParser() {
        return new FilePathParser();
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }
}
