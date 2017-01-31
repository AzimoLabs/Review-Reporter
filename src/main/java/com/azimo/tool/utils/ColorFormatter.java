package com.azimo.tool.utils;

/**
 * Created by F1sherKK on 25/01/17.
 */
public class ColorFormatter {

    public static final String RATING_SECTION = "#681E7E";
    public static final String REPLY_SECTION = "#7D3CB5";

    public static final String DEFAULT = "#06A9FC";
    public static final String COLOR_FIVE_STAR = "#00753A";
    public static final String COLOR_FOUR_STAR = "#009E47";
    public static final String COLOR_THREE_STAR = "#FFCB35";
    public static final String COLOR_TWO_STAR = "#D82735";
    public static final String COLOR_ONE_STAR = "#B21F35";


    public String getColorFromStarRating(int starRating) {
        switch (starRating) {
            case 1:
                return COLOR_ONE_STAR;
            case 2:
                return COLOR_TWO_STAR;
            case 3:
                return COLOR_THREE_STAR;
            case 4:
                return COLOR_FOUR_STAR;
            case 5:
                return COLOR_FIVE_STAR;
            default:
                return DEFAULT;
        }
    }
}
