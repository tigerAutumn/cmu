package cc.newex.spring.boot.autoconfigure.broker;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({BrokerAutoConfigration.class})
public @interface EnableBrokerConfig {
}
