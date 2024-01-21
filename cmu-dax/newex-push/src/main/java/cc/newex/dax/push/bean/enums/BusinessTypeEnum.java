package cc.newex.dax.push.bean.enums;

/**
 * 业务类型
 *
 * @author xionghui
 * @date 2018/09/13
 */
public enum BusinessTypeEnum {
  /**
   * 现货
   */
  SPOT("spot"), //

  /**
   * 指数
   */
  INDEXES("indexes"), //

  /**
   * 组合
   */
  PORTFOLIO("portfolio"), //

  /**
   * 合约
   */
  PERPETUAL("perpetual"), //
  ;

  private final String type;

  BusinessTypeEnum(final String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }
}
