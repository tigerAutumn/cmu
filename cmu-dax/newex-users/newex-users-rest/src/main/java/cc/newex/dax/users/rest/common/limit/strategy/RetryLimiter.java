package cc.newex.dax.users.rest.common.limit.strategy;

import cc.newex.dax.users.rest.common.limit.annotation.RetryLimit;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author newex-team
 * @date 2018-08-10
 */
public interface RetryLimiter {

    Object execute(final ProceedingJoinPoint point, final RetryLimit retryLimit) throws Throwable;
}
