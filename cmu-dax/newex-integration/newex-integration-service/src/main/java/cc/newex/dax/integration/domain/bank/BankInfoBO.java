package cc.newex.dax.integration.domain.bank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author newex-team
 * @date 2018-06-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankInfoBO {
    /**
     * 银行卡号
     */
    private String bankcard;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 身份证号码
     */
    private String cardNo;
    /**
     * 手机号
     */
    private String mobile;
}
