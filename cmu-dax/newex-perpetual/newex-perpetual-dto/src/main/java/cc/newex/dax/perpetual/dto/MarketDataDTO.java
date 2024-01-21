package cc.newex.dax.perpetual.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Min;

import com.alibaba.fastjson.annotation.JSONField;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketDataDTO {
  /**
   * 是Base和quote之间的组合 P_BTC_USD
   */
  private String contractCode;
  /**
   * 开盘价
   */
  @Min(0)
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal open;
  /**
   * 最高价
   */
  @Min(0)
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal high;
  /**
   * 最低价
   */
  @Min(0)
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal low;
  /**
   * 收盘价
   */
  @Min(0)
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal close;
  /**
   * 成交量
   */
  @Min(0)
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal amount;
  /**
   * 成交量
   */
  @Min(0)
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal size;
  /**
   * 0:1分钟 1:5分钟 2:15分钟 3:日 4:周 5:月
   */
  private Integer type;
  /**
   * 创建时间
   */
  private Date createdDate;
}
