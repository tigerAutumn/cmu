package cc.newex.dax.users.rest.common.limit.strategy;

import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.users.rest.common.limit.provider.ForgetPwdRateLimitParameterProvider;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 忘记密码次数限流器
 *
 * @author newex-team
 * @date 2018-08-10
 */
@Slf4j
@Component
public class ForgetPwdRetryLimiter extends AbstractRetryLimiter implements RetryLimiter {
    @Autowired
    private ForgetPwdRateLimitParameterProvider forgetPwdRateLimitParameterProvider;

    @Override
    public Object execute(final ProceedingJoinPoint point, final RetryLimit retryLimit) throws Throwable {
        final Object body = point.getArgs()[0];
        final RateLimitParameter rateLimitParameter = this.forgetPwdRateLimitParameterProvider.createRateLimitParameter(retryLimit.value(), null, body);
        final ResponseResult result = (ResponseResult) point.proceed();
        return this.getResult(rateLimitParameter, result);
    }
}
