package cc.newex.dax.asset.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户账户表
 *
 * @author newex-team
 * @date 2018-09-17 18:12:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {
    /**
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 币种
     */
    private Integer currency;

    /**
     * 锁仓账户
     */
    private BigDecimal accLockPosition;

    /**
     * 状态 0正常 1已锁定
     */
    private Integer status;

    /**
     */
    private Date createDate;

    /**
     */
    private Date updateDate;

    /**
     * 券商id
     */
    private Integer brokerId;
}