package cc.newex.dax.users.domain.subaccount;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 母子账户划转记录表
 *
 * @author newex-team
 * @date 2018-11-05 17:21:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferRecord {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 账号所属的主账号ID(如果为0表示主账号,否则为子账号)
     */
    private Long parentId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 操作类型，1-从母账户划转到子账户，2-从子账户划转到母账户
     */
    private Integer optType;

    /**
     * 币种ID
     */
    private Integer currencyId;

    /**
     * 币种数量
     */
    private BigDecimal currencyAmount;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;
}