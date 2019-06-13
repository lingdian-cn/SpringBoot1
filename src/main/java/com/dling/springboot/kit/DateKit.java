package com.dling.springboot.kit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description 日期工具类
 * @author dling
 * @date 2019-06-12 11:15:46
 * @since jdk8
 */
public class DateKit {

    public static String datePattern = "yyyy-MM-dd";
    public static String timeStampPattern = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN1 = "yyyy.MM.dd";
    public static final String DATE_PATTERN2= "yyyy/MM/dd";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DATE_TIME_WEEK_PATTERN = "yyyy-MM-dd HH:mm:ss E";
    public static final String WEEK_PATTERN = "E";
    public static final int MILLS = 0;
    public static final int SECOND = 1;
    public static final int MINUTE = 2;
    public static final int HOUR = 3;
    public static final int DAY = 4;
    public static final int MONTH = 5;
    public static final int YEAR = 6;
    public static final int WEEK = 7;


    public DateKit() {
    }

    public static void setDatePattern(String datePattern) {
        if (StringKit.isBlank(datePattern)) {
            throw new IllegalArgumentException("datePattern can not be blank");
        } else {
            datePattern = datePattern;
        }
    }

    public static void setTimeStampPattern(String timeStampPattern) {
        if (StringKit.isBlank(timeStampPattern)) {
            throw new IllegalArgumentException("timeStampPattern can not be blank");
        } else {
            timeStampPattern = timeStampPattern;
        }
    }

    /**
     * 将字符串转换为日期，字符串需符合以下格式：
     * "yyyy-MM-dd"， "HH:mm:ss"， "yyyy-MM-dd HH:mm:ss"
     * @param dateStr 字符串
     * @return Date
     */
    public static Date toDate(String dateStr) {
        if (StringKit.isBlank(dateStr)) {
            return null;
        } else {
            dateStr = dateStr.trim();
            int length = dateStr.length();

            try {
                SimpleDateFormat sdfDate;
                if (length == timeStampPattern.length()) {
                    sdfDate = new SimpleDateFormat(timeStampPattern);

                    try {
                        return sdfDate.parse(dateStr);
                    } catch (ParseException var4) {
                        dateStr = dateStr.replace(".", "-");
                        dateStr = dateStr.replace("/", "-");
                        return sdfDate.parse(dateStr);
                    }
                } else if (length == datePattern.length()) {
                    sdfDate = new SimpleDateFormat(datePattern);

                    try {
                        return sdfDate.parse(dateStr);
                    } catch (ParseException var5) {
                        dateStr = dateStr.replace(".", "-");
                        dateStr = dateStr.replace("/", "-");
                        return sdfDate.parse(dateStr);
                    }
                } else if (length == TIME_PATTERN.length()) {
                    sdfDate = new SimpleDateFormat(TIME_PATTERN);
                    try {
                        return sdfDate.parse(dateStr);
                    } catch (ParseException var5) {
                        throw new IllegalArgumentException("The date format is not supported for the time being");
                    }
                } else {
                    throw new IllegalArgumentException("The date format is not supported for the time being");
                }
            } catch (ParseException var6) {
                throw new IllegalArgumentException("The date format is not supported for the time being");
            }
        }
    }

