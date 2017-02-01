package com.azimo.tool.config.mapper;

import com.azimo.tool.utils.file.FilePathParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class ConfigFileReader {

    private FilePathParser filePathParser;

    public ConfigFileReader(FilePathParser filePathParser) {
        this.filePathParser = filePathParser;
    }

    public String readValueForKey(String configFileLine, String key) {
        String value = null;
        if (configFileLine.contains(key)) {
            value = configFileLine.replace(key, "").replaceAll("\\s+", "");
        }
        return value;
    }

    public List<String> readFile(String filePath) throws IOException {
        List<String> config = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            while (line != null) {
                config.add(line);
                line = br.readLine();
            }
            return config;
        }
    }

    public String getConfigFilePath(String configProperties,
                                    String configPropertiesLabel) throws IOException {
        List<String> config = readFile(configProperties);
        String filePath = "";
        for (String line : config) {
            if (line.contains(configPropertiesLabel)) {
                filePath = filePathParser.parsePath(line.replace(configPropertiesLabel, ""));
            }
        }
        return filePath;
    }
}
