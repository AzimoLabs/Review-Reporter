package com.azimo.tool.config.mapper;

import com.azimo.tool.config.AppConfigKey;
import com.azimo.tool.config.AppConfigLoader;
import com.azimo.tool.utils.file.FilePathParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

/**
 * Created by F1sherKK on 19/01/17.
 */
public class ConfigFileReaderTest {

    ConfigFileReader configFileReader;

    @Mock
    FilePathParser filePathParser;

    String testFileName = "testTempFile.txt";
    File testFile;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        configFileReader = new ConfigFileReader(filePathParser);
        testFile = new File(testFileName);
    }

    @Test
    public void testWhenInsertedConfigProperties_shouldReturnConfigFilePath() throws IOException {
        String mockConfigFilePath = "somewhere/on/drive/file.txt";

        PrintWriter out = new PrintWriter(testFile, "UTF-8");
        out.println(AppConfigLoader.LABEL_CONFIG_PROPERTIES + mockConfigFilePath);
        out.close();

        doAnswer(invocation -> {
            String pathToParse = (String) invocation.getArguments()[0];
            return pathToParse.replace("~", System.getProperty("user.home"));
        }).when(filePathParser).parsePath(anyString());

        String configFilePath =
            configFileReader.getConfigFilePath(testFile.getAbsolutePath(), AppConfigLoader.LABEL_CONFIG_PROPERTIES);

        assertThat(!configFilePath.contains(AppConfigLoader.LABEL_CONFIG_PROPERTIES), is(true));
        assertThat(configFilePath.equals(mockConfigFilePath), is(true));
    }

    @Test
    public void testWhenInsertedNonSingleLineFile_ShouldReturnEachLineInStringList() throws IOException {
        String line1 = "line1";
        String line2 = "line2";
        String line3 = "line3";

        String fileContent = line1 + "\n" + line2 + "\n" + line3;

        PrintWriter out = new PrintWriter(testFile, "UTF-8");
        out.println(fileContent);
        out.close();

        List<String> stringList = configFileReader.readFile(testFile.getAbsolutePath());

        assertThat(stringList.size() == 3, is (true));
        assertThat(stringList.contains(line1), is(true));
        assertThat(stringList.contains(line2), is(true));
        assertThat(stringList.contains(line3), is(true));
    }

    @Test
    public void testWhenInsertedConfigLine_ShouldDeleteKeyAndReturnValue() throws Exception {
        String mockedValue = "valueMock";
        String mockFileLine = AppConfigKey.ANDROID_PACKAGE_NAME.toLabel() + " " + mockedValue;

        String value = configFileReader.readValueForKey(mockFileLine, AppConfigKey.ANDROID_PACKAGE_NAME.toLabel());

        assertEquals(value, mockedValue);
    }

    @Test
    public void testWhenConfigFileLineDoesNotContainKey_ShouldReturnNull() throws Exception {
        String mockFileLine = "mockedRandomLine";
        String key = "thisIsInvalidKey1235!@#$%";

        String value = configFileReader.readValueForKey(mockFileLine, key);

        assertEquals(value, null);
    }

    @Test
    public void testWhenConfigPropertiesDoesNotContainLabel_ShouldReturnEmptyString() throws Exception {
        String mockConfigFilePath = "somewhere/on/drive/file.txt";

        PrintWriter out = new PrintWriter(testFile, "UTF-8");
        out.println(mockConfigFilePath);
        out.close();

        doAnswer(invocation -> {
            String pathToParse = (String) invocation.getArguments()[0];
            return pathToParse.replace("~", System.getProperty("user.home"));
        }).when(filePathParser).parsePath(anyString());

        String configFilePath =
            configFileReader.getConfigFilePath(testFile.getAbsolutePath(), AppConfigLoader.LABEL_CONFIG_PROPERTIES);

        assertEquals(configFilePath, "");
    }

    @Test
    public void testWhenTryingToReadInvalidFile_ShouldThrowError() throws Exception {
        String invalidFilePath = "somewhere/on/drive/file.txt";

        IOException caughtException = null;
        try {
            List<String> fileAsList = configFileReader.readFile(invalidFilePath);
        } catch (IOException e) {
            caughtException = e;
        }

        assertThat(caughtException != null, is(true));
    }


    @After
    public void cleanUp() {
        testFile.delete();
    }
}
