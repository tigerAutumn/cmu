package cc.newex.dax.boss.web.common.config.mvc;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * java web servlet 配置类
 *
 * @author newex-team
 * @date 2017-03-18
 **/
@Configuration
public class ServletConfig {
    @Bean(name = "customContainerCustomizer")
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400"));
            container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401"));
            container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/error/403"));
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
            container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error"));
        };
    }
}
