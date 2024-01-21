package cc.newex.dax.extra.service.admin.customer;


import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderMenuExample;
import cc.newex.dax.extra.domain.customer.WorkOrderMenu;
import cc.newex.dax.extra.dto.customer.WorkOrderMenuDTO;

import java.util.List;
import java.util.function.Predicate;

/**
 * 工单菜单表 服务接口
 *
 * @author newex-team
 * @date 2018-05-30
 */
public interface WorkOrderMenuAdminService
        extends CrudService<WorkOrderMenu, WorkOrderMenuExample, Integer> {
    /**
     * @param pageInfo
     * @param workOrderMenuDTO
     * @return
     */
    List<WorkOrderMenu> list(PageInfo pageInfo, WorkOrderMenuDTO workOrderMenuDTO);

    /**
     * 菜单树形
     *
     * @param modules
     * @param predicate
     * @return
     */
    List<TreeNode<WorkOrderMenuDTO>> getMenusTree(List<WorkOrderMenuDTO> modules, Predicate<WorkOrderMenuDTO> predicate);

    /**
     * 显示菜单的父节点，子节点全部描述
     *
     * @param menuId
     * @return
     */
    String getMenuDesc(Integer menuId);
}
