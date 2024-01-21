package cc.newex.dax.perpetual.dto;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarkIndexReasonablePriceDTO extends MarkIndexPriceDTO {
  /**
   * 获取合理价格 上限
   */
  private BigDecimal maxReasonablePrice;
  /**
   * 获取合理价格 下限
   */
  private BigDecimal minReasonablePrice;
}
