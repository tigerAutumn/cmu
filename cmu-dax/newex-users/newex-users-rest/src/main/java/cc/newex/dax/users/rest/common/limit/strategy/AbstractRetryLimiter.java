package cc.newex.dax.users.rest.common.limit.strategy;

import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.commons.ratelimiter.RateLimiter;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author newex-team
 * @date 2018-09-03
 */
public abstract class AbstractRetryLimiter implements RetryLimiter {
    @Autowired
    protected RateLimiter rateLimiter;

    protected ResponseResult getResult(final RateLimitParameter rateLimitParameter, final ResponseResult result) {
        if (result.getCode() != 0) {
            if (this.rateLimiter.canAcquire(rateLimitParameter)) {
                final long remaining = rateLimitParameter.getMaxRequestTimes() - rateLimitParameter.getUsed();
                final String msg = String.format("%s(%s)",
                        BizErrorCodeEnum.LOGON_FAILURE_YOU_STILL_HAVE_CHANCES.getMessage(new Object[]{remaining}),
                        result.getMsg()
                );
                result.setMsg(msg);
            } else {
                result.setMsg(BizErrorCodeEnum.LOGON_FAILURE_EXCEED_MAX_TIMES.getMessage(new Object[]{rateLimitParameter.getMaxRequestTimes()}));
            }
            return result;
        }
        //如果成功则清除之前失败次数记录
        this.rateLimiter.clear(rateLimitParameter.getRateLimitKey());
        return result;
    }
}
