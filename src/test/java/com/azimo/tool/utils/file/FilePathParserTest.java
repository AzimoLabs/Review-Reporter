package com.azimo.tool.utils.file;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class FilePathParserTest {

    FilePathParser pathParser;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        pathParser = new FilePathParser();
    }

    @Test
    public void testWhenInsertingPathWithTilda_ShouldChangeItToHomeLocation() {
        String path = "~/mocked/path";

        String parsedPath = pathParser.parsePath(path);
        assertThat(parsedPath.contains(System.getProperty("user.home")), is(true));
    }
}

