package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResVO {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户手机区号
     */
    private Integer areaCode;

    /**
     * 用户手机
     */
    private String mobile;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * kyc认证等级信息
     */
    private List<ProfileKycLevelVO> kyc;
    /**
     * 是否子账号
     */
    private Boolean subAccount;

    /**
     * 用户类型: 0 individual 个人  1 企业用户 enterprise
     */
    private String type;

    /**
     * 最后一次登录是否异常
     */
    private LastLoginResVO lastLogin;

    /**
     * 用户个人设置
     */
    private UserSettingsVO settings;

    /**
     * 用户等级
     */
    private String userLevel;

    /**
     * 邀请用户等级
     */
    private String inviteUserLevel;

    /**
     * 当前用户的brokerId
     */
    private Integer brokerId;

    /**
     * 组织名称
     */
    private String orgName;

}
