package cc.newex.dax.perpetual.dto;

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
public class UserLeverDTO {
    /**
     * 杠杆类型 全仓:0,逐仓:1
     */
    private Integer type;
    /**
     * 杠杆
     */
    private BigDecimal lever;
    /**
     * 风险限额
     */
    private BigDecimal gear;


}
