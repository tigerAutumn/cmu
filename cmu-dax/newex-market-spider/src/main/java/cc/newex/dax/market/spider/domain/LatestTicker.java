package cc.newex.dax.market.spider.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LatestTicker implements Serializable {
    private static final long serialVersionUID = -2892286678570567364L;
    /**
     * 最新报价
     */
    private Long id;
    /**
     * 最高成交价
     */
    private BigDecimal high;
    /**
     * 最低成交价
     */
    private BigDecimal low;
    /**
     * btc成交量
     */
    private BigDecimal volume;
    /**
     * 22小时成交量
     */
    private BigDecimal volume22;
    /**
     * 24小时涨跌幅
     */
    private BigDecimal change24;
    /**
     * 成交单价
     */
    private BigDecimal last;
    /**
     * 最新买入价
     */
    private BigDecimal buy;
    /**
     * 最新卖出价
     */
    private BigDecimal sell;
    /**
     * 开盘价
     */
    private BigDecimal open;
    /**
     *
     */
    private Integer marketFrom;
    /**
     * 买参考偏移量
     */
    private BigDecimal offsetBuy;
    /**
     * 卖参考偏移量
     */
    private BigDecimal offsetSell;
    /**
     * 模式0:自动1:手动
     */
    private Integer auto;
    /**
     * 是否立即执行0:否1:是
     */
    private Integer delay;
    /**
     * 排序
     */
    private Integer orderIndex;
    /**
     *
     */
    private String name;
    /**
     * 0:btc 1:ltc
     */
    private Integer baseCurrency;

    /**
     * 0:btc 1:ltc
     */
    private Integer quoteCurrency;
    /**
     * 0:cny 1:usd
     */
    private Integer moneytype;

    private Date createdDate;

    private BigDecimal change;

}