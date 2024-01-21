package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录二次验证表单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyLoginReqVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 验证码
     */
    private String verificationCode;

}
