package cc.newex.dax.perpetual.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金费率流水表
 *
 * @author newex-team
 * @date 2018-12-03 16:35:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AssetsFeeRate {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;

    /**
     * 生成当前数据的时间，如201809040916
     */
    private Date timeIndex;

    /**
     * 资金费率
     */
    private BigDecimal feeRate;

    /**
     * 用户持仓总数
     */
    private BigDecimal userPositionAmount;

    /**
     * 风险准备金
     */
    private BigDecimal insuranceSize;

    /**
     * 24小时成交张数
     */
    private BigDecimal amount24;

    /**
     * 24小时成交价值
     */
    private BigDecimal size24;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}