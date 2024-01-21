package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TmpOrder {

  /**
   * 仓位方向，long多，short空
   */
  private String side;

  /**
   * 剩余的数量
   */
  private BigDecimal leftAmount;

  /**
   * 挂单价格，市价单用盘口价
   */
  private BigDecimal price;

  /**
   * 每张合约摊到的保证金
   */
  private BigDecimal avgMargin;
}
