package cc.newex.dax.boss.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author newex-team
 * @date 2017-03-18
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "newex.boss.common")
public class CommonProperties {
}
