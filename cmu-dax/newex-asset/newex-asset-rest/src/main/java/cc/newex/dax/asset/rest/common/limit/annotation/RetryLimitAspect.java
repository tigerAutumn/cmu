package cc.newex.dax.asset.rest.common.limit.annotation;

import cc.newex.dax.asset.rest.common.limit.enums.RetryLimitTypeEnum;
import cc.newex.dax.asset.rest.common.limit.strategy.WithdrawRetryLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
//@Aspect
//@Component
public class RetryLimitAspect {
    @Autowired
    private WithdrawRetryLimiter withdrawRetryLimiter;

    @Pointcut(value = "@annotation(cc.newex.dax.asset.rest.common.limit.annotation.RetryLimit)")
    private void pointcut() {
    }

    @Around(value = "pointcut() && @annotation(retryLimit)")
    public Object around(final ProceedingJoinPoint point, final RetryLimit retryLimit) {
        try {
            if (retryLimit.type() == RetryLimitTypeEnum.WITHDRAW) {
                return this.withdrawRetryLimiter.execute(point, retryLimit);
            }
            return point.proceed();
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }
}

