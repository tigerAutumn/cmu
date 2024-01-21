package cc.newex.dax.perpetual.domain.bean;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 当前持仓
 *
 * @author newex-team
 * @date 2018-11-20 21:08:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrentPosition {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 是Base和Quote之间的组合 BTCUSD，BTCUSD1109
     */
    private String contractCode;

    /**
     * 基础货币名,如BTC、ETH
     */
    private String base;

    /**
     * 计价货币名，USD,CNY,USDT
     */
    private String quote;

    /**
     * 基础货币最小交易小数位
     */
    private Integer minTradeDigit;

    /**
     * 计价货币最小交易小数位
     */
    private Integer minQuoteDigit;

    /**
     * 0全仓，1逐仓
     */
    private Integer type;

    /**
     * 仓位类型，long多，short空
     */
    private String side;
    /**
     * 杠杆
     */
    private String lever;
    /**
     * 风险限额
     */
    private String gear;
    /**
     * 成交数量
     */
    private String closingAmount;

    /**
     * 已实现盈亏
     */
    private String realizedSurplus;
    /**
     * 成交均价
     */
    private String price;
    /**
     * 委托数量
     */
    private String amount;
    /**
     * 手续费
     */
    private String fee;
    /**
     * 开仓保证金
     */
    private String openMargin;
    /**
     * 维持保证金
     */
    private String maintenanceMargin;
    /**
     * 强平价
     */
    private String liqudatePrice;
    /**
     * 预强平价
     */
    private String preLiqudatePrice;
    /**
     * 仓位价值
     */
    private String size;
    /**
     * 标记价格
     */
    private String markPrice;
    /**
     * 创建时间
     */
    private Date createdDate;

}