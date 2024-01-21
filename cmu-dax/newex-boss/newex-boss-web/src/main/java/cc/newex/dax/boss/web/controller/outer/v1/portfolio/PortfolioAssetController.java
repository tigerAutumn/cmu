package cc.newex.dax.boss.web.controller.outer.v1.portfolio;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.portfolio.FuturePortfolioVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组合资产管理
 *
 * @author liutiejun
 * @date 2018-07-30
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/portfolio/asset")
public class PortfolioAssetController {

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取组合资产列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_PORTFOLIO_ASSET_VIEW"})
    public ResponseResult list(@RequestParam(value = "userId", required = false) final Long userId,
                               final DataGridPager<FuturePortfolioVO> pager) {
        log.info("------------userId: {}", userId);

        return ResultUtil.getDataGridResult();
    }

}
