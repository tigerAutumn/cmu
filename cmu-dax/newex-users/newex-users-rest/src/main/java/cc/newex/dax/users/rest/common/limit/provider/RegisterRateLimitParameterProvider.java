package cc.newex.dax.users.rest.common.limit.provider;

import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.dax.users.rest.model.ChannelEmailReqVO;
import cc.newex.dax.users.rest.model.ChannelMobileRegReqVO;
import cc.newex.dax.users.rest.model.EmailRegReqVO;
import cc.newex.dax.users.rest.model.MobileRegReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * 注册在周期时间内总请求数的限流策略
 *
 * @author newex-team
 * @date 2018-08-10
 */
@Slf4j
@Component
public class RegisterRateLimitParameterProvider
        extends AbstractRateLimitParameterProvider
        implements RateLimitParameterProvider {
    /**
     * 1天10次
     */
    private final static int[] DEFAULT_LIMITS = new int[]{10, 86400};
    private final static String RATE_LIMIT_KEY_PREFIX = "newex:users:retry-limit:register:";

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
        if (body instanceof MobileRegReqVO) {
            key = ((MobileRegReqVO) body).getMobile();
        } else if (body instanceof EmailRegReqVO) {
            key = ((EmailRegReqVO) body).getEmail();
        } else if (body instanceof ChannelMobileRegReqVO) {
            key = ((ChannelMobileRegReqVO) body).getMobile();
        } else if (body instanceof ChannelEmailReqVO) {
            key = ((ChannelEmailReqVO) body).getEmail();
        }
        log.debug("newex:users:retry-limit:register:{}", key);
        return RATE_LIMIT_KEY_PREFIX + key;
    }
}
