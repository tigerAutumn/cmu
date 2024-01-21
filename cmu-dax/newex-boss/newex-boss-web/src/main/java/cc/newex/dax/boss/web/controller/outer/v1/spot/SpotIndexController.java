package cc.newex.dax.boss.web.controller.outer.v1.spot;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.spot.SpotIndexVO;
import cc.newex.dax.market.client.admin.CoinConfigClient;
import cc.newex.dax.market.dto.enums.CoinConfigStatusEnum;
import cc.newex.dax.market.dto.result.CoinConfigDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 指数管理
 *
 * @author liutiejun
 * @date 2018-08-28
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/boss/spot/index")
public class SpotIndexController {

    @Autowired
    private CoinConfigClient coinConfigClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增指数管理")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_INDEX_ADD"})
    public ResponseResult add(@Valid final SpotIndexVO spotIndexVO, final HttpServletRequest request) {
        return ResultUtils.success();
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改指数管理")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_INDEX_EDIT"})
    public ResponseResult edit(@Valid final SpotIndexVO spotIndexVO, @RequestParam("id") final Long id,
                               final HttpServletRequest request) {

        final CoinConfigDto coinConfigDto = CoinConfigDto.builder()
                .id(spotIndexVO.getId())
                .symbol(spotIndexVO.getSymbol())
                .symbolName(spotIndexVO.getSymbolName())
                .status(spotIndexVO.getStatus())
                .statusEnum(CoinConfigStatusEnum.getByCode(spotIndexVO.getStatus()))
                .build();

        final ResponseResult result = this.coinConfigClient.create(coinConfigDto);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除指数管理")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_INDEX_REMOVE"})
    public ResponseResult remove(final Long id, final HttpServletRequest request) {
        return ResultUtils.success();
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取指数管理列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_INDEX_VIEW"})
    public ResponseResult list(@RequestParam(value = "code", required = false) final String code,
                               @RequestParam(value = "status", required = false) final Integer status,
                               final DataGridPager pager, final HttpServletRequest request) {
        final ResponseResult<List<CoinConfigDto>> result = this.coinConfigClient.list();

        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return ResultUtil.getDataGridResult();
        }

        final List<CoinConfigDto> rows = result.getData();
        final Long total = (long) CollectionUtils.size(rows);

        final DataGridPagerResult dataGridPagerResult = new DataGridPagerResult<>(total, rows);
        return ResultUtils.success(dataGridPagerResult);
    }
}
