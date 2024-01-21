package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 最新成交
 *
 * @author newex-team
 * @date 2018-12-07 16:30:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LatestTicker {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;

    /**
     * 最高成交价
     */
    private BigDecimal high;

    /**
     * 最低成交价
     */
    private BigDecimal low;

    /**
     * 24小时成交张数
     */
    private BigDecimal amount24;

    /**
     * 24小时成交价值
     */
    private BigDecimal size24;

    /**
     * 原始成交价
     */
    private BigDecimal first;

    /**
     * 最新成交价
     */
    private BigDecimal last;

    /**
     * 24小时价格涨跌幅
     */
    private BigDecimal change24;

    /**
     * 盘口最高买价
     */
    private BigDecimal buy;

    /**
     * 盘口最低卖价
     */
    private BigDecimal sell;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}