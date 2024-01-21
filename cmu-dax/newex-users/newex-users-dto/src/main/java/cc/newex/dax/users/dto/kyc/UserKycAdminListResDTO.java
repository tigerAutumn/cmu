package cc.newex.dax.users.dto.kyc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserKycAdminListResDTO {
    /**
     * id
     */
    private Long id;
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
     * 驳回原因
     */
    private String rejectReason;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 受理时间
     */
    private Date updatedDate;
    /**
     * 受理客服
     */
    private String dealUserId;
    /**
     * 认证状态
     */
    private Integer status;
    /**
     * 认证等级
     */
    private Integer level;

    /**
     * 身份证号
     */
    private String cardNumber;
    /**
     * 总条数
     */
    private Long total;

    /**
     * 券商id
     */
    private Integer brokerId;
}
