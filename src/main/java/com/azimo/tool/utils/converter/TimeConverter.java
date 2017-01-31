package com.azimo.tool.utils.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by F1sherKK on 11/01/17.
 */
public class TimeConverter {

    public String millisToTimestamp(long milliseconds) {
        Date date = new Date(milliseconds);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        return df.format(date);
    }
}
