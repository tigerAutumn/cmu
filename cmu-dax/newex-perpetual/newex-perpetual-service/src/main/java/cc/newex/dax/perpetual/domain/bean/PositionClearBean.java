package cc.newex.dax.perpetual.domain.bean;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PositionClearBean {
    /**
     * 持仓数量
     */
    private BigDecimal amount;
    /**
     * 最新成交价
     */
    private BigDecimal lastPrice;
    /**
     * 标记价格
     */
    private BigDecimal markPrice;
    /**
     * 资金费率
     */
    private BigDecimal feeRate;
    /**
     * 资金费用
     */
    private BigDecimal sumSize;

    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;

    /**
     * 已实现盈亏
     */
    private BigDecimal realizedSurplus;

    /**
     * 创建时间
     */
    private Date createdDate;
}
