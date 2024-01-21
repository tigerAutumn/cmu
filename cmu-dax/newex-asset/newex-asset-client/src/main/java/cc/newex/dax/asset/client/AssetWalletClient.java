package cc.newex.dax.asset.client;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.dto.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 06/04/2018
 */
@FeignClient(name = "${newex.feignClient.dax.asset}", path = "/inner/v1/asset")
public interface AssetWalletClient {
    @PostMapping(value = "/wallet/deposit/address")
    ResponseResult<DepositAddressDto> depositAddress(@RequestParam("userId") final long userId,
                                                     @RequestParam("biz") final String biz,
                                                     @RequestParam("currency") final String symbol,
                                                     @RequestParam("brokerId") Integer brokerId);

    @GetMapping(value = "/wallet/deposit/records")
    ResponseResult<Page<DepositRecordDto>> getDepositRecords(@RequestParam("userId") final long userId,
                                                             @RequestParam("currency") final String symbol,
                                                             @RequestParam("address") final String address,
                                                             @RequestParam("pageNum") final Integer pageNum,
                                                             @RequestParam("pageSize") final Integer pageSize,
                                                             @RequestParam("brokerId") Integer brokerId);

    @GetMapping(value = "/wallet/deposit/record")
    ResponseResult<DepositRecordDto> getDepositRecord(@RequestParam("userId") final long userId, @RequestParam("tradeNo") final String tradeNo);

    @GetMapping(value = "/wallet/withdraw/info/{currency}")
    ResponseResult<WithDrawInfoDto> getWithdrawInfo(@PathVariable("currency") String symbol,
                                                    @RequestParam("brokerId") Integer brokerId);


    @PostMapping(value = "/withdraw")
    ResponseResult<TransferRecordResDto> withdraw(@RequestParam("userId") final long userId,
                                                  @RequestParam("biz") String biz,
                                                  @RequestParam("currencyCode") final String symbol,
                                                  @RequestParam("address") final String address,
                                                  @RequestParam("amount") final BigDecimal amount,
                                                  @RequestParam("tradeNo") final String tradeNo,
                                                  @RequestParam("brokerId") Integer brokerId);


    @GetMapping(value = "/wallet/withdraw/records")
    ResponseResult<Page<WithdrawRecordDto>> getWithdrawRecords(@RequestParam("userId") final long userId,
                                                               @RequestParam("currency") final String symbol,
                                                               @RequestParam("address") final String address,
                                                               @RequestParam("pageNum") final Integer pageNum,
                                                               @RequestParam("pageSize") final Integer pageSize,
                                                               @RequestParam("brokerId") Integer brokerId);

    @GetMapping(value = "/wallet/withdraw/record")
    ResponseResult<WithdrawRecordDto> getWithdrawRecord(@RequestParam("userId") final long userId, @RequestParam("tradeNo") final String tradeNo);

}
