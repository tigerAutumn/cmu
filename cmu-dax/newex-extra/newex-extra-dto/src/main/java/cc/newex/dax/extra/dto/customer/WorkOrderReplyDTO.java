package cc.newex.dax.extra.dto.customer;

import lombok.*;

import java.util.Date;

/**
 * @author allen
 * @date 2018/6/9
 * @des
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkOrderReplyDTO {

    /**
     * 工单回复表
     */
    private Long id;
    /**
     * 工单问题表id
     */
    private Long workOrderId;
    /**
     * 处理人
     */
    private Integer adminUserId;
    /**
     * 回复内容
     */
    private String reply;
    /**
     * 类型 0:用户回复 1:客服回复
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Date createdDate;
}
