package cc.newex.dax.users.dto.kyc;

import lombok.*;

import java.util.Date;

/**
 * @author newex-team
 * @date 2018/7/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KycUserLevelDTO {
    /**
     *
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 认证状态1:一级：2:二级
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
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;
}
