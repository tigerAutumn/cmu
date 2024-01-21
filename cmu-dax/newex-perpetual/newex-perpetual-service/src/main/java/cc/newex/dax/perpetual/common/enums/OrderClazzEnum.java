package cc.newex.dax.perpetual.common.enums;

import lombok.AllArgsConstructor;

/**
 * 订单类型：10:限价 11:市价 12:计划委托 13:强平单 14:爆仓单
 *
 * @author xionghui
 * @date 2018/11/13
 */
@AllArgsConstructor
public enum OrderClazzEnum {
  TRADE(0), // 下单
  CANCEL(1), // 撤单
  ;

  private final int clazz;

  public int getClazz() {
    return this.clazz;
  }
}
