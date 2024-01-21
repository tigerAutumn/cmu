package cc.newex.dax.perpetual.rest.controller.inner.v1.ccex;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.dto.UserBalanceDTO;
import cc.newex.dax.perpetual.service.UserBalanceService;
import cc.newex.dax.perpetual.service.common.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资金划转
 *
 * @author newex-team
 */
@Slf4j
@RestController
@RequestMapping(value = "/inner/v1/perpetual/account")
public class AccountInnerController {
    @Resource
    private UserBalanceService userBalanceService;
    @Autowired
    private MarketService marketService;

    /**
     * 查询资产
     */
    @GetMapping("/balance")
    public ResponseResult<List<UserBalanceDTO>> account(@RequestParam(value = "currencyCode") final String currencyCode,
                                                        @RequestParam(value = "userIdList") final List<Long> userIdList,
                                                        @RequestParam(value = "brokerId") final Integer brokerId) {
        final List<UserBalanceDTO> userBalanceDTOlist = new ArrayList<>();
        final List<UserBalance> userBalanceList = this.userBalanceService.selectBatch(currencyCode, brokerId, userIdList, null);
        final Map<Long, BigDecimal> unrealizedSurplus = this.userBalanceService.unrealizedSurplus(brokerId, userIdList, currencyCode, this.marketService.allMarkIndexPrice());
        userBalanceList.forEach(ub -> {
            userBalanceDTOlist.add(UserBalanceDTO.builder().userId(ub.getUserId()).currencyCode(ub.getCurrencyCode())
                    .env(ub.getEnv()).totalBalance(ub.getAvailableBalance().add(ub.getFrozenBalance())
                            .add(ub.getPositionMargin()).add(ub.getPositionFee()).add(ub.getOrderMargin()).add(ub.getOrderFee()))
                    .realizedSurplus(ub.getRealizedSurplus()).unrealizedSurplus(unrealizedSurplus.get(ub.getUserId())).brokerId(ub.getBrokerId()).build());
        });
        return ResultUtils.success(userBalanceDTOlist);
    }

    /**
     * 给boss后台提供发活动币
     *
     * @param userId
     * @param brokerId
     * @param currencyCode
     * @param amount
     * @return
     */
    @PostMapping(value = "/boss-reward")
    public ResponseResult bossReward(@RequestParam(value = "userId") final long userId,
                                     @RequestParam(value = "brokerId") final Integer brokerId,
                                     @RequestParam(value = "currencyCode") final String currencyCode,
                                     @RequestParam(value = "amount") final BigDecimal amount) {
        this.userBalanceService.bossReward(currencyCode, userId, brokerId, amount);
        return ResultUtils.success();
    }
}
