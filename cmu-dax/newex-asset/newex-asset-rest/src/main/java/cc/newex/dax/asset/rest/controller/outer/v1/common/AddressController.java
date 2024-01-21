package cc.newex.dax.asset.rest.controller.outer.v1.common;

import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.common.util.BeanConvertUtil;
import cc.newex.dax.asset.criteria.WithdrawAddressExample;
import cc.newex.dax.asset.domain.AssetCurrency;
import cc.newex.dax.asset.domain.DepositAddress;
import cc.newex.dax.asset.domain.WithdrawAddress;
import cc.newex.dax.asset.dto.DepositAddressDto;
import cc.newex.dax.asset.dto.WithdrawAddressDto;
import cc.newex.dax.asset.rest.params.AddressParam;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.service.DepositAddressService;
import cc.newex.dax.asset.service.WithdrawAddressService;
import cc.newex.dax.asset.util.DomainUtil;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.wallet.client.WalletClient;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.exception.UnsupportedBiz;
import cc.newex.wallet.exception.UnsupportedCurrency;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 03/04/2018
 */
@Slf4j
@RestController
@RequestMapping("/v1/asset/{biz}/address")
public class AddressController {

    @Autowired
    DepositAddressService depositAddressService;

    @Autowired
    WithdrawAddressService withdrawAddressService;

    @Autowired
    WalletClient walletClient;
    @Autowired
    private UsersClient usersClient;

    @Autowired
    private AssetCurrencyCompressService currencyCompressService;


