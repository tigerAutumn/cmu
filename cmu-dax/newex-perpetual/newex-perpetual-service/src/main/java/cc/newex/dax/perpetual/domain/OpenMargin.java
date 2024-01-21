package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取开仓保证金dto
 *
 * @author newex-team
 * @date 2018-11-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenMargin {
  /**
   * 用户id
   */
  private Long userId;
  /**
   * 券商id
   */
  private Integer brokerId;

  /**
   * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
   */
  private String contractCode;
  /**
   * 下单方向
   */
  private String side;
  /**
   * 价格
   */
  private BigDecimal price;
  /**
   * 下单数量
   */
  private BigDecimal amount;
  /**
   * 订单处理完成数量
   */
  private BigDecimal dealAmount;
  /**
   * 开仓保证金
   */
  private BigDecimal openMargin;
  /**
   * 偏移保证金
   */
  private BigDecimal extraMargin;
  /**
   * 平均保证金
   */
  private BigDecimal avgMargin;

  /**
   * 总订单保证金和手续费
   */
  private OrderMarginFee orderMarginFee;
  /**
   * 合约
   */
  private Contract contract;

}
