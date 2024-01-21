package cc.newex.commons.support.annotation;

import cc.newex.commons.support.consts.UserAuthConsts;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 绑定当前登录的用户
 *
 * @author newex-team
 * @date 2017/12/09
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

    /**
     * 当前用户在request中的名字
     *
     * @return
     */
    String value() default UserAuthConsts.CURRENT_USER;

}
