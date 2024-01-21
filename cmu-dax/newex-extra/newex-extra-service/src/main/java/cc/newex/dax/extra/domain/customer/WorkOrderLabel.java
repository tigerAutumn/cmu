package cc.newex.dax.extra.domain.customer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 工单标签记录表
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkOrderLabel {
    /**
     * 自增id
     */
    private Long id;
    /**
     * 工单id
     */
    private Long workOrderId;
    /**
     * 跟进者
     */
    private Integer adminUserId;
    /**
     * 标签
     */
    private String label;
    /**
     * 创建时间
     */
    private Date createdDate;

    public static WorkOrderLabel getInstance() {
        return WorkOrderLabel.builder()
                .id(0L)
                .workOrderId(0L)
                .adminUserId(0)
                .label("")
                .createdDate(new Date())
                .build();
    }
}