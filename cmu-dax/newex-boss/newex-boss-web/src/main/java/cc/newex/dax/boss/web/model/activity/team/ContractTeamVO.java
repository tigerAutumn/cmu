package cc.newex.dax.boss.web.model.activity.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合约团队信息
 *
 * @author better
 * @date create in 2018-12-18 15:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractTeamVO {

    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 团队唯一标识
     */
    private String uid;

    /**
     * 团队名称
     */
    private String teamName;

    /**
     * 团队人数
     */
    private Integer teamNumber;

    /**
     * 团队头像
     */
    private String avatar;

    /**
     * 宣传语
     */
    private String slogan;

    /**
     * 团长id
     */
    private Long leaderId;

    /**
     * 团长名称
     */
    private String leaderName;

    /**
     * 团长账号
     */
    private String leaderAccount;

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
     * 活跃度
     */
    private Long liveness;

    /**
     * 期数
     */
    private Integer term;

    /**
     * 状态，0-正常，1-禁用
     */
    private Integer status;

    /**
     * 券商Id
     */
    private Integer brokerId;

    /**
     * 战队名称-英语
     */
    private String teamNameEnUs;

    /**
     * 战队宣传语-英语
     */
    private String sloganEnUs;

    /**
     * 团长名称-英语
     */
    private String leaderNameEnUs;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;
}
