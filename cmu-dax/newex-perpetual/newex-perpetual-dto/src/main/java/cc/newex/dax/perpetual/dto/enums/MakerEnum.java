package cc.newex.dax.perpetual.dto.enums;

/**
 * maker和taker枚举
 *
 * @author xionghui
 * @date 2018/11/14
 */
public enum MakerEnum {
  BOTH(0), // 双向
  MAKER(1), // 挂单方向
  TAKER(2), // 吃单方向
  ;

  private int code;

  MakerEnum(final int code) {
    this.code = code;
  }

  public int getCode() {
    return this.code;
  }

  public void setCode(final int code) {
    this.code = code;
  }
}
