package cc.newex.dax.market.job.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 公共配置类
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "newex.common")
public class CommonProperties {
    private String evn;
    private String adminEmail;
}
