package cc.newex.wallet.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author liutiejun
 * @date 2019-07-18
 */
@Slf4j
@Configuration
public class MasterNodeConfig {

    public static String masterNode;

    @Value("${newex.masterNode}")
    public void setMasterNode(final String masterNode) {
        MasterNodeConfig.masterNode = masterNode;

        log.info("masterNode: {}", masterNode);
    }

}
