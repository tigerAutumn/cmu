package cc.newex.dax.perpetual.rest.controller.admin.v1.ccex;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.dto.UserBalanceDTO;
import cc.newex.dax.perpetual.dto.UserBalanceParam;
import cc.newex.dax.perpetual.service.UserBalanceService;
import cc.newex.dax.perpetual.service.common.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin/v1/perpetual/account")
public class AccountAdminController {

    @Autowired
    private UserBalanceService userBalanceService;
    @Autowired
    private MarketService marketService;

    /**
     * 查询资产
     */
    @PostMapping(value = "/balance")
    ResponseResult<DataGridPagerResult<UserBalanceDTO>> account(
            @RequestBody final DataGridPager<UserBalanceParam> param) {

        final UserBalanceParam balanceParam = param.getQueryParameter();

        final List<UserBalanceDTO> userBalanceDTOlist = new ArrayList<>();

        final PageInfo pageInfo = param.toPageInfo();
        final List<UserBalance> userBalanceList = this.userBalanceService.selectBatch(balanceParam.getCurrencyCode(), balanceParam.getBrokerId(), balanceParam.getUserId(), pageInfo);
        final Map<Long, BigDecimal> unrealizedSurplus = this.userBalanceService.unrealizedSurplus(balanceParam.getBrokerId(), balanceParam.getUserId(), balanceParam.getCurrencyCode(), this.marketService.allMarkIndexPrice());
        userBalanceList.forEach(ub -> {
            userBalanceDTOlist.add(UserBalanceDTO.builder().userId(ub.getUserId()).currencyCode(ub.getCurrencyCode())
                    .env(ub.getEnv()).totalBalance(ub.getAvailableBalance().add(ub.getFrozenBalance())
                            .add(ub.getPositionMargin()).add(ub.getPositionFee()).add(ub.getOrderMargin()).add(ub.getOrderFee()))
                    .realizedSurplus(ub.getRealizedSurplus()).unrealizedSurplus(unrealizedSurplus.get(ub.getUserId())).brokerId(ub.getBrokerId()).build());
        });
        return ResultUtils.success(new DataGridPagerResult(pageInfo.getTotals(), userBalanceDTOlist));
    }
}
