package cc.newex.dax.boss.web.model.asset;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 锁仓计划
 *
 * @author liutiejun
 * @date 2018-07-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LockedPositionConfVO {

    private Long id;

    /**
     * 锁仓用户账户，保护手机号、邮箱，最多20个，用逗号隔开
     */
    @NotBlank
    private String accounts;

    /**
     * 锁仓名称
     */
    @NotBlank
    private String lockPositionName;

    /**
     * 锁仓币种
     */
    @NotNull
    private Integer currency;

    /**
     * 币种来源，1-用户账户，2-系统账户
     */
    private Integer currencySource;

    /**
     * 锁仓数量
     */
    @NotNull
    private BigDecimal amount;

    /**
     * 是否参与分红，0否，1是
     */
    @NotNull
    private Integer dividend;

    /**
     * 释放时间，精确到日，格式为：yyyy-MM-dd
     */
    private String unlockTime;

    /**
     * 释放规则，1-每月，2-每天
     */
    private Integer unlockCycle;

    /**
     * 释放的百分比，取值0-100，相对于锁仓数量
     */
    private Integer unlockAmount;

    /**
     * 描述
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 券商ID
     */
    private Integer brokerId;

}
