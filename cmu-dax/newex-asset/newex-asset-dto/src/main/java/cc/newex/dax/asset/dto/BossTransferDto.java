package cc.newex.dax.asset.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author lilaizhen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BossTransferDto {
    private String fromBiz;
    private String toBiz;
    private String currency;
    private Long userId;
    private BigDecimal amount;
    private Integer brokerId;
}
