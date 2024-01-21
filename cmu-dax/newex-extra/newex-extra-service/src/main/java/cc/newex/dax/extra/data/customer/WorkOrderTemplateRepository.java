package cc.newex.dax.extra.data.customer;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.customer.WorkOrderTemplateExample;
import cc.newex.dax.extra.domain.customer.WorkOrderTemplate;
import org.springframework.stereotype.Repository;

/**
 * 工单问题模版表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Repository
public interface WorkOrderTemplateRepository
        extends CrudRepository<WorkOrderTemplate, WorkOrderTemplateExample, Integer> {
}