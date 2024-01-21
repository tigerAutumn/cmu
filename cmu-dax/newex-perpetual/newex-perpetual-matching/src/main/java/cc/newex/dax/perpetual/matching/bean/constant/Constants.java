package cc.newex.dax.perpetual.matching.bean.constant;

import cc.newex.dax.perpetual.domain.Contract;

/**
 * 常量定义
 *
 * @author xionghui
 * @date 2018/10/18
 */
public class Constants {
  // 保险金的券商id
  public static int INSURANCE_BROKERID = 1;

  // perpetual的biz
  public static final String PERPETUAL_BIZ = "PERPETUAL";

  // perpetual的机器
  public static final String PERPETUAL_MACHINE = "PERPETUAL_MACHINE";

  /**
   * 构建channel name
   */
  public static String buildChannel(final Contract contract) {
    return Constants.PERPETUAL_BIZ.concat("_").concat(contract.getPairCode().toUpperCase());
  }
}
