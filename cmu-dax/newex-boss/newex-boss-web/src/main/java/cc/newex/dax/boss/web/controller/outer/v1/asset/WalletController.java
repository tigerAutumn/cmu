package cc.newex.dax.boss.web.controller.outer.v1.asset;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jinlong
 * @date 2018-06-15
 */
@RestController
@RequestMapping(value = "/v1/boss/asset/wallet")
@Slf4j
public class WalletController {

    @Autowired
    AdminServiceClient adminServiceClient;

    @GetMapping(value = "/list")
    @OpLog(name = "获取钱包账单列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_WALLET_VIEW"})
    public ResponseResult List(final DataGridPager pager,
                               @RequestParam(value = "currencyCode", required = false) final String currencyCode,
                               @RequestParam(value = "startDateMillis", required = false) final String startDateMillis) throws ParseException {

        Date sDateMillis = null;

        if (StringUtils.isNotBlank(startDateMillis)) {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            sDateMillis = sdf.parse(startDateMillis);
        }

        ResponseResult result = null;

        try {
            result = this.adminServiceClient.getBalanceSummary(currencyCode, sDateMillis, pager.getPage(), pager.getRows());
        } catch (final Exception e) {
            log.error(" coninlist list api error", e);
        }

        return ResultUtil.getDataGridResult(result);
    }

    @PutMapping(value = "/edit")
    @OpLog(name = "修改钱包参考值")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_WALLET_UPDATE"})
    public ResponseResult edit(final Integer currencyCode,
                               final BigDecimal threshold) {
        try {
            final ResponseResult responseResult = this.adminServiceClient.editThreshold(currencyCode, threshold);
            return ResultUtil.getCheckedResponseResult(responseResult);
        } catch (final Exception e) {
            log.error("update  threshold list api error", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }
}
