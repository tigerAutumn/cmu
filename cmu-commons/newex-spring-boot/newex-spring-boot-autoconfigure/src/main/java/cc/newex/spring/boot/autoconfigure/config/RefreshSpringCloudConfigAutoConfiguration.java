package cc.newex.spring.boot.autoconfigure.config;

import cc.newex.commons.spring.cloud.config.client.ConfigServicePropertySourceChangeListener;
import cc.newex.commons.spring.cloud.config.client.ConfigServicePropertySourceSubscriber;
import cc.newex.commons.spring.cloud.config.client.ContextRefreshConfigServicePropertySourceChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 自动刷新spring cloud config配置类
 *
 * @author newex-team
 * @date 2017/12/09
 */
@Slf4j
@Configuration
@EnableScheduling
@ConditionalOnProperty("spring.cloud.config.enabled")
@ConditionalOnClass(ConfigServicePropertySourceSubscriber.class)
public class RefreshSpringCloudConfigAutoConfiguration {
    @Bean
    public ConfigServicePropertySourceSubscriber configServicePropertySourceSubscriber(
            final ConfigClientProperties properties, final ConfigServicePropertySourceChangeListener listener,
            final Environment environment) {
        return new ConfigServicePropertySourceSubscriber(properties, listener, environment);
    }

    @Bean
    @ConditionalOnMissingBean(ConfigServicePropertySourceChangeListener.class)
    public ConfigServicePropertySourceChangeListener configServicePropertySourceChangeListener(
            final ContextRefresher contextRefresher) {
        log.info("use ConfigServicePropertySourceChangeListener");
        return new ContextRefreshConfigServicePropertySourceChangeListener(contextRefresher);
    }
}
