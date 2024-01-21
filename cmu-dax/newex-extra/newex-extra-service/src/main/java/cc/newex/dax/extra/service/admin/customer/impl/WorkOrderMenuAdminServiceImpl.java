package cc.newex.dax.extra.service.admin.customer.impl;


import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderMenuExample;
import cc.newex.dax.extra.data.customer.WorkOrderMenuRepository;
import cc.newex.dax.extra.domain.customer.WorkOrderMenu;
import cc.newex.dax.extra.dto.customer.WorkOrderMenuDTO;
import cc.newex.dax.extra.service.admin.customer.WorkOrderMenuAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 工单菜单表 服务实现
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@Service
public class WorkOrderMenuAdminServiceImpl
        extends AbstractCrudService<WorkOrderMenuRepository, WorkOrderMenu, WorkOrderMenuExample, Integer>
        implements WorkOrderMenuAdminService {

    @Autowired
    private WorkOrderMenuRepository workOrderMenuRepos;

    @Override
    protected WorkOrderMenuExample getPageExample(String fieldName, String keyword) {
        WorkOrderMenuExample example = new WorkOrderMenuExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    /**
     * 获取menu 列表
     *
     * @param workOrderMenuDTO
     * @return
     */
    @Override
    public List<WorkOrderMenu> list(PageInfo pageInfo, WorkOrderMenuDTO workOrderMenuDTO) {
        WorkOrderMenuExample workOrderMenuExample = new WorkOrderMenuExample();
        WorkOrderMenuExample.Criteria criteria = workOrderMenuExample.createCriteria();
        if (Objects.nonNull(workOrderMenuDTO.getParentId()) && StringUtils.isNumeric(String.valueOf(workOrderMenuDTO.getParentId()))) {
            criteria.andParentIdEqualTo(workOrderMenuDTO.getParentId());
        }
        if (StringUtils.isNotEmpty(workOrderMenuDTO.getLocale())) {
            criteria.andLocaleEqualTo(workOrderMenuDTO.getLocale());
        }
        if (Objects.nonNull(workOrderMenuDTO.getStatus())) {
            criteria.andStatusEqualTo(workOrderMenuDTO.getStatus());
        }
        if (StringUtils.isNotEmpty(workOrderMenuDTO.getName())) {
            criteria.andNameLike(workOrderMenuDTO.getName() + "%");
        }
        if (Objects.nonNull(workOrderMenuDTO.getGroupId()) && StringUtils.isNumeric(String.valueOf(workOrderMenuDTO.getGroupId()))) {
            criteria.andGroupIdEqualTo(workOrderMenuDTO.getGroupId());
        }
        return this.getByPage(pageInfo, workOrderMenuExample);

    }

    @Override
    public List<TreeNode<WorkOrderMenuDTO>> getMenusTree(List<WorkOrderMenuDTO> menus, Predicate<WorkOrderMenuDTO> predicate) {
        List<TreeNode<WorkOrderMenuDTO>> roots = new ArrayList<>();
        menus.stream()
                .filter(predicate)
                .filter(menu -> Objects.equals(menu.getParentId(), 0))
                .sorted((x, y) -> x.getId() > y.getId() ? 1 : -1)
                .forEach((WorkOrderMenuDTO workOrderMenuDTO) -> this.addMenusTreeNode(roots, menus, workOrderMenuDTO, predicate));

        return roots;

    }

    @Override
    public String getMenuDesc(Integer menuId) {
        WorkOrderMenu workOrderMenu = this.getById(menuId);
        if (workOrderMenu == null) {
            return null;
        }
        if (workOrderMenu.getParentId() == 0) {
            return workOrderMenu.getName();
        }
        return this.getMenuDesc(workOrderMenu.getParentId()) + "-" + workOrderMenu.getName();
    }

    private void addMenusTreeNode(List<TreeNode<WorkOrderMenuDTO>> children, List<WorkOrderMenuDTO> groups,
                                  WorkOrderMenuDTO group,
                                  Predicate<WorkOrderMenuDTO> predicate) {
        String id = Integer.toString(group.getId());
        String pid = Integer.toString(group.getParentId());
        String text = group.getName();
        String state = ArrayUtils.getLength(StringUtils.split(group.getGroupId(), ',')) > 0 ? "closed" : "open";
        TreeNode<WorkOrderMenuDTO> parentNode =
                new TreeNode<>(id, pid, text, state, "", false, group);
        this.addChildGroupTreeNodes(groups, parentNode, predicate);
        children.add(parentNode);
    }

    private void addChildGroupTreeNodes(List<WorkOrderMenuDTO> groups, TreeNode<WorkOrderMenuDTO> parentNode,
                                        Predicate<WorkOrderMenuDTO> predicate) {
        Integer id = Integer.valueOf(parentNode.getId());
        groups.stream()
                .filter(predicate)
                .filter(group -> Objects.equals(group.getParentId(), id))
                .sorted((x, y) -> x.getId() > y.getId() ? 1 : 0)
                .forEach(group -> this.addMenusTreeNode(parentNode.getChildren(), groups, group, predicate));
    }
}