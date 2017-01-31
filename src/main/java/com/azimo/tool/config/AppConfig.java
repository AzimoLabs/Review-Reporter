package com.azimo.tool.config;

import java.util.HashMap;

/**
 * Created by F1sherKK on 15/12/16.
 */
public class AppConfig {

    private HashMap<AppConfigKey, String> configMap = new HashMap<>();

    public void init(HashMap<AppConfigKey, String> configMap) {
        this.configMap = configMap;
    }

    public String get(AppConfigKey key) {
        return configMap.get(key);
    }

    public boolean contains(AppConfigKey key) {
        return configMap.containsKey(key);
    }
}
