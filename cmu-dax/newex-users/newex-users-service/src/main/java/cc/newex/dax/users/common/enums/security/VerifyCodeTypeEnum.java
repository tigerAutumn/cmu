package cc.newex.dax.users.common.enums.security;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * @author newex-team
 */
@Getter
public enum VerifyCodeTypeEnum {
    REGISTER("register", 60, TimeUnit.SECONDS),
    GOOGLE_SECRET("google_secret", 15, TimeUnit.MINUTES),
    GOOGLE_AUTH_SMS("google_auth_sms", 60, TimeUnit.SECONDS),
    MODIFY_LOGIN_PASSWORD("modify_login_password", 60, TimeUnit.SECONDS);

    private final String prefix;
    private final long timeout;
    private final TimeUnit timeUnit;

    VerifyCodeTypeEnum(final String prefix, final long timeout, final TimeUnit timeUnit) {
        this.prefix = prefix;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }
}
