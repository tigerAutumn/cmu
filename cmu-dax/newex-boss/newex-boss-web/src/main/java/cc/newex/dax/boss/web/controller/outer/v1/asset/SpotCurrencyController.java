package cc.newex.dax.boss.web.controller.outer.v1.asset;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.asset.dto.AssetCurrencyDTO;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.enums.CurrencyBizEnum;
import cc.newex.dax.boss.web.common.util.DateFormater;
import cc.newex.dax.boss.web.common.util.UUIDUtils;
import cc.newex.dax.boss.web.model.asset.AssetCurrencyVO;
import cc.newex.dax.spot.client.SpotUserCurrencyBalanceClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 币币 - 币种管理
 *
 * @author liutiejun
 * @date 2018-08-27
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/asset/currency/spot")
public class SpotCurrencyController {

    @Autowired
    private AdminServiceClient adminServiceClient;

    @Autowired
    private SpotUserCurrencyBalanceClient spotUserCurrencyBalanceClient;

    @OpLog(name = "分页获取币种列表信息")
    @GetMapping(value = "/list")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_SPOT_CURRENCY_VIEW"})
    public ResponseResult list(final DataGridPager<AssetCurrencyDTO> pager, @CurrentUser final User loginUser,
                               @RequestParam(value = "symbol", required = false) String symbol,
                               @RequestParam(value = "withdrawable", required = false) Integer withdrawable,
                               @RequestParam(value = "rechargeable", required = false) Integer rechargeable,
                               @RequestParam(value = "online", required = false) Integer online,
                               @RequestParam(value = "biz", required = false) String biz) {

        if (StringUtils.isBlank(symbol)) {
            symbol = null;
        }

        if (withdrawable == null || withdrawable < 0) {
            withdrawable = null;
        }

        if (rechargeable == null || rechargeable < 0) {
            rechargeable = null;
        }

        if (online == null || online < 0) {
            online = null;
        }

        if (StringUtils.isBlank(biz)) {
            biz = CurrencyBizEnum.SPOT.getName();
        }

        final AssetCurrencyDTO assetCurrencyDTO = AssetCurrencyDTO.builder()
                .symbol(symbol)
                .withdrawable(withdrawable)
                .rechargeable(rechargeable)
                .online(online)
                .biz(biz)
                .brokerId(loginUser.getLoginBrokerId())
                .build();

        pager.setQueryParameter(assetCurrencyDTO);

        final ResponseResult result = this.adminServiceClient.getCurrencies(pager);

        return ResultUtil.getDataGridResult(result);
    }

