package cc.newex.commons.openapi.specs.util;

import cc.newex.commons.openapi.specs.config.OpenApiKeyConfig;
import cc.newex.commons.openapi.specs.enums.HmacAlgorithmEnum;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.UUID;

/**
 * @author newex-team
 * @date 2018-04-28
 */
public class OpenApiUtil {

    /**
     * 以MD5算法生成OpenApi Key并以Hex格式返回
     *
     * @return MD5 Api Key并以Hex格式返回
     */
    public static String generateKey() {
        return generateKey("");
    }

    /**
     * 以MD5算法生成OpenApi Key并以Hex格式返回
     *
     * @param prefix Api Key前辍
     * @return MD5 Api Key并以Hex格式返回
     */
    public static String generateKey(final String prefix) {
        return generateKey(HmacAlgorithmEnum.HMAC_MD5, prefix);
    }

    /**
     * 以指定算法生成OpenApi Key并以Hex格式返回
     *
     * @param alg    {@link HmacAlgorithmEnum}
     * @param prefix Api Key前辍
     * @return 指定算法生成的OpenApi Key并以Hex格式返回
     */
    public static String generateKey(final HmacAlgorithmEnum alg, final String prefix) {
        final String key = UUID.randomUUID().toString() + RandomUtils.nextInt(1000, 50000000);
        return prefix + encodeHex(alg, key);
    }

    /**
     * 以SHA-256算法生成OpenApi Secret并以Hex格式返回
     *
     * @param secret 原生Secret
     * @return SHA-256算法生成的OpenApi Secret并以Hex格式返回
     */
    public static String generateSecret(final String secret) {
        return generateSecret(HmacAlgorithmEnum.HMAC_SHA_256, secret);
    }

    /**
     * 以指定算法生成OpenApi Secret并以Hex格式返回
     *
     * @param alg    {@link HmacAlgorithmEnum}
     * @param secret 原Secret
     * @return 指定算法生成的OpenApi Key并以Hex格式返回
     */
    public static String generateSecret(final HmacAlgorithmEnum alg, final String secret) {
        return encodeHex(alg, secret);
    }

    /**
     * 根据Api Key生成对应的salt(不进行加密处理)
     *
     * @param apiKey Api Key
     * @return 生成的salt字符串
     */
    public static String generatePassphraseSalt(final String apiKey) {
        return generatePassphraseSalt(apiKey, HmacAlgorithmEnum.NONE);
    }

    /**
     * 根据Api Key生成对应的salt
     *
     * @param apiKey Api Key
     * @param alg    加密算法
     * @return 生成的salt字符串
     */
    public static String generatePassphraseSalt(final String apiKey, final HmacAlgorithmEnum alg) {
        return generatePassphraseSalt(apiKey, alg, 4, 14);
    }

    /**
     * 根据Api Key生成对应的salt
     *
     * @param apiKey Api Key
     * @param start  截取Api Key开始字符(包括)的index
     * @param end    截取Api Key结束字符(不包括)的index
     * @return 生成的salt字符串
     */
    public static String generatePassphraseSalt(final String apiKey, final int start, final int end) {
        return generatePassphraseSalt(apiKey, HmacAlgorithmEnum.NONE, start, end);
    }

    /**
     * 根据Api Key生成对应的salt
     *
     * @param apiKey Api Key
     * @param alg    加密算法
     * @param start  截取Api Key开始字符(包括)的index
     * @param end    截取Api Key结束字符(不包括)的index
     * @return 生成的salt字符串
     */
    public static String generatePassphraseSalt(final String apiKey, final HmacAlgorithmEnum alg, final int start, final int end) {
        return generatePassphraseSalt(apiKey, alg, start, end, 0);
    }

