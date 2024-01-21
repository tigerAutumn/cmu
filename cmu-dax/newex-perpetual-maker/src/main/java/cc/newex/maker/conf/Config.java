package cc.newex.maker.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 配置
 *
 * @author newex-team
 * @date 2018-12-18
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "maker")
public class Config {

    /**
     * 币对
     */
    private List<String> pairs;

    /**
     * 是否线上
     */
    private boolean prod;

}
