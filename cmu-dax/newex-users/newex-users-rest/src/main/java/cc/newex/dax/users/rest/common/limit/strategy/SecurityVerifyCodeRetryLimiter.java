package cc.newex.dax.users.rest.common.limit.strategy;

import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.commons.ratelimiter.RateLimiter;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import cc.newex.dax.users.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.users.rest.common.limit.exception.RetryLimitException;
import cc.newex.dax.users.rest.common.limit.provider.SecurityVerifyCodeOnceRateLimitParameterProvider;
import cc.newex.dax.users.rest.common.limit.provider.SecurityVerifyCodeTotalRateLimitParameterProvider;
import cc.newex.dax.users.rest.model.VerifyCodeReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 短信与邮件验证码发送在周期时间内只请求一次的限流策略
 *
 * @author newex-team
 * @date 2018-08-10
 */
@Slf4j
@Component
public class SecurityVerifyCodeRetryLimiter implements RetryLimiter {
    @Autowired
    private SecurityVerifyCodeOnceRateLimitParameterProvider onceRateLimitParameterProvider;
    @Autowired
    private SecurityVerifyCodeTotalRateLimitParameterProvider totalRateLimitParameterProvider;
    @Autowired
    private RateLimiter rateLimiter;

    @Override
    public Object execute(final ProceedingJoinPoint point, final RetryLimit retryLimit) throws Throwable {
        final Object body = point.getArgs()[1];
        if (this.isNotRateLimited(body)) {
            return point.proceed();
        }
        final RateLimitParameter onceRateLimitParameter = this.onceRateLimitParameterProvider.createRateLimitParameter(retryLimit.value(), null, body);
        final RateLimitParameter totalRateLimitParameter = this.totalRateLimitParameterProvider.createRateLimitParameter(retryLimit.value(), null, body);
        this.modifyRateLimitParameterKey(onceRateLimitParameter, point.getArgs()[0]);
        this.modifyRateLimitParameterKey(totalRateLimitParameter, point.getArgs()[0]);

        //如果两种限流策略都通过
        if (this.rateLimiter.canAcquire(onceRateLimitParameter) &&
                this.rateLimiter.canAcquire(totalRateLimitParameter)) {
            log.debug("RetryLimit once key:{},total key:{}", onceRateLimitParameter.getRateLimitKey(), totalRateLimitParameter.getRateLimitKey());
            return point.proceed();
        }
        throw new RetryLimitException(BizErrorCodeEnum.REQUEST_TOO_FREQUENTLY_HAS_BEEN_RESTRICTED);
    }

    private boolean isNotRateLimited(final Object body) {
        final long userId = HttpSessionUtils.getUserId((HttpServletRequest) body);
        return userId < 10000;
    }

    private void modifyRateLimitParameterKey(final RateLimitParameter rateLimitParameter, final Object body) {
        if (body instanceof VerifyCodeReqVO) {
            final VerifyCodeReqVO form = (VerifyCodeReqVO) body;

            final String mobile = form.getMobile();
            final String email = form.getEmail();

            if (StringUtils.isNotBlank(mobile)) {
                rateLimitParameter.setRateLimitKey(rateLimitParameter.getRateLimitKey() + ":mobile");
            } else if (StringUtils.isNotBlank(email)) {
                rateLimitParameter.setRateLimitKey(rateLimitParameter.getRateLimitKey() + ":email");
            }

        }

    }
}
