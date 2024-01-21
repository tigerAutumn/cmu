package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 溢价指数流水表
 *
 * @author newex-team
 * @date 2018-11-20 18:26:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryMarkPrice {
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
     * 指数价格
     */
    private BigDecimal indexPrice;

    /**
     * 标记价格
     */
    private BigDecimal markPrice;

    /**
     * 溢价指数
     */
    private BigDecimal premiumIndex;

    /**
     * 深度加权买价
     */
    private BigDecimal askPrice;

    /**
     * 深度加权卖价
     */
    private BigDecimal bidPrice;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}