package cc.newex.dax.users.domain;

import lombok.*;

import java.util.Date;

/**
 * 用户kyc审核操作记录
 *
 * @author newex-team
 * @date 2018-05-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserKycEvent {
    /**
     *
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 认证等级1:一级：2:二级
     */
    private Integer level;
    /**
     * 认证状态0：初始值，1：通过，2：驳回，3：其他异常，4：审核中
     */
    private Integer status;
    /**
     * 认证备注信息(内部查看)
     */
    private String remarks;
    /**
     * 驳回原因
     */
    private String rejectReason;
    /**
     * 受理客服id
     */
    private Long dealUserId;
    /**
     * 受理客服姓名
     */
    private String dealUserName;
    /**
     * kyc_level_id
     */
    private Long kycLevelId;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    public static UserKycEvent getInstance() {
        return UserKycEvent.builder()
                .id(0L)
                .userId(0L)
                .level(0)
                .status(0)
                .remarks("")
                .rejectReason("")
                .dealUserId(0L)
                .dealUserName("")
                .kycLevelId(0L)
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();
    }
}