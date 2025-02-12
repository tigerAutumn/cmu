package cc.newex.commons.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统操作日志注解
 *
 * @author newex-team
 * @date 2017/12/09
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpLog {
    /**
     * 操作日志名称
     *
     * @return
     */
    String name() default "";

    /**
     * 操作日常说明
     *
     * @return
     */
    String desc() default "";

    /**
     * 日志类型
     *
     * @return
     */
    String type() default OpLogType.DEFAULT;
}
