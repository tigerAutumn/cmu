package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import java.io.Serializable;

/**
 * @author newex-team
 * @create 2018/4/10 下午10:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BindEmailVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 邮箱
     */
    @Email
    private String email;
    /**
     * 邮箱验证码
     */
    private String verificationCode;
}