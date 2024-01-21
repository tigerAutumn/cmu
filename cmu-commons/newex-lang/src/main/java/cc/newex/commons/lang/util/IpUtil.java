package cc.newex.commons.lang.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * IP 相关工具类
 *
 * @author newex-team
 */
@Slf4j
public class IpUtil {

    public static final String REAL_IP_HEADER = "X-Real-IP";

    /**
     * 十进制ip转二进制字符串表示法
     *
     * @param ip
     * @return
     */
    public static String longToString(final long ip) {
        try {
            return ipLong2DotDec(ip);
        } catch (final Exception ex) {
            log.debug("ip:{} long to string cast error", ip, ex);
        }
        return "-1";
    }

    /**
     * 二进制字符串表示法转十进制
     *
     * @param ip
     * @return
     */
    public static long toLong(final String ip) {
        try {
            return ipDotDec2Long(ip);
        } catch (final Exception ex) {
            log.debug("ip:{} string to long cast error", ip, ex);
        }
        return -1;
    }

    public static String ipLong2DotDec(final long ipLong) throws IllegalArgumentException {
        if (ipLong < 0) {
            throw new IllegalArgumentException("argument must be positive");
        } else if (ipLong > 4294967295L) {
            throw new IllegalArgumentException("argument must be smaller than 4294967295L");
        }

        final StringBuffer sb = new StringBuffer(15);
        sb.append((int) (ipLong >>> 24));
        sb.append('.');
        sb.append((int) ((ipLong & 0x00FFFFFF) >>> 16));
        sb.append('.');
        sb.append((int) ((ipLong & 0x0000FFFF) >>> 8));
        sb.append('.');
        sb.append((int) ((ipLong & 0x000000FF)));
        return sb.toString();
    }

    private static int number(final char ch) {
        final int val = ch - '0';
        if (val < 0 || val > 9) {
            throw new IllegalArgumentException();
        }
        return val;
    }

    public static int ipDotDec2Int(final String ip) {
        if (ip == null || ip.length() == 0) {
            throw new IllegalArgumentException("argument is null");
        }

        int i = 0;
        char ch = ip.charAt(i++);

        while (ch == ' ' && i < ip.length()) {
            ch = ip.charAt(i++);
        }

        int p0 = number(ch);

        ch = ip.charAt(i++);

        if (ch != '.') {
            p0 = p0 * 10 + number(ch);
            ch = ip.charAt(i++);
        }
        if (ch != '.') {
            p0 = p0 * 10 + number(ch);
            ch = ip.charAt(i++);
        }

        if (ch != '.') {
            throw new IllegalArgumentException("illegal ip : " + ip);
        }
        ch = ip.charAt(i++);

        int p1 = number(ch);
        ch = ip.charAt(i++);
        if (ch != '.') {
            p1 = p1 * 10 + number(ch);
            ch = ip.charAt(i++);
        }
        if (ch != '.') {
            p1 = p1 * 10 + number(ch);
            ch = ip.charAt(i++);
        }

        if (ch != '.') {
            throw new IllegalArgumentException("illegal ip : " + ip);
        }
        ch = ip.charAt(i++);

        int p2 = number(ch);
        ch = ip.charAt(i++);

        if (ch != '.') {
            p2 = p2 * 10 + number(ch);
            ch = ip.charAt(i++);
        }
        if (ch != '.') {
            p2 = p2 * 10 + number(ch);
            ch = ip.charAt(i++);
        }

        if (ch != '.') {
            throw new IllegalArgumentException("illegal ip : " + ip);
        }
        ch = ip.charAt(i++);

        int p3 = number(ch);
        if (i < ip.length()) {
            ch = ip.charAt(i++);
            p3 = p3 * 10 + number(ch);
        }
        if (i < ip.length()) {
            ch = ip.charAt(i++);
            p3 = p3 * 10 + number(ch);
        }

        return (p0 << 24) + (p1 << 16) + (p2 << 8) + p3;
    }

    public static long ipDotDec2Long(final String ipDotDec) throws IllegalArgumentException {
        final int intValue = ipDotDec2Int(ipDotDec);
        return ((long) intValue) & 0xFFFFFFFFL;
    }

    public static String ipHex2DotDec(String ipHex) throws IllegalArgumentException {
        if (ipHex == null) {
            throw new IllegalArgumentException("argument is null");
        }
        ipHex = ipHex.trim();
        if (ipHex.length() != 8 && ipHex.length() != 7) {
            throw new IllegalArgumentException("argument is: " + ipHex);
        }

        final long ipLong = Long.parseLong(ipHex, 16);
        return ipLong2DotDec(ipLong);
    }

    public static String ipDotDec2Hex(final String ipDotDec) throws IllegalArgumentException {
        final long ipLong = ipDotDec2Long(ipDotDec);
        String ipHex = Long.toHexString(ipLong);
        if (ipHex.length() < 8) {
            ipHex = "0" + ipHex;
        }
        return ipHex;
    }

    /**
     * 从 {@link HttpServletRequest} 对象中获；得请求端 IP 地址。
     * 先尝试从 HTTP Header: "X-Real-IP" 中获取
     * 若失败则通过  {@link HttpServletRequest#getRemoteUser()} 获取
     *
     * @param request 请求
     * @return IP 地址的字符串表示
     */
    public static String getRealIPAddress(final HttpServletRequest request) {
        final String address = request.getHeader(REAL_IP_HEADER);
        final String firstAddress = StringUtils.substringBefore(address, ",");
        if (StringUtils.isNotEmpty(firstAddress)) {
            return firstAddress;
        }
        return request.getRemoteAddr();
    }
}
