package cc.newex.dax.users.domain;

import lombok.*;

import java.io.Serializable;

/**
 * 用户登录信息领域类
 *
 * @author newex-team
 * @date 2018-04-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLoginInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private Long id;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 用户账号冻结状态 1已冻结;0未冻结 默认为0
     */
    private Integer frozen;
    /**
     * 全球手机编号(如中国86)
     */
    private Integer areaCode;
    /**
     * 用户状态0为开启，1为禁用，其他保留，默认为0
     */
    private Integer status;
    /**
     * 发送邮件包含的防钓鱼码
     */
    private String antiPhishingCode;

    public static UserLoginInfo getInstance() {
        return UserLoginInfo.builder()
                .email("")
                .mobile("")
                .areaCode(0)
                .password("")
                .frozen(0)
                .status(0)
                .build();
    }
}
