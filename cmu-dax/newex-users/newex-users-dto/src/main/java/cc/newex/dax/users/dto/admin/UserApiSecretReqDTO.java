package cc.newex.dax.users.dto.admin;

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
public class UserApiSecretReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 备注名称
     */
    @NotBlank
    @Length(min = 6, max = 20)
    private String label;
    /**
     * secret口令盐
     */
    @NotBlank
    @Length(min = 6, max = 20)
    private String passphrase;
    /**
     * 权限值
     */
    @NotBlank
    private String authorities;
    /**
     * google验证码
     */
    private String googleCode;
    /**
     * 手机验证码
     */
    private String phoneCode;
}