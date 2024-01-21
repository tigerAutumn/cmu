package cc.newex.dax.extra.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 *
 * @author liutiejun
 * @date 2018-08-07
 */
public class RegexUtils {

    private RegexUtils() {
        super();
    }

    public static boolean matches(String input, String regex) {
        final Matcher matcher = Pattern.compile(regex).matcher(input);

        if (matcher.find()) {
            return true;
        }

        return false;
    }

    public static String getValue(final String input, final String regex, final int group) {
        final Matcher matcher = Pattern.compile(regex).matcher(input);

        while (matcher.find()) {
            return matcher.group(group);
        }

        return null;
    }

    public static List<String[]> getAllValue(final String input, final String regex, final int... groups) {
        if (StringUtils.isBlank(input) || StringUtils.isBlank(regex) || ArrayUtils.isEmpty(groups)) {
            return null;
        }

        final List<String[]> result = new ArrayList<>();

        final Matcher matcher = Pattern.compile(regex).matcher(input);

        while (matcher.find()) {
            final String[] values = new String[groups.length];

            for (int i = 0; i < groups.length; i++) {
                values[i] = matcher.group(groups[i]);
            }

            result.add(values);
        }

        return result;
    }

}
