package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 登录表单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginReqVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 登录名
     */
    @NotBlank
    @Length(min = 6, max = 50)
    private String username;

    /**
     * 密码
     */
    @NotBlank
    @Length(min = 6, max = 20)
    private String password;

    /**
     * 验证码
     */
    private String verificationCode;

}
