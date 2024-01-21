package cc.newex.dax.boss.web.model.asset;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 锁仓数据
 *
 * @author liutiejun
 * @date 2018-07-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LockedPositionPageVO {
    /**
     * 用户ID
     */
    private Long userId;

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

    /**
     * 已解锁数量
     */
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
     * 是否参与分红，0否，1是
     */
    private Integer dividend;

    /**
     * 释放时间
     */
    private String releaseDate;

    /**
     * 锁仓时间
     */
    private Date createDate;

    /**
     * 更新时间 没次释放时间
     */
    private Date updateDate;

}
