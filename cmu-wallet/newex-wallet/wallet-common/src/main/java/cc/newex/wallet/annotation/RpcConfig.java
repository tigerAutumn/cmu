package cc.newex.wallet.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

import static cc.newex.wallet.annotation.RpcConfig.RpcType.JSON_RPC;

/**
 * @author newex-team
 * @data 01/04/2018
 */
@Target({ElementType.TYPE})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcConfig {
    @AliasFor("server")
    String value() default "";

    @AliasFor("value")
    String server();

    String username() default "";

    String password() default "";

    boolean needProxy() default false;

    RpcType type() default JSON_RPC;

    /**
     * rpc 类型
     */
    enum RpcType {
        JSON_RPC,
        REST_RPC
    }

}
