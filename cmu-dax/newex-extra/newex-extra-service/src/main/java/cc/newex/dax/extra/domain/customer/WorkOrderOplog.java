package cc.newex.dax.extra.domain.customer;

import lombok.*;

import java.util.Date;

/**
 * 工单操作记录表
 *
 * @author newex-team
 * @date 2018-06-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkOrderOplog {
    /**
     * 工单操作记录表
     */
    private Long id;
    /**
     * 工单id
     */
    private Long workOrderId;
    /**
     *
     */
    private Integer groupId;
    /**
     * 处理人
     */
    private Integer currentAdminUserId;
    /**
     * 处理人名称
     */
    private String currentAdminAccount;
    /**
     * 操作记录
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;
    
}