package cc.newex.dax.perpetual.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置
 *
 * @author xionghui
 * @date 2018/10/18
 */
@ConfigurationProperties(prefix = "newex.perpetual.config")
@Configuration
@Data
public class PerpetualConfig {

  public static final int DEV = 0;
  public static final int PRE = 1;
  public static final int PROD = 2;

  /**
   * 开发为:0 预发为:1 线上为:2
   */
  private Integer online;
  /**
   * rest:1 rest api:2 matching:3 scheduler:4
   */
  private Integer module;
  /**
   * 点卡抵扣
   */
  private String dkCurrency;
  /**
   * 机器人账号
   */
  private Long robot;
  /**
   * 挂单深度机器人id
   */
  private Long marketId;
  /**
   * 对账不平报警手机号，多个手机号用逗号分隔
   */
  private String systemBillPhones;

  public List<Integer> availableOnlineValue() {
    final List<Integer> onlineList = new ArrayList<>();
    if (this.online == PerpetualConfig.DEV) {
      onlineList.add(PerpetualConfig.DEV);
      onlineList.add(PerpetualConfig.PRE);
      onlineList.add(PerpetualConfig.PROD);
    } else if (this.online == PerpetualConfig.PRE) {
      onlineList.add(PerpetualConfig.PRE);
      onlineList.add(PerpetualConfig.PROD);
    } else {
      onlineList.add(PerpetualConfig.PROD);
    }
    return onlineList;
  }
}
