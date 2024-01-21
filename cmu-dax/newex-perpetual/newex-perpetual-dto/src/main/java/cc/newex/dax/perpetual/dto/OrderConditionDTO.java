package cc.newex.dax.perpetual.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConditionDTO {
  /**
   */
  private Long id;

  /**
   * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
   */
  private String contractCode;

  /**
   * 基础货币名,如btc、fbtc
   */
  private String base;

  /**
   * 计价货币名，usd,cny,usdt
   */
  private String quote;

  /**
   * 方向 0:正向,1:反向
   */
  private Integer contractDirection;

  /**
   * 触发类型：指数价格，标记价格，最新价格
   */
  private String type;

  /**
   * 触发方向，greater大于，less小于
   */
  private String direction;

  /**
   * 触发价格
   */
  private String triggerPrice;

  /**
   * 用户订单委托或者破产价格
   */
  private String price;
    /**
     * 仓位方向，long多，short空
     */
    private String side;

  /**
   * 1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short
   */
  private String detailSide;

  /**
   * 委托数量
   */
  private String amount;


  /**
   * 平均成交价格
   */
  private String avgPrice;

  /**
   * 已成交数量
   */
  private String dealAmount;

  /**
   * 委托价值
   */
  private String orderSize;

  /**
   * 已经成交价值
   */
  private String dealSize;

  /**
   * 0 未触发 1 已经触发 -1 已经撤销
   */
  private Integer status;

  /**
   * 10:限价 11:市价 13:强平单 14:爆仓单
   */
  private Integer systemType;

  /**
   * 创建时间
   */
  private Date createdDate;

}
