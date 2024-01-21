package cc.newex.dax.perpetual.openapi.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDTO {
    /**
     * 币种
     */
    private String currencyCode;
    /**
     * 可用余额
     */
    private BigDecimal availableBalance;

    /**
     * 开仓总保证金
     */
    private BigDecimal positionMargin;
    /**
     * 挂单总保证金
     */
    private BigDecimal orderMargin;
    /**
     * BTC估值
     */
    private BigDecimal valuation;
}
