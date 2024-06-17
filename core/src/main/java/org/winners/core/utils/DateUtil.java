package org.winners.core.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String formatDatetime(LocalDateTime datetime) {
        return datetime.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN));
    }

    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
    }

}
