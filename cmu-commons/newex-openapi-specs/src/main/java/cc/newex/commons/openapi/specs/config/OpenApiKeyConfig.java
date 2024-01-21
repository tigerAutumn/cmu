package cc.newex.commons.openapi.specs.config;

import cc.newex.commons.openapi.specs.enums.HmacAlgorithmEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author newex-team
 * @date 2018-06-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiKeyConfig {
    /**
     * api key 前辍用于区分api key的唯一性
     * 默认为Empty字符
     */
    private String prefix;

    /**
     * 生成api key时用的算法名称
     * 默认为MD5
     */
    private HmacAlgorithmEnum keyAlgorithm;

    /**
     * 生成api key secret时用的算法名称
     * 默认为SHA-256
     */
    private HmacAlgorithmEnum secretAlgorithm;

    /**
     * 生成api key passphrase 时用的算法名称
     * 默认为SHA-256
     */
    private HmacAlgorithmEnum passphraseAlgorithm;

    /**
     * 校验OpenAPI请求数据时用的算法名称
     * 默认为SHA-256
     */
    private HmacAlgorithmEnum validatorAlgorithm;

    /**
     * Api Key加密盐配置
     */
    private PassphraseSaltConfig passphraseSaltConfig;

    /**
     * 请求过期时间(单位秒),默认为30s
     */
    private int expiredSeconds;

    /**
     * Api Key加密盐配置类
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PassphraseSaltConfig {
        /**
         * api key对应的口令密码的加密盐算法名称
         */
        private HmacAlgorithmEnum algorithm;

        /**
         * api key 对应的口令密码的加密盐hash次数
         */
        private int hashIterations;

        /**
         * 获取口令密码的加密盐取值(从api key中）开始位置（包含）即[startIndex,endIndex)
         * 如果该值不合法（如小于0或大于最api key最大长度）则默认为Empty字符
         * 默认为4
         */
        private int startIndex;

        /**
         * 获取口令密码的加密盐取值(从api key中）结束位置（不包括）即[startIndex,endIndex)
         * 如果该值不合法（如小于0或大于最api key最大长度）则默认为Empty字符
         * 默认为14
         */
        private int endIndex;
    }
}
