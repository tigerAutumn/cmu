package cc.newex.dax.perpetual.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarkIndexPriceDTO {
    /**
     * 币对
     */
    private String contractCode;
    /**
     * 计价货币
     */
    private String quoteCurrency;
    /**
     * 交易货币
     */
    private String baseCurrency;
    /**
     * 标记价格
     */
    private BigDecimal markPrice;
    /**
     * 指数价格
     */
    private BigDecimal indexPrice;
    /**
     * 资金费率
     */
    private BigDecimal feeRate;
    /**
     * 预估的资金费率
     */
    private BigDecimal estimateFeeRate;
    /**
     * 最新成交价
     */
    private BigDecimal lastPrice;
}