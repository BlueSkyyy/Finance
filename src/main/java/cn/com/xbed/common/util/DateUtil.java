package cn.com.xbed.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期工具类，提供有关日期操作方面的方法。
 *
 * @author Silen ( 蔡俊杰 ) 114603043@qq.com
 * @version 1.0
 * @data 2013-8-9 下午2:10:21
 */
public class DateUtil {

    public static final String yrMonDayHrMinSec_ = "yyyy-MM-dd HH:mm:ss";
    public static final String yrMonDayHrMin_ = "yyyy-MM-dd HH:mm";
    public static final String yrMonDay_ = "yyyy-MM-dd";

    public static final SimpleDateFormat yrMonDayHrMinSecSDF_ = new SimpleDateFormat(yrMonDayHrMinSec_);
    public static final SimpleDateFormat yrMonDayHrMinSDF_ = new SimpleDateFormat(yrMonDayHrMin_);
    public static final SimpleDateFormat yrMonDaySDF_ = new SimpleDateFormat(yrMonDay_);

    /**
     * 时间格式
     */
    public final static String TIME_FORMAT = "HH:mm:ss:SS";

    /**
     * 缺省短日期格式
     */
    public final static String DEFAULT_SHORT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * yyyy-MM-dd HH:mm:ss格式数据。
     */
    public final static String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static DateFormat ddMMyyyySS = new SimpleDateFormat(
            "yyyyMMddHHmmss");
    public static DateFormat zstr = new SimpleDateFormat(
            DEFAULT_DATE_TIME_FORMAT);

    /**
     * yyyy-MM-dd格式数据。
     */
    public final static String DATE_ONLY_FORMAT = "yyyy-MM-dd";
    /**
     * 缺省短日期格式
     */
    public final static String DEFAULT_SHORT_DATE_FORMAT_ZH = "yyyy年M月d日";

    /**
     * 缺省长日期格式
     */
    public final static String DEFAULT_LONG_DATE_FORMAT = DEFAULT_SHORT_DATE_FORMAT
            + " " + TIME_FORMAT;

    /**
     * Java能支持的最小日期字符串（yyyy-MM-dd）。
     */
    public final static String JAVA_MIN_SHORT_DATE_STR = "1970-01-01";

    /**
     * Java能支持的最小日期字符串（yyyy-MM-dd HH:mm:ss:SS）。
     */
    public final static String JAVA_MIN_LONG_DATE_STR = "1970-01-01 00:00:00:00";

    /**
     * Java能支持的最小的Timestamp。
     */
    public final static Timestamp JAVA_MIN_TIMESTAMP = convertStrToTimestamp(JAVA_MIN_LONG_DATE_STR);

