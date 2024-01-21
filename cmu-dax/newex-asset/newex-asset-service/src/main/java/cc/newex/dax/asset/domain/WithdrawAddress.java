package cc.newex.dax.asset.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author newex-team
 * @date 2018-09-17 19:58:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WithdrawAddress {
    /**
     */
    private Integer id;

    /**
     */
    private Long userId;

    /**
     */
    private String address;

    /**
     */
    private Integer currency;

    /**
     */
    private Integer biz;

    /**
     */
    private Byte status;

    /**
     */
    private String remark;

    /**
     */
    private Date createDate;

    /**
     */
    private Date modifyDate;

    /**
     * 券商id
     */
    private Integer brokerId;
}