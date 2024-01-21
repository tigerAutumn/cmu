package cc.newex.dax.boss.web.model.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteVO {

    /**
     * 用户 id
     */
    private Long userId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 身份
     */
    private String title;

    /**
     * 更新时间
     */
    private String updateTime;

}
