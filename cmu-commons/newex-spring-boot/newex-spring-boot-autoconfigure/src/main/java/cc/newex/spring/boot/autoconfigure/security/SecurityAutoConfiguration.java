package cc.newex.spring.boot.autoconfigure.security;

import cc.newex.commons.security.csrf.CsrfInterceptor;
import cc.newex.commons.security.jwt.JwtFrozenInterceptor;
import cc.newex.commons.security.jwt.JwtInterceptor;
import cc.newex.commons.security.jwt.crypto.AesJwtTokenCryptoProvider;
import cc.newex.commons.security.jwt.crypto.JwtTokenCryptoProvider;
import cc.newex.commons.security.jwt.enums.BizTypeEnum;
import cc.newex.commons.security.jwt.model.JwtConfig;
import cc.newex.commons.security.jwt.model.JwtFrozenConfig;
import cc.newex.commons.security.jwt.token.JwtTokenProvider;
import cc.newex.commons.security.xss.XssFilter;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author newex-team
 * @date 2017/11/20
 */
@Configuration
@ConditionalOnClass({XssFilter.class, CsrfInterceptor.class, JwtInterceptor.class})
@Order(99)
@EnableConfigurationProperties({
        SecurityProperties.class, FrozenProperties.class
})
public class SecurityAutoConfiguration
        extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    private static final String CSRF_INTERCEPTOR_BEAN_NAME = "csrfInterceptor";
    private static final String JWT_INTERCEPTOR_BEAN_NAME = "jwtInterceptor";
    private static final String JWT_FROZEN_INTERCEPTOR_BEAN_NAME = "jwtFrozenInterceptor";

    private ApplicationContext applicationContext;
    private final SecurityProperties securityProperties;
    private final FrozenProperties frozenProperties;

    public SecurityAutoConfiguration(final SecurityProperties securityProperties,
                                     final FrozenProperties frozenProperties) {
        this.securityProperties = securityProperties;
        this.frozenProperties = frozenProperties;
    }

    /**
     * 配置XssFilter
     *
     * @return FilterRegistrationBean
     */
    @Bean
    @ConditionalOnProperty(prefix = "newex.security.xss", name = "enabled", matchIfMissing = true)
    public FilterRegistrationBean xssFilterRegistrationBean() {
        final String[] urlPatterns = Iterables.toArray(Splitter.on(",")
                .omitEmptyStrings()
                .trimResults()
                .split(this.securityProperties.getXss().getUrlPatterns()), String.class);

        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new XssFilter());
        registrationBean.addInitParameter(XssFilter.FILTER_POLICY,
                this.securityProperties.getXss().getPolicy());
        registrationBean.addInitParameter(XssFilter.EXCLUDE_URL_PATTERNS,
                this.securityProperties.getXss().getExcludeUrlPatterns());
        registrationBean.addUrlPatterns(urlPatterns);
        registrationBean.setName("xssFilter");
        return registrationBean;
    }

    /**
     * 配置Csrf Interceptor
     *
     * @return CsrfInterceptor
     */
    @Bean
    @ConditionalOnProperty(prefix = "newex.security.csrf", name = "enabled", matchIfMissing = true)
    public CsrfInterceptor csrfInterceptor() {
        return new CsrfInterceptor(this.securityProperties.getCsrf().getRefererPattern());
    }

    /**
     * 配置jwt Interceptor,默认开启
     *
     * @return JwtInterceptor
     */
    @Bean
    @ConditionalOnClass(JwtInterceptor.class)
    @ConditionalOnProperty(prefix = "newex.security.jwt",
            name = {"interceptor", "enabled"}, matchIfMissing = true)
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(JwtInterceptor.class)
    @ConditionalOnProperty(prefix = "newex.security.jwt", name = "enabled", matchIfMissing = true)
    public JwtTokenProvider jwtTokenProvider() {
        final SecurityProperties.Jwt jwt = this.securityProperties.getJwt();

        final JwtConfig jwtConfig = new JwtConfig();
        jwtConfig.setRequestHeaderName(jwt.getRequestHeaderName());
        jwtConfig.setCryptoKey(jwt.getCryptoKey());
        jwtConfig.setIssuer(jwt.getIssuer());
        jwtConfig.setSecret(jwt.getSecret());
        jwtConfig.setExpiration(jwt.getExpiration());
        jwtConfig.setValidateIpAndDevice(jwt.isValidateIpAndDevice());

        final JwtTokenCryptoProvider cryptoProvider = new AesJwtTokenCryptoProvider();
        return new JwtTokenProvider(jwtConfig, cryptoProvider);
    }

    @Bean
    @ConditionalOnProperty(prefix = "newex.security.frozen", name = "biz-type")
    public JwtFrozenInterceptor jwtFrozenInterceptor() {
        return new JwtFrozenInterceptor();
    }

    @Bean
    @ConditionalOnProperty(prefix = "newex.security.frozen", name = "biz-type")
    public JwtFrozenConfig jwtFrozenConfig() {
        final JwtFrozenConfig config = new JwtFrozenConfig();
        config.setBizType(BizTypeEnum.forName(this.frozenProperties.getBizType()));
        return config;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        if (this.applicationContext.containsBean(CSRF_INTERCEPTOR_BEAN_NAME)) {
            final String[] csrfPathPatterns = Iterables.toArray(Splitter.on(",")
                    .omitEmptyStrings()
                    .trimResults()
                    .split(this.securityProperties.getCsrf().getExcludePathPatterns()), String.class);
            registry.addInterceptor(this.csrfInterceptor()).excludePathPatterns(csrfPathPatterns);
        }

        if (this.applicationContext.containsBean(JWT_INTERCEPTOR_BEAN_NAME)) {
            final String[] jwtPathPatterns = Iterables.toArray(Splitter.on(",")
                    .omitEmptyStrings()
                    .trimResults()
                    .split(this.securityProperties.getJwt().getExcludePathPatterns()), String.class);
            registry.addInterceptor(this.jwtInterceptor()).excludePathPatterns(jwtPathPatterns);
        }

        if (this.applicationContext.containsBean(JWT_FROZEN_INTERCEPTOR_BEAN_NAME)) {
            final String[] frozenIncludeUrlPatterns = Iterables.toArray(Splitter.on(",")
                    .omitEmptyStrings()
                    .trimResults()
                    .split(this.frozenProperties.getUrlPatterns().getIncludeUrlPatterns()), String.class);
            final String[] frozenExcludePathPatterns = Iterables.toArray(Splitter.on(",")
                    .omitEmptyStrings()
                    .trimResults()
                    .split(this.frozenProperties.getUrlPatterns().getExcludeUrlPatterns()), String.class);

            registry.addInterceptor(this.jwtFrozenInterceptor())
                    .addPathPatterns(frozenIncludeUrlPatterns)
                    .excludePathPatterns(frozenExcludePathPatterns);
        }
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
