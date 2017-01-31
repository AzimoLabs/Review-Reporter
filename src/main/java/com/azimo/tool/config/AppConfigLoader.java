package com.azimo.tool.config;

import com.azimo.tool.config.mapper.ConfigFileReader;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class AppConfigLoader {

    public static final String CONFIG_FILE_PATH = "config.properties";
    public static final String LABEL_CONFIG_PROPERTIES = "config.dir=";

    private ConfigFileReader configFileReader;

    @Inject
    public AppConfigLoader(ConfigFileReader configFileReader) {
        this.configFileReader = configFileReader;
    }

    public AppConfig init() {
        HashMap<AppConfigKey, String> configMap = new HashMap<>();

        try {
            String fileConfigPath = configFileReader.getConfigFilePath(CONFIG_FILE_PATH, LABEL_CONFIG_PROPERTIES);
            List<String> configList = configFileReader.readFile(fileConfigPath);
            List<AppConfigKey> keyList = Arrays.asList(AppConfigKey.values());

            readConfigValuesToMap(configMap, configList, keyList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AppConfig appConfig = new AppConfig();
        appConfig.init(configMap);

        return appConfig;
    }

    private void readConfigValuesToMap(HashMap<AppConfigKey, String> configMap,
                                       List<String> configList,
                                       List<AppConfigKey> keyList) {
        for (AppConfigKey key : keyList) {
            for (String line : configList) {
                String value = configFileReader.readValueForKey(line, key.toLabel());
                if (value != null) {
                    configMap.put(key, value);
                }
            }
        }
    }
}
