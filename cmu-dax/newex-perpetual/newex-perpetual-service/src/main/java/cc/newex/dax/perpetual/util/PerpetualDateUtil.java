/**
 * @title DateUtils
 * @package cc.newex.dax.users.common.util
 * @description: newex.cc
 * @copyright: Copyright (c) 2018
 * @company:北京粒邦数字科技有限公司
 * @author newex-team
 * @date 2018/4/10 下午3:45
 */
package cc.newex.dax.perpetual.util;

import cc.newex.commons.lang.util.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author newex-team
 * @date 2018/4/10 下午3:45
 */
public class PerpetualDateUtil extends DateUtil {

    /**
     * 年月日   yyyy-MM-dd
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYYMMDD = "yyyyMMdd";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateFormat dfDateTime = new SimpleDateFormat(DATE_TIME_FORMAT);
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final DateFormat dfDateTime2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /**
     * yyyyMMdd_HHmmss
     */
    public static final DateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");



    public static long getTimeIndex(){
        SimpleDateFormat  SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return Long.parseLong(SimpleDateFormat.format(Calendar.getInstance().getTime()));
    }

    /**
     * 获取年月日时分的时间戳
     *
     * @param minute
     * @date 2018/9/26 下午4:30
     */
    public static long getTodayForMinute(final int minute) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        final long beginningOfDayInMillis = calendar.getTimeInMillis();
        final Date beginningOfDayDate = new Date(beginningOfDayInMillis);
        return beginningOfDayDate.getTime();
    }

    /**
     * @param date
     * @description 字符串转日期
     * @date 2018/6/25 下午1:38
     */
    public static Date getDate(final String date) {
        return new Date(DateUtil.getDateFromDateStr(date));
    }

//    private final static Long oneDay = 24L * 60 * 60 * 100;
//    /**
//     * @param date         日期时间
//     * @param calendarType 日期类型如：年月日时分秒等
//     * @param step         正：加时间，负：减时间
//     * @description 获取分钟加减后的时间
//     * @author newex-team
//     * @date 2018/4/10 下午3:46
//     */
//    public static Date getDateByStep(final Date date, final int calendarType, final int step) {
//        final Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(calendarType, step);
//        return cal.getTime();
//    }
//    /**
//     * 检查时间是否在24内,true:是:false:否
//     *
//     * @param date
//     * @return
//     */
//    public static boolean isIn24Hours(final Date date) {
//        final Date checkDate = addDays(date, 1);
//        return checkDate.getTime() > System.currentTimeMillis();
//    }
//
//    /**
//     * @param date
//     * @description long转日期
//     * @date 2018/6/25 下午1:38
//     */
//    public static Date getDate(final Long date) {
//        return new Date(date);
//    }
//
//    /**
//     * 判断目标时间相对于当前时间是否超过3分钟过期
//     *
//     * @param destDate 目标时间
//     * @return true|false
//     */
//    public static boolean isExpired(final Date destDate) {
//        return new Date().after(getDateByStep(destDate, Calendar.MINUTE, 3));
//    }
//
//    /**
//     * 判断目标时间相对于当前时间是否过期
//     *
//     * @param destDate 目标时间
//     * @param interval 过期间隔(单位:分钟)
//     * @return true|false
//     */
//    public static boolean isExpired(final Date destDate, final int interval) {
//        return new Date().after(getDateByStep(destDate, Calendar.MINUTE, interval));
//    }

    public static void main(final String[] args) {
        System.out.println(getDate("2018-06-25 13:38:12"));
    }
}