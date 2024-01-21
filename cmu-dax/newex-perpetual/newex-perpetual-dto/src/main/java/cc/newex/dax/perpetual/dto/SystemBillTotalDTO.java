package cc.newex.dax.perpetual.dto;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SystemBillTotalDTO {
  /**
   * id
   */
  private Long id;

  /**
   * 币种
   */
  private String currencyCode;

  /**
   * 变动手续费总和
   */
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal fee;

  /**
   * 变动收益总和
   */
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal profit;

  /**
   * 用户资产余额总和
   */
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal userBalance;

  /**
   * 用户仓位资产总和
   */
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal positionSize;

  /**
   * 用户手续费总和
   */
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal totalFee;

  /**
   * 用户账单收益总和
   */
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal totalProfit;

  /**
   * 人工调整
   */
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal baseAdjust;

  /**
   * 创建时间
   */
  private Date createdDate;

  /**
   * 修改时间
   */
  private Date modifyDate;
}
