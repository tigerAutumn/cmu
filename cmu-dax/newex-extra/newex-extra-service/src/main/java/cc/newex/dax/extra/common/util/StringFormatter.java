package cc.newex.dax.extra.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liutiejun
 * @date 2018-07-18
 */
public class StringFormatter {

    private static final String DELIM_STR = "{}";

    /**
     * 将字符串中的占位符“{}”替换为对应的参数
     *
     * @param format
     * @param arguments
     * @return
     */
    public static String format(final String format, final Object... arguments) {
        if (StringUtils.isBlank(format) || ArrayUtils.isEmpty(arguments)) {
            return format;
        }

        final StringBuilder sbuf = new StringBuilder(format.length() + 50);

        int i = 0;
        int j = -1;

        for (int k = 0; k < arguments.length; k++) {
            j = format.indexOf(DELIM_STR, i);

            if (j == -1) {
                if (i == 0) {
                    return format;
                } else {
                    sbuf.append(format, i, format.length());

                    return sbuf.toString();
                }
            } else {
                sbuf.append(format, i, j).append(arguments[k].toString());

                i = j + 2;
            }
        }

        sbuf.append(format, i, format.length());

        return sbuf.toString();
    }

}
