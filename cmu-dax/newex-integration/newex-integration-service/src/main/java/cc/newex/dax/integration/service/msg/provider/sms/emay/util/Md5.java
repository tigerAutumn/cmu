package cc.newex.dax.integration.service.msg.provider.sms.emay.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5特征码工具
 *
 * @author Frank
 */
public class Md5 {

    /**
     * MD5
     *
     * @param bytes
     * @return
     */
    public static String md5(final byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String s = null;
        final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            final byte[] tmp = md.digest();
            final char[] str = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                final byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * MD5[16位]
     *
     * @param bytes
     * @return
     */
    public static String md5For16(final byte[] bytes) {
        return md5(bytes).substring(8, 24);
    }

}
