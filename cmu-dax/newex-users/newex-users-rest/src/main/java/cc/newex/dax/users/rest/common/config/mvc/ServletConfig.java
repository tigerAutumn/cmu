package cc.newex.dax.users.rest.common.config.mvc;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * java web servlet 配置类
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Configuration
public class ServletConfig {
    private static final String TMP_PATH = "/home/admin/tmp";

    @Bean
    MultipartConfigElement multipartConfigElement() {
        final File file = new File(TMP_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        final MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(TMP_PATH);
        return factory.createMultipartConfig();
    }
}
