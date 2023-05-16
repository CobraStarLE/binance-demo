package com.binance.demo.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimestampUtil {

    private TimestampUtil() {
    }

    public static Long getUnixTimeStamp(Date date, ZoneId... zoneIds) {
        ZoneId zoneId = getZoneId(zoneIds);
        LocalDateTime localDateTime = toLocalDateTime(date, zoneId);
        return getUnixTimeStamp(zoneId, localDateTime);
    }

    /**
     * 获取指定的时区
     *
     * @param zoneIds 如果为空，则获取UTC+8时区
     * @return
     */
    public static ZoneId getZoneId(ZoneId... zoneIds) {
        if (zoneIds != null && zoneIds.length > 0) {
            return zoneIds[0];
        }
        return ZoneId.of("Asia/Shanghai");
    }

    public static LocalDateTime toLocalDateTime(Date date, ZoneId... zoneIds) {
        ZoneId zoneId = getZoneId(zoneIds);
        return date.toInstant().atZone(zoneId).toLocalDateTime();
    }

    public static Long getUnixTimeStamp(ZoneId zoneId, LocalDateTime... localDateTime) {
        LocalDateTime _localDateTime = getLocalDateTime(zoneId, localDateTime);
        ZonedDateTime zdt = _localDateTime.atZone(zoneId);
        return zdt.toInstant().toEpochMilli();
    }

    /**
     * 如果 localDateTimes不为空，则返回localDateTimes[0],<p>
     * 否则返回指定ZoneId的当前时间
     *
     * @param zoneId         如果为空，则默认为时区UTC+8
     * @param localDateTimes
     * @return
     */
    public static LocalDateTime getLocalDateTime(ZoneId zoneId, LocalDateTime... localDateTimes) {
        if (localDateTimes != null && localDateTimes.length > 0) {
            return localDateTimes[0];
        }
        if (zoneId == null) {
            zoneId = getZoneId();
        }

        return LocalDateTime.now(zoneId);
    }

    public static Long getUnixTimeStamp(LocalDate ld, ZoneId... zoneIds) {
        return getUnixTimeStamp(ld, 0, 0, 0, zoneIds);
    }

    public static Long getUnixTimeStamp(LocalDate ld, int hour, int minute, int second, ZoneId... zoneIds) {
        ZoneId zoneId = getZoneId(zoneIds);
        LocalDateTime localDateTime = toLocalDateTime(ld, hour, minute, second);
        return getUnixTimeStamp(zoneId, localDateTime);
    }

    public static LocalDateTime toLocalDateTime(LocalDate ld, int hour, int minute, int second) {
        return ld.atTime(hour, minute, second);
    }

    public static Long getUnixTimeStamp(
            int year, int month, int day, int hour, int minute, int second,
            ZoneId... zoneIds) {
        LocalDateTime ldt = getLocalDateTime(year, month, day, hour, minute, second, zoneIds);
        return getUnixTimeStamp(ldt);
    }

    public static LocalDateTime getLocalDateTime(
            int year, int month, int day, int hour, int minute, int second,
            ZoneId... zoneIds) {
        ZoneId zoneId = getZoneId(zoneIds);
        Calendar calendar = getCalendar(zoneId);
        setYYYYMMDD(calendar, year, month, day);
        setHHmmss(calendar, hour, minute, second);
        return toLocalDateTime(calendar, zoneId);
    }

    /**
     * 得到Unix时间戳，精确到毫秒
     * <p>如果参数为空，则获取当前时间</p>
     *
     * @param localDateTimes
     * @return
     */
    public static Long getUnixTimeStamp(LocalDateTime... localDateTimes) {
        ZoneId zoneId = getZoneId();
        LocalDateTime localDateTime = getLocalDateTime(zoneId, localDateTimes);
        return getUnixTimeStamp(zoneId, localDateTime);
    }

    public static Calendar getCalendar(ZoneId zoneId) {
        return Calendar.getInstance(TimeZone.getTimeZone(zoneId));
    }

    /**
     * 根据指定的年月日设置年月日
     */
    private static void setYYYYMMDD(Calendar calendar, int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);
    }

    /**
     * 根据指定的时分秒设置时分秒
     */
    private static void setHHmmss(Calendar calendar, int hour, int minute, int second) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static LocalDateTime toLocalDateTime(Calendar calendar, ZoneId zoneId) {
        return calendar.getTime().toInstant().atZone(zoneId).toLocalDateTime();
    }

    public static Long setDay(long unixTimestamp, int day) {
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTimeInMillis(unixTimestamp);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTimeInMillis();
    }

    public static Long getUnixTimeStamp(int year, int month, int day, TimeMode timeMode, ZoneId... zoneIds) {
        LocalDateTime ldt = getLocalDateTime(year, month, day, timeMode, zoneIds);
        return getUnixTimeStamp(ldt);
    }

    public static LocalDateTime getLocalDateTime(int year, int month, int day, TimeMode timeMode, ZoneId... zoneIds) {
        ZoneId zoneId = getZoneId(zoneIds);
        Calendar calendar = getCalendar(zoneId);
        setYYYYMMDD(calendar, year, month, day);
        setHHmmss(calendar, timeMode);
        return toLocalDateTime(calendar, zoneId);
    }

    /**
     * 根据指定的时间模式设置时分秒
     *
     * @param calendar
     * @param timeMode ZERO_ZERO-00:00:00<p>
     *                 TWENTY_THREE-23:59:59<p>
     *                 CURRENT-当前时间,如果为null，默认为当前的时间
     */
    private static void setHHmmss(Calendar calendar, TimeMode timeMode) {
        switch (timeMode) {
            case ZERO_ZERO:
                setHHmmss(calendar, 0, 0, 0);
                break;
            case TWENTY_THREE:
                setHHmmss(calendar, 23, 59, 59);
                break;
            default:
                break;
        }
    }

    /**
     * 判断指定的时间是否是在当天之中
     *
     * @param time
     * @return true: 是 ，false:否
     */
    public static boolean duringCurrDay(long time) {
        LocalDateTime todayZeroLDT = getCurrDateTime(TimeMode.ZERO_ZERO);
        LocalDateTime tomorrowZeroLDT = getPlusDay(todayZeroLDT, 1);
        Long startTime = getUnixTimeStamp(todayZeroLDT);
        Long endTime = getUnixTimeStamp(tomorrowZeroLDT);
        return during(time, startTime, endTime);
    }

    /**
     * 判断指定的时间是否在指定的两个时间之中之中
     *
     * @param time
     * @param startTime
     * @param endTime
     * @param isEndTimeClosure 结束时间是否闭合，如果true,则比较time<=endTime,否则比较time < endTime，默认为true
     * @return true: 是 ，false:否
     */
    public static boolean during(long time, long startTime, long endTime, boolean... isEndTimeClosure) {
        boolean endTimeClosure = true;
        if (ObjectUtils.isNotEmpty(isEndTimeClosure)) {
            endTimeClosure = isEndTimeClosure[0];
        }
        if (endTimeClosure) {
            if (time >= startTime && time <= endTime) {
                return true;
            }
        } else {
            if (time >= startTime && time < endTime) {
                return true;
            }
        }

        return false;
    }

    /**
     * 返回当前时间与当天结束之间相隔的时间（毫秒）
     *
     * @return
     */
    public static long distanceCurrDay() {
        LocalDateTime todayZeroLDT = getCurrDateTime(TimeMode.ZERO_ZERO);
        LocalDateTime tomorrowZeroLDT = getPlusDay(todayZeroLDT, 1);
        Long startTime = getUnixTimeStamp();
        Long endTime = getUnixTimeStamp(tomorrowZeroLDT);
        return endTime - startTime;
    }

    /**
     * 把Unix时间戳转为时间格式字符串
     *
     * @param unixTime
     * @param pattern
     * @param zoneIds
     * @return
     */
    public static String format(long unixTime, FastDateFormatPattern pattern, ZoneId... zoneIds) {
        if (pattern == FastDateFormatPattern.PURE_UNIX_TIME_FORMAT) {
            return String.valueOf(unixTime);
        }
        LocalDateTime localDateTime = toLocalDateTime(unixTime, zoneIds);
        return format(localDateTime, pattern);
    }

    /**
     * 把Unix时间戳转为LocalDateTime
     *
     * @param unixTime
     * @param zoneIds
     * @return
     */
    public static LocalDateTime toLocalDateTime(long unixTime, ZoneId... zoneIds) {
        Instant instant = Instant.ofEpochMilli(unixTime);
        ZoneId zoneId = getZoneId(zoneIds);
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    /**
     * 把LocalDateTime转为时间格式字符串
     *
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String format(LocalDateTime localDateTime, FastDateFormatPattern pattern) {
        switch (pattern) {
            case ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT:
            case ISO_8601_EXTENDED_DATETIME_NO_T_TIME_ZONE_FORMAT:
                return DateFormatUtils.format(getUnixTimeStamp(localDateTime), pattern.getFormatPattern());
        }

        return localDateTime.format(DateTimeFormatter.ofPattern(pattern.getFormatPattern()));
    }

    /**
     * 把时间格式字符串转为Unix时间戳
     *
     * @param formatTimeStr
     * @param patterns
     * @return
     */
    public static Long getUnixTimeStamp(String formatTimeStr, TimeMode timeMode, FastDateFormatPattern... patterns) throws ParseException {
        FastDateFormatPattern pattern = getFastDateFormatPattern(patterns);
        if (pattern == FastDateFormatPattern.PURE_DATETIME_PATTERN)
            return Long.valueOf(formatTimeStr);
        LocalDateTime ldt = toLocalDateTime(formatTimeStr, patterns);
        return getUnixTimeStamp(timeMode, ldt);
    }

    public static FastDateFormatPattern getFastDateFormatPattern(FastDateFormatPattern... patterns) {
        FastDateFormatPattern pattern;
        if (patterns == null || patterns.length == 0) {
            pattern = FastDateFormatPattern.ISO_DATETIME_NO_T_FORMAT;
        } else {
            pattern = patterns[0];
        }
        return pattern;
    }

    /**
     * 把时间格式字符串转为LocalDateTime
     *
     * @param formatTimeStr
     * @param patterns
     * @return
     */
    public static LocalDateTime toLocalDateTime(String formatTimeStr, FastDateFormatPattern... patterns) throws ParseException {
        return toLocalDateTime(formatTimeStr, getZoneId(), patterns);
    }

    /**
     * 得到Unix时间戳，精确到毫秒
     *
     * @param timeMode             如果指定timeMode，则会对sourceLocalDateTime的时分秒重写,<p>
     *                             支持         ZERO_ZERO-00:00:00<p>
     *                             TWENTY_THREE-23:59:59<p>
     *                             CURRENT-当前时间,如果为null，默认为当前的时间
     * @param sourceLocalDateTimes 指定的时间,如果为null,则为当前时间
     * @return
     */
    public static Long getUnixTimeStamp(TimeMode timeMode, LocalDateTime... sourceLocalDateTimes) {
        LocalDateTime sourceLocalDateTime = getLocalDateTime(null, sourceLocalDateTimes);
        LocalDateTime targetLocalDateTime = setHHmmss(sourceLocalDateTime, timeMode);
        return getUnixTimeStamp(targetLocalDateTime);
    }

    /**
     * 把时间格式字符串转为LocalDateTime
     *
     * @param formatTimeStr
     * @param zoneId
     * @param patterns
     * @return
     */
    public static LocalDateTime toLocalDateTime(String formatTimeStr, ZoneId zoneId, FastDateFormatPattern... patterns) throws ParseException {
        FastDateFormatPattern pattern = getFastDateFormatPattern(patterns);
        LocalDateTime ldt;
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getFormatPattern());
        // 当格式为年月日时，特殊处理为年月日时分秒格式
        switch (pattern) {
            case PURE_UNIX_TIME_FORMAT:
                ldt = toLocalDateTime(Long.valueOf(formatTimeStr), zoneId);
                break;
            case ISO_DATE_FORMAT:
            case PURE_DATE_FORMAT:
            case CN_DATE_FORMAT:
                LocalDate localDate = LocalDate.parse(formatTimeStr, df);
                ldt = localDate.atStartOfDay();
                break;
            case ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT:
            case ISO_8601_EXTENDED_DATETIME_NO_T_TIME_ZONE_FORMAT:
                Date date = DateUtils.parseDateStrictly(formatTimeStr, pattern.getFormatPattern());
                return toLocalDateTime(date.getTime(), zoneId);
            default:
                ldt = LocalDateTime.parse(formatTimeStr, df);
                break;
        }
        return ldt;
    }

    /**
     * 根据指定的时间模式设置时分秒
     *
     * @param localDateTime
     * @param timeMode      ZERO_ZERO-00:00:00<p>
     *                      TWENTY_THREE-23:59:59<p>
     *                      CURRENT-当前时间,如果为null，默认为当前的时间
     */
    public static LocalDateTime setHHmmss(LocalDateTime localDateTime, TimeMode... timeMode) {
        if (localDateTime != null) {
            if (timeMode != null && timeMode.length > 0) {
                ZoneId zoneId = getZoneId();
                Calendar calendar = toCalendar(localDateTime, zoneId);
                setHHmmss(calendar, timeMode[0]);
                LocalDateTime result = toLocalDateTime(calendar, zoneId);
                return result;
            }
        }
        return localDateTime;

    }

    public static Calendar toCalendar(LocalDateTime localDateTime, ZoneId zoneId) {
        Calendar calendar = getCalendar(zoneId);
        Date date = Date.from(localDateTime.atZone(zoneId).toInstant());
        calendar.setLenient(false);
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 得到Unix时间戳，精确到毫秒
     *
     * @param timeMode 如果指定timeMode，则会对sourceLocalDateTime的时分秒重写,<p>
     *                 支持         ZERO_ZERO-00:00:00<p>
     *                 TWENTY_THREE-23:59:59<p>
     *                 CURRENT-当前时间,如果为null，默认为当前的时间
     * @return
     */
    public static Long getUnixTimeStamp(TimeMode timeMode, long unixTimestamp) {
        LocalDateTime ldt = TimestampUtil.toLocalDateTime(unixTimestamp);
        return getUnixTimeStamp(timeMode, ldt);
    }

    /**
     * 获取当前的时间格式字符串
     *
     * @param patterns
     * @return
     */
    public static String format(FastDateFormatPattern... patterns) {
        LocalDateTime localDateTime = getCurrDateTime();
        FastDateFormatPattern pattern = FastDateFormatPattern.ISO_DATETIME_NO_T_FORMAT;
        if (ObjectUtils.isNotEmpty(patterns)) {
            pattern = patterns[0];
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern.getFormatPattern()));
    }

    /**
     * 得到当前时间
     *
     * @param zoneIds
     * @return
     */
    public static LocalDateTime getCurrDateTime(ZoneId... zoneIds) {
        ZoneId zoneId;
        if (zoneIds == null || zoneIds.length == 0) {
            zoneId = getZoneId();
        } else {
            zoneId = zoneIds[0];
        }
        return getLocalDateTime(zoneId);
    }

    /**
     * 得到当前时间
     *
     * @param zoneIds
     * @return
     */
    public static LocalDateTime getCurrDateTime(TimeMode timeMode, ZoneId... zoneIds) {
        ZoneId zoneId;
        if (zoneIds == null || zoneIds.length == 0) {
            zoneId = getZoneId();
        } else {
            zoneId = zoneIds[0];
        }
        LocalDateTime currLDT = getLocalDateTime(zoneId);
        return setHHmmss(currLDT, timeMode);
    }

    /**
     * 当前时间加上指定的月数
     *
     * @param localDateTime
     * @param months
     * @return
     */
    public static LocalDateTime getPlusMonth(LocalDateTime localDateTime, int months, TimeMode... timeMode) {
        ZoneId zoneId = getZoneId();
        Calendar calendar = toCalendar(localDateTime, zoneId);
        calendar.add(Calendar.MONTH, months);
        if (timeMode != null && timeMode.length > 0) {
            setHHmmss(calendar, timeMode[0]);
        }
        return toLocalDateTime(calendar, zoneId);
    }

    /**
     * 当前时间加上指定的年数
     *
     * @param localDateTime
     * @param years
     * @return
     */
    public static LocalDateTime getPlusYear(LocalDateTime localDateTime, int years, TimeMode... timeMode) {
        ZoneId zoneId = getZoneId();
        Calendar calendar = toCalendar(localDateTime, zoneId);
        calendar.add(Calendar.YEAR, years);
        if (timeMode != null && timeMode.length > 0) {
            setHHmmss(calendar, timeMode[0]);
        }
        return toLocalDateTime(calendar, zoneId);
    }

    /**
     * 当前时间加上指定的天数
     *
     * @param localDateTime
     * @param days
     * @return
     */
    public static LocalDateTime getPlusDay(LocalDateTime localDateTime, int days, TimeMode... timeMode) {
        ZoneId zoneId = getZoneId();
        Calendar calendar = toCalendar(localDateTime, zoneId);
        calendar.add(Calendar.DATE, days);
        if (timeMode != null && timeMode.length > 0) {
            setHHmmss(calendar, timeMode[0]);
        }
        return toLocalDateTime(calendar, zoneId);
    }

    /**
     * 当前时间加上指定的小时
     *
     * @param localDateTime
     * @param hours
     * @return
     */
    public static LocalDateTime getPlusHour(LocalDateTime localDateTime, int hours) {
        ZoneId zoneId = getZoneId();
        Calendar calendar = toCalendar(localDateTime, zoneId);
        calendar.add(Calendar.HOUR, hours);
        return toLocalDateTime(calendar, zoneId);
    }

    /**
     * 修改日期时间上的字段，比如把1992-03-27设置成2000-03-27就可以如下调用:
     * <br></br>
     * {@code LocalDateTime ldt=TimestampUtil.getLocalDateTime(1992,3,27,0,0,0);}
     * * <br></br>
     * {@code  TimestampUtil.setDateTimeField(old_ldt, Calendar.YEAR,2000);}
     * * <br></br>
     * {@code   System.out.println(TimestampUtil.format(ldt, TimestampUtil.FastDateFormatPattern.ISO_DATETIME_NO_T_FORMAT));//输出2000-03-27 00:00:00}
     *
     * @param localDateTime
     * @param calendarField
     * @param amount
     * @return
     */
    public static LocalDateTime setDateTimeField(LocalDateTime localDateTime, final int calendarField, final int amount) {
        ZoneId zoneId = getZoneId();
        Calendar calendar = toCalendar(localDateTime, zoneId);
        calendar.set(calendarField, amount);
        return toLocalDateTime(calendar, zoneId);
    }

    public static Long setDateTimeField(Long unixTimestamp, final int calendarField, final int amount) {
        ZoneId zoneId = getZoneId();
        Calendar calendar = toCalendar(unixTimestamp);
        calendar.set(calendarField, amount);
        return calendar.getTimeInMillis();
    }

    public static Calendar toCalendar(Long unixTimestamp) {
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTimeInMillis(unixTimestamp);
        return c;
    }

    public static Date toDate(String formatTimeStr, FastDateFormatPattern... patterns) throws ParseException {
        return toDate(formatTimeStr, getZoneId(), patterns);
    }

    public static Date toDate(String formatTimeStr, ZoneId zoneId, FastDateFormatPattern... patterns) throws ParseException {
        LocalDateTime ldt = toLocalDateTime(formatTimeStr, zoneId, patterns);
        return toDate(ldt, zoneId);
    }

    public static Date toDate(LocalDateTime ldt, ZoneId... zoneIds) {
        ZoneId zoneId = getZoneId(zoneIds);
        ZonedDateTime zonedDateTime = ldt.atZone(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }

    public static Date toDate(LocalDate ld, ZoneId... zoneIds) {
        LocalDateTime ldt = toLocalDateTime(ld, 0, 0, 0);
        return toDate(ldt, zoneIds);
    }

    public static Date toDate(LocalDate ld, int hour, int minute, int second, ZoneId... zoneIds) {
        LocalDateTime ldt = toLocalDateTime(ld, hour, minute, second);
        return toDate(ldt, zoneIds);
    }

    public static Date toDate(Long unixTimestamp, ZoneId... zoneIds) {

        return new Date(unixTimestamp);
    }

    public static LocalDate toLocalDate(String formatTimeStr, FastDateFormatPattern... patterns) throws ParseException {
        return toLocalDate(formatTimeStr, getZoneId(), patterns);
    }

    public static LocalDate toLocalDate(String formatTimeStr, ZoneId zoneId, FastDateFormatPattern... patterns) throws ParseException {
        LocalDateTime ldt = toLocalDateTime(formatTimeStr, zoneId, patterns);
        return ldt.toLocalDate();
    }

    public static LocalDate toLocalDate(Date date, ZoneId... zoneIds) {
        LocalDateTime ldt = toLocalDateTime(date, zoneIds);
        return toLocalDate(ldt);
    }

    public static LocalDate toLocalDate(LocalDateTime ldt) {
        return ldt.toLocalDate();
    }

    public static String format(LocalDate ldt, FastDateFormatPattern pattern) {
        LocalDateTime localDateTime = toLocalDateTime(ldt);
        return format(localDateTime, pattern);
    }

    public static LocalDateTime toLocalDateTime(LocalDate ld) {
        return ld.atTime(0, 0, 0);
    }

    /**
     * 根据指定的时分秒设置时分秒
     */
    public static LocalDateTime setHHmmss(LocalDateTime localDateTime, int hour, int minute, int second, int millisecond) {
        ZoneId zoneId = getZoneId();
        Calendar calendar = toCalendar(localDateTime, zoneId);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return toLocalDateTime(calendar, zoneId);
    }

    /**
     * 根据指定的年月日设置年月日
     */
    public static LocalDateTime setYYYYMMDD(LocalDateTime localDateTime, int year, int month, int day) {
        ZoneId zoneId = getZoneId();
        Calendar calendar = toCalendar(localDateTime, zoneId);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);
        return toLocalDateTime(calendar, zoneId);
    }

    /**
     * ZERO_ZERO-00:00:00<p>
     * TWENTY_THREE-23:59:59<p>
     * CURRENT-当前时间,如果为null，默认为当前的时间
     */
    public enum TimeMode {
        ZERO_ZERO,
        TWENTY_THREE,
        CURRENT;
    }

    public enum FastDateFormatPattern {

        PURE_UNIX_TIME_FORMAT("11111111111"),

        ISO_DATETIME_ZONE_FORMAT("yyyy-MM-dd'T'HH:mm:ssXXX"),
        ISO_DATETIME_FORMAT("yyyy-MM-dd'T'HH:mm:ss"),

        ISO_DATETIME_FORMAT_WITH_MILLIS("yyyy-MM-dd'T'HH:mm:ss.SSS"),
        ISO_DATETIME_NO_T_FORMAT("yyyy-MM-dd HH:mm:ss"),

        ISO_DATETIME_NO_T_FORMAT_WITH_MILLIS("yyyy-MM-dd HH:mm:ss.SSS"),

        ISO_DATETIME_ZONE_NO_T_FORMAT("yyyy-MM-dd HH:mm:ssXXX"),
        ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT("yyyy-MM-dd'T'HH:mm:ssZZ"),
        ISO_8601_EXTENDED_DATETIME_NO_T_TIME_ZONE_FORMAT("yyyy-MM-dd HH:mm:ssZZ"),
        ISO_DATE_FORMAT("yyyy-MM-dd"),

        ISO_TIME_FORMAT("'T'HH:mm:ss"),

        ISO_TIME_TIME_ZONE_FORMAT("'T'HH:mm:ssZZ"),

        ISO_TIME_NO_T_FORMAT("HH:mm:ss"),

        ISO_TIME_NO_T_TIME_ZONE_FORMAT("HH:mm:ssZZ"),

        NORM_DATETIME_MINUTE_PATTERN("yyyy-MM-dd HH:mm"),

        NORM_DATETIME_MINUTE_PATTERN2("yyyy/MM/dd HH:mm"),
        PURE_DATETIME_PATTERN("yyyyMMddHHmmss"),
        PURE_DATE_FORMAT("yyyyMMdd"),
        CN_DATE_FORMAT("yyyy年MM月dd日");


        private String formatPattern;

        FastDateFormatPattern(String formatPattern) {
            this.formatPattern = formatPattern;
        }

        public String getFormatPattern() {
            return formatPattern;
        }
    }

}
