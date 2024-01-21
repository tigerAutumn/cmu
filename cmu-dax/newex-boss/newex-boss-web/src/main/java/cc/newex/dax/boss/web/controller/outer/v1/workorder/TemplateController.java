package cc.newex.dax.boss.web.controller.outer.v1.workorder;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.extra.client.ExtraWorkOrderAdminClient;
import cc.newex.dax.extra.dto.customer.WorkOrderTemplateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 丁昆
 * @date 2018/6/4
 * @des 工单模版
 */
@RestController
@Slf4j
@RequestMapping("/v1/boss/workorder/template")
public class TemplateController {

    @Autowired
    ExtraWorkOrderAdminClient extraWorkOrderClient;

    @GetMapping(value = "/list")
    @OpLog(name = "获取工单模版")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_WORKORDER_TEMP_VIEW"})
    public ResponseResult list(final DataGridPager pager, final Integer menuId, final String template) {
        final WorkOrderTemplateDTO workOrderTemplateDTO = WorkOrderTemplateDTO.builder()
                .menuId(menuId)
                .template(template)
                .build();
        try {
            final ResponseResult list = this.extraWorkOrderClient.listTemplate(workOrderTemplateDTO, pager.getPage(), pager.getRows());
            return ResultUtil.getCheckedResponseResult(list);
        } catch (final Exception e) {
            log.error("list workOrder error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @PostMapping(value = "/add")
    @OpLog(name = "添加工单模版")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_WORKORDER_TEMP_ADD"})
    public ResponseResult add(final WorkOrderTemplateDTO workOrderTemplateDTO) {
        try {
            final ResponseResult list = this.extraWorkOrderClient.add(workOrderTemplateDTO);
            return ResultUtil.getCheckedResponseResult(list);
        } catch (final Exception e) {
            log.error("add workOrder error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "修改工单模版")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_WORKORDER_TEMP_UPDATE"})
    public ResponseResult edit(final WorkOrderTemplateDTO workOrderTemplateDTO) {
        try {
            final ResponseResult list = this.extraWorkOrderClient.edit(workOrderTemplateDTO);
            return ResultUtil.getCheckedResponseResult(list);
        } catch (final Exception e) {
            log.error("edit workOrder error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除工单模版")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_WORKORDER_TEMP_REMOVE"})
    public ResponseResult remove(final Integer id) {
        try {
            final ResponseResult list = this.extraWorkOrderClient.removeTemplates(id);
            return ResultUtil.getCheckedResponseResult(list);
        } catch (final Exception e) {
            log.error("remove workOrder error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

}
