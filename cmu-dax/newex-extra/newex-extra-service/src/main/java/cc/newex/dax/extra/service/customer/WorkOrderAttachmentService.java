package cc.newex.dax.extra.service.customer;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderAttachmentExample;
import cc.newex.dax.extra.domain.customer.WorkOrderAttachment;

/**
 * 工单附件表 服务接口
 *
 * @author newex-team
 * @date 2018-06-08
 */
public interface WorkOrderAttachmentService
        extends CrudService<WorkOrderAttachment, WorkOrderAttachmentExample, Long> {
}
