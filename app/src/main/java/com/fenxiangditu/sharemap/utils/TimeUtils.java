package com.fenxiangditu.sharemap.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间处理工具类
 * <p>
 * <p>Created by Cino on 2016/1/21.
 */
public class TimeUtils {

    /**
     * 获取系统当前时间
     *
     * @return
     */
    public static String getDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sDateFormat.format(new java.util.Date());
    }

    /**
     * 获取当前时间
     *
     * @param format 格式
     * @return
     */
    public static String getDate(String format) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
        return sDateFormat.format(new java.util.Date());
    }

    public static long getHourBetweenNow(String date1) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long res = 0;
        try {
            Date d1 = sDateFormat.parse(date1);
            long time = d1.getTime();
            long currentTime = System.currentTimeMillis();
            res = time - currentTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String getUTCTime(String utcTime) {
        SimpleDateFormat utcFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC")); //时区定义并进行时间获取
        Date gpsUTCDate = null;
        Calendar cal = Calendar.getInstance();
        String res = "";
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
            cal.setTime(gpsUTCDate);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            res = month + "月" + day + "日";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String getUTCYearTime(String utcTime) {
        if (TextUtils.isEmpty(utcTime)) {
            return utcTime;
        }
        SimpleDateFormat utcFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC")); //时区定义并进行时间获取
        Date gpsUTCDate = null;
        Calendar cal = Calendar.getInstance();
        if (utcFormater == null || cal == null) {
            return utcTime;
        }
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
            cal.setTime(gpsUTCDate);
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            utcTime = year + "年" + month + "月" + day + "日";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return utcTime;
    }

    public static String getUTCYearCarTime(String utcTime) throws ParseException {
        if (TextUtils.isEmpty(utcTime)) {
            return utcTime;
        }
        String STANDARD_DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT_UTC);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date dateTime = sdf.parse(utcTime);
        date.setTime(dateTime);

        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int m = date.get(Calendar.MINUTE);
        utcTime = month + "月" + day + "日";
        if (hour < 10) {
            utcTime = utcTime + " 0" + hour;
        } else {
            utcTime = utcTime + " " + hour;
        }
        if (m < 10) {
            utcTime = utcTime + ":0" + m;
        } else {
            utcTime = utcTime + ":" + m;
        }
        return utcTime;
    }
}
