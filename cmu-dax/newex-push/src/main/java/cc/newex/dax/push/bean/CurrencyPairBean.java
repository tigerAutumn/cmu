package cc.newex.dax.push.bean;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import lombok.Data;

/**
 * 币对
 *
 * @author xionghui
 * @date 2018/09/13
 */
@Data
public class CurrencyPairBean {

  /**
   * 市场id
   */
  private Integer id;
  /**
   * 交易货币 例如:ltc/etc
   */
  private Integer baseCurrency;
  /**
   * 计价货币 例人民币/美元/BTC
   */
  private Integer quoteCurrency;
  /**
   * 币种对符号 例如：etc_cny
   */
  private String code;
  /**
   * 最小委托量
   */
  @JSONField(serializeUsing = BigDecimalSerializer.class)
  private BigDecimal minTradeSize;
  /**
   * 交易价格小数位数
   */
  private Byte maxPriceDigit;
  /**
   * 交易数量小数位数
   */
  private Byte maxSizeDigit;
  /**
   * 市场来源编号
   */
  private Integer marketId;
  /**
   * 是否在线
   */
  private Byte online;
}
