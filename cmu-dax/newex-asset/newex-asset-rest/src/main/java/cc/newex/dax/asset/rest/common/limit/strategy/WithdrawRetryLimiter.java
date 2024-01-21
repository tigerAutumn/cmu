package cc.newex.dax.asset.rest.common.limit.strategy;

import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.asset.rest.common.limit.provider.WithdrawRateLimitParameterProvider;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 邮箱绑定次数限流器
 *
 * @author newex-team
 * @date 2018-08-10
 */
@Slf4j
//@Component
public class WithdrawRetryLimiter extends AbstractRetryLimiter implements RetryLimiter {
    @Autowired
    private WithdrawRateLimitParameterProvider provider;

    @Override
    public Object execute(final ProceedingJoinPoint point, final RetryLimit retryLimit) throws Throwable {
        final Object body = point.getArgs()[2];
        final RateLimitParameter rateLimitParameter = this.provider.createRateLimitParameter(retryLimit.value(), null, body);
        final ResponseResult result = (ResponseResult) point.proceed();
        return this.getResult(rateLimitParameter, result);
    }
}
