package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 找回密码
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPwdReqVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户名(手机或邮箱)
     */
    @NotBlank
    @Length(min = 6, max = 50)
    private String loginName;
    /**
     * 用户名密码
     */
    @NotBlank
    @Length(min = 6, max = 20)
    private String password;
    /**
     * 新密码
     */
    @NotBlank
    @Length(min = 6, max = 20)
    private String confirmPassword;

    /**
     * 手机验证码
     */
    @Length(min = 6, max = 6)
    private String mobileCode;

    /**
     * 邮箱验证码
     */
    @Length(min = 6, max = 6)
    private String emailCode;

    /**
     * 谷歌验证码
     */
    @Length(min = 6, max = 6)
    private String googleCode;

    /**
     * 当前行为需要审查的行为项:google,email,mobile
     */
    @NotNull
    List<String> checkItems;


}
