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
public class WithDrawInfoDto {
    private BigDecimal withdrawLimitBTC;
    private BigDecimal withdrawBTC;
    private BigDecimal canNotWithdraw;
    private BigDecimal canWithdraw;
    private BigDecimal canUsedWithdrawBTC;
    private BigDecimal allUnconfirmedBTC;
    private BigDecimal canUsedBTC;
    private BigDecimal allCanUsedBTC;
    private BigDecimal allConfirmedBTC;
    private BigDecimal miniAmount;
    private BigDecimal fee;
    private Integer needTag;
    private String tagField;
    private String alert;
    private Integer needAlert;
    private Integer withdrawable;
    private String AlertInfo;
}
