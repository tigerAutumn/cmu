package cc.newex.dax.users.rest.common.limit.annotation;

import cc.newex.dax.users.rest.common.limit.enums.RetryLimitTypeEnum;
import cc.newex.dax.users.rest.common.limit.strategy.ApiSecretRetryLimiter;
import cc.newex.dax.users.rest.common.limit.strategy.EmailBindRetryLimiter;
import cc.newex.dax.users.rest.common.limit.strategy.EnableValidateRetryLimiter;
import cc.newex.dax.users.rest.common.limit.strategy.FiatRetryLimiter;
import cc.newex.dax.users.rest.common.limit.strategy.ForgetPwdRetryLimiter;
import cc.newex.dax.users.rest.common.limit.strategy.GoogleCodeBindRetryLimiter;
import cc.newex.dax.users.rest.common.limit.strategy.GoogleCodeUnBindRetryLimiter;
import cc.newex.dax.users.rest.common.limit.strategy.LoginRetryLimiter;
import cc.newex.dax.users.rest.common.limit.strategy.MobileBindRetryLimiter;
import cc.newex.dax.users.rest.common.limit.strategy.MobileUpdateRetryLimiter;
import cc.newex.dax.users.rest.common.limit.strategy.RegisterRetryLimiter;
import cc.newex.dax.users.rest.common.limit.strategy.SecurityVerifyCodeRetryLimiter;
import cc.newex.dax.users.rest.common.limit.strategy.VerifyCodeRetryLimiter;
import cc.newex.dax.users.rest.common.limit.strategy.VerifyLoginRetryLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Aspect
@Component
public class RetryLimitAspect {

    @Autowired
    private LoginRetryLimiter loginRetryLimiter;
    @Autowired
    private VerifyCodeRetryLimiter verifyCodeRetryLimiter;
    @Autowired
    private ForgetPwdRetryLimiter forgetPwdRetryLimiter;
    @Autowired
    private RegisterRetryLimiter registerRetryLimiter;
    @Autowired
    private VerifyLoginRetryLimiter verifyLoginRetryLimiter;
    @Autowired
    private EmailBindRetryLimiter emailBindRetryLimiter;
    @Autowired
    private GoogleCodeBindRetryLimiter googleCodeBindRetryLimiter;
    @Autowired
    private GoogleCodeUnBindRetryLimiter googleCodeUnBindRetryLimiter;
    @Autowired
    private MobileBindRetryLimiter mobileBindRetryLimiter;
    @Autowired
    private MobileUpdateRetryLimiter mobileUpdateRetryLimiter;
    @Autowired
    private EnableValidateRetryLimiter enableValidateRetryLimiter;
    @Autowired
    private ApiSecretRetryLimiter apiSecretRetryLimiter;
    @Autowired
    private FiatRetryLimiter fiatRetryLimiter;
    @Autowired
    private SecurityVerifyCodeRetryLimiter securityVerifyCodeRetryLimiter;

    @Pointcut(value = "@annotation(cc.newex.dax.users.rest.common.limit.annotation.RetryLimit)")
    private void pointcut() {
    }

    @Around(value = "pointcut() && @annotation(retryLimit)")
    public Object around(final ProceedingJoinPoint point, final RetryLimit retryLimit) {
        try {
            if (retryLimit.type() == RetryLimitTypeEnum.VERIFY_CODE) {
                return this.verifyCodeRetryLimiter.execute(point, retryLimit);
            }
            if (retryLimit.type() == RetryLimitTypeEnum.LOGIN) {
                return this.loginRetryLimiter.execute(point, retryLimit);
            }
            if (retryLimit.type() == RetryLimitTypeEnum.FORGET_PASSWORD) {
                return this.forgetPwdRetryLimiter.execute(point, retryLimit);
            }
            if (retryLimit.type() == RetryLimitTypeEnum.REGISTER) {
                return this.registerRetryLimiter.execute(point, retryLimit);
            }
            if (retryLimit.type() == RetryLimitTypeEnum.VERIFY_LOGIN) {
                return this.verifyLoginRetryLimiter.execute(point, retryLimit);
            }
            if (retryLimit.type() == RetryLimitTypeEnum.EMAIL_BIND) {
                return this.emailBindRetryLimiter.execute(point, retryLimit);
            }
            if (retryLimit.type() == RetryLimitTypeEnum.GOOGLE_CODE_BIND) {
                return this.googleCodeBindRetryLimiter.execute(point, retryLimit);
            }
            if (retryLimit.type() == RetryLimitTypeEnum.GOOGLE_CODE_UNBIND) {
                return this.googleCodeUnBindRetryLimiter.execute(point, retryLimit);
            }
            if (retryLimit.type() == RetryLimitTypeEnum.MOBILE_BIND) {
                return this.mobileBindRetryLimiter.execute(point, retryLimit);
            }
            if (retryLimit.type() == RetryLimitTypeEnum.MOBILE_UPDATE) {
                return this.mobileUpdateRetryLimiter.execute(point, retryLimit);
            }
            if (retryLimit.type() == RetryLimitTypeEnum.ENABLE_VALIDATE) {
                return this.enableValidateRetryLimiter.execute(point, retryLimit);
            }
            if (retryLimit.type() == RetryLimitTypeEnum.API_SECRETS) {
                return this.apiSecretRetryLimiter.execute(point, retryLimit);
            }
            if (retryLimit.type() == RetryLimitTypeEnum.FIAT) {
                return this.fiatRetryLimiter.execute(point, retryLimit);
            }
            if (retryLimit.type() == RetryLimitTypeEnum.SECURITY_VERIFY_CODE) {
                return this.securityVerifyCodeRetryLimiter.execute(point, retryLimit);
            }
            return point.proceed();
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }
}

