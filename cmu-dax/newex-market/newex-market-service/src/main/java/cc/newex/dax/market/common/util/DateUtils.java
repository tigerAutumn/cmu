package cc.newex.dax.market.common.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateUtils {
    /**
     * 年月日   yyyy-MM-dd
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateFormat dfDateTime = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final DateFormat dfDateTime2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /**
     * yyyyMMdd_HHmmss
     */
    public static final DateFormat dfDateTime4 = new SimpleDateFormat("yyyyMMdd_HHmmss");

    /**
     * 获取今天日期以前，或以后的时间
     * 如果是正数则是以后的时间，如果是负数则是以前的时间
     */
    public static Date getDateInHourAgo(final Date date, final int hourNum) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hourNum);
        return cal.getTime();
    }

    /**
     * 获 date 时间  elapsedHourValue  小时前的时间
     *
     * @param elapsedHourValue
     * @return
     */
    public static Date getSubtractedDateByElapsedHourValue(final Date date, final long elapsedHourValue) {
        final long elapsedTimeInMillis = elapsedHourValue * 60 * 60 * 1000;
        final long currentTimeInMillis = date.getTime();

        final long previousTimeInMillis = currentTimeInMillis - elapsedTimeInMillis;
        final Date previousDate = new Date(previousTimeInMillis);
        return previousDate;
    }

    /**
     * 获取当月总天数
     *
     * @param date
     * @return
     */
    public static int getActualMaximumDate(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获 date 时间  elapsedHourValue  小时前的时间
     *
     * @param elapsedHourValue
     * @return
     */
    public static Date getSubtractedDateByElapsedHourValue(final Date date, final int elapsedHourValue) {
        return DateUtils.getSubtractedDateByElapsedHourValue(date, (long) elapsedHourValue);
    }

    public static int getDifferentDaysBetweenTwoDates(final Date beforeDate, final Date afterDate) {
        final long diffMillis = afterDate.getTime() - beforeDate.getTime();

        final long diffDays = diffMillis / (24 * 60 * 60 * 1000);

        return (int) diffDays;
    }

    /**
     * String ==> java.util.Date
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date dateString2Util(final String dateStr, final String format) {
        return new SimpleDateFormat(format).parse(dateStr, new ParsePosition(0));
    }

    /**
     * java.util.Date ==> String
     *
     * @return
     */
    public static String dateUtil2String(final Date date, final String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static Date getDate() {
        final Calendar beginningOfDayCalendar = Calendar.getInstance();
        beginningOfDayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        beginningOfDayCalendar.set(Calendar.MINUTE, 0);
        beginningOfDayCalendar.set(Calendar.SECOND, 0);
        beginningOfDayCalendar.set(Calendar.MILLISECOND, 0);
        return beginningOfDayCalendar.getTime();
    }

    /**
     * date 日期加上，或减去几天
     *
     * @param date
     * @param dateNum
     * @return
     */
    public static Date getDateInDayAgo(final Date date, final int dateNum) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, dateNum);
        return cal.getTime();
    }

    /**
     * 获取 date 日期加上  或  减去几月
     *
     * @param date
     * @param monthNum
     * @return
     */
    public static Date getDateInMonthAgo(final Date date, final int monthNum) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, monthNum);
        return cal.getTime();
    }

    /**
     * date 分钟
     *
     * @param date
     * @param minute
     * @return
     */
    public static Date getDateInMinuteAgo(final Date date, final int minute) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    /**
     * 比较两个时刻
     *
     * @param timeA 格式"2012-11-11 11:11:00"
     * @return timeA>timeB是 true 否 false
     */
    public static boolean compare(final String timeA, final String timeB) {
        final Date dateA = DateUtils.dateString2Util(timeA, DateUtils.DATE_TIME_FORMAT);
        final Date dateB = DateUtils.dateString2Util(timeB, DateUtils.DATE_TIME_FORMAT);
        if (dateA == null || dateB == null) {
            return false;
        }

        return dateA.after(dateB);
    }

    /**
     * 当前时间减指定天数
     *
     * @param numberOfDate
     * @return
     */
    public static Date getSubtractedDate(int numberOfDate) {
        final Calendar calendar = Calendar.getInstance();
        final int substractedDays = numberOfDate *= -1;
        calendar.add(5, substractedDays);
        return calendar.getTime();
    }

    public static void main(final String[] args) {
        System.out.println(BigDecimal.valueOf(0.2).compareTo(BigDecimal.valueOf(0.2)));
    }
}