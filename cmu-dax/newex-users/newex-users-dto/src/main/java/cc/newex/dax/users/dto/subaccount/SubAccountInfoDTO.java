package cc.newex.dax.users.dto.subaccount;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 子账户绑定、解锁的操作记录表
 *
 * @author newex-team
 * @date 2018-11-05 17:21:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SubAccountInfoDTO {

    /**
     * 账号所属的主账号ID(如果为0表示主账号,否则为子账号)
     */
    private Long parentId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 折合成 BTC 的估值
     */
    private BigDecimal baseBTC;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;
}
