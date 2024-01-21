package cc.newex.dax.extra.common.util;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author gi
 * @date 10/22/18
 */
public class EncryptUtils {

    public static String hmacSHA256(final String secretKey, final String message) {
        final String algorithm = "HmacSHA256";
        String hash = "";
        try {
            final Mac sha256Hmac = Mac.getInstance(algorithm);
            final SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), algorithm);
            sha256Hmac.init(secretKeySpec);
            hash = Base64.encode(sha256Hmac.doFinal(message.getBytes(StandardCharsets.UTF_8)));
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return hash.trim();
    }

    public static String apacheSha256AndBase64(final String secretKey, final String message) {
        final String algorithm = "HmacSHA256";
        String hash = "";
        try {
            final Mac sha256Hmac = Mac.getInstance(algorithm);
            final SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), algorithm);
            sha256Hmac.init(secretKeySpec);
            final byte[] bytes = sha256Hmac.doFinal(message.getBytes(StandardCharsets.UTF_8));

            hash = org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return hash;
    }
}
