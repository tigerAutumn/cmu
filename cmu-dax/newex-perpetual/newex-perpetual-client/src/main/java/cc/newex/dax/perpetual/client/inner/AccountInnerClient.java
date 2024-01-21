package cc.newex.dax.perpetual.client.inner;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.dto.UserBalanceDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/12/14
 */
@FeignClient(value = "${newex.feignClient.dax.perpetual}", path = "/inner/v1/perpetual/account")
public interface AccountInnerClient {
    /**
     * 查询资产
     */
    @GetMapping("/balance")
    ResponseResult<List<UserBalanceDTO>> account(@RequestParam(value = "currencyCode") final String currencyCode,
                                                 @RequestParam(value = "userIdList") final List<Long> userIdList,
                                                 @RequestParam(value = "brokerId") final Integer brokerId);

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
    ResponseResult bossReward(@RequestParam(value = "userId") final long userId,
                              @RequestParam(value = "brokerId") final Integer brokerId,
                              @RequestParam(value = "currencyCode") final String currencyCode,
                              @RequestParam(value = "amount") final BigDecimal amount);
}
