package cc.newex.dax.market.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author allen
 */
@Slf4j
public class StringUtil {

    public static Double toDouble(final String s) {
        return StringUtil.toDouble(s, null);
    }

    /***
     * 转换object类型参数
     * @param s
     * @param defaultValue
     * @return
     */
    public static Double toDouble(final Object s, final Double defaultValue) {
        if (s == null || "null".equalsIgnoreCase(s.toString().trim()) || "".equals(s.toString().trim()) || "Infinity"
                .equals(s.toString().trim()) || "NaN".equals(s.toString().trim())) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(s.toString().trim());
        } catch (final Exception e) {
            //log.error("", e);
            return defaultValue;
        }
    }

    public static Double toDouble(final String s, final Double defaultValue) {

        if (s == null || "null".equalsIgnoreCase(s.toString().trim()) || "".equals(s.trim()) || "Infinity".equals(
                s.trim()) || "NaN".equals(s.trim())) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(s.trim());
        } catch (final Exception e) {
            //log.error("", e);
            return defaultValue;
        }
    }

    public static Integer toInteger(final String s) {
        return StringUtil.toInteger(s, 0);
    }

    public static Integer toInteger(final String s, final Integer defaultValue) {
        if (s == null || "".equals(s.trim()) || !s.matches("^[-+]?[0-9]+$")) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(s.trim());
        } catch (final Exception e) {
            //log.error("", e);
            return defaultValue;
        }
    }

    public static Long toLong(final String s) {
        return StringUtil.toLong(s, null);
    }

    public static Long toLong(final String s, final Long defaultValue) {
        if (s == null || "null".equals(s) || "".equals(s.trim())) {
            return defaultValue;
        }
        try {
            return Long.parseLong(s.trim());
        } catch (final Exception e) {
            //log.error("long parse exception", e);
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
            //log.error("", e);
            return false;
        }
        return true;
    }

    public static boolean isEmpty(final String str) {
        if (str == null) {
            return true;
        }
        final String tempStr = str.trim();
        if (tempStr.length() == 0) {
            return true;
        }
        return tempStr.equals("null");
    }

    public static String replace(final String str, final String pattern, final String replace) {
        int s = 0;
        int e = 0;
        final StringBuffer result = new StringBuffer();

        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e + pattern.length();
        }
        result.append(str.substring(s));
        return result.toString();
    }


    /**
     * 字符串替换，将 source 中的 oldString 全部换成 newString
     *
     * @param source    源字符串
     * @param oldString 老的字符串
     * @param newString 新的字符串
     * @return 替换后的字符串
     */
    public static String replaceStr(final String source, final String oldString, final String newString) {
        final StringBuffer output = new StringBuffer();

        final int lengthOfSource = source.length();   // 源字符串长度
        final int lengthOfOld = oldString.length();   // 老字符串长度

        int posStart = 0;   // 开始搜索位置
        int pos;            // 搜索到老字符串的位置

        final String lower_s = source.toLowerCase();        //不区分大小写
        final String lower_o = oldString.toLowerCase();

        while ((pos = lower_s.indexOf(lower_o, posStart)) >= 0) {
            output.append(source.substring(posStart, pos));

            output.append(newString);
            posStart = pos + lengthOfOld;
        }

        if (posStart < lengthOfSource) {
            output.append(source.substring(posStart));
        }

        return output.toString();
    }


    public static long ipToLong(final String strIP) {
        try {
            if (strIP == null || strIP.length() == 0) {
                return 0L;
            }
            final long[] ip = new long[4];
            final int position1 = strIP.indexOf(".");
            final int position2 = strIP.indexOf(".", position1 + 1);
            final int position3 = strIP.indexOf(".", position2 + 1);
            ip[0] = Long.parseLong(strIP.substring(0, position1));
            ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
            ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
            ip[3] = Long.parseLong(strIP.substring(position3 + 1));
            return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
        } catch (final Exception ex) {
            return 0L;
        }
    }

    /**
     * 是否为中国手机号码<br>
     *
     * @param mobiles
     * @return
     * @date 2018/03/18
     */
    public static boolean isChineseMobile(final String mobiles) {
        if (StringUtil.isEmpty(mobiles)) {
            return false;
        }
        final String regex1 = "^1[3|4|5|7|8][0-9]{9}$";
        final Pattern p1 = Pattern.compile(regex1);
        final Matcher m1 = p1.matcher(mobiles);
        return m1.matches();
    }

    public static String doubleDecimalCny(final double value) {
        if (value == 0) {
            return "0";
        } else {
            String result = value + "";
            if (value % 1.0 == 0) {
                result = (long) value + "";
            }
            if (result.indexOf("E") > -1) {
                final NumberFormat formate = NumberFormat.getNumberInstance();
                formate.setMaximumFractionDigits(8);
                formate.setMaximumIntegerDigits(8);
                result = formate.format(value);
            }
            final int dotIndex = result.indexOf(".");
            if (dotIndex > -1) {
                final String part1 = result.substring(0, dotIndex);
                if (result.length() > part1.length() + 3) {
                    result = part1 + result.substring(dotIndex, part1.length() + 3);
                }
                return result;
            } else {
                return result;
            }
        }
    }

    public static String doubleDecimalBtc(final double value) {
        if (value == 0) {
            return "0";
        } else {
            double result = value;
            String resultStr = result + "";
            if (!StringUtil.isEmpty(resultStr) && resultStr.indexOf(".") > -1) {
                final String[] resultStrs = resultStr.split("[.]");
                if (resultStrs != null && resultStrs.length > 1 && resultStrs[1].length() > 6) {
                    result = Math.floor(value * 1000000) / 1000000.0;
                    resultStr = result + "";
                }
            }
            if (result % 1.0 == 0) {
                resultStr = (long) result + "";
            }
            if (resultStr.indexOf("E") > -1) {
                final NumberFormat formate = NumberFormat.getNumberInstance();
                formate.setMaximumFractionDigits(8);
                formate.setMaximumIntegerDigits(8);
                resultStr = formate.format(result);
            }
            return resultStr;
        }
    }

    /**
     * 带千分位format
     *
     * @param value
     * @param rate
     * @return
     */
    public static String doubleFormat(final double value, final int rate) {
        StringBuffer format = new StringBuffer("#,###,##0.");
        if (rate > 0) {
            for (int i = 0; i < rate; i++) {
                format.append("0");
            }
        }
        if (format.toString().endsWith(".")) {
            format = new StringBuffer("#,###,###");
        }
        final DecimalFormat df = new DecimalFormat(format.toString());
        df.setRoundingMode(RoundingMode.HALF_UP);//设置为四舍五入
        return df.format(value);
    }

    /**
     * 带千分位format,保留传入数据的精度
     *
     * @param value
     * @return
     */
    public static String doubleFormat(final double value) {
        final String valueStr = value + "";
        int rate = 0;
        if (!StringUtil.isEmpty(valueStr) && valueStr.indexOf(".") > -1) {
            final String[] tempStr = valueStr.split("\\.");
            if (tempStr.length > 1) {
                rate = tempStr[1].length();
            }
            if (valueStr.contains("E")) {
                final int vrate = StringUtil.toInteger(valueStr.substring(valueStr.indexOf("E") + 1)) + 3;
                rate -= vrate;
            }
        }
        return StringUtil.doubleFormat(value, rate);
    }

    public static String doubleDecimalBtc3(final double value) {
        if (value == 0) {
            return "0";
        } else {
            final double result = (Math.floor(value * 1000) / 1000.0);
            if (result % 1.0 == 0) {
                return (long) result + "";
            } else {
                return result + "";
            }
        }
    }

    /**
     * 按位赋值，返回新值<br>
     *
     * @param value 旧值
     * @param bit   指定位序号，从1开始
     * @param index 指定位的值，0或1
     * @return 新值
     * @date 2018/03/18
     */
    public static long setBinaryIndex(long value, final int bit, final int index) {
        final long dex = 1l;
        if (StringUtil.getBinaryIndex(value, bit) == 1) {
            if (index == 0) {
                value = value - (dex << bit - 1);
            }
        } else {
            if (index == 1) {
                value = value | (dex << bit - 1);
            }
        }
        return value;
    }

    public static int setBinaryIndex(int value, final int bit, final int index) {
        if (StringUtil.getBinaryIndex(value, bit) == 1) {
            if (index == 0) {
                value -= 1 << bit - 1;
            }
        } else if (index == 1) {
            value |= 1 << bit - 1;
        }

        return value;
    }

    /**
     * 按位取值，返回指定位的值<br>
     *
     * @param value
     * @param bit   指定位序号，从1开始
     * @return
     * @author Hu Haibin
     * @date 2018/03/18
     */
    public static int getBinaryIndex(long value, final int bit) {
        long remainder = 0;
        for (int i = 0; i < bit; i++) {
            final long factor = value / 2;
            remainder = value % 2;
            if (factor == 0) {
                if (i < bit - 1) {
                    remainder = 0;
                }
                break;
            }
            value = factor;
        }
        return (int) remainder;
    }


    /**
     * 按位取值，返回指定位的值<br>
     * <pre>
     * 1:登录进行谷歌安全认证
     * 2:邮箱绑定
     * 3：是否短信验证
     * 4:是否谷歌验证
     * 5:免支付密码
     * 6：免二次验证码
     * 7:付款进行邮件验证
     * </pre>
     *
     * @param value
     * @param bit   指定位序号，从1开始
     * @return
     * @date 2018/03/18
     */
    public static int getBinaryIndex(int value, final int bit) {
        int remainder = 0;
        for (int i = 0; i < bit; i++) {
            final int factor = value / 2;
            remainder = value % 2;
            if (factor == 0) {
                if (i < bit - 1) {
                    remainder = 0;
                }
                break;
            }
            value = factor;
        }
        return remainder;
    }

    public static boolean isNumber(final String number) {
        if (!StringUtil.isEmpty(number)) {
            final Pattern pattern = Pattern.compile("[0-9]*");
            return pattern.matcher(number).matches();
        }
        return false;
    }


    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(final Map<String, String> params) {

        final List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            final String key = keys.get(i);
            final String value = params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }


    /**
     * 从url中获得host
     *
     * @param url
     * @return
     */
    public static String getDomainFromUrl(final String url) {
        final String regEx = "(https?://[^/]+)/.*";
        final Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String listToString(final List<Long> list) {
        if (list != null && list.size() != 0) {
            final StringBuilder builder = new StringBuilder();
            final Iterator<Long> iter = list.iterator();

            for (int i = 0; iter.hasNext(); ++i) {
                if (i != 0) {
                    builder.append(",");
                }

                builder.append(iter.next());
            }

            return builder.toString();
        } else {
            return null;
        }
    }

    /**
     * 将字符串格式化成 HTML 代码输出
     * 只转换特殊字符，适合于 HTML 中的表单区域
     *
     * @param str 要格式化的字符串
     * @return 过滤后的字符串
     */
    public static String inputToHtml(String str) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        str = StringUtil.replaceStr(str, "<", "&lt;");
        str = StringUtil.replaceStr(str, ">", "&gt;");
        str = StringUtil.replaceStr(str, "/", "&#47;");
        str = StringUtil.replaceStr(str, "\\", "&#92;");
        str = StringUtil.replaceStr(str, "\r\n", "<br/>");
        str = StringUtil.replaceStr(str, " ", "&nbsp;");
        str = StringUtil.replaceStr(str, "'", "&acute;");
        str = StringUtil.replaceStr(str, "\"", "&quot;");
        str = StringUtil.replaceStr(str, "(", "&#40;");
        str = StringUtil.replaceStr(str, ")", "&#41;");
        //str = replaceStr(str,".","&#46;");
        return str;
    }

    /**
     * 将字符串格式化后 HTML 代码转成原始字符输出
     * 只有html编辑器中修改或输入表单中的修改需要调此函数
     *
     * @param str 格式化后的字符串
     * @return 原始的字符串
     */

    public static String outputToOrgStr(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        str = StringUtil.replaceStr(str, "&lt;", "<");
        str = StringUtil.replaceStr(str, "&gt;", ">");
        str = StringUtil.replaceStr(str, "&#47;", "/");
        str = StringUtil.replaceStr(str, "&#92;", "\\");
        str = StringUtil.replaceStr(str, "&nbsp;", " ");
        str = StringUtil.replaceStr(str, "&acute;", "'");
        str = StringUtil.replaceStr(str, "&quot;", "\"");
        //str = replaceStr(str, "&#46;",".");
        str = StringUtil.replaceStr(str, "&#40;", "(");
        str = StringUtil.replaceStr(str, "&#41;", ")");

        //	    str = replaceStr(str, "<br>","\r\n");
        //	    str = replaceStr(str, "&cedil;",".");
        //	    str = replaceStr(str, "    ","\t" );
        return str;
    }

    public static String randomStr(final int length) {
        final StringBuffer str = new StringBuffer();

        for (int i = 0; i < length; ++i) {
            final Random ran = new Random();
            final int choice = ran.nextInt(2) % 2 == 0 ? 65 : 97;
            final char x = (char) (choice + ran.nextInt(26));
            str.append(x);
        }

        return str.toString();
    }

    public static String UrlEncoder(String url) {
        try {
            if (url == null) {
                url = "";
            }

            final String tmp = URLEncoder.encode(url, "UTF-8");
            return tmp;
        } catch (final UnsupportedEncodingException var2) {
            return null;
        }
    }

    public static String doubleDecimalAll(final double value) {
        if (value == 0) {
            return "0";
        } else {
            double result = value;
            String resultStr = result + "";
            if (!StringUtil.isEmpty(resultStr) && resultStr.indexOf(".") > -1) {
                final String[] resultStrs = resultStr.split("[.]");
                if (resultStrs != null && resultStrs.length > 1 && resultStrs[1].length() > 6) {
                    result = Math.floor(value * 1000000) / 1000000.0;
                    resultStr = result + "";
                }
            }
            if (result % 1.0 == 0) {
                resultStr = (long) result + "";
            }
            final NumberFormat formate = NumberFormat.getNumberInstance();
            formate.setMaximumFractionDigits(12);
            formate.setMaximumIntegerDigits(12);
            resultStr = formate.format(result);
            return resultStr;
        }

    }
}