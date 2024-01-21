package cc.newex.dax.boss.web.controller.outer.v1.portfolio;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.portfolio.IndexPortfolioUtils;
import cc.newex.dax.boss.web.model.portfolio.IndexPortfolioVO;
import cc.newex.dax.market.client.admin.PortfolioClient;
import cc.newex.dax.market.dto.result.CoinConfigDto;
import cc.newex.dax.portfolio.client.admin.AdminPortfolioConfigClient;
import cc.newex.dax.portfolio.dto.PortfolioConfigDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 组合资产管理 - 指数组合
 *
 * @author liutiejun
 * @date 2018-07-23
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/portfolio/index")
public class IndexPortfolioController {

    @Autowired
    private AdminPortfolioConfigClient portfolioConfigClient;

    @Autowired
    private PortfolioClient portfolioClient;

    @Autowired
    private IndexPortfolioUtils indexPortfolioUtils;

    @GetMapping(value = "/listAllMixedIndex")
    @OpLog(name = "查询所有的混合指数列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INDEX_PORTFOLIO_VIEW"})
    public ResponseResult listAllMixedIndex() {
        final ResponseResult<List<CoinConfigDto>> responseResult = this.portfolioClient.list();

        return ResultUtil.getCheckedResponseResult(responseResult);
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "新增指数组合")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INDEX_PORTFOLIO_ADD"})
    public ResponseResult add(@Valid final IndexPortfolioVO indexPortfolioVO, final HttpServletRequest request) {
        final PortfolioConfigDTO portfolioConfigDTO = this.indexPortfolioUtils.toPortfolioConfig(indexPortfolioVO, null);

        final ResponseResult responseResult = this.portfolioConfigClient.save(portfolioConfigDTO);

        return ResultUtil.getCheckedResponseResult(responseResult);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改指数组合")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INDEX_PORTFOLIO_EDIT"})
    public ResponseResult edit(@Valid final IndexPortfolioVO indexPortfolioVO, @RequestParam("id") final Long id,
                               final HttpServletRequest request) {
        final PortfolioConfigDTO portfolioConfigDTO = this.indexPortfolioUtils.toPortfolioConfig(indexPortfolioVO, id);

        final ResponseResult responseResult = this.portfolioConfigClient.save(portfolioConfigDTO);

        return ResultUtil.getCheckedResponseResult(responseResult);
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除指数组合")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INDEX_PORTFOLIO_REMOVE"})
    public ResponseResult remove(final Integer id, final HttpServletRequest request) {

        return null;
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取指数组合列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_INDEX_PORTFOLIO_VIEW"})
    public ResponseResult list(@RequestParam(value = "name", required = false) final String name,
                               @RequestParam(value = "mixedIndexName", required = false) final String mixedIndexName,
                               @RequestParam(value = "issuerId", required = false) final Long issuerId,
                               final DataGridPager<PortfolioConfigDTO> pager) {

        try {
            final PortfolioConfigDTO.PortfolioConfigDTOBuilder builder = PortfolioConfigDTO.builder();

            if (StringUtils.isNotBlank(name)) {
                builder.name(name);
            }

            if (StringUtils.isNotBlank(mixedIndexName)) {
                builder.indexCurrency(mixedIndexName);
            }

            if (issuerId != null) {
                builder.issuerId(issuerId);
            }

            final PortfolioConfigDTO portfolioConfigDTO = builder.build();

            pager.setQueryParameter(portfolioConfigDTO);

            final ResponseResult<DataGridPagerResult<PortfolioConfigDTO>> responseResult = this.portfolioConfigClient.getList(pager);
            final DataGridPagerResult<PortfolioConfigDTO> portfolioConfigResult = responseResult.getData();

            final DataGridPagerResult<IndexPortfolioVO> indexPortfolioResult = this.indexPortfolioUtils.toIndexPortfolio(portfolioConfigResult);

            return ResultUtils.success(indexPortfolioResult);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }

        return ResultUtil.getDataGridResult();
    }

}
