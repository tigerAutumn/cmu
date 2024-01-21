package cc.newex.dax.users.rest.common.limit.provider;

import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.dax.users.rest.model.ResetPwdReqVO;
import cc.newex.dax.users.service.cache.AppCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * 修改密码在周期时间内总请求数的限流策略
 *
 * @author newex-team
 * @date 2018-08-10
 */
@Slf4j
@Component
public class ForgetPwdRateLimitParameterProvider
        extends AbstractRateLimitParameterProvider
        implements RateLimitParameterProvider {
    @Autowired
    private AppCacheService appCacheService;
    /**
     * 1天10次
     */
    private final static int[] DEFAULT_LIMITS = new int[]{10, 86400};
    private final static String RATE_LIMIT_KEY_PREFIX = "newex:users:retry-limit:forget-pwd:";

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
        if (body instanceof ResetPwdReqVO) {
            final String loginName = ((ResetPwdReqVO) body).getLoginName();
            key = this.appCacheService.getResetPwdLoginName(loginName);
        }
        log.debug("newex:users:retry-limit:forget-pwd:{}", key);
        return RATE_LIMIT_KEY_PREFIX + key;
    }
}
