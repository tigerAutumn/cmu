package cc.newex.dax.users.rest.common.limit.provider;

import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

/**
 * 谷歌验证码解绑周期时间内总请求数的限流策略
 *
 * @author newex-team
 * @date 2018-08-10
 */
@Slf4j
@Component
public class GoogleCodeUnBindRateLimitParameterProvider
        extends AbstractRateLimitParameterProvider
        implements RateLimitParameterProvider {
    /**
     * 1天10次
     */
    private final static int[] DEFAULT_LIMITS = new int[]{10, 86400};
    private final static String RATE_LIMIT_KEY_PREFIX = "newex:users:retry-limit:google-code-unbind:";

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
        log.debug("newex:users:retry-limit:google-code-unbind:{}", key);
        return RATE_LIMIT_KEY_PREFIX + key;
    }
}
