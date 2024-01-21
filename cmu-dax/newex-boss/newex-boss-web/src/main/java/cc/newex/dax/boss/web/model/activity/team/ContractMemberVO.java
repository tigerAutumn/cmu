package cc.newex.dax.boss.web.model.activity.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 团队成员信息
 *
 * @author newex-team
 * @date 2018-12-18 12:17:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractMemberVO {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 团队id
     */
    private Long teamId;

    /**
     * 团队成员userId
     */
    private Long userId;

    /**
     * 用户名，手机号或者邮箱
     */
    private String username;

    /**
     * 邀请人id
     */
    private Long inviteId;

    /**
     * 币种，和合约模拟赛中的合约编码对应
     */
    private String currencyCode;

    /**
     * 总资产
     */
    private BigDecimal balance;

    /**
     * 当前盈亏
     */
    private BigDecimal pnl;

    /**
     * 今日活跃度
     */
    private Long todayLiveness;

    /**
     * 累积活跃度
     */
    private Long liveness;

    /**
     * 期数
     */
    private Integer term;

    /**
     * 虚拟总资产
     */
    private BigDecimal virtualBalance;

    /**
     * 虚拟盈亏
     */
    private BigDecimal virtualPnl;

    /**
     * 状态，0-正常，1-禁用
     */
    private Integer status;

    /**
     * 券商Id
     */
    private Integer brokerId;
}