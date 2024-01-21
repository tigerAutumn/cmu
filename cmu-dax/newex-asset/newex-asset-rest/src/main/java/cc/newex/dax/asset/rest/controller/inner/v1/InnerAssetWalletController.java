package cc.newex.dax.asset.rest.controller.inner.v1;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.common.util.BeanConvertUtil;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.domain.*;
import cc.newex.dax.asset.dto.*;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.service.DepositAddressService;
import cc.newex.dax.asset.service.NotifyUserService;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.dax.asset.util.DomainUtil;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.exception.UnsupportedBiz;
import cc.newex.wallet.exception.UnsupportedCurrency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static cc.newex.dax.asset.common.enums.BizErrorCodeEnum.UNAUTHORIZED;

/**
 * @author newex-team
 * @data 2018/6/5
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/asset/wallet")
public class InnerAssetWalletController {

    @Autowired
    TransferRecordService recordService;
    @Autowired
    private DepositAddressService depositAddressService;
    @Autowired
    private AssetCurrencyCompressService currencyCompressService;

    @Autowired
    private NotifyUserService notifyUserService;
    @Autowired
    private UsersClient usersClient;

    @PostMapping("/deposit/address")
    ResponseResult<DepositAddressDto> depositAddress(@RequestParam("userId") long userId,
                                                     @RequestParam("biz") String biz,
                                                     @RequestParam("currency") String symbol,
                                                     @RequestParam("brokerId") Integer brokerId,
                                                     HttpServletRequest request) {
        try {
            NotifyUser notifyUser = this.notifyUserService.getByUserId(userId);
            if (ObjectUtils.isEmpty(notifyUser)) {
                return ResultUtils.failure(UNAUTHORIZED);
            }
            CurrencyEnum walletCurrency = CurrencyEnum.parseName(symbol);
            walletCurrency = CurrencyEnum.toMainCurrency(walletCurrency);
            BizEnum bizEnum = BizEnum.parseBiz(biz);

            AssetCurrency info = this.currencyCompressService.getCurrency(symbol, BizEnum.SPOT.getName(), brokerId);
            if (info.getRechargeable() > 0) {
                String domain = DomainUtil.getDomain(request);
                ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
                if (brokerIdResult.getCode() != 0) {
                    return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
                }
                DepositAddress address = this.depositAddressService.getDepositAddressFromWallet(userId, bizEnum, walletCurrency, brokerIdResult.getData());
                DepositAddressDto addressDto = DepositAddressDto.builder().address(address.getAddress()).biz(bizEnum.getIndex()).userId
                        (userId).currency(walletCurrency.getIndex()).build();
                AssetCurrency assetCurrency = this.currencyCompressService.getCurrency(symbol, biz, brokerId);
                if (!ObjectUtils.isEmpty(assetCurrency)) {
                    BeanConvertUtil.fillAddressDtoInfo(addressDto, assetCurrency);
                }
                return ResultUtils.success(addressDto);
            } else {
                DepositAddressDto addressDto = DepositAddressDto.builder().biz(bizEnum.getIndex()).userId
                        (userId).rechargeable(info.getRechargeable()).currency(walletCurrency.getIndex()).build();
                return ResultUtils.success(addressDto);

            }

        } catch (UnsupportedCurrency | UnsupportedBiz e) {
            log.error("depositAddress error", e);
            return ResultUtils.failure(e);
        } catch (Throwable e) {
            log.error("depositAddress error", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @GetMapping(value = "/deposit/records")
    ResponseResult<Page<DepositRecordDto>> getDepositRecords(@RequestParam("userId") long userId,
                                                             @RequestParam("currency") String symbol,
                                                             @RequestParam("address") String address,
                                                             @RequestParam("pageNum") Integer pageNum,
                                                             @RequestParam("pageSize") Integer pageSize,
                                                             @RequestParam("brokerId") Integer brokerId) {
        try {

            TransferRecordExample example = new TransferRecordExample();
            TransferRecordExample.Criteria criteria = example.createCriteria();
            criteria.andTransferTypeEqualTo(TransferType.DEPOSIT.getCode()).andUserIdEqualTo(userId);
            if (StringUtils.hasText(symbol)) {
                CurrencyEnum coin = CurrencyEnum.parseName(symbol);
                criteria.andCurrencyEqualTo(coin.getIndex());
            }
            if (StringUtils.hasText(address)) {
                criteria.andToEqualTo(address);
            }
            PageEntity pageEntity = PageEntity.getPage(this.recordService, pageNum, pageSize, example);
            List<TransferRecord> transferRecords = pageEntity.getData();
            AssetCurrency info = this.currencyCompressService.getCurrency(symbol, BizEnum.SPOT.getName(), brokerId);
            List<DepositRecordDto> depositRecordDtos = transferRecords.parallelStream().map((record) -> {
                DepositRecordDto deposit = new DepositRecordDto();
                BeanConvertUtil.convert(deposit, record);
                deposit.setExplorerUrl(info.getTxExplorerUrl());
                return deposit;
            }).collect(Collectors.toList());
            pageEntity.setData(depositRecordDtos);
            Page<DepositRecordDto> page = new Page<>();
            BeanUtils.copyProperties(pageEntity, page);

            return ResultUtils.success(page);
        } catch (UnsupportedCurrency e) {
            log.error("getDepositRecord error", e);
            return ResultUtils.failure("getDepositRecord error");
        } catch (Throwable e) {
            log.error("getDepositRecord error", e);
            return ResultUtils.failure(e.getMessage());
        }
    }

    @GetMapping(value = "/deposit/record")
    ResponseResult<DepositRecordDto> getDepositRecord(@RequestParam("userId") long userId,
                                                      @RequestParam("tradeNo") String tradeNo,
                                                      @RequestParam("brokerId") Integer brokerId) {
        try {

            TransferRecordExample example = new TransferRecordExample();
            TransferRecordExample.Criteria criteria = example.createCriteria();
            criteria.andTransferTypeEqualTo(TransferType.DEPOSIT.getCode()).andUserIdEqualTo(userId);
            criteria.andTraderNoEqualTo(tradeNo);
            criteria.andBrokerIdEqualTo(brokerId);
            TransferRecord record = this.recordService.getOneByExample(example);

            DepositRecordDto deposit = new DepositRecordDto();
            BeanConvertUtil.convert(deposit, record);

            return ResultUtils.success(deposit);
        } catch (UnsupportedCurrency e) {
            log.error("getDepositRecord error", e);
            return ResultUtils.failure("getDepositRecord error");
        } catch (Throwable e) {
            log.error("getDepositRecord error", e);
            return ResultUtils.failure(e.getMessage());
        }
    }

    @GetMapping(value = "/withdraw/info/{currency}")
    ResponseResult<WithDrawInfoDto> getWithdrawInfo(@PathVariable("currency") String symbol,
                                                    @RequestParam("brokerId") Integer brokerId) {
        try {
            AssetCurrency info = this.currencyCompressService.getCurrency(symbol, BizEnum.SPOT.getName(), brokerId);

            if (info != null) {
                WithDrawInfoDto withDrawInfoDto = new WithDrawInfoDto();
                BeanConvertUtil.fillWithdrawInfo(withDrawInfoDto, info);
                return ResultUtils.success(withDrawInfoDto);
            } else {
                return ResultUtils.failure(BizErrorCodeEnum.INVALID_CURRENCY);
            }
        } catch (Throwable e) {
            log.error("get withdraw currency info error {} ", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @GetMapping(value = "/withdraw/records")
    ResponseResult<Page<WithdrawRecordDto>> getWithdrawRecords(@RequestParam("userId") long userId,
                                                               @RequestParam("currency") String symbol,
                                                               @RequestParam("address") String address,
                                                               @RequestParam("pageNum") Integer pageNum,
                                                               @RequestParam("pageSize") Integer pageSize,
                                                               @RequestParam("brokerId") Integer brokerId) {
        try {
            TransferRecordExample example = new TransferRecordExample();
            TransferRecordExample.Criteria criteria = example.createCriteria();
            criteria.andTransferTypeEqualTo(TransferType.WITHDRAW.getCode());
            criteria.andUserIdEqualTo(userId);
            criteria.andBrokerIdEqualTo(brokerId);
            if (StringUtils.hasText(symbol)) {
                CurrencyEnum coin = CurrencyEnum.parseName(symbol);
                criteria.andCurrencyEqualTo(coin.getIndex());
            }
            if (StringUtils.hasText(address)) {
                criteria.andToEqualTo(address);
            }
            AssetCurrency info = this.currencyCompressService.getCurrency(symbol, BizEnum.SPOT.getName(), brokerId);
            PageEntity pageEntity = PageEntity.getPage(this.recordService, pageNum, pageSize, example);
            List<TransferRecord> transferRecords = pageEntity.getData();
            List<WithdrawRecordDto> withdrawRecordDtos = transferRecords.parallelStream().map((record) -> {
                WithdrawRecordDto withdrawRecordDto = new WithdrawRecordDto();
                BeanConvertUtil.convert(withdrawRecordDto, record);
                withdrawRecordDto.setExplorerUrl(info.getTxExplorerUrl());
                return withdrawRecordDto;
            }).collect(Collectors.toList());
            pageEntity.setData(withdrawRecordDtos);
            Page<WithdrawRecordDto> page = new Page<>();
            BeanUtils.copyProperties(pageEntity, page);
            return ResultUtils.success(page);

        } catch (UnsupportedCurrency e) {
            log.error("getWithdrawRecord", e);
            return ResultUtils.failure(e);
        } catch (Throwable e) {
            log.error("getWithdrawRecord", e);
            return ResultUtils.failure("getWithdrawRecord error");
        }

    }

    @GetMapping(value = "/withdraw/record")
    ResponseResult<WithdrawRecordDto> getWithdrawRecords(@RequestParam("userId") long userId, @RequestParam("tradeNo") String tradeNo) {
        try {
            TransferRecordExample example = new TransferRecordExample();
            TransferRecordExample.Criteria criteria = example.createCriteria();
            criteria.andTransferTypeEqualTo(TransferType.WITHDRAW.getCode());
            criteria.andUserIdEqualTo(userId);
            criteria.andTraderNoEqualTo(tradeNo);
            TransferRecord record = this.recordService.getOneByExample(example);

            WithdrawRecordDto withdrawRecordDto = new WithdrawRecordDto();
            BeanConvertUtil.convert(withdrawRecordDto, record);
            return ResultUtils.success(withdrawRecordDto);

        } catch (UnsupportedCurrency e) {
            log.error("getWithdrawRecord", e);
            return ResultUtils.failure(e);
        } catch (Throwable e) {
            log.error("getWithdrawRecord", e);
            return ResultUtils.failure("getWithdrawRecord error");
        }

    }

}
