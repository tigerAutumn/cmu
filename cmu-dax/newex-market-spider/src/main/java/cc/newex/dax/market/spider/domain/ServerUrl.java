package cc.newex.dax.market.spider.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;


/**
 * market 的路径
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Repository
@Slf4j
@Data
@ConfigurationProperties(prefix = "newex.market.send")
public class ServerUrl {
    private String serverLocation;

}
