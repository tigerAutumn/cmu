package cc.newex.dax.users.rest.common.limit.strategy;

import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.users.rest.common.limit.provider.FiatRateLimitParameterProvider;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 法币设置次数限流器
 *
 * @author newex-team
 * @date 2018-08-10
 */
@Slf4j
@Component
public class FiatRetryLimiter extends AbstractRetryLimiter implements RetryLimiter {
    @Autowired
    private FiatRateLimitParameterProvider provider;

    @Override
    public Object execute(final ProceedingJoinPoint point, final RetryLimit retryLimit) throws Throwable {
        final Object body = this.getHttpRequestObject(point.getArgs());
        final RateLimitParameter rateLimitParameter = this.provider.createRateLimitParameter(retryLimit.value(), null, body);
        final ResponseResult result = (ResponseResult) point.proceed();
        return this.getResult(rateLimitParameter, result);
    }

    private Object getHttpRequestObject(final Object[] args) {
        for (final Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                return arg;
            }
        }
        return null;
    }
}
