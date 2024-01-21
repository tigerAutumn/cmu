package cc.newex.dax.boss.web.controller.outer.v1.asset;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.asset.dto.AssetCurrencyDTO;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.enums.CurrencyBizEnum;
import cc.newex.dax.boss.web.common.util.DateFormater;
import cc.newex.dax.boss.web.model.asset.AssetCurrencyVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * 法币 - 币种管理
 *
 * @author liutiejun
 * @date 2018-08-27
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/asset/currency/c2c")
public class C2CCurrencyController {

    @Autowired
    private AdminServiceClient adminServiceClient;

    @OpLog(name = "分页获取币种列表信息")
    @GetMapping(value = "/list")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_C2C_CURRENCY_VIEW"})
    public ResponseResult list(final DataGridPager<AssetCurrencyDTO> pager, @CurrentUser final User loginUser,
                               @RequestParam(value = "symbol", required = false) String symbol,
                               @RequestParam(value = "transfer", required = false) Integer transfer,
                               @RequestParam(value = "online", required = false) Integer online) {

        if (StringUtils.isBlank(symbol)) {
            symbol = null;
        }

        if (transfer == null || transfer < 0) {
            transfer = null;
        }

        if (online == null || online < 0) {
            online = null;
        }

        final String biz = CurrencyBizEnum.C2C.getName();

        final AssetCurrencyDTO assetCurrencyDTO = AssetCurrencyDTO.builder()
                .symbol(symbol)
                .transfer(transfer)
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
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_C2C_CURRENCY_ADD"})
    public ResponseResult add(@Valid final AssetCurrencyVO assetCurrencyVO) {

        final AssetCurrencyDTO assetCurrencyDTO = AssetCurrencyDTO.builder()
                .biz(CurrencyBizEnum.C2C.getName())
                .symbol(assetCurrencyVO.getSymbol())
                .sign(assetCurrencyVO.getSign())
                .fullName(assetCurrencyVO.getFullName())
                .currencyPictureUrl(assetCurrencyVO.getCurrencyPictureUrl())
                .sort(assetCurrencyVO.getSort())
                .online(assetCurrencyVO.getOnline())
                .transfer(assetCurrencyVO.getTransfer())
                .decimalPlaces(assetCurrencyVO.getDecimalPlaces())
                .type(assetCurrencyVO.getType())
                .isAssetVisible(assetCurrencyVO.getIsAssetVisible())
                .platformCommissionRate(assetCurrencyVO.getPlatformCommissionRate())
                .minOrderTotalPerOrder(assetCurrencyVO.getMinOrderTotalPerOrder())
                .maxOrderTotalPerOrder(assetCurrencyVO.getMaxOrderTotalPerOrder())
                .maxIncompleteOrderTotalPerUser(assetCurrencyVO.getMaxIncompleteOrderTotalPerUser())
                .deposit(assetCurrencyVO.getDeposit())
                .minOrderTotalPerTradingOrder(assetCurrencyVO.getMinOrderTotalPerTradingOrder())
                .maxOrderTotalPerTradingOrder(assetCurrencyVO.getMaxOrderTotalPerTradingOrder())
                .dapps(assetCurrencyVO.getDapps())
                .issuePrice(assetCurrencyVO.getIssuePrice())
                .zone(assetCurrencyVO.getZone())
                .createdDate(new Date())
                .withdrawable(0)
                .rechargeable(0)
                .exchange(0)
                .receive(0)
                .brokerId(assetCurrencyVO.getBrokerId())
                .build();

        final ResponseResult result = this.adminServiceClient.addCurrency(assetCurrencyDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改币种")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ASSET_C2C_CURRENCY_EDIT"})
    public ResponseResult edit(@Valid final AssetCurrencyVO assetCurrencyVO, @RequestParam("id") final Integer id) {
        Date createdDate = DateFormater.parse(assetCurrencyVO.getCreatedDate());
        if (createdDate == null) {
            createdDate = new Date();
        }

        final AssetCurrencyDTO assetCurrencyDTO = AssetCurrencyDTO.builder()
                .id(id)
                .biz(CurrencyBizEnum.C2C.getName())
                .symbol(assetCurrencyVO.getSymbol())
                .sign(assetCurrencyVO.getSign())
                .fullName(assetCurrencyVO.getFullName())
                .currencyPictureUrl(assetCurrencyVO.getCurrencyPictureUrl())
                .sort(assetCurrencyVO.getSort())
                .online(assetCurrencyVO.getOnline())
                .transfer(assetCurrencyVO.getTransfer())
                .decimalPlaces(assetCurrencyVO.getDecimalPlaces())
                .type(assetCurrencyVO.getType())
                .isAssetVisible(assetCurrencyVO.getIsAssetVisible())
                .platformCommissionRate(assetCurrencyVO.getPlatformCommissionRate())
                .minOrderTotalPerOrder(assetCurrencyVO.getMinOrderTotalPerOrder())
                .maxOrderTotalPerOrder(assetCurrencyVO.getMaxOrderTotalPerOrder())
                .maxIncompleteOrderTotalPerUser(assetCurrencyVO.getMaxIncompleteOrderTotalPerUser())
                .deposit(assetCurrencyVO.getDeposit())
                .minOrderTotalPerTradingOrder(assetCurrencyVO.getMinOrderTotalPerTradingOrder())
                .maxOrderTotalPerTradingOrder(assetCurrencyVO.getMaxOrderTotalPerTradingOrder())
                .dapps(assetCurrencyVO.getDapps())
                .issuePrice(assetCurrencyVO.getIssuePrice())
                .zone(assetCurrencyVO.getZone())
                .createdDate(createdDate)
                .withdrawable(0)
                .rechargeable(0)
                .exchange(0)
                .receive(0)
                .brokerId(assetCurrencyVO.getBrokerId())
                .build();

        final ResponseResult result = this.adminServiceClient.editCurrency(assetCurrencyDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

}
