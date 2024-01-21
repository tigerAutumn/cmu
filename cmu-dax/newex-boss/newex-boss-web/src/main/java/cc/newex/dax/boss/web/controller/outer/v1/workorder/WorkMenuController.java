package cc.newex.dax.boss.web.controller.outer.v1.workorder;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.extra.client.ExtraWorkOrderAdminClient;
import cc.newex.dax.extra.dto.customer.WorkOrderMenuDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author jinlong
 * @date 2018-06-15
 */
@RestController
@RequestMapping(value = "/v1/boss/workorder/workMenu")
@Slf4j
public class WorkMenuController {

    @Autowired
    ExtraWorkOrderAdminClient extraWorkOrderAdminClient;

    @GetMapping(value = "/getWorkMenuTree")
    @OpLog(name = "获取工单父级ID")
    public ResponseResult getGroupTree() {
        try {
            final ResponseResult groups = extraWorkOrderAdminClient.menuTree();
            return ResultUtil.getCheckedResponseResult(groups);
        } catch (final Exception e) {
            log.error("getWorkMenuTree api error ", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @GetMapping(value = "/list")
    @OpLog(name = "工单菜单列表")
    public ResponseResult list(final DataGridPager<WorkOrderMenuDTO> pager,
                               @RequestParam(value = "status", required = false) final Integer status,
                               @RequestParam(value = "name", required = false) final String name) {
        final WorkOrderMenuDTO dto = WorkOrderMenuDTO.builder()
                .status(status)
                .name(name)
                .build();
        pager.setQueryParameter(dto);

        try {
            final ResponseResult result = extraWorkOrderAdminClient.listMenu(pager);
            return ResultUtil.getCheckedResponseResult(result);

        } catch (final Exception e) {
            log.error("list workMenu api error ", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }


    @RequestMapping(value = "/edit")
    @OpLog(name = "修改工单菜单列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_WORK_ORDER_MENU_EDIT"})
    public ResponseResult edit(final Integer id, @Valid final WorkOrderMenuDTO ct,
                               final HttpServletRequest request) {

        final String groupList = request.getParameter("groupList");
        final WorkOrderMenuDTO build = WorkOrderMenuDTO.builder()
                .id(id)
                .locale(ct.getLocale())
                .parentId(ct.getParentId())
                .name(ct.getName())
                .status(ct.getStatus())
                .groupId(groupList)
                .build();
        try {
            final ResponseResult result = extraWorkOrderAdminClient.edit(build);
            return ResultUtil.getCheckedResponseResult(result);

        } catch (final Exception e) {
            log.error("edit workMenu api error ", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "新增工单菜单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_WORK_ORDER_MENU_ADD"})
    public ResponseResult add(@Valid final WorkOrderMenuDTO ct, final HttpServletRequest request) {

        final String groupList = request.getParameter("groupList");
        final WorkOrderMenuDTO build = WorkOrderMenuDTO.builder()
                .locale(ct.getLocale())
                .parentId(ct.getParentId())
                .name(ct.getName())
                .status(ct.getStatus())
                .groupId(groupList)
                .build();
        try {
            final ResponseResult result = extraWorkOrderAdminClient.add(build);
            return ResultUtil.getCheckedResponseResult(result);

        } catch (final Exception e) {
            log.error("add workMenu api error ", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除工单菜单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_WORK_ORDER_MENU_REMOVE"})
    public ResponseResult remove(final Integer id) {
        try {
            final ResponseResult result = extraWorkOrderAdminClient.removeMenus(id);
            return ResultUtil.getCheckedResponseResult(result);

        } catch (final Exception e) {
            log.error("delete workMenu api error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }
}
