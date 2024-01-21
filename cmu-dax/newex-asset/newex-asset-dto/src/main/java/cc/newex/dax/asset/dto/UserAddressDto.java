package cc.newex.dax.asset.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author newex-team
 * @data 2018/6/5
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAddressDto implements Serializable {
    /**
     * userID
     */
    @NotNull
    @Range(min = 0)
    private Long userId;
    /**
     * 地址
     */
    private String address;
    /**
     * 币种
     */
    private Integer currency;
    /**
     * spot1 c2c2 future3
     */
    private Integer biz;
    /**
     * 地址类型 1充值deposit 2提现withdraw
     */
    @NotNull
    private Integer type;
    /**
     *
     */
    private Date createDate;
    /**
     *
     */
    private Date modifyDate;
}
