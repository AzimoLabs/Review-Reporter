package com.azimo.tool.utils.converter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class TimeConverterTest {

    TimeConverter timeConverter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        timeConverter = new TimeConverter();
    }

    @Test
    public void testWhenInsertingMilliseconds_ShouldReturnValidOutputFormat() {
        long currentTimeInMillis = System.currentTimeMillis();

        Date expectedDate = new Date(currentTimeInMillis);
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String expectedFormattedTime = dateFormat.format(expectedDate);

        String formattedTime = timeConverter.millisToTimestamp(currentTimeInMillis);

        assertThat(expectedFormattedTime.equals(formattedTime), is(true));
    }
}
