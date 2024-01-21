package cc.newex.dax.perpetual.domain.commonprop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionConfig {
    /**
     * 数量
     */
    private int amount;
    /**
     * 开仓保证金率
     */
    private BigDecimal openMarginRate;
    /**
     * 维持保证净率
     */
    private BigDecimal maintenanceMarginRate;
}
