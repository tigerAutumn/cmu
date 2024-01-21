package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileRegReqVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 区域码
     */
    @NotNull
    private Integer areaCode;
    /**
     * 手机
     */
    @NotBlank
    @Length(min = 6, max = 20)
    private String mobile;
    /**
     * 验证码
     */
    @NotBlank
    @Length(min = 6, max = 6)
    private String verificationCode;
    /**
     * 密码
     */
    @NotBlank
    @Length(min = 6, max = 20)
    private String password;
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
