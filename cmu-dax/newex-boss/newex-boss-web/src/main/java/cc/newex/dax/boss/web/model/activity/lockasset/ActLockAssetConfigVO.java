package cc.newex.dax.boss.web.model.activity.lockasset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
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
public class ActLockAssetConfigVO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 活动标识
     */
    @NotBlank
    private String actIdentify;

    /**
     * 活动入口地址
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
     * 锁仓币种ID
     */
    @NotNull
    private Integer currencyId;

    /**
     * 币种简称
     */
    private String currencySymbol;

    /**
     * 活动锁仓总量
     */
    @NotNull
    @Min(0)
    private BigDecimal amoutTotal;

    /**
     * 已锁仓量
     */
    @Min(0)
    private BigDecimal amoutUsed;

    /**
     * 单人锁仓上限
     */
    @Min(0)
    private BigDecimal amoutLimitMax;

    /**
     * 单人起购数量
     */
    @Min(0)
    private BigDecimal amoutLimitMin;

    /**
     * 锁仓次数限制
     */
    @Min(0)
    private Integer timesLimit;

    /**
     * 参加锁仓人次基数
     */
    @Min(0)
    private Integer baseCount;

    /**
     * 活动状态（备用）1-未开始，2-进行中，3-已结束
     */
    private Byte status;

    /**
     * 锁仓类型
     */
    @NotNull
    private Byte type;

    /**
     * 首次解锁比例（单位%）
     */
    @Range(min = 0,max = 100,message = "首次解锁比例要求不小于0，不大于1")
    private BigDecimal unlockRateFirst;

    /**
     * 之后每一期的释放比例（单位%）
     */
    @Range(min = 0,max = 100,message = "解锁比例要求不小于0，不大于1")
    private BigDecimal unlockRateRemainder;

    /**
     * 释放周期 1小时，2-天，3-周，4-月 ，5-季度，6-半年，7-年'
     */
    private Byte unit;

    /**
     * 周期长度
     */
    private Integer period;

    /**
     * 是否分红
     */
    private Byte havaReward;

    /**
     * 语言
     */
    @NotBlank
    private String locale;

    /**
     * 活动名称
     */
    @NotBlank
    private String actName;

    /**
     * banner地址
     */
    private String bannerUrl;

    /**
     * 奖励规则（json字符串）
     */
    private String reward;

    /**
     * 活动规则
     */
    private String rules;
}
