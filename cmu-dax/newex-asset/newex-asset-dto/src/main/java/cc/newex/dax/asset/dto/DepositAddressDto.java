package cc.newex.dax.asset.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author
 * @date 2018-04-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DepositAddressDto {

    boolean needTag = false;

    String tagField;

    String alert;

    Long userId;
    BigDecimal minDepositAmount;
    private int depositConfirm;
    private Integer rechargeable;
    /**
     *
     */
    private String address;
    /**
     *
     */
    private Integer currency;
    /**
     *
     */
    private Integer biz;

    private Integer needAlert;

    private Integer withdrawConfirm;


}