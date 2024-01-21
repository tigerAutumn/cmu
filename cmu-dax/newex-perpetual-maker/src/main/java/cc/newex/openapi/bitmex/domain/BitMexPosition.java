package cc.newex.openapi.bitmex.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liutiejun
 * @date 2019-04-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BitMexPosition {

    private Long account;

    private String symbol;

    private String currency;

    private Double commission;

    /**
     * 起始保证金
     */
    private Double initMarginReq;

    /**
     * 维持保证金
     */
    private Double maintMarginReq;

    /**
     * 风险限额
     */
    private Long riskLimit;

    /**
     * leverage: 1 / initMarginReq.
     */
    private Double leverage;

    /**
     * Indicates where your position is in the ADL queue
     */
    private Integer deleveragePercentile;

    private Long rebalancedPnl;

    private Long prevRealisedPnl;

    private Long prevUnrealisedPnl;

    private Double prevClosePrice;

    private Integer openingQty;

    private Long openingCost;

    /**
     * 当前仓位数量
     */
    private Integer currentQty;

    private Long currentCost;

    private Long realisedCost;

    private Long unrealisedCost;

    /**
     * 标记价格
     */
    private Double markPrice;

    private Long markValue;

    /**
     * 已实现盈亏
     */
    private Long realisedPnl;

    /**
     * 未实现盈亏
     */
    private Long unrealisedPnl;

    private Double avgCostPrice;

    /**
     * 平均开仓价格
     */
    private Double avgEntryPrice;

    /**
     * 强平价格
     */
    private Double liquidationPrice;

    /**
     * 破产价格
     */
    private Double bankruptPrice;

    private Double lastPrice;

}
