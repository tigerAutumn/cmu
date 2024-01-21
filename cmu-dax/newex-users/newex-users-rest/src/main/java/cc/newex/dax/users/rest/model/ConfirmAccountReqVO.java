package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmAccountReqVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户名(手机或邮箱)
     */
    @NotBlank
    @Length(min = 6, max = 50)
    private String loginName;

    /**
     * 验证码
     */
    @NotBlank
    @Length(min = 4, max = 4)
    private String verificationCode;

    /**
     * 序列号
     */
    @NotBlank
    @Length(min = 36, max = 36)
    private String serialNO;

}
