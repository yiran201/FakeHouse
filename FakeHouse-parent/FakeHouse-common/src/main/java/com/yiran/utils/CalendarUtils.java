package com.yiran.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * yiran
 */
public class CalendarUtils {


    /**
     * 获取本月前的12个月 不包括当前月份
     * @param splitStr 分隔符,默认为 -
     * @return List  ["2022-1", "2022-2", ...]
     */
    public static List<String> getPreMonthWihtoutCurrent(String splitStr) throws Exception {

        List<String> months = new ArrayList<>();
        // 获取日历对象,默认当前时间
        Calendar calendar = Calendar.getInstance();
        // 推断时间, 往前12个月
        calendar.add(Calendar.MONTH, -13);

        SimpleDateFormat format = new SimpleDateFormat("yyyy"+splitStr+"MM");

        // 推断每一个月份并格式化输出
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            Date date = calendar.getTime();
            String date_str = format.format(date);
            months.add(date_str);
//            System.out.println(date_str);
        }
        return months;
    }


    /**
     * 获取本月前的12个月 不包括当前月份
     * @return List  ["2022-1", "2022-2", ...]
     */
    public static List<String> getPreMonthWihtoutCurrent() throws Exception {
        return getPreMonthWihtoutCurrent("-");
    }






    /**
     * 获取本月前的12个月 包括当前月份
     * @param splitStr 分隔符,默认为 -
     * @return List  ["2022-1", "2022-2", ...]
     */
    public static List<String> getPreMonth(String splitStr) throws Exception {

        List<String> months = new ArrayList<>();

        // 获取日历对象,默认当前时间
        Calendar calendar = Calendar.getInstance();
        // 推断时间, 往前12个月
        calendar.add(Calendar.MONTH, -12);

        SimpleDateFormat format = new SimpleDateFormat("yyyy"+ splitStr +"MM");
        // 推断每一个月份并格式化输出
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            Date date = calendar.getTime();
            String date_str = format.format(date);
            months.add(date_str);
//            System.out.println(date_str);
        }
        return months;
    }

    /**
     * 获取本月前的12个月 包括当前月份
     * @return List  ["2022-1", "2022-2", ...]
     */
    public static List<String> getPreMonth() throws Exception {
        return getPreMonth("-");
    }


    /**
     * 获取今日的Date时间对象
     * @return 今日日期
     * @throws Exception
     */
    public static Date getTodayDate() throws Exception
    {
        return Calendar.getInstance().getTime();
    }


    /**
     * 获取本周的第一天
     * @return 本周第一天的Date对象
     */
    public static Date getFirstDayOfThisWeek() throws Exception
    {
        Calendar calendar = Calendar.getInstance();

        // 获取今天在本周的位置  从星期日开始为 1
        int count = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, -count+2);

        return calendar.getTime();
    }


    /**
     * 获取本月的第一天
     * @return 本月的第一天
     */
    public static Date getFirstDayOfThisMonth()
    {
        Calendar calendar = Calendar.getInstance();

        int count = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.DATE, -count+1);

        return calendar.getTime();
    }




}
