package cc.newex.dax.perpetual.dto;

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
public class DepthDataDTO {
  /**
   * 币种pairCode
   */
  private String pairCode;
  /**
   * 卖一价（深度中）
   */
  @Builder.Default
  private BigDecimal askFirstPrice = BigDecimal.ZERO;
  /**
   * 买一价（深度中）
   */
  @Builder.Default
  private BigDecimal bidFirstPrice = BigDecimal.ZERO;
}
