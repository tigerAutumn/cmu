package cc.newex.dax.perpetual.util;

import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.DirectionEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;

import java.math.BigDecimal;

/**
 * 下单计算工具
 *
 * @author newex-team
 * @date 2018-11-05
 */
public class OrderUtil {
  /**
   * 开仓价值
   *
   * @param amount 张数
   * @param price 下单价格
   */
  public static BigDecimal getOrderCost(final BigDecimal amount, final BigDecimal price) {
    return BigDecimalUtil.divide(amount, price);
  }

  /**
   * 净头寸 (持仓价值+挂空单价值+挂多单价值)
   *
   * @param cost
   */
  public static BigDecimal getNetPosition(final BigDecimal... cost) {
    return BigDecimalUtil.add(cost);
  }

  /**
   * 开仓保证金率= 维持保证金费率+维持保证金费率+((风险限额/最低档位)/每档的差值*维持保证金费率))
   *
   * @param currencyPair 币对
   * @param gear 风险限额
   */
  public static BigDecimal getOpenMarginRate(final CurrencyPair currencyPair,
      final BigDecimal gear) {
    BigDecimal openMarginRate = currencyPair.getMaintainRate().add(currencyPair.getMaintainRate());
    BigDecimal tmpGear = gear;
    while (tmpGear.compareTo(currencyPair.getMinGear()) > 0) {
      openMarginRate = openMarginRate.add(currencyPair.getMaintainRate());
      tmpGear = tmpGear.subtract(currencyPair.getMinGear());
    }
    return openMarginRate;
  }

  /**
   * 获取杠杆=1/开仓保证金率
   *
   * @param currencyPair
   * @param gear
   * @return
   */
  public static BigDecimal getLeverage(final CurrencyPair currencyPair, final BigDecimal gear) {
    return BigDecimal.ONE.divide(getOpenMarginRate(currencyPair, gear), PerpetualConstants.LEVER_SCALE, BigDecimal.ROUND_DOWN);
  }

  /**
   * 价格偏移保证金
   *
   * @param contract 合约
   * @param amount 开仓数量
   * @param price 开仓价格
   * @param reasonablePrice 合理价格
   */
  public static BigDecimal getPriceDeviationMargin(final Contract contract, BigDecimal amount, final BigDecimal price,
                                                   final BigDecimal reasonablePrice) {
    amount = BigDecimalUtil.multiply(amount, contract.getUnitAmount());
    if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
      return BigDecimalUtil.subtract(BigDecimalUtil.multiply(amount, price),
              BigDecimalUtil.multiply(amount, reasonablePrice)).abs();
    }
    return BigDecimalUtil.subtract(BigDecimalUtil.divide(amount, price),
        BigDecimalUtil.divide(amount, reasonablePrice)).abs();
  }
}
