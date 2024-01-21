package cc.newex.dax.boss.web.controller.outer.v1.c2c;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.c2c.client.C2CConfigAdminClient;
import cc.newex.dax.c2c.client.C2CCurrencyAdminClient;
import cc.newex.dax.c2c.dto.admin.CurrencyConfigDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * @author newex-jinlong
 * @date 2018-04-28
 */
@RestController
@RequestMapping(value = "/v1/boss/c2c/config")
@Slf4j
public class ConfigController {

    @Autowired
    private C2CConfigAdminClient c2CConfigAdminClient;

    @Autowired
    private C2CCurrencyAdminClient c2CCurrencyAdminClient;

    @RequestMapping(value = "/top", method = RequestMethod.GET)
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_CONFIG_VIEW"})
    @OpLog(name = "查看基础配置信息")
    public ResponseResult top() {
        final ResponseResult result = this.c2CConfigAdminClient.getConfig();
        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/updateTopInfo", method = RequestMethod.POST)
    @OpLog(name = "基础设置修改")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_CONFIG_BASE_EDIT"})
    public ResponseResult updateInfo(@RequestParam final String key, @RequestParam final String value) {
        final ResponseResult result = this.c2CConfigAdminClient.updateConfig(key, value);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/limit")
    @OpLog(name = "限额管理")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_CONFIG_VIEW"})
    public ResponseResult limit(@RequestParam(value = "type") final Integer type) {
        final ResponseResult result = this.c2CCurrencyAdminClient.getAllCurrencyList(type);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/spilLover")
    @OpLog(name = "溢价管理")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_CONFIG_VIEW"})
    public ResponseResult spilLover(final DataGridPager pager) {

        return ResultUtils.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @OpLog(name = "限额管理修改")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_CONFIG_EDIT"})
    public ResponseResult update(final CurrencyConfigDTO currency) {
        final CurrencyConfigDTO currencyConfigDTO = CurrencyConfigDTO.builder()
                .id(currency.getId())
                .minOrderTotalPerOrder(currency.getMinOrderTotalPerOrder())
                .maxOrderTotalPerOrder(currency.getMaxOrderTotalPerOrder())
                .maxIncompleteOrderTotalPerUser(currency.getMaxIncompleteOrderTotalPerUser()).build();
        final ResponseResult result = this.c2CCurrencyAdminClient.updateCurrency(currencyConfigDTO);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @OpLog(name = "限额管理冻结")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_CONFIG_FREEZE"})
    public ResponseResult disable(@RequestParam(value = "cid") final Long cid, @RequestParam(value = "isTradeAllowed") final Integer isTradeAllowed) {
        final CurrencyConfigDTO currencyConfigDTO = CurrencyConfigDTO.builder()
                .id(cid)
                .isTradeAllowed(isTradeAllowed == 0 ? 1 : 0).build();
        final ResponseResult result = this.c2CCurrencyAdminClient.updateCurrency(currencyConfigDTO);
        return ResultUtil.getCheckedResponseResult(result);
    }
}
