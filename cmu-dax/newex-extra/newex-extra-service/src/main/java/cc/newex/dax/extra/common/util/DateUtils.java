package cc.newex.dax.extra.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author liutiejun
 * @date 2018-08-15
 */
public class DateUtils {

    /**
     * 使用给定的年、月、日、时、分、秒等数据生成对应的日期
     *
     * @param year
     * @param month
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date getADate(final int year, final int month, final int date, final int hour, final int minute, final int second) {
        final Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, date, hour, minute, second);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

}
