package cc.newex.dax.users.dto.kyc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycSecondForeignDTO {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 选择国家/地区
     */
    private Integer country;
    /**
     * 姓
     */
    private String firstName;
    /**
     * 中间名
     */
    private String middleName;
    /**
     * 名
     */
    private String lastName;
    /**
     * kyc二级认证token
     */
    private String kycToken;
    /**
     * 地址 1
     */
    private String address1;
    /**
     * 地址 2
     */
    private String address2;
    /**
     * 地址 3
     */
    private String address3;
    /**
     * 城市
     */
    private String city;
    /**
     * 邮编
     */
    private String zipCode;

}
