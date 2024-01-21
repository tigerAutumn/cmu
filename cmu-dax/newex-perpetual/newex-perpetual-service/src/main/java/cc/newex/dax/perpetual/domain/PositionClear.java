package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户持仓清算流水表
 *
 * @author newex-team
 * @date 2019-01-09 14:12:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PositionClear {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 业务方ID
     */
    private Integer brokerId;

    /**
     * 资金费率
     */
    private BigDecimal feeRate;

    /**
     * 0全仓，1逐仓
     */
    private Integer type;

    /**
     * 0未处理，1已处理
     */
    private Integer status;

    /**
     * 仓位方向，long多，short空
     */
    private String side;

    /**
     * 基础货币名,如BTC、FBTC
     */
    private String base;

    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;

    /**
     * 合约 id
     */
    private Long contractId;

    /**
     * 扣除或增加可用余额
     */
    private BigDecimal availableBalance;

    /**
     * 扣除或增加开仓总保证金
     */
    private BigDecimal openMargin;

    /**
     * 扣除或增加挂单总保证金
     */
    private BigDecimal orderMargin;

    /**
     * 扣除或增加挂单冻结总手续费
     */
    private BigDecimal orderFee;

    /**
     * 前余额
     */
    private BigDecimal beforeBalance;

    /**
     * 后余额
     */
    private BigDecimal afterBalance;

    /**
     * 最新成交价
     */
    private BigDecimal lastPrice;

    /**
     * 已实现盈余
     */
    private BigDecimal realizedSurplus;

    /**
     * 持仓张数
     */
    private BigDecimal amount;

    /**
     * 挂单冻结总手续费
     */
    private BigDecimal sumSize;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     */
    private BigDecimal markPrice;
}