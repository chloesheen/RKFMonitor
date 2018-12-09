package com.example.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static String setDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM dd YYYY hh:mm a", Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        return dateFormatter.format(currentDate);

    }
}
