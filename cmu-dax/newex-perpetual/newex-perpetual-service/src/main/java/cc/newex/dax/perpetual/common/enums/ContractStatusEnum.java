package cc.newex.dax.perpetual.common.enums;

public enum ContractStatusEnum {

  NORMAL(0), // 正常
  CLEARING(1), // 清算中
  CLEAR_FINISH(2), // 清算结束
  MATCHING_STOP(3), // 停撮合
  ;

  private final int code;

  ContractStatusEnum(final int code) {
    this.code = code;
  }

  public static ContractStatusEnum fromInteger(final Integer code) {
    if (code == null) {
      return null;
    }

    for (final ContractStatusEnum e : ContractStatusEnum.values()) {
      if (code.equals(e.code)) {
        return e;
      }
    }
    return null;
  }

  public int getCode() {
    return this.code;
  }
}
