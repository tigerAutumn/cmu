package cc.newex.dax.extra.rest.common.config.mvc;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * spring mvc 配置类
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        final MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();

        // 设置validator模式为快速失败返回
        postProcessor.setValidator(this.validator());

        return postProcessor;
    }

    @Bean
    public Validator validator() {
        final ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory();

        final Validator validator = validatorFactory.getValidator();

        return validator;
    }

    @Override
    public void configurePathMatch(final PathMatchConfigurer configurer) {
        final AntPathMatcher matcher = new AntPathMatcher();

        // 请求路径可以不区分大小写
        matcher.setCaseSensitive(false);

        configurer.setPathMatcher(matcher);
    }

}
