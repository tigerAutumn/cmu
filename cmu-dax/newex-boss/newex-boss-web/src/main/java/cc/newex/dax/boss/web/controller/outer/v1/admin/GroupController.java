package cc.newex.dax.boss.web.controller.outer.v1.admin;

import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.Group;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.admin.service.GroupService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @date 2017-03-18
 */
@RestController
@RequestMapping(value = "/v1/boss/admin/groups")
public class GroupController {
    @Resource
    private GroupService groupService;

    @GetMapping("/{id}/users")
    @OpLog(name = "分页获取当前组织的管理员列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_GROUP_VIEW"})
    public ResponseResult getAdminUsers(@PathVariable(value = "id") final Integer id) {
        return ResultUtils.success(this.groupService.getAdminUsers(id));
    }

    @GetMapping(value = "/getGroupTree")
    @OpLog(name = "获取组织机构列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_GROUP_VIEW"})
    public ResponseResult getGroupTree(@CurrentUser final User loginUser) {
        List<Group> groups = this.groupService.getAll();
        if (loginUser.getBrokerId() != 0) {
            groups = groups.stream().filter(x -> loginUser.getBrokerId().equals(x.getBrokerId())).collect(Collectors.toList());
        }
        final List<TreeNode<Group>> roots = this.groupService.getGroupTree(groups, x -> NumberUtil.gt(x.getStatus(), -1));
        return ResultUtils.success(roots);
    }

    @GetMapping(value = "/listByPage")
    @OpLog(name = "获取组织机构列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_GROUP_VIEW"})
    public ResponseResult listByPage(@CurrentUser final User loginUser, final DataGridPager pager, final Integer id) {
        final int pid = ObjectUtils.defaultIfNull(id, 0);
        final PageInfo pageInfo = pager.toPageInfo();
        final List<Group> list = this.groupService.getByPage(pageInfo, pid, loginUser.getBrokerId());
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "新增组织机构")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_GROUP_ADD"})
    public ResponseResult add(@CurrentUser final User loginUser, final Group po) {
        po.setAdminUserId(loginUser.getId());
        if (loginUser.getBrokerId() == 0) {
            po.setBrokerId(po.getId());
        } else {
            po.setBrokerId(loginUser.getBrokerId());
        }
        po.setPath("");
        po.setCreatedDate(new Date());
        po.setUpdatedDate(new Date());
        this.groupService.add(po);
        return ResultUtils.success();
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑指定ID的组织机构")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_GROUP_EDIT"})
    public ResponseResult edit(final Group po) {
        this.groupService.editById(po);
        return ResultUtils.success();
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "邮件指定ID的组织机构")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_GROUP_REMOVE"})
    public ResponseResult remove(final Integer id, final Integer pid) {
        this.groupService.remove(id, pid);
        return ResultUtils.success();
    }

    @GetMapping(value = "/getGroup")
    @OpLog(name = "获取指定ID组织机构信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_GROUP_VIEW"})
    public ResponseResult getGroup(final Integer id) {
        return ResultUtils.success(this.groupService.getById(id));
    }

    @GetMapping(value = "/getChildGroups")
    @OpLog(name = "获取子组织机构列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_GROUP_VIEW"})
    public ResponseResult getChildGroups(@CurrentUser final User user, final Integer id) {
        final int parentId = ObjectUtils.defaultIfNull(id, 0);
        List<Group> groups = this.groupService.getChildren(parentId);
        if (user.getBrokerId() == 0) {
            groups = groups.stream().filter(group -> group.getBrokerId().equals(user.getBrokerId())).collect(Collectors.toList());
        }
        final List<TreeNode<Group>> treeNodes = new ArrayList<>(groups.size());
        for (final Group po : groups) {
            final String mid = Integer.toString(po.getId());
            final String pid = Integer.toString(po.getParentId());
            final String text = po.getName();
            final TreeNode<Group> node = new TreeNode<>(mid, pid, text, "closed", "", false, po);
            treeNodes.add(node);
        }
        return ResultUtils.success(treeNodes);
    }

    @GetMapping(value = "/rebuildPath")
    @OpLog(name = "重新组织机构树路径")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_GROUP_EDIT"})
    public ResponseResult rebuildPath() {
        this.groupService.rebuildAllPath();
        return ResultUtils.success();
    }
}
