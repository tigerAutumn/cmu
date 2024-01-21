package cc.newex.dax.perpetual.common.enums;

import cc.newex.dax.perpetual.common.exception.BizException;
import lombok.AllArgsConstructor;

/**
 * 订单的做多做空方向：多为买或平卖，空为卖或者平买
 *
 * @author xionghui
 * @date 2018/11/13
 */
@AllArgsConstructor
public enum OrderDetailSideEnum {
  OPEN_LONG("open_long"), // 开多
  OPEN_SHORT("open_short"), // 开空
  CLOSE_LONG("close_long"), // 平多
  CLOSE_SHORT("close_short"), // 平空
  ;

  private final String side;

  public String getSide() {
    return this.side;
  }

  public static OrderSideEnum getOrderSideEnum(final String side) {
    if (OPEN_LONG.side.equals(side) || CLOSE_SHORT.side.equals(side)) {
      return OrderSideEnum.LONG;
    } else if (OPEN_SHORT.side.equals(side) || CLOSE_LONG.side.equals(side)) {
      return OrderSideEnum.SHORT;
    }
    throw new BizException(BizErrorCodeEnum.ILLEGAL_PARAM);
  }

  /**
   * 判断是否为开仓
   *
   * @param side
   * @return
   */
  public static boolean isOpen(final String side) {
    if (side.equals(OrderDetailSideEnum.OPEN_LONG.getSide())
        || side.equals(OrderDetailSideEnum.OPEN_SHORT.getSide())) {
      return true;
    }
    return false;

  }

  /**
   * 判断是否为平仓
   *
   * @param side
   * @return
   */
  public static boolean isClose(final String side) {
    if (side.equals(OrderDetailSideEnum.CLOSE_LONG.getSide())
        || side.equals(OrderDetailSideEnum.CLOSE_SHORT.getSide())) {
      return true;
    }
    return false;
  }
}
