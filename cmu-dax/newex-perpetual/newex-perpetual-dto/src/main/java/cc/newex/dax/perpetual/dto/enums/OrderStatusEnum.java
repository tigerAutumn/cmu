package cc.newex.dax.perpetual.dto.enums;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;

/**
 * 订单状态
 *
 * @author xionghui
 * @date 2018/11/14
 */
@AllArgsConstructor
public enum OrderStatusEnum {
  NOT_DEAL(0), // 未成交
  PART_DEAL(1), // 部分成交
  COMPLETE(2), // 全部成交
  CANCELING(3), // 撤单中
  CANCELED(-1), // 已撤单
  ;

  private static final Set<Integer> FINISH_ENUM_SET = new HashSet<>();
  static {
    FINISH_ENUM_SET.add(OrderStatusEnum.NOT_DEAL.getCode());
    FINISH_ENUM_SET.add(OrderStatusEnum.PART_DEAL.getCode());
    FINISH_ENUM_SET.add(OrderStatusEnum.CANCELING.getCode());
  }

  private final int code;

  public int getCode() {
    return this.code;
  }

  public static boolean isStatusNotFinish(int code) {
    return FINISH_ENUM_SET.contains(code);
  }
}
