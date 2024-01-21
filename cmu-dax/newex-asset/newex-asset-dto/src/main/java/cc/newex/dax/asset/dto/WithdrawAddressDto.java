package cc.newex.dax.asset.dto;

import lombok.*;

/**
 * @author newex-team
 * @date 2018-04-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WithdrawAddressDto {
    boolean needTag = false;

    String tagField;
    /**
     *
     */
    private Long userId;
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


    /**
     *
     */
    private String remark;
}