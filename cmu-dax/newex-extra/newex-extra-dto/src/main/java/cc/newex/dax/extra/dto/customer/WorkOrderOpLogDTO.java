package cc.newex.dax.extra.dto.customer;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkOrderOpLogDTO {
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
//    /**
//     * 创建时间
//     */
//    private Date createdDate;
//    /**
//     * 更新时间
//     */
//    private Date updatedDate;
}
