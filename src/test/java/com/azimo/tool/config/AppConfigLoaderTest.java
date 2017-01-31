package com.azimo.tool.config;

import com.azimo.tool.config.mapper.ConfigFileReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 19/01/17.
 */
public class AppConfigLoaderTest {

    AppConfigLoader appConfigLoader;

    @Mock
    ConfigFileReader configFileReader;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        appConfigLoader = new AppConfigLoader(configFileReader);
    }

    @Test
    public void testOnInit_shouldReturnConfiguredAppConfig() throws IOException {
        String mockFilePath = "mockedFilePath";
        when(configFileReader.getConfigFilePath(anyString(), anyString())).thenReturn(mockFilePath);

        String value1 = "mockedValue1";
        String value2 = "mockedValue2";
        String line1 = AppConfigKey.ANDROID_PACKAGE_NAME + " " + value1;
        String line2 = AppConfigKey.APPLICATION_NAME + " " + value2;
        List<String> configFileLineList = Arrays.asList(line1, line2);
        when(configFileReader.readFile(mockFilePath)).thenReturn(configFileLineList);
        when(configFileReader.readValueForKey(line1, AppConfigKey.ANDROID_PACKAGE_NAME.toLabel())).thenReturn(value1);
        when(configFileReader.readValueForKey(line2, AppConfigKey.APPLICATION_NAME.toLabel())).thenReturn(value2);

        AppConfig appConfig = appConfigLoader.init();

        assertEquals(appConfig.get(AppConfigKey.ANDROID_PACKAGE_NAME), value1);
        assertEquals(appConfig.get(AppConfigKey.APPLICATION_NAME), value2);
    }
}
