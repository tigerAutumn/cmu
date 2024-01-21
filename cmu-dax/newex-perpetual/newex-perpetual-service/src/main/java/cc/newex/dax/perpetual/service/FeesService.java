package cc.newex.dax.perpetual.service;

import java.math.BigDecimal;

import cc.newex.dax.perpetual.dto.enums.MakerEnum;

/**
 * 手续费接口
 *
 * @author xionghui
 * @date 2018/10/18
 */
public interface FeesService {

  /**
   * 刷新手续费配置
   */
  void refresh();

  /**
   * 获取配置
   *
   * @param userId
   * @param pairCode
   * @param makerEnum
   * @return
   */
  BigDecimal getFeeRate(Long userId, String pairCode, MakerEnum makerEnum, final Integer brokerId);

  /**
   * 获取计价币USD价格
   *
   * @param pairCode
   */
  BigDecimal getUsdPrice(String pairCode);
}
