package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 账户资产
 *
 * @author newex-team
 * @date 2019-01-11 16:50:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserBalance {
    /**
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 是否测试币 0:线上币,1:测试币
     */
    private Integer env;

    /**
     * 测试币领取时间
     */
    private Long rewardTime;

    /**
     * 冻结状态：0未冻结，1冻结
     */
    private Integer frozenStatus;

    /**
     * 可用余额
     */
    private BigDecimal availableBalance;

    /**
     * 冻结余额
     */
    private BigDecimal frozenBalance;

    /**
     * 仓位资产：正数为多仓，负数为空仓
     */
    private BigDecimal positionSize;

    /**
     * 开仓总保证金
     */
    private BigDecimal positionMargin;

    /**
     * 仓位总冻结手续费
     */
    private BigDecimal positionFee;

    /**
     * 挂单总保证金
     */
    private BigDecimal orderMargin;

    /**
     * 挂单冻结总手续费
     */
    private BigDecimal orderFee;

    /**
     * 总已实现盈余
     */
    private BigDecimal realizedSurplus;

    /**
     * 业务方ID
     */
    private Integer brokerId;

    /**
     * 用户状态，0 正常，1 强平中，2 爆仓中
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date modifyDate;
}