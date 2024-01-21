package cc.newex.dax.extra.service.admin.customer.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderAttachmentExample;
import cc.newex.dax.extra.data.customer.WorkOrderAttachmentRepository;
import cc.newex.dax.extra.domain.customer.WorkOrderAttachment;
import cc.newex.dax.extra.service.admin.customer.WorkOrderAttachmentAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 工单附件表 服务实现
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@Service
public class WorkOrderAttachmentAdminServiceImpl
        extends AbstractCrudService<WorkOrderAttachmentRepository, WorkOrderAttachment, WorkOrderAttachmentExample, Long>
        implements WorkOrderAttachmentAdminService {

    @Autowired
    private WorkOrderAttachmentRepository workOrderAttachmentRepos;

    @Override
    protected WorkOrderAttachmentExample getPageExample(final String fieldName, final String keyword) {
        final WorkOrderAttachmentExample example = new WorkOrderAttachmentExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}