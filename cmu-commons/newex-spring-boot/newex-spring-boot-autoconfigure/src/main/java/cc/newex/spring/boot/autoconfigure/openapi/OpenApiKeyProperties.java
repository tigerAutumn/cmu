package cc.newex.spring.boot.autoconfigure.openapi;

import cc.newex.commons.openapi.specs.enums.HmacAlgorithmEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author newex-team
 * @date 2017/11/20
 */
@ConfigurationProperties(prefix = "newex.openapi.apikey")
public class OpenApiKeyProperties {
    private String prefix = "";
    private String keyAlgorithm = HmacAlgorithmEnum.HMAC_MD5.getName();
    private String secretAlgorithm = HmacAlgorithmEnum.HMAC_SHA_256.getName();
    private String passphraseAlgorithm = HmacAlgorithmEnum.HMAC_SHA_256.getName();
    private String validatorAlgorithm = HmacAlgorithmEnum.HMAC_SHA_256.getName();
    private PassphraseSalt passphraseSalt = new PassphraseSalt();
    private int expiredSeconds = 30;

    /**
     * 获取Api Key 前辍(用于区分Api Key的唯一性)
     */
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * 设置Api Key 前辍用于区分Api Key的唯一性
     *
     * @param prefix Api Key 前辍
     */
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    /**
     * 获取生成Api Key时用的算法名称
     *
     * @return 生成Api Key时用的算法名称
     */
    public String getKeyAlgorithm() {
        return this.keyAlgorithm;
    }

    /**
     * 设置生成Api Key时用的算法名称
     *
     * @param keyAlgorithm 生成Api Key时用的算法名称
     */
    public void setKeyAlgorithm(final String keyAlgorithm) {
        this.keyAlgorithm = keyAlgorithm;
    }

    /**
     * 获取生成Api Key secret时用的算法名称
     *
     * @return 生成Api Key secret时用的算法名称
     */
    public String getSecretAlgorithm() {
        return this.secretAlgorithm;
    }

    /**
     * 设置生成Api Key secret时用的算法名称
     *
     * @param secretAlgorithm 生成Api Key secret时用的算法名称
     */
    public void setSecretAlgorithm(final String secretAlgorithm) {
        this.secretAlgorithm = secretAlgorithm;
    }

    /**
     * 获取生成Api Key passphrase 时用的算法名称
     *
     * @return 生成Api Key passphrase 时用的算法名称
     */
    public String getPassphraseAlgorithm() {
        return this.passphraseAlgorithm;
    }

    /**
     * 设置生成Api Key passphrase 时用的算法名称
     *
     * @param passphraseAlgorithm 生成Api Key passphrase 时用的算法名称
     */
    public void setPassphraseAlgorithm(final String passphraseAlgorithm) {
        this.passphraseAlgorithm = passphraseAlgorithm;
    }

    /**
     * 获取校验OpenAPI请求数据时用的算法名称
     *
     * @return 校验OpenAPI请求数据时用的算法名称
     */
    public String getValidatorAlgorithm() {
        return this.validatorAlgorithm;
    }

    /**
     * 设置校验OpenAPI请求数据时用的算法名称
     *
     * @param validatorAlgorithm 校验OpenAPI请求数据时用的算法名称
     */
    public void setValidatorAlgorithm(final String validatorAlgorithm) {
        this.validatorAlgorithm = validatorAlgorithm;
    }

    /**
     * 获取Api Key加密盐配置
     *
     * @return Api Key加密盐配置
     */
    public PassphraseSalt getPassphraseSalt() {
        return this.passphraseSalt;
    }

    /**
     * 设置Api Key加密盐配置
     *
     * @param passphraseSalt Api Key加密盐配置
     */
    public void setPassphraseSalt(final PassphraseSalt passphraseSalt) {
        this.passphraseSalt = passphraseSalt;
    }

    /**
     * 获取请求过期时间(单位秒),默认为30s
     *
     * @return 请求过期时间(单位秒), 默认为30s
     */
    public int getExpiredSeconds() {
        return this.expiredSeconds;
    }

    /**
     * 设置请求过期时间(单位秒),默认为30s
     *
     * @param expiredSeconds 请求过期时间(单位秒),默认为30s
     */
    public void setExpiredSeconds(final int expiredSeconds) {
        this.expiredSeconds = expiredSeconds;
    }

    /**
     * Api Key加密盐配置类
     */
    public static class PassphraseSalt {
        private String algorithm = HmacAlgorithmEnum.NONE.getName();
        private int hashIterations = 0;
        private int startIndex = 4;
        private int endIndex = 14;

        /**
         * 获取Api Key对应的口令密码的加密盐算法名称
         *
         * @return Api Key对应的口令密码的加密盐算法名称
         */
        public String getAlgorithm() {
            return this.algorithm;
        }

        /**
         * 设置Api Key 对应的口令密码的加密盐(可选）
         *
         * @param algorithm Api Key对应的口令密码的加密盐算法名称
         */
        public void setAlgorithm(final String algorithm) {
            this.algorithm = algorithm;
        }

        /**
         * 获取Api Key 对应的口令密码的加密盐hash次数
         *
         * @return 对应的口令密码的加密盐hash次数
         */
        public int getHashIterations() {
            return this.hashIterations;
        }

        /**
         * 设置Api Key对应的口令密码的加密盐hash次数
         *
         * @param hashIterations 对应的口令密码的加密盐hash次数
         */
        public void setHashIterations(final int hashIterations) {
            this.hashIterations = hashIterations;
        }

        /**
         * 获取口令密码的加密盐取值(从Api Key中）开始位置（包含）即[startIndex,endIndex)
         * 如果该值不合法（如小于0或大于最Api Key最大长度）则默认为Empty字符
         * 默认为4
         *
         * @return 口令密码的加密盐取值(从Api Key中)开始位置
         */
        public int getStartIndex() {
            return this.startIndex;
        }

        /**
         * 设置口令密码的加密盐取值(从Api Key中）开始位置
         *
         * @param startIndex 口令密码的加密盐取值(从Api Key中)开始位置
         */
        public void setStartIndex(final int startIndex) {
            this.startIndex = startIndex;
        }

        /**
         * 获取口令密码的加密盐取值(从Api Key中）结束位置（不包括）即[startIndex,endIndex)
         * 如果该值不合法（如小于0或大于最Api Key最大长度）则默认为Empty字符
         * 默认为14
         *
         * @return 口令密码的加密盐取值(从Api Key中 ） 结束位置
         */
        public int getEndIndex() {
            return this.endIndex;
        }

        /**
         * 设置口令密码的加密盐取值(从Api Key中）结束位置
         *
         * @param endIndex 获取口令密码的加密盐取值(从Api Key中）结束位置
         */
        public void setEndIndex(final int endIndex) {
            this.endIndex = endIndex;
        }
    }
}
