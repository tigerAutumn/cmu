package cc.newex.dax.users.common.util;

import java.security.MessageDigest;

/**
 * 安全哈希算法（Secure Hash Algorithm）
 * 主要适用于数字签名标准（Digital Signature Standard DSS）里面定义的数字签名算法（Digital Signature Algorithm DSA）。
 * 对于长度小于2^64位的消息，SHA1会产生一个160位的消息摘要。当接收到消息的时候，这个消息摘要可以用来验证数据的完整性。在传输的过程中，
 * 数据很可能会发生变化，那么这时候就会产生不同的消息摘要。
 * SHA1有如下特性：不可以从消息摘要中复原信息；两个不同的消息不会产生同样的消息摘要。
 *
 * @author newex-team
 * @date 2018/5/26
 */
public class SHA1Util {

    /**
     * @param data
     * @description 生成加密字符串
     * @date 2018/5/26 下午1:43
     */
    public static String getSha1(final String data) {

        if (data == null || data.length() == 0) {
            return null;
        }
        final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            final MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(data.getBytes("UTF-8"));

            final byte[] md = mdTemp.digest();
            final int j = md.length;
            final char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                final byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (final Exception e) {
            return null;
        }
    }

    public static String getSha256(final String data) {

        if (data == null || data.length() == 0) {
            return null;
        }
        final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            final MessageDigest mdTemp = MessageDigest.getInstance("SHA256");
            mdTemp.update(data.getBytes("UTF-8"));

            final byte[] md = mdTemp.digest();
            final int j = md.length;
            final char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                final byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (final Exception e) {
            return null;
        }
    }

    public static void main(final String[] args) {
        System.out.println(getSha1("123456"));

    }

}