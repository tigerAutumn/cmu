package cc.newex.dax.perpetual.domain.bean;

import java.math.BigDecimal;
import java.util.List;

import cc.newex.dax.perpetual.service.UserPositionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 合约明细
 *
 * @author newex-team
 * @date 2018-11-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ContractDetail {
  /**
   * 合约code
   */
  private String contractCode;
  /**
   * 头部用户列表
   */
  private List<UserPositionService.UserRank> userRankList;

  private String deliveryDate = "永续";
  /**
   * 新委托的起始保证金
   */
  private String initMargin = "1.00% + 开仓佣金 + 平仓佣金";
  /**
   * 维持保证金
   */
  private String maintainRate = "0.50% + 平仓佣金 + 资金费率";

  /**
   * 资金费用收取间隔
   */
  private String deliveryTime = "每 8 小时";
  /**
   * 资金费率
   */
  private String feeRate = "-0.3750%";
  /**
   * 预测费率
   */
  private String preFeeRate = "-0.3750%";
  /**
   * 指数价格
   */
  private BigDecimal indexPrice;
  /**
   * 标记价格
   */
  private BigDecimal markPrice;
  /**
   * 标记方法
   */
  private String markMethod = "FairPrice";
  /**
   * 合理基差
   */
  private BigDecimal reasonablePrice;
  /**
   * 合理基差率
   */
  private BigDecimal reasonableRate;
  /**
   * 风险限额
   */
  private BigDecimal riskLimit;
  /**
   * 风险限额递增值
   */
  private BigDecimal riskLimitIncrease = new BigDecimal(100);

  /**
   * 未平仓合约数量
   */
  private BigDecimal unPositionAmount;

  /**
   * 类型
   */
  private String type = "结算货币为BTC，计价货币为USDT";
  /**
   * 合约大小
   */
  private BigDecimal usd = BigDecimal.ONE;
  /**
   * 结算
   */
  private String delivery = "此合约为永续无结算合约";
  /**
   * 最小的价格变化
   */
  private BigDecimal minPrice = new BigDecimal(0.5);

  /**
   * 最大委托价格
   */
  private BigDecimal maxPrice;

  /**
   * 最大委托数量
   */
  private BigDecimal maxAmount;

  /**
   * 最小合约数量
   */
  private BigDecimal minAmount = BigDecimal.ONE;

}
