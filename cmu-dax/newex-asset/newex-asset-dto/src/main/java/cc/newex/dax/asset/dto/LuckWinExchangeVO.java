package cc.newex.dax.asset.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author newex
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LuckWinExchangeVO {
    private BigDecimal amount;
    private String luckyWinAddress;
    private String emailCode;
    private String smsCode;
    private String googleCode;
}
