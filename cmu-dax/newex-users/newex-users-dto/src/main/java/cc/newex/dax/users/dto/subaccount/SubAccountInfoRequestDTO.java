package cc.newex.dax.users.dto.subaccount;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 添加子账户的请求信息
 *
 * @author liutiejun
 * @date 2018-11-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SubAccountInfoRequestDTO {

    /**
     * 手机号或者邮箱
     */
    @NotBlank
    private String username;

    /**
     * 密码
     */
    @NotBlank
    private String password;

    /**
     * 备注
     */
    private String remark;

    /**
     * 谷歌验证码
     */
    private String googleCode;

    /**
     * email验证码
     */
    @Length(min = 6, max = 6)
    private String emailCode;

    /**
     * 手机验证码
     */
    private String mobileCode;
}
