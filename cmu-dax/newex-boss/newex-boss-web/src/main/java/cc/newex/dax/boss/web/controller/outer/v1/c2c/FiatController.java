package cc.newex.dax.boss.web.controller.outer.v1.c2c;


import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.c2c.client.C2CSystemBillTotalAdminClient;
import cc.newex.dax.c2c.dto.admin.SystemBillTotalDTO;
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
@RequestMapping(value = "/v1/boss/c2c/balancefiat")
@Slf4j
public class FiatController {

    @Autowired
    C2CSystemBillTotalAdminClient c2CSystemBillTotalAdminClient;

    @GetMapping(value = "/fiatlist")
    @OpLog(name = "获取法币账单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_C2C_FIAT_BALANCE_VIEW"})
    public ResponseResult fiatList(final DataGridPager pager, final String currencyCode,
                                   final String startDateMillis, final String endDateMillis) {

        Long bDateMillis = null;
        Long eDateMillis = null;
        String code=null;
        if (StringUtils.isNotBlank(startDateMillis)) {
            bDateMillis = DateUtil.getDateFromDateStr(startDateMillis);
        }
        if (StringUtils.isNotBlank(endDateMillis)) {
            eDateMillis = DateUtil.getDateFromDateStr(endDateMillis);
        }
        if (currencyCode != null && !"" .equals(currencyCode)) {
            code = currencyCode.toLowerCase();
        }
        try {
            ResponseResult<List<SystemBillTotalDTO>> fiatsystembill = c2CSystemBillTotalAdminClient.querySystemBillTotal(code, bDateMillis, eDateMillis);
            if (fiatsystembill != null && fiatsystembill.getCode() == 0) {
                final Map<String, Object> modelMap = new HashMap<>(2);
                modelMap.put("total", fiatsystembill.getData().size());
                modelMap.put("rows", fiatsystembill.getData());
                return ResultUtils.success(modelMap);
            }else{
                log.info("balance fiatList error"+fiatsystembill.getMsg());
                return ResultUtils.success();

            }
        } catch (final Exception e) {
            log.error("balance fiatList error", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }
}
