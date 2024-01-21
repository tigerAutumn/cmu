package cc.newex.dax.asset.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @author newex-team
 * @date 2018-09-17 19:27:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositAddress {
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
    private Date createDate;

    /**
     */
    private Date modifyDate;

    /**
     * 券商id
     */
    private Integer brokerId;
}