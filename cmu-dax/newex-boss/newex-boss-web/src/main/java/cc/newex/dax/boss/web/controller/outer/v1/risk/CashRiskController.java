package cc.newex.dax.boss.web.controller.outer.v1.risk;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 提现风控管理
 *
 * @author liutiejun
 * @date 2018-07-18
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/risk/cash")
public class CashRiskController {

    @Autowired
    private AdminServiceClient adminServiceClient;

    @GetMapping(value = "/getCurrentUser")
    @OpLog(name = "查询当前用户信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_RISK_CASH_VIEW"})
    public ResponseResult getCurrentUser(@CurrentUser final User loginUser, final HttpServletRequest request) {
        return ResultUtils.success(loginUser);
    }

    @GetMapping(value = "/get")
    @OpLog(name = "查询大额提现触发身份验证的配置信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_RISK_CASH_VIEW"})
    public ResponseResult get(final HttpServletRequest request) {
        final ResponseResult responseResult = this.adminServiceClient.maxBtcWithdrawAmount();

        return ResultUtil.getCheckedResponseResult(responseResult);
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑大额提现触发身份验证的配置信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_RISK_CASH_EDIT"})
    public ResponseResult edit(@RequestParam("maxAmount") @NotBlank final String maxAmount, final HttpServletRequest request) {
        final ResponseResult responseResult = this.adminServiceClient.setMaxBtcWithdrawAmount(maxAmount);

        return ResultUtil.getCheckedResponseResult(responseResult);
    }

    @GetMapping(value = "/getCheckAmount")
    @OpLog(name = "查询提现审核额度")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_CHECK_AMOUNT_VIEW"})
    public ResponseResult getCheckAmount(final HttpServletRequest request) {
        final ResponseResult responseResult = this.adminServiceClient.auditBtcWithdrawAmount();

        return ResultUtil.getCheckedResponseResult(responseResult);
    }

    @PostMapping(value = "/editCheckAmount")
    @OpLog(name = "设置提现审核额度")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_CHECK_AMOUNT_EDIT"})
    public ResponseResult editCheckAmount(@RequestParam("checkAmount") @NotBlank final String checkAmount, final HttpServletRequest request) {
        final ResponseResult responseResult = this.adminServiceClient.setAuditBtcWithdrawAmount(checkAmount);

        return ResultUtil.getCheckedResponseResult(responseResult);
    }

}
