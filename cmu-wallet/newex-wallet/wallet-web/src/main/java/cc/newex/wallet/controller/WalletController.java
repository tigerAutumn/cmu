package cc.newex.wallet.controller;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.criteria.CurrencyBalanceExample;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.AddressDto;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.CurrencyBalance;
import cc.newex.wallet.service.AddressService;
import cc.newex.wallet.service.CurrencyBalanceService;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.IWallet;
import cc.newex.wallet.wallet.WalletContext;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 26/03/2018
 */
@Slf4j
@RestController
@RequestMapping("/wallet/v1")
public class WalletController {
    @Autowired
    private WalletContext context;

    @Autowired
    private CurrencyBalanceService balanceService;

    @Autowired
    private AddressService addressService;

    @PostMapping("/address")
    public ResponseResult<AddressDto> genNewAddress(@RequestParam(value = "currency") final Integer currency,
                                                    @RequestParam(value = "userId") final Long userId,
                                                    @RequestParam(value = "biz") final Integer biz) {

        log.info("genNewAddress, currency: {}, userId: {}, biz: {}", currency, userId, biz);

        final AddressDto addressDto = new AddressDto();
        try {
            CurrencyEnum coin = CurrencyEnum.parseValue(currency);
            BizEnum.parseBiz(biz);
//            if (CurrencyEnum.isErc20(coin)) {
//                coin = CurrencyEnum.ETH;
//            }
            coin = CurrencyEnum.toMainCurrency(coin);
            final IWallet wallet = this.context.getWallet(coin);

            final Address address = wallet.genNewAddress(userId, biz);
            addressDto.setUserId(userId);
            addressDto.setBiz(biz);
            addressDto.setAddress(address.getAddress());
        } catch (final Throwable e) {
            log.error("genNewAddress error,userId:{},currery:{},biz:{}", userId, currency, biz, e);
            return ResultUtils.failure("genNewAddress error");
        }

        return ResultUtils.success(addressDto);

    }

    @GetMapping("/address/valid")
    public ResponseResult checkAddressValid(@RequestParam(value = "currency") final Integer currency,
                                            @RequestParam(value = "address") final String address) {
        final JSONObject json = new JSONObject();
        try {
            final CurrencyEnum coin = CurrencyEnum.parseValue(currency);
            final IWallet wallet = this.context.getWallet(coin);
            final boolean valid = wallet.checkAddress(address);
            json.put("address", address);
            json.put("valid", valid);
        } catch (final Throwable e) {
            log.error("checkAddressValid error", e);
            return ResultUtils.failure("checkAddressValid error");
        }

        return ResultUtils.success(json);

    }

    @GetMapping("/balance")
    public ResponseResult genBalance(@RequestParam(value = "currency") final Integer currency) {

        final JSONObject json = new JSONObject();
        try {
            final CurrencyEnum coin = CurrencyEnum.parseValue(currency);
            final CurrencyBalanceExample example = new CurrencyBalanceExample();
            example.createCriteria().andCurrencyIndexEqualTo(coin.getIndex());
            final CurrencyBalance balance = this.balanceService.getOneByExample(example);
            if (ObjectUtils.isEmpty(balance)) {
                json.put("balance", BigDecimal.ZERO);
            } else {
                json.put("balance", balance.getBalance());
            }

        } catch (final Throwable e) {
            log.error("genBalance error", e);
            return ResultUtils.failure("genBalance error");
        }
        return ResultUtils.success(json);

    }

    @PostMapping(value = "/address/need/{currency}")
    public ResponseResult<?> postNeedAddress(@PathVariable(value = "currency") final Integer currency,
                                             @RequestBody final List<String> addresses) {

        try {
            final CurrencyEnum coin = CurrencyEnum.parseValue(currency);
            if (!CollectionUtils.isEmpty(addresses)) {
                final List<Address> res = addresses.parallelStream().map((addr) -> {
                    String[] tmp = addr.split(":");
                    return Address.builder()
                            .address(tmp[0]).index(Integer.parseInt(tmp[1])).userId(-1L).balance(BigDecimal.ZERO)
                            .currency(coin.getName()).biz(0).nonce(0).status((byte) Constants.WAITING)
                            .createDate(Date.from(Instant.now()))
                            .updateDate(Date.from(Instant.now()))
                            .build();
                }).collect(Collectors.toList());
                final ShardTable table = ShardTable.builder().prefix(coin.getName()).build();
                this.addressService.batchAddOnDuplicateKey(res, table);

            } else {
                return ResultUtils.failure("postNeedAddress error: addresses is empty");
            }

            return ResultUtils.success();

        } catch (final Throwable e) {
            log.error("postNeedAddress error", e);
            return ResultUtils.failure("postNeedAddress error");
        }

    }

    @GetMapping(value = "/address/need/{currency}")
    public ResponseResult<?> getNeedAddressCount(@PathVariable(value = "currency") final Integer currency) {
        final JSONObject json = new JSONObject();
        try {
            final CurrencyEnum coin = CurrencyEnum.parseValue(currency);
            final ShardTable table = ShardTable.builder().prefix(coin.getName()).build();
            final AddressExample example = new AddressExample();
            example.createCriteria().andUserIdEqualTo(-1L);
            example.setOrderByClause("id desc");

            final Address address = this.addressService.getOneByExample(example, table);
            int maxIndex = 0;
            if (!ObjectUtils.isEmpty(address)) {
                maxIndex = address.getIndex();
            }

            final int count = this.addressService.countByExam(example, table);

            json.put("currency", coin.getName());
            json.put("count", count);
            json.put("index", maxIndex + 1);

        } catch (final Throwable e) {
            log.error("getNeedAddressCount error", e);
            return ResultUtils.failure("getNeedAddressCount error");
        }
        return ResultUtils.success(json);
    }

    @GetMapping("/balance/all")
    public ResponseResult<List<CurrencyBalance>> genAllBalance() {
        try {
            final List<CurrencyBalance> allCurrencyBalance = this.balanceService.getAll();
            return ResultUtils.success(allCurrencyBalance);
        } catch (final Throwable e) {
            log.error("genAllBalance error", e);
            return ResultUtils.failure("genAllBalance error");
        }
    }

}
