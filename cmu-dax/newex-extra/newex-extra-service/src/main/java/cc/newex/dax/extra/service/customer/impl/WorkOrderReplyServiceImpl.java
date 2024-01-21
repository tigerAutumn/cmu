package cc.newex.dax.extra.service.customer.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderReplyExample;
import cc.newex.dax.extra.data.customer.WorkOrderReplyRepository;
import cc.newex.dax.extra.domain.customer.WorkOrderReply;
import cc.newex.dax.extra.service.customer.WorkOrderReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 工单回复表 服务实现
 *
 * @author newex-team
 * @date 2018-06-08
 */
@Slf4j
@Service
public class WorkOrderReplyServiceImpl
        extends AbstractCrudService<WorkOrderReplyRepository, WorkOrderReply, WorkOrderReplyExample, Long>
        implements WorkOrderReplyService {

    @Override
    protected WorkOrderReplyExample getPageExample(String fieldName, String keyword) {
        WorkOrderReplyExample example = new WorkOrderReplyExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }


}