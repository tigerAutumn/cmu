package cc.newex.dax.market.spider.common.util.orepool;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
public class MathUtils {

    public static Float toFloat(final String s) {
        if (s == null || "".equals(s.trim())) {
            return null;
        }
        try {
            return Float.parseFloat(s.trim());
        } catch (final Exception e) {
            return null;
        }
    }

    public static Double toDouble(final String s) {
        if (s == null || "".equals(s.trim())) {
            return null;
        }
        try {
            return Double.parseDouble(s.trim().replace(",", "").replace("%", ""));
        } catch (final Exception e) {
            return null;
        }
    }

    public static Float toFloat(final String s, final Float defaultValue) {
        if (s == null || "".equals(s.trim())) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(s.trim());
        } catch (final Exception e) {
            return defaultValue;
        }
    }

    public static Double toDouble(final String s, final Double defaultValue) {

        if (s == null || "".equals(s.trim())) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(s.trim());
        } catch (final Exception e) {
            return defaultValue;
        }
    }

    public static Integer toInteger(final String s) {
        return MathUtils.toInteger(s, 0);
    }

    public static Integer toInteger(final String s, final Integer defaultValue) {
        if (s == null || "".equals(s.trim()) || !s.matches("^[-+]?[0-9]+$")) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(s.trim().replace(",", ""));
        } catch (final Exception e) {
            return defaultValue;
        }
    }

    public static Long toLong(final String s) {
        return MathUtils.toLong(s, null);
    }

    public static Long toLong(final String s, final Long defaultValue) {
        if (s == null || "".equals(s.trim())) {
            return defaultValue;
        }
        try {
            return Long.parseLong(s.trim());
        } catch (final Exception e) {
            return defaultValue;
        }
    }

    public static boolean isInt(final String id) {
        if (id == null) {
            return false;
        }
        try {
            Integer.parseInt(id.trim());
        } catch (final Exception e) {
            return false;
        }
        return true;
    }

    public static long[] getLong(final String str) {
        if (str == null || str.trim().length() == 0 || "".equals(str)) {
            return null;
        }
        final String[] strs = str.split(",");
        long[] ids = null;
        if (strs != null && strs.length > 0) {
            ids = new long[strs.length];
            for (int i = 0; i < strs.length; i++) {
                ids[i] = MathUtils.toLong(strs[i], 0L);
            }
        }
        return ids;
    }

    public static long[] getLong(final String str, final String charStr) {
        if (str == null || str.trim().length() == 0 || "".equals(str)) {
            return null;
        }
        final String[] strs = str.split(charStr);
        long[] ids = null;
        if (strs != null && strs.length > 0) {
            ids = new long[strs.length];
            for (int i = 0; i < strs.length; i++) {
                ids[i] = MathUtils.toLong(strs[i], 0L);
            }
        }
        return ids;
    }

    public static int[] getInteger(final String str, final String charStr) {
        if (str == null || str.trim().length() == 0 || "".equals(str)) {
            return null;
        }
        final String[] strs = str.split(charStr);
        int[] ids = null;
        if (strs != null && strs.length > 0) {
            ids = new int[strs.length];
            for (int i = 0; i < strs.length; i++) {
                ids[i] = MathUtils.toInteger(strs[i], 0);
            }
        }
        return ids;
    }

}