    /**
     * 根据Api Key生成对应的salt
     *
     * @param apiKey     Api Key字符串
     * @param alg        加密算法
     * @param start      截取Api Key开始字符(包括)的index
     * @param end        截取Api Key结束字符(不包括)的index
     * @param iterations 对应的口令密码的加密盐hash次数(最多不超过5次）
     * @return 生成的salt字符串
     */
    public static String generatePassphraseSalt(final String apiKey, final HmacAlgorithmEnum alg, final int start, final int end, final int iterations) {
        return generatePassphraseSalt(apiKey,
                OpenApiKeyConfig.PassphraseSaltConfig.builder()
                        .algorithm(alg)
                        .startIndex(start)
                        .endIndex(end)
                        .hashIterations(iterations)
                        .build()
        );
    }

    /**
     * 根据Api Key生成对应的salt
     *
     * @param apiKey Api Key字符串
     * @param config Api Key加密盐配置类
     * @return 生成的salt字符串
     */
    public static String generatePassphraseSalt(final String apiKey, final OpenApiKeyConfig.PassphraseSaltConfig config) {
        final int times = Math.min(config.getHashIterations(), 5);
        String salt = StringUtils.substring(apiKey, config.getStartIndex(), config.getEndIndex());
        for (int i = 0; i < times; ++i) {
            salt = encodeHex(config.getAlgorithm(), salt);
        }
        return salt;
    }

    /**
     * 以SHA-256算法给OpenAPI的 passphrase加salt并以Hex格式返回
     *
     * @param apiKey        open Api Key
     * @param rawPassphrase open api 密码
     * @return {@link DigestUtils#sha256Hex(String)}
     */
    public static String encodePassphrase(final String apiKey, final String rawPassphrase) {
        return encodePassphrase(HmacAlgorithmEnum.HMAC_SHA_256, rawPassphrase, generatePassphraseSalt(apiKey));
    }

    /**
     * 以SHA-256算法给OpenAPI的 passphrase加salt,其中salt值为apikey截取部分字符串,并以Hex格式返回
     *
     * @param apiKey        open Api Key
     * @param rawPassphrase open api 密码
     * @param start         apiKey截取部分字符串开始位置
     * @param end           apiKey截取部分字符串结果位置(不包含)
     * @return {@link DigestUtils#sha256Hex(String)}
     */
    public static String encodePassphrase(final String apiKey, final String rawPassphrase, final int start, final int end) {
        return encodePassphrase(HmacAlgorithmEnum.HMAC_SHA_256, rawPassphrase, generatePassphraseSalt(apiKey, start, end));
    }

    /**
     * 以指定算法给OpenAPI的 passphrase加salt,并以Hex格式返回
     *
     * @param alg           加密算法
     * @param rawPassphrase 原passphrase
     * @param salt          加密盐
     * @return 加密后的Hex字符串
     */
    public static String encodePassphrase(final HmacAlgorithmEnum alg, final String rawPassphrase, final String salt) {
        return encodeHex(alg, StringUtils.trim(rawPassphrase) + salt);
    }

    /**
     * 按指定算法生成加密后的Hex字符串
     *
     * @param alg  加密算法
     * @param data 加密字符串
     * @return 加密后的Hex字符串
     */
    public static String encodeHex(final HmacAlgorithmEnum alg, final String data) {
        Validate.notNull(alg, "SignatureAlgorithm cannot be null.");
        Validate.notNull(data, "Signing data cannot be null.");

        switch (alg) {
            case NONE:
                return data;
            case HMAC_MD5:
                return DigestUtils.md5Hex(data);
            case HMAC_SHA_1:
                return DigestUtils.sha1Hex(data);
            case HMAC_SHA_256:
                return DigestUtils.sha256Hex(data);
            case HMAC_SHA_384:
                return DigestUtils.sha384Hex(data);
            case HMAC_SHA_512:
                return DigestUtils.sha512Hex(data);
            default:
                throw new IllegalArgumentException("The '" + alg.name() + "' algorithm cannot be used for signing.");
        }
    }
}
