package cc.newex.dax.extra.service.admin.customer;


import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderTemplateExample;
import cc.newex.dax.extra.domain.customer.WorkOrderTemplate;
import cc.newex.dax.extra.dto.customer.WorkOrderTemplateDTO;

import java.util.List;

/**
 * 工单问题模版表 服务接口
 *
 * @author newex-team
 * @date 2018-05-30
 */
public interface WorkOrderTemplateAdminService
        extends CrudService<WorkOrderTemplate, WorkOrderTemplateExample, Integer> {
    /**
     * 获取template列表
     *
     * @param workOrderTemplateDTO
     * @return
     */
    List<WorkOrderTemplate> list(PageInfo pageInfo, WorkOrderTemplateDTO workOrderTemplateDTO);
}
