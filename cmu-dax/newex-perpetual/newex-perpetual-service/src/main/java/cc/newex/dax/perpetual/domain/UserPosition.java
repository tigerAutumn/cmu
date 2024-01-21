package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户合约持仓数据表
 *
 * @author newex-team
 * @date 2018-11-20 21:08:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPosition {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 基础货币名,如BTC、ETH
     */
    private String base;

    /**
     * 计价货币名，USD,CNY,USDT
     */
    private String quote;

    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;

    /**
     * 0全仓，1逐仓
     */
    private Integer type;

    /**
     * 杠杆
     */
    private BigDecimal lever;

    /**
     * 档位
     */
    private BigDecimal gear;

    /**
     * 仓位类型，long多，short空
     */
    private String side;

    /**
     * 持仓数量
     */
    private BigDecimal amount;

    /**
     * 正在平仓的张数
     */
    private BigDecimal closingAmount;

    /**
     * 开仓保证金
     */
    private BigDecimal openMargin;

    /**
     * 维持保证金
     */
    private BigDecimal maintenanceMargin;

    /**
     * 维持仓位冻结手续费
     */
    private BigDecimal fee;

    /**
     * 开仓价格: 仓位平均价
     */
    private BigDecimal price;

    /**
     * 仓位价值
     */
    private BigDecimal size;

    /**
     * 预强平价
     */
    private BigDecimal preLiqudatePrice;

    /**
     * 强平价
     */
    private BigDecimal liqudatePrice;

    /**
     * 破产价
     */
    private BigDecimal brokerPrice;

    /**
     * 已实现盈余
     */
    private BigDecimal realizedSurplus;

    /**
     * 挂单保证金
     */
    private BigDecimal orderMargin;

    /**
     * 挂单冻结手续费
     */
    private BigDecimal orderFee;

    /**
     * 多单的张数
     */
    private BigDecimal orderLongAmount;

    /**
     * 空单的张数
     */
    private BigDecimal orderShortAmount;

    /**
     * 多单的价值
     */
    private BigDecimal orderLongSize;

    /**
     * 空单的价值
     */
    private BigDecimal orderShortSize;

    /**
     * 业务方ID
     */
    private Integer brokerId;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}