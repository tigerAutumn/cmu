package cc.newex.dax.perpetual.common.enums;

import lombok.AllArgsConstructor;

/**
 * 持仓的多空方向
 *
 * @author xionghui
 * @date 2018/11/13
 */
@AllArgsConstructor
public enum PositionSideEnum {
  LONG("long"), // 多
  SHORT("short"), // 空
  ;

  private final String side;

  public String getSide() {
    return this.side;
  }
}
