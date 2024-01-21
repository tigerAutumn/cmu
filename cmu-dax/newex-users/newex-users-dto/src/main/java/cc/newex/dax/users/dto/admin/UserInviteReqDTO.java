package cc.newex.dax.users.dto.admin;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInviteReqDTO implements Serializable {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 会员等级
     */
    private String userLevel;
    /**
     * 身份更新时间
     */
    private Date updateDate;
    /**
     * 最后一个用户id
     */
    private Long lastUserId = 0L;
    /**
     * 分页大小
     */
    private Integer pageSize = 20;
}
