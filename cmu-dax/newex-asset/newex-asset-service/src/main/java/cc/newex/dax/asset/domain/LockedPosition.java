package cc.newex.dax.asset.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 锁仓记录表
 *
 * @author newex-team
 * @date 2018-09-17 19:35:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LockedPosition {
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
     * 原锁仓数量
     */
    private BigDecimal amount;

    /**
     * 当前锁仓数量
     */
    private BigDecimal lockAmount;

    /**
     * 锁仓类型描述
     */
    private String lockPositionName;

    /**
     * 是否参与分红
     */
    private Integer dividend;

    /**
     */
    private Date releaseDate;

    /**
     */
    private Date nextReleaseDate;

    /**
     * 解锁计划
     */
    private String releaseContent;

    /**
     * 状态 0锁定失败 1已锁定
     */
    private Integer status;

    /**
     * 描述
     */
    private String remark;

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