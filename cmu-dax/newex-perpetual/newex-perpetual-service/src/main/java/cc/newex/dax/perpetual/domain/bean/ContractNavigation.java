package cc.newex.dax.perpetual.domain.bean;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 合约明细
 *
 * @author newex-team
 * @date 2018-11-26
 */
@Data
@Builder
public class ContractNavigation {
  /**
   * 合约code
   */
  private String code;
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
  private Integer direction;
  /**
   * 基础货币最小交易小数位
   */
  private Integer minTradeDigit;
  /**
   * 计价货币最小交易小数位
   */
  private Integer minQuoteDigit;
  /**
   * 最新价
   */
  private String price;
  /**
   * 涨跌幅
   */
  private String fluctuation;
  /**
   * 最高价
   */
  private String high;
  /**
   * 最低价
   */
  private String low;
  /**
   * 24小时成交张数
   */
  private String amount24;
  /**
   * 24小时成交价值
   */
  private String size24;
  /**
   * 持仓量
   */
  private String totalPosition;
  /**
   * 资金费率
   */
  private String fund;
  /**
   * 标记价格
   */
  private String markPrice;
  /**
   * 指数价格
   */
  private String indexPrice;
  /**
   * 一张合约对应的quote面值,默认1
   */
  private BigDecimal unitAmount;
  /**
   * 是否测试盘 0:线上盘,1:测试盘
   */
  private Integer env;
  /**
   * 最大杠杆
   */
  private BigDecimal maxLever;

}
