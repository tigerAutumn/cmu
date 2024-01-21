package cc.newex.dax.integration.service.msg.provider.sms.emay.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final long ONE_HOUR_TIME_LONG = 3600000;

    public static String toString(final Date date, final String format) {
        String dateStr = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat(format);
            dateStr = sdf.format(date);
        } catch (final Exception e) {
        }
        return dateStr;
    }

    public static Date parseDate(final String dateStr, final String format) {
        Date date = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
        } catch (final Exception e) {
        }
        return date;
    }

    /**
     * 获取日期当天的最小时间日期,0点
     */
    public static Date getMinTimeDateByDate(final Date date) {
        if (date == null) {
            return null;
        }
        final String datestr = toString(date, "yyyyMMdd");
        return parseDate(datestr, "yyyyMMdd");
    }

    /**
     * 获取日期当天的最大时间日期,12点整
     */
    public static Date getMaxTimeDateByDate(final Date date) {
        if (date == null) {
            return null;
        }
        final String datestr = toString(date, "yyyyMMdd");
        final Date d = parseDate(datestr, "yyyyMMdd");
        return new Date(d.getTime() + 24l * 60l * 60l * 1000l - 1l);
    }

    public static long subTime(final Date startDate, final Date endDate) {
        return endDate.getTime() - startDate.getTime();
    }

    /**
     * 获取上月第一天最早时间
     *
     * @return Date
     */
    public static Date getLastMonthFirstDay() {
        final Calendar cal_1 = Calendar.getInstance();// 获取当前日期
        cal_1.setTime(getMinTimeDateByDate(new Date()));
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);
        return cal_1.getTime();
    }

    /**
     * 获取上月最后一天最晚时间
     *
     * @return Date
     */
    public static Date getLastMonthLastDay() {
        final Calendar cale = Calendar.getInstance();
        cale.setTime(getMinTimeDateByDate(new Date()));
        cale.add(Calendar.MONTH, -1);
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new Date(cale.getTime().getTime() + 1000l * 60l * 60l * 24l - 1l);
    }

    /**
     * 获取本月第一天最早时间
     *
     * @return Date
     */
    public static Date getNowMonthFirstDay() {
        final Calendar cal_1 = Calendar.getInstance();// 获取当前日期
        cal_1.setTime(getMinTimeDateByDate(new Date()));
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);
        return cal_1.getTime();
    }

    /**
     * 获取本月最后一天最晚时间
     *
     * @return Date
     */
    public static Date getNowMonthLastDay() {
        final Calendar cale = Calendar.getInstance();
        cale.setTime(getMinTimeDateByDate(new Date()));
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new Date(cale.getTime().getTime() + 1000l * 60l * 60l * 24l - 1l);
    }

    /**
     * 获取本月最后一天
     *
     * @return Date
     */
    public static Date getTheMonthLastDay(final Date date) {
        if (date == null) {
            return null;
        }
        final Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
        cale.set(Calendar.HOUR, 0);
        cale.set(Calendar.HOUR_OF_DAY, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.SECOND, 0);
        cale.set(Calendar.MILLISECOND, 0);
        return cale.getTime();
    }

    public static void main(final String[] args) {
//		System.out.println(toString(getTheMonthLastDay(new Date()), "yyyy-MM-dd HH:mm:ss"));
//		System.out.println(toString(getLastMonthLastDay(), "yyyy-MM-dd HH:mm:ss"));
//		System.out.println(toString(getNowMonthFirstDay(), "yyyy-MM-dd HH:mm:ss"));
//		System.out.println(toString(getNowMonthLastDay(), "yyyy-MM-dd HH:mm:ss"));
    }

}
