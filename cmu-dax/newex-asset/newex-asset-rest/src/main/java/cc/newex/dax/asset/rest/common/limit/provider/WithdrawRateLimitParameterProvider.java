package cc.newex.dax.asset.rest.common.limit.provider;

import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

/**
 * 注册在周期时间内总请求数的限流策略
 *
 * @author newex-team
 * @date 2018-08-10
 */
@Slf4j
@Component
public class WithdrawRateLimitParameterProvider
        extends AbstractRateLimitParameterProvider
        implements RateLimitParameterProvider {
    /**
     * 1天5次
     */
    private final static int[] DEFAULT_LIMITS = new int[]{5, 86400};
    private final static String RATE_LIMIT_KEY_PREFIX = "newex:asset:retry-limit:withdraw:";

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
        if (body instanceof HttpServletRequest) {
            final JwtUserDetails jwtUserDetails = JwtTokenUtils.getCurrentLoginUser((HttpServletRequest) body);
            if (jwtUserDetails != null) {
                key = String.valueOf(jwtUserDetails.getUserId());
            }
        }
        log.debug("newex:users:retry-limit:withdraw:{}", key);
        return RATE_LIMIT_KEY_PREFIX + key;
    }
}
