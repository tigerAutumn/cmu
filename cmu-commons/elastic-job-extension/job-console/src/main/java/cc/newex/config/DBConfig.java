package cc.newex.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by liuhailin on 2017/7/17.
 */

@Configuration
@ConfigurationProperties(prefix = "datasource")
@Data
@Component
public class DBConfig {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int maxActive;
    private int minIdle;

}
