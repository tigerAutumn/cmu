package cc.newex.dax.asset.rest.controller.outer.v1.common;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.dao.TransferRecordRepository;
import cc.newex.dax.asset.domain.AssetCurrency;
import cc.newex.dax.asset.domain.PageEntity;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.domain.TransferRecordDto;
import cc.newex.dax.asset.dto.DepositRecordDto;
import cc.newex.dax.asset.dto.WithdrawRecordDto;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.dax.asset.util.DomainUtil;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
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

/**
 * @author newex-team
 * @data 06/04/2018
 */
@Slf4j
@RestController
@RequestMapping("/v1/asset/{biz}/record")
public class RecordController {
    @Autowired
    TransferRecordService recordService;
    @Autowired
    TransferRecordRepository recordRepository;
    @Autowired
    private UsersClient usersClient;
    @Autowired
    private AssetCurrencyCompressService currencyCompressService;

    @GetMapping("/deposit/{currency}")
    public ResponseResult getDepositRecord(@PathVariable("biz") String biz, @PathVariable("currency") String currency,
                                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                           final HttpServletRequest request) {

        ResponseResult<PageEntity> result;
        try {
            BizEnum bizEnum = BizEnum.parseBiz(biz);
            Long userId = this.getUserId(request);
            if (ObjectUtils.isEmpty(userId)) {
                return ResultUtils.failure(BizErrorCodeEnum.UNAUTHORIZED);
            }

            String domain = DomainUtil.getDomain(request);
            ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
            if (brokerIdResult.getCode() != 0) {
                return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
            }

            TransferRecordExample example = new TransferRecordExample();
            example.createCriteria()
                    .andTransferTypeEqualTo(TransferType.DEPOSIT.getCode())
                    .andBizEqualTo(bizEnum.getIndex())
                    .andUserIdEqualTo(userId)
                    .andBrokerIdEqualTo(brokerIdResult.getData());
            if (StringUtils.hasText(currency)) {
                CurrencyEnum coin = CurrencyEnum.parseName(currency);
                example.getOredCriteria().get(0).andCurrencyEqualTo(coin.getIndex());
            }
            PageEntity pageEntity = PageEntity.getPage(this.recordService, pageNum, pageSize, example);
            List<TransferRecord> transferRecords = pageEntity.getData();
            AssetCurrency info = this.currencyCompressService.getCurrency(currency, biz, brokerIdResult.getData());
            List<DepositRecordDto> depositRecordDtos = transferRecords.parallelStream().map((record) -> {

                String from = record.getFrom();
                //去除 btc ltc usdt bch iota txid中的-之后的
                if (from.contains("-")) {
                    from = from.substring(0, from.indexOf("-"));
                }
                DepositRecordDto deposit = DepositRecordDto.builder()
                        .address(record.getTo())
                        .amount(record.getAmount().toPlainString())
                        .biz(record.getBiz())
                        .confirmation(record.getConfirmation())
                        .currency(record.getCurrency())
                        .txId(from)
                        .explorerUrl(info.getTxExplorerUrl() + from)
                        .userId(record.getUserId())
                        .status(record.getStatus().intValue())
                        .updateDate(record.getUpdateDate())
                        .build();
                CurrencyEnum coin = CurrencyEnum.parseValue(record.getCurrency());
                if (deposit.getConfirmation() >= coin.getConfirmNum()) {
                    deposit.setConfirmation(Integer.MAX_VALUE);
                }
                return deposit;
            }).collect(Collectors.toList());
            pageEntity.setData(depositRecordDtos);

            result = ResultUtils.success(pageEntity);

        } catch (UnsupportedCurrency e) {
            RecordController.log.error("getDepositRecord error", e);
            return ResultUtils.failure("getDepositRecord error");
        } catch (Throwable e) {
            RecordController.log.error("getDepositRecord error", e);
            return ResultUtils.failure(e.getMessage());
        }

        return result;

    }

