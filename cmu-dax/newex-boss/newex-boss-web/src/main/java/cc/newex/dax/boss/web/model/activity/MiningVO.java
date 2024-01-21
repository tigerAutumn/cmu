package cc.newex.dax.boss.web.model.activity;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MiningVO {

    /**
     * id
     */
    private Long actId;
    /**
     * 活动id
     */
    private Long activityIdd;
    /**
     * 语言
     */
    private String language;
    /**
     * 标题
     */
    private String title;
    /**
     * cn
     */
    private String titleCN;
    /**
     * en
     */
    private String titleEN;
    /**
     * 是否结束: 0:未结束，1:已结束
     */
    private Integer finishFlag;
    /**
     *
     */
    private String status;
    private String startDate;
    private String endDate;
    /**
     * 挖矿币种
     */
    private String currencyCode;
    /**
     * 矿池总量
     */
    private BigDecimal totalLimit;
    /**
     * 挖矿币对id,逗号分隔
     */
    private String productIds;
    /**
     * 奖励
     */
    private BigDecimal reward;
    /**
     * 挖矿每日上限
     */
    @NotNull()
    private BigDecimal dailyLimit;
    /**
     * 单人单日挖矿上限
     */
    private BigDecimal personDailyLimit;
    /**
     * 手续费返还持仓用户比例
     */
    private BigDecimal feeHoldReturn;
    /**
     * 手续费返还项目方比例
     */
    private BigDecimal feeProjectReturn;
    /**
     * 个人返还持仓上限
     */
    private BigDecimal personHoldLimit;
    /**
     * 项目账号
     */
    private Long projectAccount;
    /**
     * 项目方是否返还: 0:未返还，1:已返还
     */
    private Integer projectReturnFlag;
    /**
     * 当前持有矿币用户数
     */
    private Integer personHolds;
    /**
     * 参与用户数
     */
    private Integer personMining;
    /**
     * 送矿币折合BTC
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal miningReward;
    /**
     * 当前平台矿币总量
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal platformMinings;
    /**
     * 挖矿产出总量
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal totalMinings;
    /**
     * 有效持仓总量
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal effectiveHoldMinings;
    /**
     * 返还手续费折合BTC
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal feeReward;
    /**
     * 全部返还折合BTC
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal totalReward;
    /**
     * 活动解释链接
     */
    private String explainLink;

    /**
     * 是否页面展示
     */
    private Integer online;

}
