package cc.newex.dax.users.dto.membership;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInviteDTO implements Serializable {
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
     * 邀请码
     */
    private String inviteCode;
    /**
     * 个人专属活动链接
     */
    private String activityUrl;
    /**
     * 送币数量
     */
    private Double currencyNum;
    /**
     * 活动送币
     */
    private Integer currencyId;
    /**
     * 活动币code
     */
    private String currencyCode;
    /**
     * 邀请人送币数量
     */
    private Double inviteCurrencyNum;
    /**
     * 邀请人活动送币
     */
    private Integer inviteCurrencyId;
}
