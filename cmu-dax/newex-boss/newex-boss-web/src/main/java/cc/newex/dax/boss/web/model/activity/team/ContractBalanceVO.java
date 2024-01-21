package cc.newex.dax.boss.web.model.activity.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 合约模拟金信息
 *
 * @author newex-team
 * @date 2018-12-18 12:17:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractBalanceVO {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * boss中管理员id
     */
    private Long managerId;

    /**
     * 团队成员userId
     */
    private Long userId;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 模拟金
     */
    private BigDecimal amount;

    /**
     * 券商Id
     */
    private Integer brokerId;
}