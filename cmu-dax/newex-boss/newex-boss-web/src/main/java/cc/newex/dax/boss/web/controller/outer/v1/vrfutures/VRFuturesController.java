package cc.newex.dax.boss.web.controller.outer.v1.vrfutures;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.web.model.vrfutures.VRFuturesVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 模拟合约管理 - 合约列表
 *
 * @author liutiejun
 * @date 2018-07-02
 */
@RestController
@Slf4j
@RequestMapping("/v1/boss/vr/futures")
public class VRFuturesController {

    @RequestMapping(value = "/add")
    @OpLog(name = "新增合约")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_VR_FUTURES_ADD"})
    public ResponseResult add(@Valid final VRFuturesVO futuresVO, final HttpServletRequest request) {

        return ResultUtils.success();
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改合约")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_VR_FUTURES_EDIT"})
    public ResponseResult edit(@Valid final VRFuturesVO futuresVO, @RequestParam(value = "id", required = false) final Integer id,
                               final HttpServletRequest request) {

        return ResultUtils.success();
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取合约列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_VR_FUTURES_VIEW"})
    public ResponseResult list(@RequestParam(value = "name", required = false) final String name, final DataGridPager pager) {

        return ResultUtils.success();
    }

    @RequestMapping(value = "/delivery")
    @OpLog(name = "合约交割")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_VR_FUTURES_DELIVERY"})
    public ResponseResult delivery(final HttpServletRequest request) {

        return ResultUtils.success();
    }

}
