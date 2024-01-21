package cc.newex.dax.extra.service.admin.customer.impl;


import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderTemplateExample;
import cc.newex.dax.extra.data.customer.WorkOrderTemplateRepository;
import cc.newex.dax.extra.domain.customer.WorkOrderTemplate;
import cc.newex.dax.extra.dto.customer.WorkOrderTemplateDTO;
import cc.newex.dax.extra.service.admin.customer.WorkOrderTemplateAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 工单问题模版表 服务实现
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@Service
public class WorkOrderTemplateAdminServiceImpl
        extends AbstractCrudService<WorkOrderTemplateRepository, WorkOrderTemplate, WorkOrderTemplateExample, Integer>
        implements WorkOrderTemplateAdminService {

    @Autowired
    private WorkOrderTemplateRepository workOrderTemplateRepos;

    @Override
    protected WorkOrderTemplateExample getPageExample(String fieldName, String keyword) {
        WorkOrderTemplateExample example = new WorkOrderTemplateExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    /**
     * 获取template列表
     *
     * @param workOrderTemplateDTO
     * @return
     */
    @Override
    public List<WorkOrderTemplate> list(PageInfo pageInfo, WorkOrderTemplateDTO workOrderTemplateDTO) {
        WorkOrderTemplateExample workOrderTemplateExample = new WorkOrderTemplateExample();
        WorkOrderTemplateExample.Criteria criteria = workOrderTemplateExample.createCriteria();
        if (Objects.nonNull(workOrderTemplateDTO.getMenuId()) && StringUtils.isNumeric(String.valueOf(workOrderTemplateDTO.getMenuId()))) {
            criteria.andMenuIdEqualTo(workOrderTemplateDTO.getMenuId());
        }
        if (Objects.nonNull(workOrderTemplateDTO.getTemplate()) && StringUtils.isNotEmpty(workOrderTemplateDTO.getTemplate())) {
            criteria.andTemplateLike(workOrderTemplateDTO.getTemplate() + "%");
        }
        return this.getByPage(pageInfo, workOrderTemplateExample);
    }

}