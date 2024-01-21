package cc.newex.spring.boot.autoconfigure.broker;

import cc.newex.commons.broker.interceptor.BrokerInterceptor;
import cc.newex.commons.broker.service.BrokerService;
import cc.newex.commons.dictionary.consts.BrokerIdConsts;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Order(101)
@Configuration
@EnableConfigurationProperties({BrokerProperties.class})
public class BrokerAutoConfigration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private BrokerProperties brokerProperties;

    public BrokerAutoConfigration(BrokerProperties brokerProperties) {
        this.brokerProperties = brokerProperties;
    }

    private static final String BROKER_INTERCEPTOR_BEAN_NAME = "brokerInterceptor";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    @ConditionalOnMissingBean(BrokerService.class)
    public BrokerService brokerService() {
        return new BrokerService() {
            @Override
            public Integer getBrokerIdFromUserClient(String key) {
                return BrokerIdConsts.COIN_MEX;
            }
        };
    }

    @Bean(BROKER_INTERCEPTOR_BEAN_NAME)
    public BrokerInterceptor brokerInterceptor() {
        return new BrokerInterceptor();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        if (this.applicationContext.containsBean(BROKER_INTERCEPTOR_BEAN_NAME)) {
            final String[] brokerIncludeUrlPatterns = Iterables.toArray(Splitter.on(",")
                    .omitEmptyStrings()
                    .trimResults()
                    .split(this.brokerProperties.getUrlPatterns()), String.class);
            final String[] brokerExcludePathPatterns = Iterables.toArray(Splitter.on(",")
                    .omitEmptyStrings()
                    .trimResults()
                    .split(this.brokerProperties.getExcludeUrlPatterns()), String.class);

            registry.addInterceptor(this.brokerInterceptor())
                    .addPathPatterns(brokerIncludeUrlPatterns)
                    .excludePathPatterns(brokerExcludePathPatterns);
        }
    }
}
