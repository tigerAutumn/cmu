package cc.newex.dax.users.domain.subaccount;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 子账户绑定、解锁的操作记录表
 *
 * @author newex-team
 * @date 2018-11-06 17:06:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SubAccountRecord {
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
     * 操作类型，1-绑定，2-解锁
     */
    private Integer optType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;
}