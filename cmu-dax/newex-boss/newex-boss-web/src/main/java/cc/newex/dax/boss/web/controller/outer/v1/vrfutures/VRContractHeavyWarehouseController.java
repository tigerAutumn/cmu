package cc.newex.dax.boss.web.controller.outer.v1.vrfutures;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 模拟合约管理 - 合约重仓用户
 *
 * @author liutiejun
 * @date 2018-07-03
 */
public class VRContractHeavyWarehouseController {

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取合约列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_VR_FUTURES_VIEW"})
    public ResponseResult moreList(@RequestParam(value = "name", required = false) final String name, final DataGridPager pager) {

        return ResultUtils.success();
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取合约列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_VR_FUTURES_VIEW"})
    public ResponseResult emptyList(@RequestParam(value = "name", required = false) final String name, final DataGridPager pager) {

        return ResultUtils.success();
    }
}
