package cc.newex.dax.users.rest.common.limit.provider;

import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.dax.users.rest.model.VerifyCodeReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * 短信与邮件验证码发送在周期时间内总请求数的限流策略
 *
 * @author newex-team
 * @date 2018-08-10
 */
@Slf4j
@Component
public class VerifyCodeTotalRateLimitParameterProvider
        extends AbstractRateLimitParameterProvider
        implements RateLimitParameterProvider {
    /**
     * 1天20次
     */
    private final static int[] DEFAULT_LIMITS = new int[]{20, 86400};
    private final static String RATE_LIMIT_KEY_PREFIX = "newex:users:retry-limit:verify-code:total-request:";

    @Override
    public RateLimitParameter createRateLimitParameter(final String limitValue, final Type targetType, final Object body) {
        return this.getRateLimitParameter(limitValue, targetType, body, DEFAULT_LIMITS);
    }

    @Override
    protected String getRateLimitKey(final Type targetType, final Object body) {
        String key = "";

        if (body instanceof VerifyCodeReqVO) {
            final VerifyCodeReqVO form = (VerifyCodeReqVO) body;

            final String mobile = form.getMobile();
            final String email = form.getEmail();

            if (StringUtils.isNotBlank(mobile)) {
                key = "mobile:" + mobile;
            } else if (StringUtils.isNotBlank(email)) {
                key = "email:" + email;
            }
        }

        log.debug("newex:users:retry-limit:verify-code:total-request:{}", key);

        return RATE_LIMIT_KEY_PREFIX + key;
    }

}
