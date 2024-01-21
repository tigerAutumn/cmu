package cc.newex.dax.boss.web.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author liutiejun
 * @date 2018-08-09
 */
public class DateUtils {

    private DateUtils() {
        super();
    }

    /**
     * 设置时、分、秒为0
     *
     * @param date
     * @return
     */
    public static Date setZeroForHMS(final Date date) {
        if (date == null) {
            return null;
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * 设置日期的某一个字段为0
     *
     * @param date
     * @param field
     * @return
     */
    public static Date setZero(final Date date, final int field) {
        if (date == null) {
            return null;
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(field, 0);

        return calendar.getTime();
    }

}
