package cc.newex.dax.asset.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WithdrawDto implements Serializable {
    private Long userId;
    /**
     *
     */
    private String address;
    /**
     *
     */
    private BigDecimal amount;
    /**
     *
     */
    private BigDecimal fee;
    /**
     *
     */
    private Integer currency;
    /**
     *
     */
    private Integer biz;
}
