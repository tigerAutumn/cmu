package cc.newex.spring.boot.autoconfigure.session;

import cc.newex.commons.ucenter.annotation.EnableTokenSession;
import cc.newex.commons.ucenter.config.SessionConfig;
import cc.newex.commons.ucenter.data.SessionRepository;
import cc.newex.commons.ucenter.service.SessionService;
import cc.newex.commons.ucenter.service.impl.SessionServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author newex-team
 * @date 2018-06-11
 */
@Configuration
@ConditionalOnClass(SessionRepository.class)
@EnableConfigurationProperties(SessionProperties.class)
public class SessionAutoConfiguration implements BeanPostProcessor {
    private final SessionProperties sessionProperties;

    public SessionAutoConfiguration(final SessionProperties sessionProperties) {
        this.sessionProperties = sessionProperties;
    }

    @Bean
    public SessionConfig sessionConfig() {
        return SessionConfig.builder()
                .maxInactiveInterval(Duration.ofSeconds(this.sessionProperties.getMaxInactiveInterval()))
                .build();
    }

    @Bean
    public SessionService sessionService() {
        return new SessionServiceImpl();
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(EnableTokenSession.class)) {
            final EnableTokenSession enableTokenSession = bean.getClass().getAnnotation(EnableTokenSession.class);
            final long maxInterval = Math.max(enableTokenSession.maxInactiveIntervalInSeconds(), this.sessionProperties.getMaxInactiveInterval());
            this.sessionService().setMaxInactiveInterval(maxInterval);
        }
        return bean;
    }
}


