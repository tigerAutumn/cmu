package cc.newex.dax.perpetual.util;

import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.PositionSideEnum;
import cc.newex.dax.perpetual.common.enums.PositionTypeEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公式工具类
 *
 * @author xionghui
 * @date 2018/11/14
 */
public class FormulaUtil {

  /**
   * 计算强平价、预强平价和破产价
   */
  public static int calculationBrokerForcedLiquidationPrice(final List<Contract> contractList,
      final Map<String, MarkIndexPriceDTO> markIndexPriceMap, final UserPosition userPosition,
      final List<UserPosition> userPositionList, final UserBalance userBalance) {
    int total = 0;
    if (CollectionUtils.isEmpty(userPositionList)) {
      return total;
    }
    // 排除未实现盈亏的余额
    BigDecimal totalBalance = userBalance.getAvailableBalance();
    // 计算总的未实现盈亏
    for (final UserPosition up : userPositionList) {
      if (!up.getBase().equals(userPosition.getBase())
          || up.getAmount().compareTo(BigDecimal.ZERO) == 0) {
        continue;
      }
      total++;
      // 逐仓不计算
      if (up.getType() == PositionTypeEnum.PART_IN.getType()) {
        continue;
      }
      final BigDecimal diff =
              FormulaUtil.calcProfit(up, markIndexPriceMap.get(up.getContractCode()).getMarkPrice());
      if (diff.compareTo(BigDecimal.ZERO) < 0) {
        totalBalance = totalBalance.add(diff);
      }
    }
    final Map<String, BigDecimal> preLiqudatePriceThresholdMap = new HashMap<>();
    final Map<String, Integer> minQuoteDigitMap = new HashMap<>();
    for (final Contract contract : contractList) {
      preLiqudatePriceThresholdMap.put(contract.getContractCode(),
          contract.getPreLiqudatePriceThreshold());
      minQuoteDigitMap.put(contract.getContractCode(),
              contract.getMinQuoteDigit());
    }
    // 计算所有仓位的价格
    for (final UserPosition up : userPositionList) {
      if (!up.getBase().equals(userPosition.getBase())
          || up.getAmount().compareTo(BigDecimal.ZERO) == 0) {
        continue;
      }
      // 逐仓且不是自己不计算
      if (up.getType() == PositionTypeEnum.PART_IN.getType()
          && !userPosition.getId().equals(up.getId())) {
        continue;
      }
      BigDecimal availableBalance = totalBalance;
      if (up.getType() == PositionTypeEnum.ALL_IN.getType()) {
        // 自己的未实现盈亏不计算
        final BigDecimal diff =
                FormulaUtil.calcProfit(up, markIndexPriceMap.get(up.getContractCode()).getMarkPrice());
        if (diff.compareTo(BigDecimal.ZERO) < 0) {
          availableBalance = availableBalance.subtract(diff);
        }
      }
      // 总保证金
      BigDecimal totalMargin = up.getType() == PositionTypeEnum.ALL_IN.getType()
          ? up.getOpenMargin().add(availableBalance)
          : up.getOpenMargin();
      totalMargin = totalMargin.max(up.getOpenMargin());
      // 总保证金到维持保证金差值
      final BigDecimal forcedLiquidationMargin = totalMargin.subtract(up.getMaintenanceMargin());
      if (PositionSideEnum.LONG.getSide().equals(up.getSide())) {
        up.setBrokerPrice(BigDecimalUtil.divideUp(up.getAmount(), up.getSize().add(totalMargin), minQuoteDigitMap.get(up.getContractCode())));
        up.setLiqudatePrice(
                BigDecimalUtil.divideUp(up.getAmount(), up.getSize().add(forcedLiquidationMargin), minQuoteDigitMap.get(up.getContractCode())));

        final BigDecimal preDecimal = up.getPrice().subtract(up.getLiqudatePrice())
                .multiply(preLiqudatePriceThresholdMap.get(up.getContractCode())).abs();
        up.setPreLiqudatePrice(up.getLiqudatePrice().add(preDecimal).setScale(minQuoteDigitMap.get(up.getContractCode()), BigDecimal.ROUND_UP));
      } else {
        if (up.getSize().compareTo(totalMargin) <= 0) {
          up.setBrokerPrice(PerpetualConstants.MAX_PRICE);
        } else {
          up.setBrokerPrice(BigDecimalUtil.divide(up.getAmount(), up.getSize().subtract(totalMargin), minQuoteDigitMap.get(up.getContractCode()))
                  .min(PerpetualConstants.MAX_PRICE));
        }
        if (up.getSize().compareTo(forcedLiquidationMargin) <= 0) {
          up.setLiqudatePrice(PerpetualConstants.MAX_PRICE);
        } else {
          up.setLiqudatePrice(BigDecimalUtil.divide(up.getAmount(), up.getSize().subtract(forcedLiquidationMargin), minQuoteDigitMap.get(up.getContractCode()))
                  .min(PerpetualConstants.MAX_PRICE));
        }

        final BigDecimal preDecimal = up.getLiqudatePrice().subtract(up.getPrice())
                .multiply(preLiqudatePriceThresholdMap.get(up.getContractCode())).abs();
        up.setPreLiqudatePrice(up.getLiqudatePrice().subtract(preDecimal).setScale(minQuoteDigitMap.get(up.getContractCode()), BigDecimal.ROUND_DOWN));
      }
    }
    return total;
  }

  /**
   * 计算可用的保证金,排除未实现盈亏的余额
   */
  public static BigDecimal countAvailableBalance(final UserBalance userBalance,
                                                 final Map<String, MarkIndexPriceDTO> markIndexPriceMap,
                                                 final List<UserPosition> userPositionList) {
    BigDecimal totalBalance = userBalance.getAvailableBalance();
    // 计算总的未实现盈亏
    for (final UserPosition up : userPositionList) {
      if (up.getAmount().compareTo(BigDecimal.ZERO) == 0) {
        continue;
      }
      // 逐仓不计算
      if (up.getType() == PositionTypeEnum.PART_IN.getType()) {
        continue;
      }
      // 计算盈亏
      final BigDecimal diff = FormulaUtil.calcProfit(up, markIndexPriceMap.get(up.getContractCode()).getMarkPrice());
      if (diff.compareTo(BigDecimal.ZERO) < 0) {
        totalBalance = totalBalance.add(diff);
      }
    }
    return totalBalance;
  }

  /**
   * 计算盈亏
   *
   * @param userPosition 持仓
   * @param markPrice 标记价格
   */
  public static BigDecimal calcProfit(final UserPosition userPosition, final BigDecimal markPrice) {
    if (userPosition.getPrice().compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.ZERO;
    }
    final BigDecimal value = BigDecimalUtil.divide(userPosition.getAmount(), userPosition.getPrice());
    final BigDecimal markValue = BigDecimalUtil.divide(userPosition.getAmount(), markPrice);
    final BigDecimal diff = PositionSideEnum.LONG.getSide().equals(userPosition.getSide()) ?
            value.subtract(markValue) : markValue.subtract(value);
    return diff;
  }
}
