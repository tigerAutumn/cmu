package cc.newex.dax.perpetual.common.enums;

import lombok.AllArgsConstructor;

/**
 * 订单类型：10:限价 11:市价 12:计划委托 13:强平单 14:爆仓单
 *
 * @author xionghui
 * @date 2018/11/13
 */
@AllArgsConstructor
public enum OrderSystemTypeEnum {
  LIMIT(10), // 限价
  MARKET(11), // 市价
  FORCED_LIQUIDATION(13), // 强平
  EXPLOSION(14), // 爆仓
  CONTRA_TRADE_SOURCE(15), // 对敲
  CONTRA_TRADE_TARGET(16), // 被对敲

  MARGIN_AUTO_CANCEL(20), // 保证金不够自动取消，该type只用于标记，不是真正的类型
  SETTLEMENT_CANCEL(21), // 清算取消订单，该type只用于标记，不是真正的类型
  BROKER_PRICE_CANCEL(22), // 订单价格不合法，低于或者高于破产价格
  ;

  private final int systemType;

  public int getSystemType() {
    return this.systemType;
  }
}
