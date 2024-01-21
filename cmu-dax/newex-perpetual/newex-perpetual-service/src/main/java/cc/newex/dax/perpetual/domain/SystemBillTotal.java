package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对账汇总表
 *
 * @author newex-team
 * @date 2019-01-11 19:12:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SystemBillTotal {
    /**
     * id
     */
    private Long id;

    /**
     * 上次一最大的系统手续费id
     */
    private Long systemBillId;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 变动手续费总和
     */
    private BigDecimal fee;

    /**
     * 变动收益总和
     */
    private BigDecimal profit;

    /**
     * 用户资产余额总和
     */
    private BigDecimal userBalance;

    /**
     * 用户持仓价值总和
     */
    private BigDecimal positionSize;

    /**
     * 用户手续费总和
     */
    private BigDecimal totalFee;

    /**
     * 用户账单收益总和
     */
    private BigDecimal totalProfit;

    /**
     * 人工调整
     */
    private BigDecimal baseAdjust;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}