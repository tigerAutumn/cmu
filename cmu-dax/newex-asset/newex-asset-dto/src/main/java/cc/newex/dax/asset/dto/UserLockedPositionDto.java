package cc.newex.dax.asset.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * //@author newex-team
 * //@data 07/04/2018
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLockedPositionDto implements Serializable {
    /**
     * 锁仓用户账号（逗号分隔）
     */
    @NotNull
    private List<String> users;
    /**
     * 锁仓名称
     */
    @NotNull
    private String lockedPositionName;
    /**
     * 币种
     */
    @NotNull
    private Integer currency;
    /**
     * 锁仓数量
     */
    @NotNull
    private BigDecimal amount;
    /**
     * 是否参与分红
     */
    @NotNull
    private Integer dividend;
    /**
     * 开始释放时间
     */
    @NotNull
    private Date releaseDate;
    /**
     * 释放频率
     */
    @NotNull
    private Integer releaseFrequency;
    /**
     * 释放比例（百分比）
     */
    @NotNull
    private BigDecimal releaseProportion;
    /**
     * 描述
     */
    private String remark;
    /**
     * 操作人
     */
    private String operator;

    private Integer brokerId;

}
