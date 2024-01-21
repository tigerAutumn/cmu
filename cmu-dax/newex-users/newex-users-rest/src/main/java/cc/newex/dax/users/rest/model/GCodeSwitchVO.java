package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author newex-team
 * @date 2018/4/10 下午6:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GCodeSwitchVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 相关行为业务
     */
    @NotNull
    private Integer behavior;
    /**
     * 谷歌验证码
     */
    @NotBlank
    @Length(min = 6, max = 6)
    private String googleCode;
    /**
     * 邮箱验证码
     */
    @Length(min = 6, max = 6)
    private String emailCode;
    /**
     * 手机验证码
     */
    @Length(min = 6, max = 6)
    private String mobileCode;

}