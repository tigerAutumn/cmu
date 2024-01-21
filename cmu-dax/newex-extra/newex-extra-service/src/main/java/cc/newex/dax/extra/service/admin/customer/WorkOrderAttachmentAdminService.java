package cc.newex.dax.extra.service.admin.customer;


import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderAttachmentExample;
import cc.newex.dax.extra.domain.customer.WorkOrderAttachment;

/**
 * 工单附件表 服务接口
 *
 * @author newex-team
 * @date 2018-05-30
 */
public interface WorkOrderAttachmentAdminService
        extends CrudService<WorkOrderAttachment, WorkOrderAttachmentExample, Long> {
}
