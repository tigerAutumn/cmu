package cc.newex.dax.users.dto.kyc;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KycChinaCacheDTO {
    /**
     * 姓名
     */
    private String name;
    /**
     * 证件号
     */
    private String idCardNumber;
    /**
     * 性别
     */
    private String gender;
    /**
     * 国家
     */
    @Builder.Default
    private Integer country = 156;
    /**
     * 正面照片路径
     */
    private String frontPath;
    /**
     * 背面照片路径
     */
    private String backPath;
    /**
     * 认证结果
     */
    private String remarks;
    /**
     * 过期时间
     */
    private String validDate;


}