    /**
     * 获取当前日期 格式:年月日---TESTED
     *
     * @return
     */
    public static Date getCurDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurDateTime());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);// 尽量设置这个,
        // 否则返回的Date类型实际上还是保存着毫秒的，例如今天是2015-09-01，用此方法获得的Date和parseDate("2015-09-01")会有所不同，后者毫秒数是0
        return cal.getTime();
    }

    /**
     * 增加或减少天数,注意入参本身是不改变的---TESTED
     *
     * @param date
     * @param amount
     *            可以传负整数
     * @return
     */
    public static Date addDays(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

    /**
     * 设置时间
     *
     * @param date
     * @return
     */
    public static Date setTime(Date date, Integer number) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, number);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当前日期时间 格式:年月日时分秒---TESTED
     *
     * @return
     */
    public static Date getCurDateTime() {
        return new Date();
    }

    /**
     * String To Date---TESTED
     *
     * @param dateStr 待转换的字符串型日期；
     * @param format  转化的日期格式
     * @return 返回该字符串的日期型数据；
     */
    public static Date stringToDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 将日期串或日期时间串转换成日期,例如2015-08-29或2015-08-29 11:46:03转成Sat Aug 29 00:00:00 CST
     * 2015---TESTED
     *
     * @param yr_mon_dayStr 传入2015-08-29或2015-08-29 11:46:40的字符串,亲测 2015-08-29 aaa 的字符串也能转
     * @return
     */
    public static Date parseDate(String yr_mon_dayStr) {
        try {
            return yrMonDaySDF_.parse(yr_mon_dayStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 将时期时间串转成日期 Sat Aug 29 12:00:14 CST 2015---TESTED
     *
     * @param yr_mon_day_day_hr_min_secStr 传入2015-08-29 11:59:13, 不能是别的格式
     * @return
     */
    public static Date parseDateTime(String yr_mon_day_day_hr_min_secStr) {
        try {
            return yrMonDayHrMinSecSDF_.parse(yr_mon_day_day_hr_min_secStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public static Date parseDateTimeNoSec(String yr_mon_day_day_hr_minStr) {
        try {
            return yrMonDayHrMinSDF_.parse(yr_mon_day_day_hr_minStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }


    /**
     * 获取当前日期的上一周星期一的日期。注意只返回yyyy-MM-dd格式的数据。
     *
     * @return
     */
    public static String getMondayDateForLastWeek() {
        Calendar cal = Calendar.getInstance();
        // n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
        int n = -1;
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.add(Calendar.DATE, n * 7);
        // 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 获取当前日期的上一周星期日的日期。注意只返回yyyy-MM-dd格式的数据。
     *
     * @return
     */
    public static String getSundayDateForLastWeek() {
        Calendar cal = Calendar.getInstance();
        // n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
        int n = -1;
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.add(Calendar.DATE, n * 7);
        // 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }


    /**
     * 取得指定日期所在周的第一天
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * 取得指定日期所在周的最后一天
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    /**
     * 获取指定日期在当年中的所在周数。
     *
     * @param dateStr 年月日 时分秒。
     */
    public static int getWeekOfYearByDate(String dateStr) {
        Calendar calendar = Calendar.getInstance();// new GregorianCalendar();
        Date date = DateUtil.convertStrToDate(dateStr,
                DateUtil.DEFAULT_DATE_TIME_FORMAT);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 指定日期的天
     */
    public static int getDayOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();// new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 把字符串转换为Timestamp类型，对于短日期格式，自动把时间设为系统当前时间。
     *
     * @return Timestamp
     * @see #convertStrToTimestamp(String, boolean)
     */
    public static Timestamp convertStrToTimestamp(String dateStr) {
        return convertStrToTimestamp(dateStr, false);
    }

    /**
     * 把字符串转换为Timestamp类型，对于短日期格式，自动把时间设为0。
     *
     * @return Timestamp
     * @see #convertStrToTimestamp(String, boolean)
     */
    public static Timestamp convertStrToTimestampZero(String dateStr) {
        return convertStrToTimestamp(dateStr, true);
    }

    /**
     * 把字符串转换为Timestamp类型。
     *
     * @param dateStr     - 日期字符串，只支持"yyyy-MM-dd"和"yyyy-MM-dd HH:mm:ss:SS"两种格式。
     *                    如果为"yyyy-MM-dd"，系统会自动取得当前时间补上。
     * @param addZeroTime - 当日期字符串为"yyyy-MM-dd"这样的格式时，addZeroTime为true表示
     *                    用0来设置HH:mm:ss:SS，否则用当前Time来设置。
     * @return Timestamp
     */
    private static Timestamp convertStrToTimestamp(String dateStr,
                                                   boolean addZeroTime) {
        if (dateStr == null) {
            return null;
        }

        String dStr = dateStr.trim();
        if (dStr.indexOf(" ") == -1) {
            if (addZeroTime) {
                dStr = dStr + " 00:00:00:00";
            } else {
                dStr = dStr + " " + getCurrDateStr(DateUtil.TIME_FORMAT);
            }
        }

        Date utilDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                DEFAULT_LONG_DATE_FORMAT);

        try {
            utilDate = simpleDateFormat.parse(dStr);
        } catch (Exception ex) {
            throw new RuntimeException("DateUtil.convertStrToTimestamp(): "
                    + ex.getMessage());
        }

        return new Timestamp(utilDate.getTime());
    }

    /**
     * 得到系统当前时间的Timestamp对象
     *
     * @return 系统当前时间的Timestamp对象
     */
    public static Timestamp getCurrTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Date getCurrDate() {
        return new Date();
    }

    public static Date getCurrDateLostTime() {
        return formateStrDate(getCurrDateStr(DATE_ONLY_FORMAT), DATE_ONLY_FORMAT);
    }

    /**
     * <p>
     * 取得当前日期，并将其转换成格式为"dateFormat"的字符串 例子：假如当前日期是 2003-09-24 9:19:10，则：
     * <p>
     * <pre>
     * getCurrDateStr("yyyyMMdd")="20030924"
     * getCurrDateStr("yyyy-MM-dd")="2003-09-24"
     * getCurrDateStr("yyyy-MM-dd HH:mm:ss")="2003-09-24 09:19:10"
     * </pre>
     * <p>
     * </p>
     *
     * @param dateFormat String 日期格式字符串
     * @return String
     */
    public static String getCurrDateStr(String dateFormat) {
        return convertDateToStr(new Date(), dateFormat);
    }

    /**
     * @param date 2013-11-07 14:14:14
     * @return 20131107141414
     */
    public static String formateDate(Date date) {
        String str1 = "";
        try {
            str1 = ddMMyyyySS.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str1;
    }

    /**
     * 把当前时间按照默认格式转成String，（默认格式yyyy-MM-dd HH:mm:ss）
     *
     * @return
     */
    public static String formateDateStr() {
        return convertDateToStr(new Date(), DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 将Date对象转成Str，（默认格式yyyy-MM-dd HH:mm:ss）
     *
     * @param date
     * @return
     */
    public static String formateDateStr(Date date) {
        return convertDateToStr(date, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 把Date对象按照指定的格式转成String
     *
     * @param date
     * @param formatStyle
     * @return
     */
    public static String formateDateStr(Date date, String formatStyle) {
        return convertDateToStr(date, formatStyle);
    }

    /**
     * 把String类型按照默认的格式转成Date类型
     *
     * @param date
     * @return
     */
    public static Date formateStrDate(String date) {
        return convertStrToDate(date, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 把String类型按照指定的格式转成Date类型
     *
     * @param date
     * @param formatStyle
     * @return
     */
    public static Date formateStrDate(String date, String formatStyle) {
        return convertStrToDate(date, formatStyle);
    }

    /**
     * 将日期类型转换成指定格式的日期字符串
     *
     * @param date       待转换的日期
     * @param dateFormat 日期格式字符串
     * @return String
     */
    public static String convertDateToStr(Date date, String dateFormat) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    /**
     * 将指定格式的字符串转换成日期类型
     *
     * @param date       待转换的日期字符串
     * @param dateFormat 日期格式字符串
     * @return Date
     */
    public static Date convertStrToDate(String dateStr, String dateFormat) {
        if (dateStr == null || dateStr.equals("")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            return sdf.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("DateUtil.convertStrToDate():" + e.getMessage());
        }
    }

    /**
     * 计算两个日期之间的相隔的年、月、日。注意：只有计算相隔天数是准确的，相隔年和月都是 近似值，按一年365天，一月30天计算，忽略闰年和闰月的差别。
     *
     * @param datepart  两位的格式字符串，yy表示年，MM表示月，dd表示日，HH表示时，mm表示分，ss表示秒
     * @param startdate 开始日期
     * @param enddate   结束日期
     * @return double 如果enddate>startdate，返回一个大于0的实数，否则返回一个小于等于0的实数
     */
    public static double dateDiff(String datepart, Date startdate, Date enddate) {
        if (datepart == null || datepart.equals("")) {
            throw new IllegalArgumentException("DateUtil.dateDiff()方法非法参数值："
                    + datepart);
        }

        double second = (double) (enddate.getTime() - startdate.getTime())
                / 1000;
        double days = (double) (enddate.getTime() - startdate.getTime())
                / (60 * 60 * 24 * 1000);

        if (datepart.equals("yy")) {
            days = days / 365;
        } else if (datepart.equals("MM")) {
            days = days / 30;
        } else if (datepart.equals("dd")) {
            return days;
        } else if (datepart.equals("HH")) {
            return second / (60 * 60);
        } else if (datepart.equals("mm")) {
            return second / 60;
        } else if (datepart.equals("ss")) {
            return second;
        } else {
            throw new IllegalArgumentException("DateUtil.dateDiff()方法非法参数值："
                    + datepart);
        }
        return days;
    }

    /**
     * 把日期对象加减年、月、日后得到新的日期对象
     *
     * @param depart 年、月、日
     * @param number 如果是 减就 -1
     *               加减因子
     * @param date   需要加减年、月、日的日期对象
     * @return Date 新的日期对象
     */
    public static Date addDate(String datepart, int number, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (datepart.equals("yy")) {
            cal.add(Calendar.YEAR, number);
        } else if (datepart.equals("MM")) {
            cal.add(Calendar.MONTH, number);
        } else if (datepart.equals("dd")) {
            cal.add(Calendar.DATE, number);
        } else if (datepart.equals("HH")) {
            cal.add(Calendar.HOUR_OF_DAY, number);
        } else if (datepart.equals("mm")) {
            cal.add(Calendar.MINUTE, number);
        } else if (datepart.equals("ss")) {
            cal.add(Calendar.SECOND, number);
        } else {
            throw new IllegalArgumentException("DateUtil.addDate()方法非法参数值：" + datepart);
        }

        return cal.getTime();
    }

    /**
     * 将普通时间 格式的字符串转化成unix时间戳
     *
     * @param dateStr
     * @param dateFormat
     * @return
     * @version 1.0
     * @author Silen ( 蔡俊杰 ) 114603043@qq.com 13422196609
     * @data 2013-8-9 上午9:50:33
     */
    public static long convertDateStrToUnixTimeStamp(String dateStr, String dateFormat) {
        long timeStamp = 0;
        if (null == dateStr || "".equals(dateStr.trim())) {
            timeStamp = DateUtil.convertStrToDate(dateStr, dateFormat).getTime() / 1000;
        }
        return timeStamp;
    }

    /**
     * 将unix时间戳转化成普通时间 格式的字符串
     *
     * @param timeStamp
     * @param dateFormat
     * @return
     * @version 1.0
     * @author Silen ( 蔡俊杰 ) 114603043@qq.com 13422196609
     * @data 2013-8-9 上午9:50:39
     */
    public static String convertUnixTimeStampToDateStr(long timeStamp, String dateFormat) {
        String dateStr = "";
        if (timeStamp != 0) {
            Long timestamp = Long.parseLong(timeStamp + "") * 1000;
            dateStr = DateUtil.convertDateToStr(new Date(timestamp), dateFormat);
        }
        return dateStr;
    }

    /**
     * 获取当前unix时间的秒数。
     *
     * @return
     * @version 1.0
     * @author Silen ( 蔡俊杰 ) 114603043@qq.com 13422196609
     * @data 2013-8-9 上午9:50:43
     */
    public static long getCurrentUnixTimeSecond() {
        return getCurrTimestamp().getTime() / 1000;
    }


    /**
     * @param str
     * @return void
     * @throws ParseException
     * @Title: formatString
     * @Description: TODO  20131010121212 这个格式转换成 yyyy-MM-dd HH:mm:ss
     * @author dao
     * @date 2013年10月31日 下午7:57:23
     */
    public static String formateStirng(String str) {
        String str1 = "";
        try {
            DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date dDate = format.parse(str);
            str1 = format2.format(dDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str1;
    }

    /**
     * 如果该日期字符串是有效的返回true
     *
     * @param date
     * @return
     */
    public static boolean isLagelDateOfString(String date) {
        Pattern p = Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}$");
        Matcher match = p.matcher(date);
        return match.matches();
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 返回指定日期的月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), 1, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), 1, 23, 59, 59);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的上个月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) - 1, 1, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的上个月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) - 1, 1, 23, 59, 59);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的下个月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, 1, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的下个月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, 1, 23, 59, 59);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 根据年份、月份获取当月第一天的date对象
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayByYearMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String args[]) {

//		System.out.println(DateUtil.getCurrDate().getHours());
//		System.out.println(DateUtil.addDate("dd", -1, DateUtil.getCurrDateLostTime()));
        System.out.println(getYearMonth(new Date()));

    }

    /**
     * 1. 计算两个日期之间有多少天<br>
     * 2. 只考虑日期,不考虑时间 <br>
     * 3. 既包括开始日期,也包括结束 <br>
     * 4. 举例: 2015-08-19 和 2015-08-18 结果是2 2015-08-19和2015-08-19结果是1 <br>
     *
     * @param endDate
     * @param beginDate
     * @return
     */
    public static long getTwoDateIntervalContainEnd(Date endDate, Date beginDate) {
        return getTwoDateInterval(endDate, beginDate);
    }

    /**
     * 1. 计算两个日期之间有多少天<br>
     * 2. 只考虑日期,不考虑时间 <br>
     * 3. 包括开始日期,不包括结束 <br>
     * 4. 举例: 2015-08-19 和 2015-08-18 结果是1 2015-08-19和2015-08-19结果是0 <br>
     *
     * @param endDate   第一个时间
     * @param beginDate 第二个时间
     * @return 天数
     */
    public static long getTwoDateInterval(Date endDate, Date beginDate) {
        endDate = trimTimeFromDate(endDate);
        beginDate = trimTimeFromDate(beginDate);
        long day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 把时间的时间部分去除, 只留取日期部分---TESTED
     *
     * @param date
     * @return
     */
    public static Date trimTimeFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);// 必须设置这个，否则毫秒数不同会导致compareDate方法在毫秒数上有差异
        return cal.getTime();
    }

    public static String getYearMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(date);
    }

    /**
     * 增加或减少秒数,注意入参本身是不改变的---TESTED
     *
     * @param date
     * @param amount 可以传负整数
     * @return
     */
    public static Date addSecounds(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, amount);
        return cal.getTime();
    }

    /**
     * 增加或减少小时数,注意入参本身是不改变的---TESTED
     *
     * @param date
     * @param amount 可以传负整数
     * @return
     */
    public static Date addHours(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, amount);// 用HOUR和HOUR_OF_DAY结果一样
        return cal.getTime();
    }

    /**
     * 获取整点
     *
     * @param amount 传递的小时
     * @return
     */
    public static Calendar getIntegral(Calendar calendar, int amount) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), amount, 0, 0);
        return calendar;
    }


    /**
     * 获得年月字符串 格式2015-08-01
     *
     * @param date
     * @return
     */
    public static String getYearMonDayStr_(Date date) {
        return yrMonDaySDF_.format(date);
    }

    /**
     * 获得年月日时分秒字符串 格式2015-11-11 19:52:35
     *
     * @param date
     * @return
     */
    public static String getYearMonDayHrMinSecStr_(Date date) {
        return yrMonDayHrMinSecSDF_.format(date);
    }
}