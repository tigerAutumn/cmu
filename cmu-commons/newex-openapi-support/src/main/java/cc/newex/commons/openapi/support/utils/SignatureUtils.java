package cc.newex.commons.openapi.support.utils;

import cc.newex.commons.openapi.specs.enums.HmacAlgorithmEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;


/**
 * Signature Utils.<br/>
 * Created by newex-team on 2018/2/1 11:41.
 */
@Slf4j
public class SignatureUtils {
    /**
     * Signing a Message.<br/>
     * <p>
     * using: Base64(HMAC_X)
     * </p>
     *
     * @param timestamp   the number of seconds since Unix Epoch in UTC. Decimal values are allowed. eg: 1517461697
     * @param method      eg: post
     * @param requestPath eg: "/orders"
     * @param queryString eg: "?symbol=btc-usd"
     * @param body        be valid JSON, eg: {"symbol":"btc_usd","contract_type":"this_week"}
     * @param secret      user's api secret eg: E65791902180E9EF4510DB6A77F6EBAE
     * @param alg         {@link HmacAlgorithmEnum} name
     * @return String signing a message eg: htE+Ws10dIF/7UYF2uf70342qrPko+dnYERU0yXpv/0=
     */
    public static String generate(final String timestamp, String method, final String requestPath,
                                  String queryString, String body, final String secret, final HmacAlgorithmEnum alg) {
        method = method.toUpperCase();
        body = StringUtils.defaultIfBlank(body, StringUtils.EMPTY);
        queryString = StringUtils.isEmpty(queryString) ? "" : "?" + queryString;
        final String preHash = timestamp + method + requestPath + queryString + body;
        log.debug("generate signature Algorithm:{},preHash:{}", alg.getName(), preHash);
        return encodeBase64(alg, secret, preHash);
    }

    /**
     * 按指定签名算法生成BASE64 HMAC字符串
     *
     * @param alg    签名算法
     * @param secret 签名key
     * @param data   签名字符串
     * @return BASE64签名字符串
     */
    public static String encodeBase64(final HmacAlgorithmEnum alg, final String secret, final String data) {
        Validate.notNull(alg, "SignatureAlgorithm cannot be null.");
        Validate.notNull(data, "Signing Secret cannot be null.");

        switch (alg) {
            case HMAC_MD5:
                return Base64.encodeBase64String(new HmacUtils(HmacAlgorithms.HMAC_MD5, secret).hmac(data));
            case HMAC_SHA_1:
                return Base64.encodeBase64String(new HmacUtils(HmacAlgorithms.HMAC_SHA_1, secret).hmac(data));
            case HMAC_SHA_224:
                return Base64.encodeBase64String(new HmacUtils(HmacAlgorithms.HMAC_SHA_224, secret).hmac(data));
            case HMAC_SHA_256:
                return Base64.encodeBase64String(new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secret).hmac(data));
            case HMAC_SHA_384:
                return Base64.encodeBase64String(new HmacUtils(HmacAlgorithms.HMAC_SHA_384, secret).hmac(data));
            case HMAC_SHA_512:
                return Base64.encodeBase64String(new HmacUtils(HmacAlgorithms.HMAC_SHA_512, secret).hmac(data));
            default:
                throw new IllegalArgumentException("The '" + alg.name() + "' algorithm cannot be used for signing.");
        }
    }
}
