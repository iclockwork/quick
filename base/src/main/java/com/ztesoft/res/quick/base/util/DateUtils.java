package com.ztesoft.res.quick.base.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

/**
 * DateUtils
 *
 * @author: fengwang
 * @date: 2018-2-7 15:38
 * @version: 1.0
 * @since: JDK 1.8
 */
public class DateUtils {
    /**
     * DATE_PATTERN_DEFAULT_LONG
     */
    public static final String DATE_PATTERN_DEFAULT_LONG = "yyyy-MM-dd HH:mm:ss";

    /**
     * DATE_PATTERN_SLASH_LONG
     */
    public static final String DATE_PATTERN_SLASH_LONG = "yyyy/MM/dd HH:mm:ss";

    /**
     * DATE_PATTERN_DEFAULT_LONG_PRECISION_MIN
     */
    public static final String DATE_PATTERN_DEFAULT_LONG_PRECISION_MIN = "yyyy-MM-dd HH:mm";

    /**
     * DATE_PATTERN_DEFAULT
     */
    public static final String DATE_PATTERN_DEFAULT = "yyyy-MM-dd";

    /**
     * DATE_PATTERN_DEFAULT_SHORT
     */
    public static final String DATE_PATTERN_DEFAULT_SHORT = "yyyyMMdd";

    /**
     * DATE_PATTERN_DEFAULT_TIME
     */
    public static final String DATE_PATTERN_DEFAULT_TIME = "HH:mm";

    /**
     * String to date.
     *
     * @param str     str
     * @param pattern pattern
     * @return the date
     */
    public static Date strToDate(String str, String pattern) {
        return DateTimeFormat.forPattern(pattern).parseDateTime(str).toDate();
    }

    /**
     * Date to string.
     *
     * @param date    date
     * @param pattern pattern
     * @return the string
     */
    public static String dateToStr(Date date, String pattern) {
        return new DateTime(date).toString(pattern);
    }
}
