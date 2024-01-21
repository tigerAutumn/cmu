package cc.newex.dax.perpetual.domain;

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
public class OrderMarginFee {
    /**
     * 总订单保证金
     */
    private BigDecimal orderMargin;

    /**
     * 订单手续费
     */
    private BigDecimal orderFee;
}