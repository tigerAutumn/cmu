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
public class WorkOrderAttachmentDTO {

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
}
