package cc.newex.dax.market.spider.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@ConfigurationProperties(prefix = "newex.market.spider")
public final class MarketSpiderConfig {
    private String signKey;

}
