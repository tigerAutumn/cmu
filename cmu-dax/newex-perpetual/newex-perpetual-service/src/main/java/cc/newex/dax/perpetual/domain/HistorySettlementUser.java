package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 清算历史记录表
 *
 * @author newex-team
 * @date 2018-11-20 18:26:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HistorySettlementUser {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;

    /**
     * 类型 1.交割 2.清算
     */
    private Integer type;

    /**
     * 交割日期
     */
    private Date settlementDate;

    /**
     * 生成当前数据的时间，如201809040916
     */
    private Long timeIndex;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * before余额
     */
    private BigDecimal beforeBalance;

    /**
     * after余额
     */
    private BigDecimal afterBalance;

    /**
     * after-before余额
     */
    private BigDecimal diffBalance;

    /**
     * 业务方ID
     */
    private Integer brokerId;

    /**
     * 交割合约张数
     */
    private BigDecimal settlementAmount;

    /**
     * 交割平均价
     */
    private BigDecimal settlementPrice;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}