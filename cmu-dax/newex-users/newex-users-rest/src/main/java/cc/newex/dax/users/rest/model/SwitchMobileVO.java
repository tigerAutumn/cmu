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
 * @date 2018/4/10 下午5:05
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SwitchMobileVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 谷歌验证码
     */
    @NotBlank
    @Length(min = 6, max = 6)
    private String googleCode;

    /**
     * 手机验证码
     */
    @NotBlank
    @Length(min = 6, max = 6)
    private String mobileCode;

}