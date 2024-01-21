package cc.newex.dax.users.dto.kyc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycUploadReqDTO {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 选择国家/地区
     */
    private Integer country;
    /**
     * 姓名
     */
    private String name;
    /**
     * 证件号码
     */
    private String cardNumber;
    /**
     * 性别1:男 2:女
     */
    private String gender;
    /**
     * 证件有效期
     */
    private String validDate;
    /**
     * kyc一级认证结果失败信息
     */
    private String message;
}
