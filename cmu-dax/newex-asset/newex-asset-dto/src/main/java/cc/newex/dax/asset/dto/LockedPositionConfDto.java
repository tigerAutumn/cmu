package cc.newex.dax.asset.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * //@author newex-team
 * //@data 07/04/2018
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LockedPositionConfDto implements Serializable {
    /**
     *
     */
    private Long id;
    /**
     * 锁仓类型
     */
    private String lockPositionName;
    /**
     * 币种
     */
    private Integer currency;
    /**
     * 锁仓数量
     */
    private String amount;
    /**
     * 解锁计划
     */
    private String unlockedPosition;
    /**
     * 是否参与分红
     */
    private Integer dividend;
    /**
     * 描述
     */
    private String remark;
    /**
     *
     */
    private Date createDate;

    private String currencyName;

    /**
     * 业务类型 1.具体时间 2按周期 --传入分钟数
     */
    private Integer releaseType;
    /**
     * 币种来源 1.用户账户 2是系统账户 暂时不用
     */
    private Integer currencySource;

}
