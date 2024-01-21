package cc.newex.dax.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户kyc表
 *
 * @author newex-team
 * @date 2018-09-13 14:29:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserKycInfo {
    /**
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 姓
     */
    private String firstName;

    /**
     * 中间名称
     */
    private String middleName;

    /**
     * 名
     */
    private String lastName;

    /**
     * 性别 未知,男 ,女
     */
    private String gender;

    /**
     * 国家编码
     */
    private Integer country;

    /**
     * 证件类型1：身份证，2：护照，3：驾驶证
     */
    private Integer cardType;

    /**
     * 证件号码
     */
    private String cardNumber;

    /**
     * 证件有效期
     */
    private String validDate;

    /**
     * 地址1
     */
    private String address1;

    /**
     * 地址2
     */
    private String address2;

    /**
     * 地址3
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

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

    /**
     * 券商ID
     */
    private Integer brokerId;

    public static UserKycInfo getInstance() {
        return UserKycInfo.builder()
                .id(0L)
                .userId(0L)
                .firstName("")
                .middleName("")
                .lastName("")
                .gender("")
                .country(0)
                .cardType(1)
                .cardNumber("")
                .validDate("")
                .address1("")
                .address2("")
                .address3("")
                .city("")
                .zipCode("")
                .createdDate(new Date())
                .updatedDate(new Date())
                .brokerId(1)
                .build();
    }
}