    /**
     * 将字符串转换为指定格式的日期
     * @param dateStr 字符串
     * @return Date
     */
    public static Date toDate(String dateStr, String datePattern) {
        Date date = null;
        if (StringKit.notBlank(dateStr)) {
            dateStr = dateStr.trim();
            SimpleDateFormat sdfDate = new SimpleDateFormat(datePattern);
            try {
                date = sdfDate.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 将日期转换为"yyyy-MM-dd"格式的字符串
     * @param date 日期
     * @return String
     */
    public static String toStr(Date date) {
        return toStr(date, datePattern);
    }

    /**
     * 将日期转换为指定格式的字符串
     * @param date 日期
     * @param pattern 格式
     * @return String
     */
    public static String toStr(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 当前时间的毫秒数（纳秒）
     * @param isNano true显示纳秒；false显示毫秒
     * @return Long
     */
    public static Long current(boolean isNano){
        return isNano ? System.nanoTime() : System.currentTimeMillis();
    }

    /**
     * 当前时间的秒数
     * @return Long
     */
    public static int currentSeconds() {
        return (int)(System.currentTimeMillis() / 1000L);
    }

    /**
     * 今日字符串，格式："yyyy-MM-dd HH:mm:ss"
     * @return String
     */
    public static String today(){
        return toStr(new Date(), timeStampPattern);
    }

    /**
     * 获取指定日期的年份、月份、日等字段值
     * @param date 日期
     * @param field 字段类型 Calendar的常量值，如：Calendar.MONTH
     * @return int
     */
    public static int getFieldValue(Date date, int field){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (Calendar.MONTH == field)
            return calendar.get(field)+1;
        return calendar.get(field);
    }

    /**
     * 获取指定日期的星期
     * @param date 日期
     * @return String
     */
    public static String getWeekly(Date date){
        return toStr(date, WEEK_PATTERN);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        int value = calendar.get(Calendar.DAY_OF_WEEK);
//        switch (value) {
//            case 1:
//                return "星期日";
//            case 2:
//                return "星期一";
//            case 3:
//                return "星期二";
//            case 4:
//                return "星期三";
//            case 5:
//                return "星期四";
//            case 6:
//                return "星期五";
//            case 7:
//                return "星期六";
//            default:
//                return "-";
//        }
    }

    /**
     * 计算两个日期间的差值
     * @param start 开始日期
     * @param end 结束日期
     * @param type 差值类型，时、分、秒等，例如：DateKit.SECOND
     * @param abs 是否取绝对值
     * @return Long
     */
    public static Long dateDiff(Date start, Date end, int type, boolean abs){
        Long diff = end.getTime()-start.getTime();
        Long yearDiff = (long) getFieldValue(end, Calendar.YEAR) - (long) getFieldValue(start, Calendar.YEAR);
        Long monthDiff = (long) getFieldValue(end, Calendar.MONTH) - (long) getFieldValue(start, Calendar.MONTH);
        switch (type) {
            // 毫秒
            case MILLS:
                break;
            // 秒
            case SECOND:
                diff = diff/1000L;
                break;
            // 分
            case MINUTE:
                diff = diff/(1000L*60);
                break;
            // 时
            case HOUR:
                diff = diff/(1000L*60*60);
                break;
            // 天
            case DAY:
                diff = diff/(1000L*60*60*24);
                break;
            // 月
            case MONTH:
                diff = yearDiff*12 + monthDiff;
                break;
            // 年
            case YEAR:
                diff = yearDiff;
                break;
            default:
                return -1L;
        }
        if (abs && diff < 0L) {
            diff *= -1L;
        }
        return diff;
    }

    /**
     * 计算两个日期间的差值
     * @param startStr 开始日期字符串，支持格式： "yyyy-MM-dd"， "HH:mm:ss"， "yyyy-MM-dd HH:mm:ss"
     * @param endStr 结束日期字符串，支持格式： "yyyy-MM-dd"， "HH:mm:ss"， "yyyy-MM-dd HH:mm:ss"
     * @param type 差值类型，时、分、秒等，例如：DateKit.SECOND
     * @param abs 是否取绝对值
     * @return Long
     */
    public static Long dateDiff(String startStr, String endStr, int type, boolean abs){
        Date start = toDate(startStr);
        Date end = toDate(endStr);
        Long diff = end.getTime()-start.getTime();
        Long yearDiff = (long) getFieldValue(end, Calendar.YEAR) - (long) getFieldValue(start, Calendar.YEAR);
        Long monthDiff = (long) getFieldValue(end, Calendar.MONTH) - (long) getFieldValue(start, Calendar.MONTH);
        switch (type) {
            // 毫秒
            case MILLS:
                break;
            // 秒
            case SECOND:
                diff = diff/1000L;
                break;
            // 分
            case MINUTE:
                diff = diff/(1000L*60);
                break;
            // 时
            case HOUR:
                diff = diff/(1000L*60*60);
                break;
            // 天
            case DAY:
                diff = diff/(1000L*60*60*24);
                break;
            // 月
            case MONTH:
                diff = yearDiff*12 + monthDiff;
                break;
            // 年
            case YEAR:
                diff = yearDiff;
                break;
            default:
                return -1L;
        }
        if (abs && diff < 0L) {
            diff *= -1L;
        }
        return diff;
    }

    /**
     * 给指定日期加减指定类型的数值
     * @param date 日期
     * @param type 类型，例如：DateKit.SECOND
     * @param amount 值
     * @return
     */
    public static Date dateAdd(Date date, int type, int amount){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (type) {
            // 毫秒
            case MILLS:
                c.add(Calendar.MILLISECOND, amount);
                break;
            // 秒
            case SECOND:
                c.add(Calendar.SECOND, amount);
                break;
            // 分
            case MINUTE:
                c.add(Calendar.MINUTE, amount);
                break;
            // 时
            case HOUR:
                c.add(Calendar.HOUR_OF_DAY, amount);
                break;
            // 天
            case DAY:
                c.add(Calendar.DAY_OF_MONTH, amount);
                break;
            // 月
            case MONTH:
                c.add(Calendar.MONTH, amount);
                break;
            // 年
            case YEAR:
                c.add(Calendar.YEAR, amount);
                break;
            // 周
            case WEEK:
                c.add(Calendar.DAY_OF_WEEK, amount);
                break;
            default:
        }
        return c.getTime();
    }

    /**
     * 给指定日期加减指定类型的数值
     * @param dateStr 日期字符串，支持格式： "yyyy-MM-dd"， "HH:mm:ss"， "yyyy-MM-dd HH:mm:ss"
     * @param type 类型，例如：DateKit.SECOND
     * @param amount 值
     * @return
     */
    public static Date dateAdd(String dateStr, int type, int amount){
        Date date = toDate(dateStr);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (type) {
            // 毫秒
            case MILLS:
                c.add(Calendar.MILLISECOND, amount);
                break;
            // 秒
            case SECOND:
                c.add(Calendar.SECOND, amount);
                break;
            // 分
            case MINUTE:
                c.add(Calendar.MINUTE, amount);
                break;
            // 时
            case HOUR:
                c.add(Calendar.HOUR_OF_DAY, amount);
                break;
            // 天
            case DAY:
                c.add(Calendar.DAY_OF_MONTH, amount);
                break;
            // 月
            case MONTH:
                c.add(Calendar.MONTH, amount);
                break;
            // 年
            case YEAR:
                c.add(Calendar.YEAR, amount);
                break;
            // 周
            case WEEK:
                c.add(Calendar.DAY_OF_WEEK, amount);
                break;
            default:
        }
        return c.getTime();
    }



}