    @GetMapping("/withdraw/{currency}")
    public ResponseResult getWithdrawRecord(@PathVariable("biz") String biz, @PathVariable("currency") String currency,
                                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                            final HttpServletRequest request) {
        ResponseResult<PageEntity> result;
        try {
            BizEnum bizEnum = BizEnum.parseBiz(biz);
            Long userId = this.getUserId(request);
            if (ObjectUtils.isEmpty(userId)) {
                return ResultUtils.failure(BizErrorCodeEnum.UNAUTHORIZED);
            }
            String domain = DomainUtil.getDomain(request);
            ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
            if (brokerIdResult.getCode() != 0) {
                return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
            }

            TransferRecordExample example = new TransferRecordExample();
            example.createCriteria()
                    .andTransferTypeEqualTo(TransferType.WITHDRAW.getCode())
                    .andBizEqualTo(bizEnum.getIndex())
                    .andUserIdEqualTo(userId)
                    .andBrokerIdEqualTo(brokerIdResult.getData());
            if (StringUtils.hasText(currency)) {
                CurrencyEnum coin = CurrencyEnum.parseName(currency);
                example.getOredCriteria().get(0).andCurrencyEqualTo(coin.getIndex());
            }
            AssetCurrency info = this.currencyCompressService.getCurrency(currency, biz, brokerIdResult.getData());
            PageEntity pageEntity = PageEntity.getPage(this.recordService, pageNum, pageSize, example);
            List<TransferRecord> transferRecords = pageEntity.getData();
            List<WithdrawRecordDto> withdrawRecordDtos = transferRecords.parallelStream().map((record) -> {
                WithdrawRecordDto withdraw = WithdrawRecordDto.builder()
                        .address(record.getTo())
                        .amount(record.getAmount().toPlainString())
                        .biz(record.getBiz())
                        .fee(record.getFee().toPlainString())
                        .currency(record.getCurrency())
                        .txId(record.getFrom())
                        .explorerUrl(info.getTxExplorerUrl() + record.getFrom())
                        .userId(record.getUserId())
                        .updateDate(record.getUpdateDate())
                        .build();
                return withdraw;
            }).collect(Collectors.toList());
            pageEntity.setData(withdrawRecordDtos);

            result = ResultUtils.success(pageEntity);

        } catch (UnsupportedCurrency e) {
            RecordController.log.error("getWithdrawRecord", e);
            return ResultUtils.failure("getWithdrawRecord");
        } catch (Throwable e) {
            RecordController.log.error("getWithdrawRecord", e);
            return ResultUtils.failure("getWithdrawRecord");
        }

        return result;
    }

    @GetMapping("/{currency}")
    public ResponseResult<TransferRecordDto> getRecord(@PathVariable("biz") String biz, @PathVariable("currency") String currency,
                                                       @RequestParam(value = "traderNo") String traderNo,
                                                       final HttpServletRequest request) {
        ResponseResult<TransferRecordDto> result;
        try {
            BizEnum bizEnum = BizEnum.parseBiz(biz);
            Long userId = this.getUserId(request);
            if (ObjectUtils.isEmpty(userId)) {
                return ResultUtils.failure(BizErrorCodeEnum.UNAUTHORIZED);
            }
            String domain = DomainUtil.getDomain(request);
            ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
            if (brokerIdResult.getCode() != 0) {
                return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
            }

            AssetCurrency info = this.currencyCompressService.getCurrency(currency, biz, brokerIdResult.getData());

            TransferRecordExample example = new TransferRecordExample();
            example.createCriteria().andTraderNoEqualTo(traderNo);
            TransferRecord record = this.recordService.getOneByExample(example);
            TransferRecordDto recordDto = new TransferRecordDto();
            BeanUtils.copyProperties(record, recordDto);
            recordDto.setAmount(record.getAmount().toPlainString());
            recordDto.setFee(record.getFee().toPlainString());
            recordDto.setExplorerUrl(info.getTxExplorerUrl());
            result = ResultUtils.success(recordDto);

        } catch (Throwable e) {
            RecordController.log.error("getRecord error", e);
            return ResultUtils.failure("getRecord error");
        }

        return result;
    }

    private Long getUserId(final HttpServletRequest request) {
        final JwtUserDetails userJwt = JwtTokenUtils.getCurrentLoginUser(request);
        if (userJwt.getStatus() != 0) {
            return null;
            //return ResultUtils.failure(BizErrorCodeEnum.UNAUTHORIZED);
        }
        return userJwt.getUserId();
    }
}
