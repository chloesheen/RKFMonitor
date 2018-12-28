package com.example.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static String setDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM dd yyyy hh:mm a", Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        return dateFormatter.format(currentDate);

    }

    public static String setOnlyDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM dd yyyy", Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        return dateFormatter.format(currentDate);

    }

    public static Date convertToDate(String stringdate) {
        SimpleDateFormat dateformatter = new SimpleDateFormat("EEEE, MMMM dd yyyy hh:mm a", Locale.US);
        dateformatter.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        try {
            return (Date) dateformatter.parse(stringdate);
        }catch(Exception e) { e.printStackTrace();}
        return null;
    }

    public static Date convertToMonthDate(String stringdate) {
        SimpleDateFormat dateformatter = new SimpleDateFormat("MMMM", Locale.US);
        dateformatter.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        try {
            return (Date) dateformatter.parse(stringdate);
        }catch(Exception e) { e.printStackTrace();}
        return null;
    }
}
