package cc.newex.dax.users.dto.admin;

import cc.newex.dax.users.dto.common.UserLevelEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInviteDetailDTO implements Serializable {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户手机
     */
    private String mobile;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户身份
     */
    private UserLevelEnum userLevel;
    /**
     * 用户姓名
     */
    private String userName;


    /**
     * 邀请人用户id
     */
    private Long inviteUserId;
    /**
     * 邀请人手机
     */
    private String inviteMobile;
    /**
     * 邀请人邮箱
     */
    private String inviteEmail;
    /**
     * 邀请人身份
     */
    private UserLevelEnum inviteUserLevel;
    /**
     * 邀请人姓名
     */
    private String inviteUserName;
    /**
     * 券商ID
     */
    private Integer brokerId;
}
