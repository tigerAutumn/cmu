package cc.newex.dax.extra.service.customer;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderTemplateExample;
import cc.newex.dax.extra.domain.customer.WorkOrderTemplate;

/**
 * 工单问题模版表 服务接口
 *
 * @author newex-team
 * @date 2018-06-08
 */
public interface WorkOrderTemplateService
        extends CrudService<WorkOrderTemplate, WorkOrderTemplateExample, Integer> {

}
