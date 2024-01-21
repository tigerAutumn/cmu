package cc.newex.dax.extra.domain.customer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 工单回复表
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkOrderReply {
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

    public static WorkOrderReply getInstance() {
        return WorkOrderReply.builder()
                .id(0L)
                .workOrderId(0L)
                .adminUserId(0)
                .reply("")
                .type(0)
                .createdDate(new Date())
                .build();
    }
}