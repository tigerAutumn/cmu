package cc.newex.dax.integration.rest.common.config.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

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
    public TemplateEngine templateEngine() {
        final TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.addTemplateResolver(this.stringTemplateResolver());
        return templateEngine;
    }

    private ITemplateResolver stringTemplateResolver() {
        final StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setTemplateMode("TEXT");
        templateResolver.setCacheable(false);
        return templateResolver;
    }
}
