package com.azimo.tool.config.module;

import com.azimo.tool.config.AppConfig;
import com.azimo.tool.config.AppConfigLoader;
import com.azimo.tool.config.mapper.ConfigFileReader;
import com.azimo.tool.utils.file.FilePathParser;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by F1sherKK on 16/01/17.
 */
@Module
public class ConfigModule {

    @Singleton
    @Provides
    public AppConfigLoader provideAppConfigLoader(ConfigFileReader configFileReader) {
        return new AppConfigLoader(configFileReader);
    }

    @Singleton
    @Provides
    public AppConfig provideApplicationConfig(AppConfigLoader appConfigLoader) {
        return appConfigLoader.init();
    }

    @Singleton
    @Provides
    public ConfigFileReader provideConfigFileReader(FilePathParser filePathParser) {
        return new ConfigFileReader(filePathParser);
    }
}
