package cc.newex.commons.openapi.specs.annotation;

import org.springframework.http.HttpMethod;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Validator for authorization checking.<br/>
 * Created by newex-team on 2018/2/1 10:29. <br/>
 * <p>
 * Usage:
 * <blockquote><pre>
 *   ` @RequestMapping("/time")
 *   ` @OpenApiAuthValidator(method = HttpMethod.GET, validate = false)
 * </pre></blockquote><p>
 * </p>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenApiAuthValidator {
    /**
     * 1.mark request method. default GET;<br/>
     * 2.This value is used to signing. <br/>
     * <p>
     * tips: recommended to use: @RequestMapping("/path")
     */
    HttpMethod method() default HttpMethod.GET;

    /**
     * Must be signed and verified. default true.
     */
    boolean validate() default true;

    /**
     * if api key is freeze then it whether available. default true.
     */
    boolean availableAtFrozen() default true;
}
