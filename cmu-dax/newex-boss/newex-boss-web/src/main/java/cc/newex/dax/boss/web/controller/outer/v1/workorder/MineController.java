package cc.newex.dax.boss.web.controller.outer.v1.workorder;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.extra.client.ExtraWorkOrderCustomerClient;
import cc.newex.dax.extra.dto.customer.WorkOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jinlong
 * @date 2018-06-15
 */
@RestController
@RequestMapping(value = "/v1/boss/workorder/mine")
@Slf4j
public class MineController {

    @Autowired
    ExtraWorkOrderCustomerClient extraWorkOrderCustomerClient;

    /**
     * 获取状态
     */
    @GetMapping(value = "/status")
    @OpLog(name = "获取工单状态")
    public ResponseResult getAllStatus(final HttpServletRequest request) {
        try {
            final ResponseResult<?> result = this.extraWorkOrderCustomerClient.status();
            return ResultUtil.getCheckedResponseResult(result);

        } catch (final Throwable e) {
            log.error("get currencies error", e);
        }
        return ResultUtils.success();
    }

    @GetMapping(value = "/list")
    @OpLog(name = "工单菜单列表")
    public ResponseResult list(final DataGridPager<WorkOrderDTO> pager,
                               @RequestParam(value = "type", required = false) final Integer type,
                               @RequestParam(value = "classify", required = false) final Integer classify,
                               @RequestParam(value = "status", required = false) final Integer status,
                               @RequestParam(value = "workId", required = false) final Long workId,
                               @RequestParam(value = "userId", required = false) final Long userId) {

        final WorkOrderDTO build = WorkOrderDTO.builder()
                .source(type)
                .menuId(classify)
                .status(status)
                .id(workId)
                .userId(userId)
                .build();
        pager.setQueryParameter(build);

        try {
            final ResponseResult result = this.extraWorkOrderCustomerClient.getListByPage(pager);
            return ResultUtil.getCheckedResponseResult(result);
        } catch (final Exception e) {
            log.error("get mine list api error ", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }

    }

}
