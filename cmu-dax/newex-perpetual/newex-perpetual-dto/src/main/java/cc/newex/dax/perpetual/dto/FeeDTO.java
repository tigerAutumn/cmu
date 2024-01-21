package cc.newex.dax.perpetual.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FeeDTO {
  /**
   */
  private Integer id;

  /**
   * pair_code的base走手续费
   */
  private String pairCode;

  /**
   * 0:both 1:maker 2:taker
   */
  private Integer side;

  /**
   * 手续费
   */
  private BigDecimal rate;

  /**
   * 业务方ID
   */
  private Integer brokerId;

  /**
   * 创建时间
   */
  private Date createdDate;

  /**
   * 更新时间
   */
  private Date modifyDate;
}
