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
public class UserAssetsBalanceDTO {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 可用余额
     */
    private BigDecimal availableBalance;

}
