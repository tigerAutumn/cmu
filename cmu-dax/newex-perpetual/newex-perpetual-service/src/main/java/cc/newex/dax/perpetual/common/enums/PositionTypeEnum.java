package cc.newex.dax.perpetual.common.enums;

import lombok.AllArgsConstructor;

/**
 * 全仓和逐仓
 *
 * @author xionghui
 * @date 2018/11/14
 */
@AllArgsConstructor
public enum PositionTypeEnum {
  ALL_IN(0), // 全仓
  PART_IN(1), // 逐仓
  ;

  private final int type;

  public int getType() {
    return this.type;
  }
}
