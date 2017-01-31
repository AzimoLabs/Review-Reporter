package com.azimo.tool.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

/**
 * Created by F1sherKK on 26/01/17.
 */
public class ColorFormatterTest {

    ColorFormatter colorFormatter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        colorFormatter = new ColorFormatter();
    }

    @Test
    public void testWhenInsertedStarRatingWithValueOne_ShouldReturnCorrectColorString() {
        assertEquals(colorFormatter.getColorFromStarRating(1), ColorFormatter.COLOR_ONE_STAR);
    }

    @Test
    public void testWhenInsertedStarRatingWithValueTwo_ShouldReturnCorrectColorString() {
        assertEquals(colorFormatter.getColorFromStarRating(2), ColorFormatter.COLOR_TWO_STAR);
    }

    @Test
    public void testWhenInsertedStarRatingWithValueThree_ShouldReturnCorrectColorString() {
        assertEquals(colorFormatter.getColorFromStarRating(3), ColorFormatter.COLOR_THREE_STAR);
    }

    @Test
    public void testWhenInsertedStarRatingWithValueFour_ShouldReturnCorrectColorString() {
        assertEquals(colorFormatter.getColorFromStarRating(4), ColorFormatter.COLOR_FOUR_STAR);
    }

    @Test
    public void testWhenInsertedStarRatingWithValueFive_ShouldReturnCorrectColorString() {
        assertEquals(colorFormatter.getColorFromStarRating(5), ColorFormatter.COLOR_FIVE_STAR);
    }

    @Test
    public void testWhenInsertedUnsupportedValue_ShouldReturnDefaultColorString() {
        assertEquals(colorFormatter.getColorFromStarRating(12345), ColorFormatter.DEFAULT);
    }
}
