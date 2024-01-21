package cc.newex.dax.users.common.util;

import cc.newex.commons.lang.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author newex-team
 * @date 2018/4/10 下午3:45
 */
public class DateUtils extends DateUtil {
    private final static Long oneDay = 24L * 60 * 60 * 100;

    /**
     * @param date         日期时间
     * @param calendarType 日期类型如：年月日时分秒等
     * @param step         正：加时间，负：减时间
     * @description 获取分钟加减后的时间
     * @author newex-team
     * @date 2018/4/10 下午3:46
     */
    public static Date getDateByStep(final Date date, final int calendarType, final int step) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarType, step);
        return cal.getTime();
    }

    /**
     * @param date
     * @description 字符串转日期
     * @date 2018/6/25 下午1:38
     */
    public static Date getDate(final String date) {
        return new Date(DateUtil.getDateFromDateStr(date));
    }

    /**
     * 检查时间是否在24内,true:是:false:否
     *
     * @param date
     * @return
     */
    public static boolean isIn24Hours(final Date date) {
        final Date checkDate = addDays(date, 1);
        return checkDate.getTime() > System.currentTimeMillis();
    }

    /**
     * @param date
     * @description long转日期
     * @date 2018/6/25 下午1:38
     */
    public static Date getDate(final Long date) {
        return new Date(date);
    }

    /**
     * 判断目标时间相对于当前时间是否超过3分钟过期
     *
     * @param destDate 目标时间
     * @return true|false
     */
    public static boolean isExpired(final Date destDate) {
        return new Date().after(getDateByStep(destDate, Calendar.MINUTE, 3));
    }

    /**
     * 判断目标时间相对于当前时间是否过期
     *
     * @param destDate 目标时间
     * @param interval 过期间隔(单位:分钟)
     * @return true|false
     */
    public static boolean isExpired(final Date destDate, final int interval) {
        return new Date().after(getDateByStep(destDate, Calendar.MINUTE, interval));
    }

    public static void main(final String[] args) {
        System.out.println(getDate("2018-06-25 13:38:12"));
    }
}