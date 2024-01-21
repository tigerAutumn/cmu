package cc.newex.dax.asset.rest.params;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WithdrawParam {
    /**
     * 提现地址
     */
    @NotBlank
    String address;
    /**
     * 提现金额
     */
    @NotNull
    @Range(min = 0)
    BigDecimal amount;
    /**
     * 手续费
     */
    @Range(min = 0)
    @NotNull
    BigDecimal fee;
    /**
     * 邮箱验证码
     */
    @NotBlank
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
     * 身份证号
     */
    private String cardNumber;

}
