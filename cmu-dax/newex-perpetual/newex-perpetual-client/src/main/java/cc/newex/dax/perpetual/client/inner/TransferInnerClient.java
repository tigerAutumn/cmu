package cc.newex.dax.perpetual.client.inner;

import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.dto.AssetsTransferDTO;
import cc.newex.dax.perpetual.dto.UserAssetsBalanceDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(value = "${newex.feignClient.dax.perpetual}", path = "/inner/v1/perpetual/asset")
public interface TransferInnerClient {

    @PostMapping(value = "/transfer-in")
    ResponseResult transferIn(@RequestParam("userId") final long userId,
                              @RequestParam("currencyCode") final String currencyCode,
                              @RequestParam("brokerId") final Integer brokerId,
                              @RequestParam("amount") final BigDecimal amount,
                              @RequestParam("tradeNo") final String tradeNo,
                              @RequestParam(value = "transferType", required = false) final Integer transferType);

    @PostMapping(value = "/transfer-out")
    ResponseResult transferOut(@RequestParam("userId") final long userId,
                               @RequestParam("currencyCode") final String currencyCode,
                               @RequestParam("brokerId") final Integer brokerId,
                               @RequestParam("amount") final BigDecimal amount,
                               @RequestParam("tradeNo") final String tradeNo,
                               @RequestParam(value = "transferType", required = false) final Integer transferType);

    @GetMapping(value = "/transfer-list")
    ResponseResult<DataGridPagerResult<AssetsTransferDTO>> transferList(
            @RequestParam(value = "brokerId", required = false) final Integer brokerId,
            @RequestParam(value = "userIds", required = false) final Long[] userIds,
            @RequestParam(value = "currencyCodes", required = false) final String[] currencyCodes,
            @RequestParam(value = "tradeNos", required = false) final String[] tradeNos,
            @RequestParam(value = "startTime", required = false) final Long startTimeMills,
            @RequestParam(value = "endTime", required = false) final Long endTimeMills,
            @RequestParam(value = "page", required = false, defaultValue = "1") final Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "100") final Integer pageSize);

    /**
     * 返回给asset用户合约资产列表
     *
     * @param userId
     * @param brokerId
     */
    @GetMapping(value = "/balance")
    ResponseResult<List<UserAssetsBalanceDTO>> queryUserBalance(
            @RequestParam(value = "userId") final Long userId,
            @RequestParam(value = "brokerId") final Integer brokerId);
}
