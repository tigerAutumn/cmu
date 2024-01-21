package cc.newex.dax.users.dto.kyc;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KycSecondDTO {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 姓名
     */
    @NotBlank
    @Length(min = 1, max = 36)
    private String name;
    /**
     * 证件号码
     */
    @NotBlank
    private String cardNumber;
    /**
     * kyc二级认证token
     */
    private String kycToken;
    /**
     * 选择国家/地区
     */
    private Integer country;
    /**
     * kyc认证结果：1：通过，2：驳回，3：其他异常
     */
    private Integer status;
    /**
     * kyc二级认证结果失败信息
     */
    private String message;

    private String bizNo;
}
