package cc.newex.wallet.client;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.wallet.dto.AddressDto;
import cc.newex.wallet.pojo.CurrencyBalance;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.List;

/**
 * @author newex-team
 * @data 05/04/2018
 */
@FeignClient(name = "${newex.feignClient.wallet.core:newex-wallet-core}")
public interface WalletClient {
    /**
     * 生成新地址
     *
     * @param currency
     * @param userId
     * @param biz
     * @return
     */
    @PostMapping(value = "/wallet/v1/address")
    ResponseResult<AddressDto> generateNewAddress(@RequestParam(value = "currency") final Integer currency,
                                                  @RequestParam(value = "userId") final Long userId,
                                                  @RequestParam(value = "biz") final Integer biz);

    /**
     * 主要针对一些没有公钥或者不能通过公钥生成地址的币种（比如iota）
     *
     * @param currency
     * @param addresses
     * @return
     */
    @PostMapping(value = "/wallet/v1/address/need/{currency}")
    ResponseResult<?> postNeedAddress(@PathVariable(value = "currency") final Integer currency,
                                      @RequestBody final List<String> addresses);

    /**
     * 主要针对一些没有公钥或者不能通过公钥生成地址的币种（比如iota）
     *
     * @param currency
     * @return
     */
    @GetMapping(value = "/wallet/v1/address/need/{currency}")
    ResponseResult<JSONObject> getNeedAddressCount(@PathVariable(value = "currency") final Integer currency);
    /**
     * @param currency
     * @param userId
     * @param biz
     * @return
     */
    @PostMapping(value = "/wallet/v1/transfer-out")
    ResponseResult<?> transferOut(@RequestParam(value = "currency") final Integer currency,
                                  @RequestParam(value = "userId") final Long userId,
                                  @RequestParam(value = "biz") final Integer biz);

    /**
     * 检查地址正确性
     *
     * @param currency
     * @param address
     * @return
     */
    @GetMapping("/wallet/v1/address/valid")
    ResponseResult checkAddressValid(@RequestParam(value = "currency") final Integer currency,
                                     @RequestParam(value = "address") final String address);

    /**
     * 查询所有币种余额
     *
     * @return
     */
    @GetMapping("/wallet/v1/balance/all")
    ResponseResult<List<CurrencyBalance>> genAllBalance();
}
