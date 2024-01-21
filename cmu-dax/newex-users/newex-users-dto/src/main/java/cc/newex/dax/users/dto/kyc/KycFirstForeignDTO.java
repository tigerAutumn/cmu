package cc.newex.dax.users.dto.kyc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycFirstForeignDTO {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 选择国家/地区
     */
    @NotBlank
    private Integer country;
    /**
     * 姓
     */
    @NotBlank
    private String firstName;
    /**
     * 中间名
     */
    private String middleName;
    /**
     * 名
     */
    @NotBlank
    private String lastName;
    /**
     * 证件类型1：身份证，2：护照，3：驾驶证
     */
    private String cardType;
    /**
     * 证件号码
     */
    @NotBlank
    private String cardNumber;
}
