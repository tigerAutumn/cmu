package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * API KEY Request DTO
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserApiSecretReqVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 备注名称
     */
    @NotBlank
    @Length(min = 1, max = 20)
    private String label;
    /**
     * secret口令盐
     */
    @NotBlank
    @Length(min = 6, max = 20)
    private String passphrase;
    /**
     * 权限值 提币：withdraw,交易：trade
     */
    @NotBlank
    private String authorities;
    /**
     * 验证码
     */
    @NotBlank
    @Length(min = 6, max = 6)
    private String verificationCode;
    /**
     * ip白名单
     */
    private String ipWhiteLists;
}