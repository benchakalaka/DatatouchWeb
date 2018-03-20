package com.datascope.ui.utils.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    private static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
    private static final String DATE_FORMAT_JUST_DATE = "yyyy-MM-dd";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT, java.util.Locale.getDefault());
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_JUST_DATE);

    public static Date parseDate(String dateToParse) {
        try {
            return DateUtils.DATE_FORMATTER.parse(dateToParse);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String parseDate(LocalDate dateToParse) {
        try {
            return dateToParse.format(formatter);
        } catch (Exception e) {
            e.printStackTrace();
            return LocalDate.now().format(formatter);
        }
    }
}
