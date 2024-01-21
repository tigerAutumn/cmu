package cc.newex.dax.extra.service.admin.customer.impl;


import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderExample;
import cc.newex.dax.extra.data.customer.WorkOrderRepository;
import cc.newex.dax.extra.domain.customer.WorkOrder;
import cc.newex.dax.extra.dto.customer.WorkOrderDTO;
import cc.newex.dax.extra.service.admin.customer.WorkOrderAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 工单表 服务实现
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@Service
public class WorkOrderAdminServiceImpl
        extends AbstractCrudService<WorkOrderRepository, WorkOrder, WorkOrderExample, Long>
        implements WorkOrderAdminService {

    @Autowired
    private WorkOrderRepository workOrderRepos;

    @Override
    protected WorkOrderExample getPageExample(String fieldName, String keyword) {
        WorkOrderExample example = new WorkOrderExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }


    /**
     * 分页查询
     *
     * @param pageInfo
     * @param workOrderDTO
     * @return
     */
    @Override
    public List<WorkOrder> listByPage(PageInfo pageInfo, WorkOrderDTO workOrderDTO) {

        WorkOrderExample workOrderExample = new WorkOrderExample();
        WorkOrderExample.Criteria criteria = workOrderExample.createCriteria();
        if (Objects.nonNull(workOrderDTO.getStatus()) && StringUtils.isNumeric(String.valueOf(workOrderDTO.getStatus()))) {
            criteria.andStatusEqualTo(workOrderDTO.getStatus());
        }
        if (Objects.nonNull(workOrderDTO.getUserId()) && StringUtils.isNumeric(String.valueOf(workOrderDTO.getUserId()))) {
            criteria.andUserIdEqualTo(workOrderDTO.getUserId());
        }
        if (Objects.nonNull(workOrderDTO.getMenuId()) && StringUtils.isNumeric(String.valueOf(workOrderDTO.getMenuId()))) {
            criteria.andMenuIdEqualTo(workOrderDTO.getMenuId());
        }
        if (StringUtils.isNotEmpty(workOrderDTO.getLocale())) {
            criteria.andLocaleEqualTo(workOrderDTO.getLocale());
        }
        return this.getByPage(pageInfo, workOrderExample);
    }

    /**
     * 个人工单按条件查询
     *
     * @param pageInfo
     * @param workOrderDTO
     * @return
     */
    @Override
    public List<WorkOrder> getListByPage(PageInfo pageInfo, WorkOrderDTO workOrderDTO) {
        WorkOrderExample workOrderExample = new WorkOrderExample();
        WorkOrderExample.Criteria criteria = workOrderExample.createCriteria();
        if (Objects.nonNull(workOrderDTO.getId())) {
            criteria.andIdEqualTo(workOrderDTO.getId());
        }
        if (Objects.nonNull(workOrderDTO.getUserId())) {
            criteria.andUserIdEqualTo(workOrderDTO.getUserId());
        }
        if (Objects.nonNull(workOrderDTO.getSource())) {
            criteria.andSourceEqualTo(workOrderDTO.getSource());
        }
        if (Objects.nonNull(workOrderDTO.getMenuId())) {
            criteria.andMenuIdEqualTo(workOrderDTO.getMenuId());
        }
        if (Objects.nonNull(workOrderDTO.getStatus())) {
            criteria.andStatusEqualTo(workOrderDTO.getStatus());
        }

        if (Objects.nonNull(workOrderDTO.getCreatedDate())) {
            criteria.andCreatedDateEqualTo(workOrderDTO.getCreatedDate());
        }
        return this.getByPage(pageInfo, workOrderExample);
    }

    @Override
    public List<WorkOrder> listByAdminUser(WorkOrderDTO workOrderDTO) {
        WorkOrderExample workOrderExample = new WorkOrderExample();
        WorkOrderExample.Criteria criteria = workOrderExample.createCriteria();

        if (Objects.nonNull(workOrderDTO.getId())) {
            criteria.andIdEqualTo(workOrderDTO.getId());
        }
        if (Objects.nonNull(workOrderDTO.getAdminUserId())) {
            criteria.andAdminUserIdEqualTo(workOrderDTO.getAdminUserId());
        }
        if (Objects.nonNull(workOrderDTO.getStatus())) {
            criteria.andStatusEqualTo(workOrderDTO.getStatus());
        }
        if (Objects.nonNull(workOrderDTO.getUserId())) {
            criteria.andUserIdEqualTo(workOrderDTO.getUserId());
        }
        if (StringUtils.isNotEmpty(workOrderDTO.getUserName())) {
            criteria.andUserNameEqualTo(workOrderDTO.getUserName());
        }

        return this.workOrderRepos.getListByExample(workOrderExample);
    }
}