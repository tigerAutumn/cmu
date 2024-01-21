package cc.newex.dax.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户kyc等级
 *
 * @author newex-team
 * @date 2018-09-13 14:29:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserKycLevel {
    /**
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

    public static UserKycLevel getInstance() {
        return UserKycLevel.builder()
                .id(0L)
                .userId(0L)
                .level(0)
                .status(0)
                .remarks("")
                .createdDate(new Date())
                .updatedDate(new Date())
                .brokerId(1)
                .build();
    }
}