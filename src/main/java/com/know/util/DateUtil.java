package com.know.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: FaceCat
 * @Date: 2020/12/3 14:46
 */
public class DateUtil {

        public static final String FORMAT_DATE_DEFAULT = "yyyy-MM-dd";
        public static final String FORMAT_DATE_YYYYMMDD = "yyyyMMdd";
        public static final String FORMAT_DATE_YYYYMMDDHH = "yyyyMMddHH";
        public static final String FORMAT_DATE_YYYYMMDDHHMM = "yyyyMMddHHmm";
        public static final String FORMAT_DATE_YYYY_MM_DD_HHMMSS = "yyyyMMddHHmmss";
        public static final String FORMAT_DATE_YYYY_MM_DD_HHMMSS_SSS = "yyyyMMddHHmmssSSS";
        public static final String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd";
        public static final String FORMAT_DATE_PATTERN_1 = "yyyy/MM/dd";
        public static final String FORMAT_DATE_PATTERN_2 = "yyyy/M/dd";
        public static final String FORMAT_DATE_PATTERN_3 = "yyyy/MM/d";
        public static final String FORMAT_DATE_PATTERN_4 = "yyyy/M/d";
        public static final String FORMAT_DATE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
        public static final String FORMAT_DATE_YYYY_MM_DD_HHMM = "yyyy-MM-dd HHmm";
        public static final String FORMAT_DATE_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
        public static final String FORMAT_DATE_HH_MM = "HH:mm";
        public static final String FORMAT_DATE_HH_MM_SS = "HH:mm:ss";
        public static final String FORMAT_DATE_HHMM = "HHmm";
        public static final String FORMAT_DATE_HHMMSS = "HHmmss";
        public static final String FORMAT_WORK_TIME = "yyyy-MM-dd HHmmss";
        public static final String FORMAT_DATE_BEGIN = "yyyy-MM-dd 00:00:00";
        public static final String FORMAT_DATE_END = "yyyy-MM-dd 23:59:59";

        public static final int compareDate(String stringValue1, String stringValue2) throws ParseException {
        Date date1 = tryParse(stringValue1);
        if (date1 == null) {
            throw new ParseException("Can not parse " + stringValue1 + " to Date.", 0);
        } else {
            Date date2 = tryParse(stringValue2);
            if (date2 == null) {
                throw new ParseException("Can not parse " + stringValue1 + " to Date.", 0);
            } else {
                return date1.compareTo(date2);
            }
        }
    }

        public static final int compareDate(String stringValue1, String stringValue2, String formatPattern) throws ParseException {
        Date date1 = tryParse(stringValue1, formatPattern);
        if (date1 == null) {
            throw new ParseException("Can not parse " + stringValue1 + " to Date.", 0);
        } else {
            Date date2 = tryParse(stringValue2, formatPattern);
            if (date2 == null) {
                throw new ParseException("Can not parse " + stringValue1 + " to Date.", 0);
            } else {
                return date1.compareTo(date2);
            }
        }
    }

        public static final String getCurrentDateAsString() {
        return getCurrentDateAsString("yyyy-MM-dd");
    }

        public static final String getCurrentDateTimeAsString() {
        return getCurrentDateAsString("yyyy-MM-dd HH:mm:ss");
    }

        public static final String getCurrentDateAsString(String formatPattern) {
        Date date = new Date();
        return format(date, formatPattern);
    }

        public static final Date getCurrentDate() {
        return new Date();
    }

        public static final String format(Date date) {
        return date == null ? "" : format(date, "yyyy-MM-dd");
    }

        public static final String formatDateTime(Date date) {
        return date == null ? "" : format(date, "yyyy-MM-dd HH:mm:ss");
    }

        public static final String formatTimestamp(Date date) {
        return date == null ? "" : format(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

        public static final Date parseTimestamp(String date) {
        return date == null ? null : parse(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

        public static final String format(Date date, String formatPattern) {
        return date == null ? "" : (new SimpleDateFormat(formatPattern)).format(date);
    }

        public static final Date parse(String stringValue) {
        return parse(stringValue, "yyyy-MM-dd");
    }

        public static final Date parse(String stringValue, String formatPattern) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);

        try {
            return format.parse(stringValue);
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }

        public static final Date tryParse(String stringValue, String... pattern) {
        return pattern.length == 0 ? parse(stringValue, "yyyy-MM-dd") : parse(stringValue, pattern[0]);
    }

        public static int getDayOfWeek(int SUN_FST_DAY_OF_WEEK) {
        if (SUN_FST_DAY_OF_WEEK <= 7 && SUN_FST_DAY_OF_WEEK >= 1) {
            return SUN_FST_DAY_OF_WEEK == 1 ? 7 : SUN_FST_DAY_OF_WEEK - 1;
        } else {
            return 0;
        }
    }

        public static Timestamp parseTimestamp(String stringValue, String formatPattern) {
        return new Timestamp(parse(stringValue, formatPattern).getTime());
    }

        public static Timestamp parseTimestamp(Date d) {
        return new Timestamp(d.getTime());
    }

        public static Date addMilliseconds(Date date, int amount) {
        return add(date, 14, amount);
    }

        public static Date addMinutes(Date date, int amount) {
        return add(date, 12, amount);
    }

        public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(calendarField, amount);
            return c.getTime();
        }
    }

        public static String getBeginDate(Date date) {
        return date == null ? "" : format(date, "yyyy-MM-dd 00:00:00");
    }

        public static String getEndDate(Date date) {
        return date == null ? "" : format(date, "yyyy-MM-dd 23:59:59");
    }

        public static void main(String[] args) {
        String var1 = "2008/05/06";

        try {
            System.out.println(getCurrentDate());
            System.out.println(compareDate("2013-02-02 20:00:10", "2013-02-02 20:00:00"));
            System.out.println(compareDate("2013-02-02 20:00:10", "2013-02-02 20:00:00", "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException var3) {
            var3.printStackTrace();
        }

    }
    }

