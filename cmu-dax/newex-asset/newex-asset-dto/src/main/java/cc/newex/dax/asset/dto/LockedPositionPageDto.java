package cc.newex.dax.asset.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

/**
 * 给后台管理系统返回的数据
 *
 * @author lilaizhen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LockedPositionPageDto {

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
     * 币种名称
     */
    private String currencyName;
    /**
     * 原锁仓数量
     */
    private String amount;
    /**
     * 当前锁仓数量
     */
    private String lockAmount;

    private String unlockAmount;
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
    /**
     * 状态 0锁定失败 1已锁定
     */
    private Integer status;
    /**
     * 释放规则
     */
    private String releaseRule;

    private Integer brokerId;
}
