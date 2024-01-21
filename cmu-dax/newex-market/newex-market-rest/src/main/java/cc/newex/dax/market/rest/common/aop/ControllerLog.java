package cc.newex.dax.market.rest.common.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Component
@Documented
@Inherited
public @interface ControllerLog {
    /**
     * 自定义描述
     */
    String descInfo() default StringUtils.EMPTY;

    /**
     * 是否打印日志
     */
    boolean isLog() default true;

}
