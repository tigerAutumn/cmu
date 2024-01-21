package cc.newex.wallet.signature.autoconfig;

import cc.newex.wallet.signature.RemoteServer;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author newex-team
 * @create 2018-11-21 上午10:47
 **/
@Configuration
@ConfigurationProperties(prefix = "hessian.server")
@Data
public class HessionServerProperty {

    List<RemoteServer> config;

}
