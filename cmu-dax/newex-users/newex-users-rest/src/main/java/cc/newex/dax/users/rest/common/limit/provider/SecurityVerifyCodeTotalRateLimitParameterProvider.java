package cc.newex.dax.users.rest.common.limit.provider;

import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

/**
 * 个人中心短信与邮件验证码发送在周期时间内总请求数的限流策略
 *
 * @author newex-team
 * @date 2018-08-10
 */
@Slf4j
@Component
public class SecurityVerifyCodeTotalRateLimitParameterProvider
        extends AbstractRateLimitParameterProvider
        implements RateLimitParameterProvider {
    /**
     * 1天100次
     */
    private final static int[] DEFAULT_LIMITS = new int[]{100, 86400};
    private final static String RATE_LIMIT_KEY_PREFIX = "newex:users:retry-limit:security-verify-code:total-request:";

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
            key = String.valueOf(HttpSessionUtils.getUserId((HttpServletRequest) body));
        }
        log.debug("newex:users:retry-limit:security-verify-code:total-request:{}", key);
        return RATE_LIMIT_KEY_PREFIX + key;
    }
}
