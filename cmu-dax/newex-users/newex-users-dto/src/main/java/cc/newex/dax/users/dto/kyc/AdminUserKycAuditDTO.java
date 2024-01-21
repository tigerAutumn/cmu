package cc.newex.dax.users.dto.kyc;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminUserKycAuditDTO implements Serializable {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * kyc等级,1和2
     */
    private Integer level;
    /**
     * 认证状态0：初始值，1：通过，2：驳回，3：其他异常
     */
    private Integer status;
    /**
     * 认证备注信息(内部查看)
     */
    private String remarks;
    /**
     * 驳回原因(展示给用户)
     */
    private String rejectReason;
    /**
     * 受理客服id
     */
    private Long dealUserId;
    /**
     * 受理客服名称
     */
    private String dealUserName;
}
