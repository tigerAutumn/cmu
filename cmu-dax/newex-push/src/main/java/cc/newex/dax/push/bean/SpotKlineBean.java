package cc.newex.dax.push.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import lombok.Data;

/**
 * 现货Kline
 *
 * @author xionghui
 * @date 2018/09/14
 */
@Data
public class SpotKlineBean {
  /**
   * 交易数据
   */
  private Long id;
  /**
   * 来源
   */
  private Integer marketFrom;
  /**
   * 0:1分钟 1:5分钟 2:15分钟 3:日 4:周
   */
  private Byte type;
  /**
   * 开盘价
   */
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal open;
  /**
   * 最高价
   */
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal high;
  /**
   * 最低价
   */
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal low;
  /**
   * 收盘价
   */
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal close;
  /**
   * 成交量
   */
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal volume;
  /**
   * 币总数
   */
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal coinVolume;
  /**
   * 创建时间
   */
  private Date createdDate;
}
