package cc.newex.dax.asset.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 锁仓记录表
 *
 * @author newex-team
 * @date 2018-07-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LockedPositionDto {
    /**
     * 币种
     */
    private Integer currency;

    /**
     * 币种名称
     */
    private String currencyName;
    /**
     * 原锁仓数量
     */
    private BigDecimal amount;
    /**
     * 当前锁仓数量
     */
    private BigDecimal lockAmount;

    private BigDecimal unlockAmount;
    /**
     * 锁仓类型
     */
    private Long lockPositionType;
    /**
     * 锁仓类型描述
     */
    private String lockPositionName;
    /**
     * 是否参与分红
     */
    private Integer dividend;
    /**
     * 释放时间
     */
    private Date releaseDate;
    /**
     * 下次释放时间
     */
    private Date nextReleaseDate;
    /**
     * 描述
     */
    private String remark;
    /**
     * 锁仓时间
     */
    private Date createDate;
    /**
     * 更新时间 没次释放时间
     */
    private Date updateDate;

    private Integer status;
    /**
     * 释放规则
     */
    private String releaseRule;
}