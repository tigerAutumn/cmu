package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
public class UserApiSecretDeleteReqVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * google验证码
     */
    @Length(min = 6, max = 6)
    private String verificationCode;
}