package cn.houlinan.mylife.utils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author jin.yu
 * @version 1.0
 * @Description:时间操作工具类<BR>
 * @TODO <BR>
 * @Title: TRS 杭州办事处互动系统（TRSAPP）<BR>
 * @ClassName: DateUtil
 * @Copyright: Copyright (c) TRS北京拓尔思信息技术股份有限公司<BR>
 * @Company: TRS北京拓尔思信息技术股份有限公司杭州办事处(www.trs.com.cn)<BR>
 * @date 2014-3-6 下午04:37:49
 */
public class DateUtil {

    public static Date date = null;

    // public static DateFormat dateFormat = null;

    public static Calendar calendar = null;

    /**
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     * @throws ParseException
     * @Description: TODO(格式化日期) <BR>
     * @author jinyu
     * @date 2014-3-6 下午04:42:09
     * @version 1.0
     */
    public static Date parseDate(String dateStr, String format)
            throws ParseException {
        Date date = null;
        if (dateStr.indexOf("CST") >= 0) {
            date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH).parse(dateStr);
        } else if (dateStr.indexOf("GMT") >= 0) {
            date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH).parse(dateStr);
        } else {
            date = new SimpleDateFormat(format).parse(dateStr);
        }
//        DateFormat dateFormat = new SimpleDateFormat(format);
//        date = dateFormat.parse(dateStr);
        return date;
    }

    /**
     * Description: 比较两个时间的大小，如果第一个参数时间大于第二个，返回true<BR>
     *
     * @param startDate
     * @param endDate
     * @return boolean
     * @author wen.junhui
     * @date 2016年3月8日 下午8:12:14
     */
    public static boolean compareTo(Date _startDate, Date _endDate) {
        long _sTemp = _startDate == null ? 0L : _startDate.getTime();
        long _eTemp = _endDate == null ? 0L : _endDate.getTime();
        return _sTemp - _eTemp > 0L;
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期：YYYY-MM-DD 格式
     * @return Date
     * @throws ParseException
     */
    public static Date parseDate(String dateStr) throws ParseException {
        return parseDate(dateStr, "yyyy-MM-dd");
    }

    /**
     * 功能描述：格式化输出日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return 返回字符型日期
     */
    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                DateFormat dateFormat = new SimpleDateFormat(format);
                result = dateFormat.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 功能描述：格式化输出日期
     *
     * @param date   Timestamp 日期
     * @param format String 格式
     * @return 返回字符型日期
     */
    public static String format(Timestamp date, String format) {
        String result = "";
        try {
            if (date != null) {
                DateFormat dateFormat = new SimpleDateFormat(format);
                result = dateFormat.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 功能描述：
     *
     * @param date Date 日期
     * @return
     */
    public static String format(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    public static String formatDateTime(Date startDate) {
        return format(startDate, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 功能描述：返回年份
     *
     * @param date Date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述：返回月份
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日份
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 功能描述：返回字符型日期
     *
     * @param date 日期
     * @return 返回字符型日期 yyyy/MM/dd 格式
     */
    public static String getDate(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 功能描述：返回字符型时间
     *
     * @param date Date 日期
     * @return 返回字符型时间 HH:mm:ss 格式
     */
    public static String getTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    /**
     * 功能描述：返回字符型日期时间
     *
     * @param date Date 日期
     * @return 返回字符型日期时间 yyyy/MM/dd HH:mm:ss 格式
     */
    public static String getDateTime(Date date) {
        return format(date, "yyyy/MM/dd HH:mm:ss");
    }

    /**
     * @return Date
     * @throws @Author：liujian 2017年12月6日 上午10:08:59
     * @Title: getPreDateOfNow
     * @Description: 获取当前日期的前一天
     */
    public static Date getPreDateOfNow() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        date = cal.getTime();
        return date;
    }

    /**
     * @return Date
     * @throws @author liujian 2018年03月06日 上午10:08:59
     * @Title: getPreDateOfNow
     * @Description: 获取当前日期的前3天的日期
     */
    public static Date getPre3DateOfNow() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -3);
        date = cal.getTime();
        return date;
    }

    /**
     * Description: 获取当前日期的前一天 <BR>
     *
     * @return
     * @author liu.jian
     * @date 2017年11月21日 下午5:38:25
     */
    public static String getPreDate() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        date = cal.getTime();
        return format(date, "yyyy.MM.dd");
    }

    /**
     * Description: 获取指定日期的前一天 <BR>
     *
     * @return
     * @author liu.jian
     * @date 2017年11月21日 下午5:38:25
     */
    public static String getPreDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        date = cal.getTime();
        return format(date, "yyyy-MM-dd");
    }

    /**
     * Description: 获取指定日期的后一天的日期串 <BR>
     *
     * @return
     * @author liu.jian
     * @date 2018年03月06日 上午10:08:59
     */
    public static String getNextDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        return format(date, "yyyy-MM-dd");
    }

    /**
     * Description: 获取指定日期的前3天的日期 <BR>
     *
     * @return
     * @author liu.jian
     * @date 2018年03月06日 上午10:08:59
     */
    public static Date getPre3Date(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -3);
        date = cal.getTime();
        return date;
    }

    /**
     * Description: 获取指定日期的后3天的日期 <BR>
     *
     * @return
     * @author liu.jian
     * @date 2018年03月06日 上午10:08:59
     */
    public static String getNext3Date(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 2);
        date = cal.getTime();
        return format(date, "yyyy-MM-dd");
    }

    /**
     * @Title: getPreMonthStr @Description: 获取指定日期的前一月 @param date
     * 指定日期 @return @return String 反回前一个月的字符串，格式：yyyy-MM @throws
     */
    public static String getPreMonthStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        date = cal.getTime();
        return format(date, "yyyy-MM");
    }

    /**
     * @Title: getPreMonthDate @Description: 获取指定日期的前一月 @param date
     * 指定日期 @return @return Date 反回前一个月的日期 @throws
     */
    public static Date getPreMonthDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        date = cal.getTime();
        return date;
    }

    /**
     * Description: 获取当前日期前一天的截止时间 <BR>
     *
     * @return
     * @author phfz
     * @date 2017年11月24日 下午4:45:38
     */
    public static Date getPreEndDate() {
        Date finalDate = new Date();
        // 根据标识，获取时间
        Calendar cal = Calendar.getInstance();
        cal.setTime(finalDate);
        cal.add(Calendar.DATE, -1);// 把日期往前减少一天
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return finalDate = cal.getTime();
    }

    /**
     * Description: 获取当前日期的前一周<BR>
     *
     * @return
     * @author phfz
     * @date 2017年11月24日 下午4:49:51
     */
    public static Date getPreWeek() {
        Calendar cal = Calendar.getInstance();
        // 时分秒置0
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    /**
     * Description: 获取指定日期的前一周 <BR>
     *
     * @param date
     * @return
     * @author phfz
     * @date 2018年3月8日 下午2:31:42
     */
    public static String getPreWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -7);
        date = cal.getTime();
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Description: 获取当前日期的前一个月 <BR>
     *
     * @return
     * @author phfz
     * @date 2017年11月24日 下午4:51:59
     */
    public static Date getPreMonth() {
        Calendar cal = Calendar.getInstance();
        // 时分秒置0
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * Description: 获取当前日期的前一年<BR>
     *
     * @return
     * @author phfz
     * @date 2017年11月24日 下午4:52:15
     */
    public static Date getPreYear() {
        Calendar cal = Calendar.getInstance();
        // 时分秒置0
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.YEAR, -1);
        return cal.getTime();
    }

    /**
     * Description:获取偏移的日期(分、秒置0,时加1) <BR>
     *
     * @param date
     * @return
     * @author phfz
     * @date 2017年11月28日 下午3:34:54
     */
    public static Date shiftDate(Date date) {
        // 根据标识，获取时间
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 1);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return date = cal.getTime();
    }

    /**
     * Description: 指定日期增加n小时 <BR>
     *
     * @param date
     * @param i
     * @return
     * @author phfz
     * @date 2017年11月28日 下午3:56:33
     */
    public static Date addDateHour(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, i);// 24小时制
        return date = cal.getTime();
    }

    /**
     * @param date
     * @param i
     * @return
     * @throws
     * @Title: addDateMinute
     * @Description: 指定日期增加n分
     * @Author：liujian 2018年9月6日 上午9:19:13
     */
    public static Date addDateMinute(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, i);
        return date = cal.getTime();
    }

    /**
     * @param date
     * @param i
     * @return
     * @throws
     * @Title: addDateSecond
     * @Description: 指定日期增加n秒
     * @Author：liujian 2018年7月25日 下午3:32:59
     */
    public static Date addDateSecond(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, i);
        return date = cal.getTime();
    }

    /**
     * Description: 判断日期格式是否符合 <BR>
     *
     * @param compareDate
     * @return
     * @author phfz
     * @date 2018年3月15日 下午3:47:23
     */
    public static boolean compareDate(String compareDate) {
        // 利用java中的SimpleDateFormat类，指定日期格式，注意yyyy,MM大小写
        // 这里的日期格式要求javaAPI中有详细的描述，不清楚的话可以下载相关API查看
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // SimpleDateFormat format=new SimpleDateFormat("yyyyMM");
        // 设置日期转化成功标识
        boolean dateflag = true;
        // 这里要捕获一下异常信息
        try {
            format.parse(compareDate);
        } catch (Exception e) {
            dateflag = false;
        }
        // 成功：true ;失败:false
        return dateflag;
    }

    /**
     * 功能描述：日期相加
     *
     * @param date Date 日期
     * @param day  int 天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        calendar = Calendar.getInstance();
        long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 功能描述：日期相减
     *
     * @param date  Date 日期
     * @param date1 Date 日期
     * @return 返回相减后的日期
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1))
                / (24 * 3600 * 1000));
    }

    /**
     * 功能描述：取得指定月份的第一天
     *
     * @param strdate String 字符型日期
     * @return String yyyy-MM-dd 格式
     * @throws ParseException
     */
    public static String getMonthBegin(String strdate) throws ParseException {
        date = parseDate(strdate);
        return format(date, "yyyy-MM") + "-01";
    }

    /**
     * 功能描述：取得指定月份的最后一天
     *
     * @param strdate String 字符型日期
     * @return String 日期字符串 yyyy-MM-dd格式
     * @throws ParseException
     */
    public static String getMonthEnd(String strdate) throws ParseException {
        date = parseDate(getMonthBegin(strdate));
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatDate(calendar.getTime());
    }

    /**
     * @param date
     * @return
     * @throws ParseException
     * @throws
     * @Title: getDayStart
     * @Description: 获取一天的开始时间
     * @Author：liujian 2018年9月6日 上午9:25:20
     */
    public static Date getDayStart(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return date = cal.getTime();
    }

    /**
     * @param date
     * @return
     * @throws ParseException
     * @throws
     * @Title: getDayEnd
     * @Description:获取一天中的结束时间
     * @Author：liujian 2018年9月6日 上午9:23:58
     */
    public static Date getDayEnd(Date date) throws ParseException {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return date = cal.getTime();
    }

    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatDate(Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    /**
     * 功能描述：以指定的格式来格式化日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return String 日期字符串
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Description: 返回系统当前时间 <BR>
     *
     * @return Date 日期对象
     * @author jin.yu
     * @date 2014-4-4 上午11:19:23
     * @version 1.0
     */
    public static Date now() {
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    /**
     * Description: 以字符串返回系统当前时间，返回格式为：yyyy-MM-dd HH:mm:ss <BR>
     *
     * @return String系统当前时间，格式：yyyy-MM-dd HH:mm:ss
     * @author jin.yu
     * @date 2014-4-4 上午11:19:18
     * @version 1.0
     */
    public static String nowFormat() {
        Date date = new Date(System.currentTimeMillis());
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Description:以字符串返回系统当前日期，返回格式为：yyyy-MM-dd<BR>
     *
     * @return
     * @author phfz
     * @date 2017年12月1日 下午3:59:07
     */
    public static String nowDate() {
        Date date = new Date();
        return format(date, "yyyy-MM-dd");
    }

    /**
     * Description: 获取一天内当前时间前i小时 <BR>
     *
     * @param i
     * @return
     * @throws ParseException
     * @author phfz
     * @date 2017年12月4日 下午2:27:25
     */
    public static String getBeforeITime(String date, int i)
            throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date now = df.parse(date);
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY,
                calendar.get(Calendar.HOUR_OF_DAY) - i);
        return df.format(calendar.getTime());
    }

    /**
     * Description:获取事件持续时间 <BR>
     *
     * @param beginDate
     * @param endDate
     * @return
     * @throws ParseException
     * @author phfz
     * @date 2017年12月4日 下午2:29:51
     */
    public static String getKeepTime(String beginDate, String endDate)
            throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = df.parse(endDate);
        Date date = df.parse(beginDate);
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String timeStr = "" + day + ";" + hour + ";" + min + ";" + s + ";";
        return timeStr.replaceAll("(\\d+)", "0$1").replaceAll("0*(\\d{2})",
                "$1");
    }

    public static Timestamp nowSqlDate() throws ParseException {

        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date du = sp.parse(nowFormat());
        Timestamp st = new Timestamp(du.getTime());
        return st;
    }

    public static Timestamp nowSqlDate(String date) throws ParseException {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date du = sp.parse(date);
        Timestamp st = new Timestamp(du.getTime());
        return st;

    }

    public static java.sql.Date nowSqlToDate(String date)
            throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd");
        Date date1 = format.parse(date);
        return new java.sql.Date((date1.getTime()));

    }

    /**
     * Description: 取得两个日期之间的日期，返回日期的集合 <BR>
     *
     * @param dBegin 开始时间
     * @param dEnd   结束时间
     * @return List 日期的集合 日期格式为：yyyy-MM-dd
     * @author jin.yu
     * @date 2014-4-11 下午03:37:02
     * @version 1.0
     */
    public static List<String> findDates(Date dBegin, Date dEnd) {
        List<String> lDate = new ArrayList<String>();
        lDate.add(formatDate(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(formatDate(calBegin.getTime()));
        }
        return lDate;
    }

    /**
     * Description:和当前日期进行比较 <BR>
     *
     * @param sDate
     * @return 比较结果
     * @throws ParseException
     * @author zhangzun
     * @date 2014-5-4 下午03:14:07
     * @version 1.0
     */
    public static int compareNow(String sDate) throws ParseException {
        Date _date = parseDate(sDate);
        Date now = now();
        return _date.compareTo(now);
    }

    /**
     * Description: Date类型转成XMLGregorianCalendar类型，用于XML类型的时间格式<BR>
     *
     * @param date
     * @return
     * @author liu.jian
     * @date 2016年3月16日 下午3:15:54
     */
    public static XMLGregorianCalendar convertToXMLGregorianCalendar(
            Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return gc;
    }

    /**
     * Description: XMLGregorianCalendar类型转成Date类型 <BR>
     *
     * @param cal
     * @return
     * @throws Exception
     * @author liu.jian
     * @date 2016年3月16日 下午3:17:25
     */
    public static Date convertToDate(XMLGregorianCalendar cal)
            throws Exception {
        GregorianCalendar ca = cal.toGregorianCalendar();
        return ca.getTime();
    }

    public static void main(String[] args) throws ParseException {
         Date d = new Date();
        // List<String> date = findDates(d,DateUtil.addDate(d, 10));
        // for (String str : date) {
        // System.out.println(str);
        // }
        // Date d = new Date();
        // System.out.println(d.toString());
        // System.out.println(formatDate(d).toString());
        // System.out.println(getMonthBegin(formatDate(d).toString()));
        // System.out.println(getMonthBegin("2008/07/19"));
        // System.out.println(getMonthEnd("2008/07/19"));
        // System.out.println(format(d,"yyyy-MM-dd"));
        // System.out.println(getDay(d));
        // System.out.println(getMonth(d));
        // System.out.println(getYear(d));
        // System.out.println(format(parseDate("2014-03-19"), "yyyy/MM/dd"));
        // System.out.println(DateUtil.now());
        // System.out.println(formatDateTime(DateUtil.now()));
        // System.out.println(getPreMonthDate(DateUtil.now()));
        // System.out.println(System.currentTimeMillis()-getMillis(parseDate("20140419","yyyyMMdd")));

        // Date currentTime = new Date();
        // SimpleDateFormat formatter = new SimpleDateFormat(
        // "yyyy-MM-dd HH:mm:ss");
        // String dateString = formatter.format(currentTime);
        // String hour;
        // hour = dateString.substring(11, 19);
        // System.out.println("hour:::" + hour);
        //
//        int _nNum = 0;
//        List<String> list = new ArrayList<String>();
//        list.add("000000");
//        list.add("000011");
//        list.add("000011");
//        list.add("000012");
//        list.add("000412");
//        int[] array = new int[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            String dateString = list.get(i);
//            _nNum = Integer.parseInt(dateString);
//            array[i] = _nNum;
//        }
        String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";
        System.out.println(getCron(d,CRON_DATE_FORMAT));
    }
    /***
     *
     * @param date 时间
     * @return  cron类型的日期
     */
    public static String getCron(final Date  date,String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

}
