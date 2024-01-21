package cc.newex.dax.users.dto.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WithdrawCheckCodeDTO {

    /**
     * email验证码
     */
    @Length(min = 6, max = 6)
    private String emailCode;

    /**
     * 谷歌验证码
     */
    private String googleCode;

    /**
     * 手机验证码
     */
    private String mobileCode;

    /**
     * 身份证号码验证
     */
    private String cardNumber;

    private Integer behavior;

}