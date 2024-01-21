package cc.newex.dax.users.rest.common.limit.provider;

import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.dax.users.rest.model.AddMobileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * 手机号绑定在周期时间内总请求数的限流策略
 *
 * @author newex-team
 * @date 2018-08-10
 */
@Slf4j
@Component
public class MobileBindRateLimitParameterProvider
        extends AbstractRateLimitParameterProvider
        implements RateLimitParameterProvider {
    /**
     * 1天10次
     */
    private final static int[] DEFAULT_LIMITS = new int[]{10, 86400};
    private final static String RATE_LIMIT_KEY_PREFIX = "newex:users:retry-limit:mobile-bind:";

    @Override
    public RateLimitParameter createRateLimitParameter(final String limitValue,
                                                       final Type targetType,
                                                       final Object body) {
        return this.getRateLimitParameter(limitValue, targetType, body, DEFAULT_LIMITS);
    }

    @Override
    protected String getRateLimitKey(final Type targetType,
                                     final Object body) {
        String key = "";
        if (body instanceof AddMobileVO) {
            key = ((AddMobileVO) body).getMobile();
        }
        log.debug("newex:users:retry-limit:mobile-bind:{}", key);
        return RATE_LIMIT_KEY_PREFIX + key;
    }
}
