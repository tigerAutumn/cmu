package cc.newex.dax.perpetual.domain.bean;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.exception.CloneFailedException;

import java.math.BigDecimal;

/**
 * short订单，支持clone
 *
 * @author xionghui
 * @date 2018/11/05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShortOrder implements Cloneable {
  /**
   */
  private Long id;

  /**
   * 订单id
   */
  private Long orderId;

  /**
   * 用户id
   */
  private Long userId;

  /**
   * 0:下单 1:撤单
   */
  private Integer clazz;

  /**
   * 被动委托：0:不care 1:只做maker，如何是taker就撤单
   */
  private Integer mustMaker;

  /**
   * 仓位方向，long多，short空
   */
  private String side;

  /**
   * 委托数量
   */
  private BigDecimal amount;

  /**
   * 用户订单委托或者破产价格
   */
  private BigDecimal price;

  /**
   * 已成交数量
   */
  private BigDecimal dealAmount;

  /**
   * 穿仓价值
   */
  private BigDecimal brokerSize;

  /**
   * 0 等待成交 1 部分成交 2 已经成交 -1 已经撤销
   */
  private Integer status;

  /**
   * 10:限价 11:市价 13:强平单 14:爆仓单
   */
  private Integer systemType;

  /**
   * 订单关联id
   */
  private Long relationOrderId;

  /**
   * 业务方ID
   */
  private Integer brokerId;

  /**
   * 以下为临时变量
   */

  // 成交方向
  private String otherSide;
  // 当次成交数量
  private BigDecimal currentAmount;
  // 当次成交价
  private BigDecimal currentPrice;
  // 当次成交额
  private BigDecimal currentSize;
  // 穿仓金额，负数为保险金账号减钱，正数为保险金账号加钱
  private BigDecimal lostSize;
  // cancel订单理由
  private int reason;

  @Override
  public ShortOrder clone() {
    try {
      return (ShortOrder) super.clone();
    } catch (final CloneNotSupportedException e) {
      throw new CloneFailedException(e);
    }
  }
}
