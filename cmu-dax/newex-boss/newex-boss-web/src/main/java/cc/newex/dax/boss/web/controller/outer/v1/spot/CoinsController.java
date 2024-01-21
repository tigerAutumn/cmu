package cc.newex.dax.boss.web.controller.outer.v1.spot;


import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.spot.client.SpotSystemBillTotalClient;
import cc.newex.dax.spot.dto.ccex.SystemBillTotalDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dingkun
 * @date 2018-06-15
 */
@RestController
@RequestMapping(value = "/v1/boss/spot/balancecoins")
@Slf4j
public class CoinsController {

    @Autowired
    SpotSystemBillTotalClient spotSystemBillTotalClient;

    @GetMapping(value = "/coinslist")
    @OpLog(name = "获取币币账单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_COINS_BALANCE_VIEW"})
    public ResponseResult fiatList(final DataGridPager pager, final String currencyCode,
                                   final String startDateMillis, final String endDateMillis) {

        Long sDateMillis = null;
        Long eDateMillis = null;
        String code=null;
        if (StringUtils.isNotBlank(startDateMillis)) {
            sDateMillis = DateUtil.getDateFromDateStr(startDateMillis);
        }
        if (StringUtils.isNotBlank(endDateMillis)) {
            eDateMillis = DateUtil.getDateFromDateStr(endDateMillis);
        }

        if (currencyCode != null && !"" .equals(currencyCode)) {
            code = currencyCode.toLowerCase();
        }
        try {
            final ResponseResult<List<SystemBillTotalDTO>> systemBillTotalDTO = this.spotSystemBillTotalClient.querySystemBillTotal(code, sDateMillis, eDateMillis);
            if (systemBillTotalDTO != null && systemBillTotalDTO.getCode() == 0) {
                final Map<String, Object> modelMap = new HashMap<>();
                modelMap.put("total", systemBillTotalDTO.getData().size());
                modelMap.put("rows", systemBillTotalDTO.getData());
                return ResultUtils.success(modelMap);
            }
        } catch (final Exception e) {
            log.error("balance coninlist list error", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
        return ResultUtils.success();
    }

}
