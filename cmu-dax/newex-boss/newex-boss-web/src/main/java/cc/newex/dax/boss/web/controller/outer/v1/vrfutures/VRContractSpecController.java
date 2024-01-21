package cc.newex.dax.boss.web.controller.outer.v1.vrfutures;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.web.model.extra.cms.NewsExtraVO;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 模拟合约管理 - 合约规格
 *
 * @author liutiejun
 * @date 2018-07-02
 */
public class VRContractSpecController {

    @RequestMapping(value = "/add")
    @OpLog(name = "新增合约规格")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_VR_CONTRACT_SPEC_ADD"})
    public ResponseResult add(@Valid final NewsExtraVO newsExtraVO, final HttpServletRequest request) {

        return ResultUtils.success();
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改合约规格")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_VR_CONTRACT_SPEC_EDIT"})
    public ResponseResult edit(@Valid final NewsExtraVO newsExtraVO, @RequestParam(value = "id", required = false) final Integer id,
                               final HttpServletRequest request) {

        return ResultUtils.success();
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取合约规格列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_CONTRACT_SPEC_VIEW"})
    public ResponseResult list(@RequestParam(value = "categoryId", required = false) final Integer categoryId,
                               @RequestParam(value = "locale", required = false) final String locale,
                               @RequestParam(value = "title", required = false) final String title, final DataGridPager pager) {

        return ResultUtils.success();
    }

}
