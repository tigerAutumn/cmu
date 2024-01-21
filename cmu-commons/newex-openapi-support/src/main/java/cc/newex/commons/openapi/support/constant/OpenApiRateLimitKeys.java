package cc.newex.commons.openapi.support.constant;

/**
 * @author newex-team
 * @date 2018-04-28
 */
public class OpenApiRateLimitKeys {

    public static final String NAMESPACE = "newex:openapi:";

    /**
     * http 接口请求访问频率控制 redis 缓存记录的 key
     */
    public static final String RATE_LIMIT_KEY_PREFIX = NAMESPACE + "rate_limit:";
}
