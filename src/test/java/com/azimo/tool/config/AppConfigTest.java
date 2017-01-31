package com.azimo.tool.config;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class AppConfigTest {

    AppConfig appConfig;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        appConfig = new AppConfig();
    }

    @Test
    public void testOnInit_ShouldStoreConfigValues() {
        String applicationNameMock = "Review-Reporter";
        String androidPackageNameMock = "mock.mock.mock";

        HashMap<AppConfigKey, String> testConfigConfiguration = new HashMap<>();
        testConfigConfiguration.put(AppConfigKey.ANDROID_PACKAGE_NAME, androidPackageNameMock);
        testConfigConfiguration.put(AppConfigKey.APPLICATION_NAME, applicationNameMock);

        appConfig.init(testConfigConfiguration);

        assertTrue(appConfig.contains(AppConfigKey.ANDROID_PACKAGE_NAME));
        assertTrue(appConfig.contains(AppConfigKey.APPLICATION_NAME));
        assertEquals(appConfig.get(AppConfigKey.ANDROID_PACKAGE_NAME), androidPackageNameMock);
        assertEquals(appConfig.get(AppConfigKey.APPLICATION_NAME), applicationNameMock);
    }
}
