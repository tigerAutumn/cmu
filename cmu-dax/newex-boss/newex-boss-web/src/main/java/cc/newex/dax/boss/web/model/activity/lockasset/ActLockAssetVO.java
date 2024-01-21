package cc.newex.dax.boss.web.model.activity.lockasset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author newex-team
 * @date 2018/11/21 16:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActLockAssetVO {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 活动标识
     */
    @NotBlank
    private String actIdentify;

    /**
     * 活动名称
     */
    @NotBlank
    private String actName;

    /**
     * 活动链接
     */
    private String actUrl;

    /**
     * 活动开始时间
     */
    private Date actStartTime;

    /**
     * 活动结束时间
     */
    private Date actEndTime;

    /**
     * 券商ID
     */
    @NotNull
    private Integer brokerId;

    /**
     * 被锁币种ID
     */
    @NotNull
    private Integer currencyId;

    /**
     * 活动锁仓总量
     */
    @NotNull
    private BigDecimal amoutTotal;

    /**
     * 已抢量
     */
    private BigDecimal amoutUsed;

    /**
     * 每个用户限制锁仓量
     */
    private BigDecimal amoutLimitMax;

    /**
     * 起购数量
     */
    private BigDecimal amoutLimitMin;

    /**
     * 允许锁仓次数
     */
    private Integer timesLimit;

    /**
     * 活动状态 1-未开始，2-进行中，3-已结束
     */
    private Byte status;

    /**
     * 语言
     */
    @NotNull
    private String locale;

    /**
     * 锁仓类型
     */
    @NotNull
    private Byte type;

    /**
     * 奖励（json数据）
     */
    private String reward;

    /**
     * 活动banner地址
     */
    private String bannerUrl;

    /**
     * 活动规则
     */
    private String rules;

    /**
     * 参加活动人数
     */
    private Integer count;

}
