package cc.newex.openapi.cmx.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.management.RuntimeErrorException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * API Signature Utils
 *
 * @author cointobe-sdk-team
 * @date 2018/04/28
 */
public class SignatureUtils {
    public static final String HMAC_SHA256 = "HmacSHA256";
    public static final String CHARSET = "UTF-8";
    public static Mac MAC;

    static {
        try {
            SignatureUtils.MAC = Mac.getInstance(SignatureUtils.HMAC_SHA256);
        } catch (final NoSuchAlgorithmException var1) {
            throw new RuntimeErrorException(new Error("Can't get Mac's instance."));
        }
    }

    public static String generate(final String timestamp, String method, final String requestPath,
                                  String queryString, String body, final String secretKey)
            throws CloneNotSupportedException, InvalidKeyException, UnsupportedEncodingException {
        method = method.toUpperCase();
        body = body == null ? "" : body;
        queryString = StringUtils.isEmpty(queryString) ? "" : "?" + queryString;
        final String preHash = timestamp + method + requestPath + queryString + body;
        final byte[] secretKeyBytes = secretKey.getBytes(SignatureUtils.CHARSET);
        final SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, SignatureUtils.HMAC_SHA256);
        final Mac mac = (Mac) SignatureUtils.MAC.clone();
        mac.init(secretKeySpec);
        return Base64.getEncoder().encodeToString(mac.doFinal(preHash.getBytes(SignatureUtils.CHARSET)));
    }
}
