package cc.newex.dax.boss.web.controller.outer.v1.portfolio;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.portfolio.ActivePortfolioVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 组合资产管理 - 主动管理组合
 *
 * @author liutiejun
 * @date 2018-07-23
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/portfolio/active")
public class ActivePortfolioController {

    @RequestMapping(value = "/add")
    @OpLog(name = "新增主动管理组合")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVE_PORTFOLIO_ADD"})
    public ResponseResult add(@Valid final ActivePortfolioVO activePortfolioVO, final HttpServletRequest request) {

        return null;
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改主动管理组合")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVE_PORTFOLIO_EDIT"})
    public ResponseResult edit(@Valid final ActivePortfolioVO activePortfolioVO, @RequestParam("id") final Integer id,
                               final HttpServletRequest request) {

        return null;
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除主动管理组合")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVE_PORTFOLIO_REMOVE"})
    public ResponseResult remove(final Integer id, final HttpServletRequest request) {

        return null;
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取主动管理组合列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVE_PORTFOLIO_VIEW"})
    public ResponseResult list(@RequestParam(value = "name", required = false) final String name, final DataGridPager<ActivePortfolioVO> pager) {

        return ResultUtil.getDataGridResult();
    }

}