    @RequestMapping(value = "/add")
    @OpLog(name = "新增币种")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_SPOT_CURRENCY_ADD"})
    public ResponseResult add(@Valid final AssetCurrencyVO assetCurrencyVO) {

        final AssetCurrencyDTO assetCurrencyDTO = AssetCurrencyDTO.builder()
                .symbol(assetCurrencyVO.getSymbol())
                .fullName(assetCurrencyVO.getFullName())
                .sign(assetCurrencyVO.getSign())
                .currencyPictureUrl(assetCurrencyVO.getCurrencyPictureUrl())
                .biz(assetCurrencyVO.getBiz())
                .online(assetCurrencyVO.getOnline())
                .withdrawable(assetCurrencyVO.getWithdrawable())
                .rechargeable(assetCurrencyVO.getRechargeable())
                .exchange(assetCurrencyVO.getExchange())
                .receive(assetCurrencyVO.getReceive())
                .transfer(assetCurrencyVO.getTransfer())
                .sort(assetCurrencyVO.getSort())
                .depositConfirm(assetCurrencyVO.getDepositConfirm())
                .minDepositAmount(assetCurrencyVO.getMinDepositAmount())
                .withdrawFee(assetCurrencyVO.getWithdrawFee())
                .withdrawConfirm(assetCurrencyVO.getWithdrawConfirm())
                .minWithdrawAmount(assetCurrencyVO.getMinWithdrawAmount())
                .maxWithdrawAmount(assetCurrencyVO.getMaxWithdrawAmount())
                .needTag(assetCurrencyVO.getNeedTag())
                .tagField(assetCurrencyVO.getTagField())
                .zone(assetCurrencyVO.getZone())
                .txExplorerUrl(assetCurrencyVO.getTxExplorerUrl())
                .cnWikiUrl(assetCurrencyVO.getCnWikiUrl())
                .twWikiUrl(assetCurrencyVO.getTwWikiUrl())
                .usWikiUrl(assetCurrencyVO.getUsWikiUrl())
                .currencySummary(assetCurrencyVO.getCurrencySummary())
                .needAlert(assetCurrencyVO.getNeedAlert())
                .expireDate(this.getExpireDate(assetCurrencyVO))
                .blockBrowser(assetCurrencyVO.getBlockBrowser())
                .brokerId(assetCurrencyVO.getBrokerId())
                .build();

        final ResponseResult result = this.adminServiceClient.addCurrency(assetCurrencyDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    private Long getExpireDate(final AssetCurrencyVO assetCurrencyVO) {
        // 是否开启未来下线，0-否，1-是
        final Integer futureDown = assetCurrencyVO.getFutureDown();

        // 下线时间
        final String expireDateStr = assetCurrencyVO.getExpireDate();

        if (futureDown == null || futureDown.equals(0) || StringUtils.isBlank(expireDateStr)) {
            return null;
        }

        Date expireDate = DateFormater.parse(expireDateStr, "yyyy-MM-dd");

        // 将毫秒设置为0
        expireDate = DateUtils.setMilliseconds(expireDate, 0);

        if (expireDate == null) {
            return null;
        }

        return expireDate.getTime();
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改币种")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_SPOT_CURRENCY_EDIT"})
    public ResponseResult edit(@Valid final AssetCurrencyVO assetCurrencyVO, @RequestParam("id") final Integer id) {

        final AssetCurrencyDTO assetCurrencyDTO = AssetCurrencyDTO.builder()
                .id(id)
                .symbol(assetCurrencyVO.getSymbol())
                .fullName(assetCurrencyVO.getFullName())
                .sign(assetCurrencyVO.getSign())
                .currencyPictureUrl(assetCurrencyVO.getCurrencyPictureUrl())
                .biz(assetCurrencyVO.getBiz())
                .online(assetCurrencyVO.getOnline())
                .withdrawable(assetCurrencyVO.getWithdrawable())
                .rechargeable(assetCurrencyVO.getRechargeable())
                .exchange(assetCurrencyVO.getExchange())
                .receive(assetCurrencyVO.getReceive())
                .transfer(assetCurrencyVO.getTransfer())
                .sort(assetCurrencyVO.getSort())
                .depositConfirm(assetCurrencyVO.getDepositConfirm())
                .minDepositAmount(assetCurrencyVO.getMinDepositAmount())
                .withdrawFee(assetCurrencyVO.getWithdrawFee())
                .withdrawConfirm(assetCurrencyVO.getWithdrawConfirm())
                .minWithdrawAmount(assetCurrencyVO.getMinWithdrawAmount())
                .maxWithdrawAmount(assetCurrencyVO.getMaxWithdrawAmount())
                .needTag(assetCurrencyVO.getNeedTag())
                .tagField(assetCurrencyVO.getTagField())
                .zone(assetCurrencyVO.getZone())
                .txExplorerUrl(assetCurrencyVO.getTxExplorerUrl())
                .cnWikiUrl(assetCurrencyVO.getCnWikiUrl())
                .twWikiUrl(assetCurrencyVO.getTwWikiUrl())
                .usWikiUrl(assetCurrencyVO.getUsWikiUrl())
                .currencySummary(assetCurrencyVO.getCurrencySummary())
                .needAlert(assetCurrencyVO.getNeedAlert())
                .expireDate(this.getExpireDate(assetCurrencyVO))
                .blockBrowser(assetCurrencyVO.getBlockBrowser())
                .brokerId(assetCurrencyVO.getBrokerId())
                .build();

        final ResponseResult result = this.adminServiceClient.editCurrency(assetCurrencyDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/one-step")
    @OpLog(name = "一键冻结/解冻充值,划转")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_SPOT_CURRENCY_ONE_STEP"})
    public ResponseResult oneStepFrozen(@RequestParam(value = "type") final Integer type) {
        final ResponseResult result = this.adminServiceClient.oneStepFrozen("spot", type);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/unhold")
    @OpLog(name = "资产解冻")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_SPOT_CURRENCY_UNHOLD"})
    public ResponseResult unhold(@RequestParam(value = "brokerId") final Integer brokerId,
                                 @RequestParam(value = "userId") final Long userId,
                                 @RequestParam(value = "currencyId") final Integer currencyId,
                                 @RequestParam(value = "amount") final BigDecimal amount,
                                 final HttpServletRequest request) {

        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        log.info("-------------------------------unhold currency, adminId: {}, brokerId: {}, userId: {}, currencyId: {}, amount: {}",
                user.getId(), brokerId, userId, currencyId, amount);

        final String tradeNo = "boss:admin:" + user.getId() + ":" + UUIDUtils.getUUID32();

        final ResponseResult result = this.spotUserCurrencyBalanceClient.unhold(tradeNo, brokerId, userId, currencyId, amount);

        return ResultUtil.getCheckedResponseResult(result);
    }

}