    @GetMapping("/deposit/{currency}")
    public ResponseResult<?> getNewAddress(@PathVariable("biz") String biz,
                                           @PathVariable("currency") String currency,
                                           final HttpServletRequest request) {

        try {
            Long userId = this.getUserId(request);
            if (ObjectUtils.isEmpty(userId)) {
                return ResultUtils.failure(BizErrorCodeEnum.UNAUTHORIZED);
            }
            CurrencyEnum walletCurrency = CurrencyEnum.parseName(currency);

            walletCurrency = CurrencyEnum.toMainCurrency(walletCurrency);
            BizEnum bizEnum = BizEnum.parseBiz(biz);

            String domain = DomainUtil.getDomain(request);
            ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
            if (brokerIdResult.getCode() != 0) {
                return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
            }
            //若币种的充值未开，则不生成新地址
            AssetCurrency info = this.currencyCompressService.getCurrency(walletCurrency.getName(), bizEnum.getIndex(), brokerIdResult.getData());
            if (ObjectUtils.isEmpty(info)) {
                log.error("Asset not support : {}", walletCurrency.name());
                return ResultUtils.failure(BizErrorCodeEnum.DEPOSIT_CLOSE);
            }
            //该币种已经停了充值
            if (info.getRechargeable().intValue() == 0) {
                log.info("deposit pause, currency : {}, userId : {}", currency, userId);
                return ResultUtils.failure(BizErrorCodeEnum.DEPOSIT_CLOSE);
            }

            DepositAddress address = this.depositAddressService.getDepositAddress(userId, bizEnum, walletCurrency, brokerIdResult.getData());
            if (ObjectUtils.isEmpty(address)){
                AddressController.log.error("getDepositAddress is null");
                return ResultUtils.failure(BizErrorCodeEnum.DEPOSIT_CLOSE);
            }

            DepositAddressDto addressDto = DepositAddressDto.builder()
                    .address(address.getAddress())
                    .biz(bizEnum.getIndex())
                    .userId(userId)
                    .build();
            AssetCurrency assetCurrency = this.currencyCompressService.getCurrency(currency, biz, brokerIdResult.getData());
            if (!ObjectUtils.isEmpty(assetCurrency)) {
                BeanConvertUtil.fillAddressDtoInfo(addressDto, assetCurrency);
            }
            return ResultUtils.success(addressDto);

        } catch (UnsupportedCurrency | UnsupportedBiz e) {
            AddressController.log.error("getNewAddress error", e);
            return ResultUtils.failure(e);
        } catch (Throwable e) {
            AddressController.log.error("getNewAddress", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }

        //return result;

    }

    @PostMapping("/withdraw/{currency}")
    public ResponseResult addWithdrawAddress(final HttpServletRequest request,
                                             @PathVariable("biz") String biz,
                                             @PathVariable("currency") String currency,
                                             @RequestBody @Valid AddressParam addressParam) {
        try {
            CurrencyEnum coin = CurrencyEnum.parseName(currency);
            Long userId = this.getUserId(request);
            if (ObjectUtils.isEmpty(userId)) {
                return ResultUtils.failure(BizErrorCodeEnum.UNAUTHORIZED);
            }
            /**
             * 验证充值地址是否正确
             */
            try {

                ResponseResult responseResult = this.walletClient.checkAddressValid(coin.getIndex(), addressParam.getAddress());
                JSONObject jsonObject = JSON.parseObject(responseResult.getData() + "");
                if (BooleanUtils.isFalse((Boolean) jsonObject.get("valid"))) {
                    return ResultUtils.failure(BizErrorCodeEnum.INVALID_ADDRESS);
                }
            } catch (Exception e) {
                AddressController.log.error("WithdrawController addWithdrawAddress is exception", e);
                return ResultUtils.failure(BizErrorCodeEnum.INVALID_ADDRESS);
            }

            String domain = DomainUtil.getDomain(request);
            ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
            if (brokerIdResult.getCode() != 0) {
                return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
            }

            //Currency coin = Currency.parseName(currency);
            BizEnum bizEnum = BizEnum.parseBiz(biz);
            WithdrawAddressExample example = new WithdrawAddressExample();
            example.createCriteria()
                    .andCurrencyEqualTo(coin.getIndex())
                    .andUserIdEqualTo(userId)
                    .andAddressEqualTo(addressParam.getAddress())
                    .andBizEqualTo(bizEnum.getIndex())
                    .andStatusEqualTo((byte) 0)
                    .andBrokerIdEqualTo(brokerIdResult.getData());
            WithdrawAddress address = this.withdrawAddressService.getOneByExample(example);
            if (ObjectUtils.isEmpty(address)) {
                address = WithdrawAddress.builder()
                        .address(addressParam.getAddress())
                        .biz(bizEnum.getIndex())
                        .currency(coin.getIndex())
                        .remark(addressParam.getRemark())
                        .status((byte) 0)
                        .userId(userId)
                        .brokerId(brokerIdResult.getData())
                        .build();
                this.withdrawAddressService.add(address);
            } else {
                return ResultUtils.failure(BizErrorCodeEnum.ADDRESS_HAS_EXISTED);
            }

        } catch (UnsupportedCurrency | UnsupportedBiz e) {
            log.error("getWithdrawAddress UnsupportedCurrency UnsupportedBiz {} ", e);
            return ResultUtils.failure(e);
        } catch (Throwable e) {
            log.error("getWithdrawAddress error throwable {}", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }

        return ResultUtils.success();

    }

    @GetMapping("/withdraw/{currency}")
    public ResponseResult<?> getWithdrawAddress(@PathVariable("biz") String biz, @PathVariable("currency") String currency, final HttpServletRequest request) {

        ResponseResult result;
        try {
            Long userId = this.getUserId(request);
            CurrencyEnum coin = CurrencyEnum.parseName(currency);
            BizEnum bizEnum = BizEnum.parseBiz(biz);

            String domain = DomainUtil.getDomain(request);
            ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
            if (brokerIdResult.getCode() != 0) {
                return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
            }

            WithdrawAddressExample example = new WithdrawAddressExample();
            example.createCriteria()
                    .andCurrencyEqualTo(coin.getIndex())
                    .andUserIdEqualTo(userId)
                    .andBizEqualTo(bizEnum.getIndex())
                    .andStatusEqualTo((byte) 0)
                    .andBrokerIdEqualTo(brokerIdResult.getData());
            List<WithdrawAddress> addresses = this.withdrawAddressService.getWithdrawAddress(userId, bizEnum, coin);

            AssetCurrency assetCurrency = this.currencyCompressService.getCurrency(currency, biz, brokerIdResult.getData());

            List<WithdrawAddressDto> addressDtos = addresses.parallelStream().map((address) -> {
                WithdrawAddressDto addressDto = WithdrawAddressDto.builder()
                        .address(address.getAddress())
                        .biz(address.getBiz())
                        .currency(address.getCurrency())
                        .userId(address.getUserId())
                        .needTag(assetCurrency.getNeedTag() > 0)
                        .tagField(LocaleUtils.getMessage(assetCurrency.getTagField()))
                        .remark(address.getRemark())
                        .build();

                return addressDto;
            }).collect(Collectors.toList());
            result = ResultUtils.success(addressDtos);

        } catch (UnsupportedCurrency e) {
            return ResultUtils.failure(BizErrorCodeEnum.INVALID_CURRENCY);
        } catch (UnsupportedBiz e) {
            return ResultUtils.failure(BizErrorCodeEnum.INVALID_BIZ);
        } catch (Throwable e) {
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }

        return result;

    }

    private Long getUserId(final HttpServletRequest request) {
        final JwtUserDetails userJwt = JwtTokenUtils.getCurrentLoginUser(request);
        if (userJwt.getStatus() != 0) {
            return null;
        }
        return userJwt.getUserId();
    }
}
