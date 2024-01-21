package cc.newex.dax.extra.data.customer;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.customer.WorkOrderAttachmentExample;
import cc.newex.dax.extra.domain.customer.WorkOrderAttachment;
import org.springframework.stereotype.Repository;

/**
 * 工单附件表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Repository
public interface WorkOrderAttachmentRepository
        extends CrudRepository<WorkOrderAttachment, WorkOrderAttachmentExample, Long> {
}