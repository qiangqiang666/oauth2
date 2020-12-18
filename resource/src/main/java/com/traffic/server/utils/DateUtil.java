package com.traffic.server.utils;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/28.
 */
public class DateUtil {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    // 获得当前月--开始日期
    public static String getMinMonthDate(Date date) {
        Calendar calendar = Calendar.getInstance();

            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            return dateFormat.format(calendar.getTime());
    }

    // 获得当前月--结束日期
    public static String getMaxMonthDate(Date date){
        Calendar calendar = Calendar.getInstance();

            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            return dateFormat.format(calendar.getTime());

    }

    /**
     * @Description: 格式化时间 yyyy-MM-dd HH:mm:ss
     * @Param:
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static String dateToString(Date time) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ctime = formatter.format(time);

        return ctime;
    }
    public static String addHour(Integer hour) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, hour);
        Date date = calendar.getTime();
        String ordErexpireDatetime = sdf.format(date);
        return ordErexpireDatetime;
    }

    /**
     * @Description: 格式化时间 yyyy-MM-dd
     * @Param:
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static String dateToDayString(Date time) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("YYYY-MM-dd");
        String ctime = formatter.format(time);

        return ctime;
    }

    /**
     * @Description: 格式化时间 yyyy-MM-dd
     * @Param:
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static Date stringToDay(String time){
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("YYYY-MM-dd");
        Date ctime = null;
        try {
            ctime = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ctime;
    }


    /**
     * @Description: 取系统当前时间 yyyy-MM-dd HH:mm:ss
     * @Param:
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static String Now() {
        return dateToString(new Date());
    }


    /**
     * @Description: 取系统当前时间 yyyy-MM-dd
     * @Param:
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static String getYYYY_MM_DD() {
        return dateToString(new Date()).substring(0, 10);

    }

    /**
     * @Description: 取系统给定时间 yyyy-MM-dd
     * @Param:
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static String getYYYY_MM_DD(String date) {
        return date.substring(0, 10);

    }

    public static String getMin() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("M");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    public static String getHour() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("H");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    public static String getDay() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("d");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    public static String getMonth() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("M");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    public static String getYear() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    public static String getWeek() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("E");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    /**
     * @Description: 格式化时间
     * @Param:
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static final String getFormatTime(String str, String srcFormat, String targetFormat) {
        if (isNullStr(str)) {
            return "";
        }
        Date date = null;
        String retString = str;
        try {
            SimpleDateFormat srcFormatter = new SimpleDateFormat(srcFormat);
            SimpleDateFormat targetFormatter = new SimpleDateFormat(targetFormat);
            date = srcFormatter.parse(str);
            retString = targetFormatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retString;
    }

    /**
     * @Description: 空值检查
     * @Param:
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static boolean isNullStr(String str) {
        boolean flag = false;
        if (str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str) || "undefined".equalsIgnoreCase(str)) {
            flag = true;
        }
        return flag;
    }

    /**
     * @Description: 获取给定时间当前周内第一天日期 yyyy-MM-dd
     * @Param:
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static String getWeekStartDate(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param dateFormat 格式
     * @param startTime  起止时间
     * @param endTime    结束时间
     * @Description: 计算天数差
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static int getDayTimeDifference(String dateFormat, String startTime, String endTime) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateFormat);
        Long from = simpleFormat.parse(startTime).getTime();
        Long to = simpleFormat.parse(endTime).getTime();
        int days = (int) ((to - from) / (1000 * 60 * 60 * 24));
        return days;
    }

    /**
     * @param dateFormat 格式
     * @param startTime  起止时间
     * @param endTime    结束时间
     * @Description: 计算小时差
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static int getHourTimeDifference(String dateFormat, String startTime, String endTime) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateFormat);
        Long from = simpleFormat.parse(startTime).getTime();
        Long to = simpleFormat.parse(endTime).getTime();
        int hours = (int) ((to - from) / (1000 * 60 * 60));
        return hours;
    }

    /**
     * @param dateFormat 格式
     * @param startTime  起止时间
     * @param endTime    结束时间
     * @Description: 计算分钟差
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static int getMinuteTimeDifference(String dateFormat, String startTime, String endTime) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateFormat);
        Long from = simpleFormat.parse(startTime).getTime();
        Long to = simpleFormat.parse(endTime).getTime();
        int minutes = (int) ((to - from) / (1000 * 60));
        return minutes;
    }

    /**
     * @Description: 获取多少天之前或者之后的时间日期
     * @Param:
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static Date dayCalculate(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, num);
        Date d = c.getTime();
        return d;
    }

    /**
     * @Description: 获取多少月之前或者之后的时间日期
     * @Param:
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static Date monthCalculate(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, num);
        Date d = c.getTime();
        return d;
    }

    /**
     * @Description: 获取多少年之前或者之后的时间日期
     * @Param:
     * @return:
     * @Author: bite
     * @Date: 2019/6/7
     */
    public static Date yearCalculate(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, num);
        Date d = c.getTime();
        return d;
    }


    public static Date todayStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date min(Date date1, Date date2) {
        return date1.compareTo(date2) > 0 ? date2 : date1;
    }

    public static Date max(Date date1, Date date2) {
        return date1.compareTo(date2) > 0 ? date1 : date2;
    }

    public static int days(Date date1, Date date2) {
        long ts = Math.abs(date1.getTime() - date2.getTime());
        return (int) (ts / 1000 / 3600 / 24);
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
}
