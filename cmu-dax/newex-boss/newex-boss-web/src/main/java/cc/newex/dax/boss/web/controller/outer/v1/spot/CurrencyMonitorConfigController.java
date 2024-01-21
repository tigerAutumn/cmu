package cc.newex.dax.boss.web.controller.outer.v1.spot;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.spot.CurrencyMonitorConfigVO;
import cc.newex.dax.spot.client.CurrencyMonitorAdminClient;
import cc.newex.dax.spot.dto.ccex.currency.CurrencyMonitorConfigDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author liutiejun
 * @date 2019-01-15
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/spot/currency/monitor-config")
public class CurrencyMonitorConfigController {

    @Autowired
    private CurrencyMonitorAdminClient currencyMonitorAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增CurrencyMonitorConfig")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_MONITOR_CONFIG_ADD"})
    public ResponseResult add(@Valid final CurrencyMonitorConfigVO currencyMonitorConfigVO, final HttpServletRequest request) {
        final CurrencyMonitorConfigDTO currencyMonitorConfigDTO = CurrencyMonitorConfigDTO.builder()
                .currencyId(currencyMonitorConfigVO.getCurrencyId())
                .threshold(currencyMonitorConfigVO.getThreshold())
                .status(currencyMonitorConfigVO.getStatus())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.currencyMonitorAdminClient.saveCurrencyMonitorConfig(currencyMonitorConfigDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改CurrencyMonitorConfig")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_MONITOR_CONFIG_EDIT"})
    public ResponseResult edit(@Valid final CurrencyMonitorConfigVO currencyMonitorConfigVO, final Long id, final HttpServletRequest request) {
        final CurrencyMonitorConfigDTO currencyMonitorConfigDTO = CurrencyMonitorConfigDTO.builder()
                .id(id)
                .currencyId(currencyMonitorConfigVO.getCurrencyId())
                .threshold(currencyMonitorConfigVO.getThreshold())
                .status(currencyMonitorConfigVO.getStatus())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.currencyMonitorAdminClient.updateCurrencyMonitorConfig(currencyMonitorConfigDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取CurrencyMonitorConfig列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_MONITOR_CONFIG_VIEW"})
    public ResponseResult list(@RequestParam(value = "status", required = false) final Integer status,
                               @RequestParam(value = "currencyId", required = false) final Integer currencyId,
                               final DataGridPager<CurrencyMonitorConfigDTO> pager) {
        final CurrencyMonitorConfigDTO.CurrencyMonitorConfigDTOBuilder builder = CurrencyMonitorConfigDTO.builder();

        if (status != null) {
            builder.status(status);
        }

        if (currencyId != null) {
            builder.currencyId(currencyId);
        }

        final CurrencyMonitorConfigDTO currencyMonitorConfigDTO = builder.build();
        pager.setQueryParameter(currencyMonitorConfigDTO);

        final ResponseResult responseResult = this.currencyMonitorAdminClient.listCurrencyMonitorConfig(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除CurrencyMonitorConfig")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_MONITOR_CONFIG_REMOVE"})
    public ResponseResult remove(@RequestParam("id") final Long id) {
        final ResponseResult result = this.currencyMonitorAdminClient.removeCurrencyMonitorConfig(id);

        return ResultUtil.getCheckedResponseResult(result);
    }

}
