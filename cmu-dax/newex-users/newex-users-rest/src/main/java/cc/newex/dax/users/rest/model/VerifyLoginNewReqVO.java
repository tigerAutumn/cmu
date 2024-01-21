package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录二次验证表单, 匹配一个就ok
 *
 * @author newex-team
 * @date 2018/8/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyLoginNewReqVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 验证类型
     */
    private String verificationType;

    /**
     * 验证码
     */
    private String verificationCode;

}