package cc.newex.dax.perpetual.dto;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LatestTickerDTO {

  /**
   * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
   */
  private String contractCode;

  /**
   * 最高成交价
   */
  private String high;

  /**
   * 最低成交价
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
   * 原始成交价
   */
  private String first;

  /**
   * 最新成交价
   */
  private String last;

  /**
   * 24小时价格涨跌幅
   */
  private String change24;

  /**
   * 24小时价格涨跌幅比例
   */
  private String changePercentage;

  /**
   * 盘口最高买价
   */
  private String buy;

  /**
   * 盘口最低卖价
   */
  private String sell;

  /**
   * 创建时间
   */
  private Date createdDate;
}
