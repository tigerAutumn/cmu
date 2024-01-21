package cc.newex.dax.boss.web.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author liutiejun
 * @date 2018-07-27
 */
@Slf4j
public class DateFormater {

    public static Date parse(final String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        return parse(dateStr, null);
    }

    public static Date parse(final String dateStr, String pattern) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }

        try {
            final Date date = DateUtils.parseDate(dateStr, pattern);

            return date;
        } catch (final ParseException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    public static Date parseNoSecond(final String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        final String pattern = "yyyy-MM-dd HH:mm";

        return parse(dateStr, pattern);
    }

    public static String format(final Date date) {
        if (date == null) {
            return null;
        }

        return format(date, null);
    }

    public static String format(final Date date, String pattern) {
        if (date == null) {
            return null;
        }

        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }

        final String dateStr = DateFormatUtils.format(date, pattern);

        return dateStr;
    }

    public static String formatNoSecond(final Date date) {
        if (date == null) {
            return null;
        }

        final String pattern = "yyyy-MM-dd HH:mm";

        return format(date, pattern);
    }

}
