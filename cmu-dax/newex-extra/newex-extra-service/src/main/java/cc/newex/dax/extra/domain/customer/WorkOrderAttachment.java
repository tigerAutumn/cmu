package cc.newex.dax.extra.domain.customer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 工单附件表
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkOrderAttachment {
    /**
     * 工单附件表
     */
    private Long id;
    /**
     * 工单id或回复id
     */
    private Long workOrderId;
    /**
     * 附件来源 0:工单 1:工单回复
     */
    private Integer type;
    /**
     * 原文件名
     */
    private String originalName;
    /**
     * 附件路径
     */
    private String path;
    /**
     * 描叙
     */
    private String desc;
    /**
     * 创建时间
     */
    private Date createdDate;

    public static WorkOrderAttachment getInstance() {
        return WorkOrderAttachment.builder()
                .id(0L)
                .workOrderId(0L)
                .type(0)
                .originalName("")
                .path("")
                .desc("")
                .createdDate(new Date())
                .build();
    }
}