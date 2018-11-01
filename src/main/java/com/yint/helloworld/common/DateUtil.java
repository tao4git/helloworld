package com.yint.helloworld.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Note:
 * Create by : yintao
 * Create time: 2018/3/20 10:33
 */
public class DateUtil {

    public static final String df1 = "yyyyMMddHHmmss";
    public static final String df2 = "yyyy-MM-dd HH:mm:ss";
    public static final String df3 = "yyyy-MM-dd HH:mm:ss SSS";
    public static final String df4 = "yyyyMMdd";

    public static String longToDateStr(Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    public static String longToDateStr(Long millSec,String fomartter) {
        SimpleDateFormat sdf = new SimpleDateFormat(fomartter);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    public static String dateToStr(Date date,String fomartter) {
        SimpleDateFormat sdf = new SimpleDateFormat(fomartter);
        return sdf.format(date);
    }

    public static String getNow(String fomartter){
        SimpleDateFormat sdf = new SimpleDateFormat(fomartter);
        return sdf.format(new Date());
    }

    public static String getNow(){
        SimpleDateFormat sdf = new SimpleDateFormat(df1);
        return sdf.format(new Date());
    }
    public static String getCurDay(){
        SimpleDateFormat sdf = new SimpleDateFormat(df4);
        return sdf.format(new Date());
    }

    public static Date strToDate(String dateStr, String format) throws ParseException {
        return (new SimpleDateFormat(format)).parse(dateStr);
    }

    public static Date strToDate(String dateStr) throws ParseException {
        return strToDate(dateStr, "yyyy-MM-dd HH:mm:ss SSS");
    }

    public static long strToLong(String datetime) {
        Date date = null;
        try {
            date = strToDate(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long d = date.getTime();
        return d;
    }


    /**
     * 当前时间String
     * @param pattern
     * @return
     */
    public static String currentDate(String pattern){
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *  小时格式，如9:00,19:00
     * @param beginHour
     * @param endHour
     * @return
     */
    public static boolean belongCalendar(String beginHour, String endHour) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now =null;
        Date beginDate = null;
        Date endDate = null;
        try {
            now = df.parse(df.format(new Date()));
            beginDate = df.parse(beginHour);
            endDate = df.parse(endHour);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Boolean flag = belongCalendar(now, beginDate, endDate);
        return flag;
    }


    public static void main(String[] args) {
        String str = DateUtil.longToDateStr(System.currentTimeMillis());
        System.out.println(str);
    }
}
