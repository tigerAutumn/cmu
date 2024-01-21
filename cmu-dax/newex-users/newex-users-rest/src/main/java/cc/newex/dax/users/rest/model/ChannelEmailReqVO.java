package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelEmailReqVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 邮箱
     */
    @Email
    @NotBlank
    @Length(min = 6, max = 50)
    private String email;
    /**
     * 密码
     */
    @NotBlank
    @Length(min = 6, max = 20)
    private String password;
    /**
     * 验证码
     */
    @NotBlank
    @Length(min = 6, max = 6)
    private String verificationCode;
    /**
     * 邀请码
     */
    private String inviteCode;
    /**
     * 活动编码
     */
    @Builder.Default
    private String activityCode = "register";
    /**
     * 邀请渠道
     */
    private String utm_source;
}
