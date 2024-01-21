package cc.newex.dax.asset.rest.controller.inner.v1;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.common.enums.TransferAuditStatus;
import cc.newex.dax.asset.common.enums.TransferStatus;
import cc.newex.dax.asset.common.util.BeanConvertUtil;
import cc.newex.dax.asset.common.util.BizClientUtil;
import cc.newex.dax.asset.common.util.TradeNoUtil;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.domain.AssetCurrency;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.domain.TransferRecordAudit;
import cc.newex.dax.asset.domain.TransferRecordDto;
import cc.newex.dax.asset.dto.*;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.service.TransferRecordAuditService;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.dax.asset.service.WithdrawAddressService;
import cc.newex.wallet.client.WalletClient;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.exception.UnsupportedBiz;
import cc.newex.wallet.exception.UnsupportedCurrency;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static java.math.BigDecimal.ZERO;

/**
 * 内部服务调用API Controller
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/asset")
public class InnerTransferController {

    @Autowired
    TransferRecordService recordService;
    @Autowired
    WithdrawAddressService withdrawAddressService;
    @Autowired
    private AssetCurrencyCompressService currencyCompressService;
    @Autowired
    private WalletClient walletClient;
    @Autowired
    private TransferRecordService transferRecordService;
    @Autowired
    private TransferRecordAuditService transferRecordAuditService;

    @PostMapping(value = "/transfer-in")
    ResponseResult transferIn(@RequestParam("userId") long userId,
                              @RequestParam("currencyId") int currencyId,
                              @RequestParam("amount") BigDecimal amount,
                              @RequestParam("tradeNo") String tradeNo) {
        try {

            TransferRecordExample example = new TransferRecordExample();
            example.createCriteria().andTraderNoEqualTo(tradeNo);
            TransferRecord record = this.recordService.getOneByExample(example);
            if (ObjectUtils.isEmpty(record)) {
                return ResultUtils.failure(BizErrorCodeEnum.NOT_FOUND);
            }
            currencyId = record.getCurrency();
            if (record.getTransferType().intValue() == TransferType.WITHDRAW.getCode()) {
                boolean success = this.recordService.sendWithdraw(record);
                if (success) {
                    return ResultUtils.success();
                } else {
                    InnerTransferController.log.error("sendWithdraw error");
                    return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
                }
            } else if (record.getTransferType().intValue() == TransferType.TRANSFER.getCode()) {
                BizEnum toBiz = BizEnum.parseBiz(record.getTo());
                CurrencyEnum currency = CurrencyEnum.parseValue(currencyId);
                record.setStatus((byte) TransferStatus.SENDING.getCode());
                record.setUpdateDate(Date.from(Instant.now()));
                this.transferRecordService.editById(record);
                ResponseResult result = BizClientUtil.transferIn(toBiz.getIndex(), userId, currency, amount, tradeNo, record.getBrokerId());
                if (result.getCode() == 0) {
                    record.setStatus((byte) TransferStatus.CONFIRMED.getCode());
                    record.setUpdateDate(Date.from(Instant.now()));
                    this.transferRecordService.editById(record);
                    return ResultUtils.success();
                } else {
                    InnerTransferController.log.error("transferIn error;{}", result);
                    return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
                }
            } else if (record.getTransferType().intValue() == TransferType.TRANSFER_OUT.getCode()) {
                BizEnum toBiz = BizEnum.parseBiz(record.getBiz());
                CurrencyEnum currency = CurrencyEnum.parseValue(currencyId);
                record.setStatus((byte) TransferStatus.SENDING.getCode());
                record.setUpdateDate(Date.from(Instant.now()));
                this.transferRecordService.editById(record);
                Long toUser = Long.valueOf(record.getTo());

                //同一个业务线，保证tradeNo的唯一性
                tradeNo = tradeNo + "-in";
                ResponseResult result = BizClientUtil.transferIn(toBiz.getIndex(), toUser, currency, amount, tradeNo, record.getBrokerId());
                if (result.getCode() == 0) {
                    record.setStatus((byte) TransferStatus.CONFIRMED.getCode());
                    record.setUpdateDate(Date.from(Instant.now()));
                    this.transferRecordService.editById(record);
                    return ResultUtils.success();
                } else {
                    InnerTransferController.log.error("transferIn error;{}", result);
                    return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
                }
            } else if (record.getTransferType() == TransferType.LOCKED_POSITION.getCode()) {
                record.setStatus((byte) TransferStatus.CONFIRMED.getCode());
                this.transferRecordService.confirmLockPositionRecord(tradeNo, userId, currencyId, record, amount);
                return ResultUtils.success();
            } else if (record.getTransferType() == TransferType.UNLOCKED_POSITION.getCode()) {
                return ResultUtils.success();
            } else if (record.getTransferType().equals(TransferType.PAY_TOKEN.getCode())) {
                return ResultUtils.success();
            } else {
                InnerTransferController.log.error("transferIn error, don't support transfer type:{}", record.getTransferType().intValue());
                return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
            }


        } catch (UnsupportedCurrency | UnsupportedBiz e) {
            InnerTransferController.log.error("transferIn error", e);
            return ResultUtils.failure(e);
        } catch (Throwable e) {
            InnerTransferController.log.error("transferIn,error message is：", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }

    }


//    @PostMapping(value = "/transfer-out")
//    ResponseResult transferOut(@RequestParam("userId")  long userId,
//                               @RequestParam("currencyId")  long currencyId,
//                               @RequestParam("amount")  BigDecimal amount,
//                               @RequestParam("tradeNo")  String tradeNo) {
//        return null;
//
//    }

    @PostMapping("/transfer-out")
    public ResponseResult transferOut(@RequestParam("currency") String symbol,
                                      @RequestParam("fromUser") Long fromUser,
                                      @RequestParam("toUser") Long toUser,
                                      @RequestParam("amount") BigDecimal amount,
                                      @RequestParam(value = "tradeNo", required = false) String tradeNo,
                                      @RequestParam("brokerId") Integer brokerId) {
        try {
            CurrencyEnum coin = CurrencyEnum.parseName(symbol);

            TransferRecord record = TransferRecord.builder()
                    .from(fromUser.toString())
                    .to(toUser.toString())
                    .biz(BizEnum.SPOT.getIndex())
                    .currency(coin.getIndex())
                    .userId(fromUser)
                    .traderNo(tradeNo)
                    .amount(amount)
                    .fee(ZERO)
                    .transferType(TransferType.TRANSFER_OUT.getCode())
                    .status((byte) TransferStatus.WAITING.getCode())
                    .brokerId(brokerId)
                    .build();
            ResponseResult result = this.transferRecordService.createTransferRecord(record);
            if (result.getCode() != 0) {
                log.error("createWithdrawRecord fail");
            } else {
                TransferRecordResDto recordResDto = (TransferRecordResDto) result.getData();
                JSONObject res = new JSONObject();
                res.put("tradeNo", recordResDto.getTraderNo());
                result.setData(res);
            }
            return result;

        } catch (UnsupportedCurrency | UnsupportedBiz e) {
            log.error("transfer error", e);
            return ResultUtils.failure(e);
        } catch (Throwable e) {
            log.error("transfer error ", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }

    }


    @PostMapping(value = "/add-token")
    ResponseResult addToken(@RequestParam("userId") long userId,
                            @RequestParam("currency") String symbol,
                            @RequestParam("fromBiz") String fromBiz,
                            @RequestParam("toBiz") String toBiz,
                            @RequestParam("amount") BigDecimal amount,
                            @RequestParam("brokerId") Integer brokerId) {
        try {
            log.info("add token:{} to {},amount:{} begin", symbol, userId, amount);
            BizEnum toBizEnum = BizEnum.parseBiz(toBiz);
            BizEnum fromBizEnum = BizEnum.parseBiz(fromBiz);
            CurrencyEnum currency = CurrencyEnum.parseName(symbol);
            String tradeNo = TradeNoUtil.getTradeNo();
            TransferRecord record = TransferRecord.builder()
                    .from(fromBizEnum.getName())
                    .to(toBizEnum.getName())
                    .amount(amount)
                    .biz(fromBizEnum.getIndex())
                    .userId(userId)
                    .currency(currency.getIndex())
                    .transferType(TransferType.ADD_TOKEN.getCode())
                    .traderNo(tradeNo)
                    .status((byte) TransferStatus.WAITING.getCode())
                    .brokerId(brokerId)
                    .build();
            this.transferRecordService.add(record);

            record.setStatus((byte) TransferStatus.SENDING.getCode());
            record.setUpdateDate(Date.from(Instant.now()));
            this.transferRecordService.editById(record);
            ResponseResult result = BizClientUtil.addToken(toBizEnum.getIndex(), userId, currency, amount, tradeNo, record.getBrokerId());
            if (result.getCode() == 0) {
                record.setStatus((byte) TransferStatus.CONFIRMED.getCode());
                record.setUpdateDate(Date.from(Instant.now()));
                this.transferRecordService.editById(record);
                log.info("add token:{} to {},amount:{} end", currency, userId, amount);
                return ResultUtils.success();
            } else {
                log.info("add token:{} to {},amount:{} error {}", currency, userId, amount, result);
                return result;
            }

        } catch (UnsupportedCurrency | UnsupportedBiz e) {
            InnerTransferController.log.error("addToken error", e);
            return ResultUtils.failure(e);
        } catch (Throwable e) {
            InnerTransferController.log.error("addToken,error message is：", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }

    }

    @PostMapping("/withdraw")
    ResponseResult<TransferRecordResDto> withdraw(@RequestParam("userId") long userId,
                                                  @RequestParam("biz") String biz,
                                                  @RequestParam("currencyCode") String currencyCode,
                                                  @RequestParam("address") String address,
                                                  @RequestParam("amount") BigDecimal amount,
                                                  @RequestParam(value = "tradeNo", required = false) String tradeNo,
                                                  @RequestParam("brokerId") Integer brokerId) {
        try {

            CurrencyEnum coin = CurrencyEnum.parseName(currencyCode);
            BizEnum bizEnum = BizEnum.parseBiz(biz);
            if (ObjectUtils.isEmpty(userId)) {
                return ResultUtils.failure("useId is null");
            }

            /**
             * 验证提现地址是否正确
             */

            ResponseResult responseResult = this.walletClient.checkAddressValid(coin.getIndex(), address);
            JSONObject jsonObject = JSON.parseObject(responseResult.getData() + "");
            if (BooleanUtils.isFalse(jsonObject.getBoolean("valid"))) {
                return ResultUtils.failure(BizErrorCodeEnum.INVALID_ADDRESS);
            }

            AssetCurrency info = this.currencyCompressService.getCurrency(currencyCode, biz, brokerId);
            BigDecimal fee = info.getWithdrawFee();
            if (amount.compareTo(info.getMinWithdrawAmount()) < 0) {
                return ResultUtils.failure(BizErrorCodeEnum.WITHDRAW_AMOUNT_NOT_ENOUGH);
            }
            TransferRecord record = TransferRecord.builder()
                    .to(address)
                    .biz(bizEnum.getIndex())
                    .currency(coin.getIndex())
                    .userId(userId)
                    .amount(amount)
                    .fee(fee)
                    .traderNo(tradeNo)
                    .transferType(TransferType.WITHDRAW.getCode())
                    .status((byte) TransferStatus.WAITING.getCode())
                    .brokerId(brokerId)
                    .build();
            return this.transferRecordService.createTransferRecord(record);
        } catch (UnsupportedCurrency | UnsupportedBiz e) {
            log.error("withdraw error", e);
            return ResultUtils.failure(e);
        } catch (Throwable e) {
            log.error("withdraw error", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @GetMapping("/transfer-record/{traderNo}")
    public ResponseResult<TransferRecordDto> getRecord(@PathVariable(value = "traderNo") String traderNo) {
        TransferRecordDto recordDto = new TransferRecordDto();
        try {
            TransferRecordExample example = new TransferRecordExample();
            example.createCriteria().andTraderNoEqualTo(traderNo);
            TransferRecord record = this.recordService.getOneByExample(example);
            if (ObjectUtils.isEmpty(record)) {
                log.error("record is null");
                return ResultUtils.failure("record is null");
            }
            BeanUtils.copyProperties(record, recordDto);
            recordDto.setAmount(record.getAmount().toPlainString());
            recordDto.setFee(record.getFee().toPlainString());
        } catch (Throwable e) {
            log.error("getRecord error", e);
            return ResultUtils.failure("getRecord error");
        }
        return ResultUtils.success(recordDto);
    }

    @PostMapping("bossTransfer")
    public ResponseResult bossTransfer(@RequestBody BossTransferDto bossTransferDto) {
        log.info("bosstransfer vo  = {}", bossTransferDto);
        try {
            CurrencyEnum coin = CurrencyEnum.parseName(bossTransferDto.getCurrency());
            BizEnum fromBiz = BizEnum.parseBiz(bossTransferDto.getFromBiz());
            BizEnum toBiz = BizEnum.parseBiz(bossTransferDto.getToBiz());
            TransferRecord record = TransferRecord.builder()
                    .from(fromBiz.getName())
                    .to(toBiz.getName())
                    .biz(fromBiz.getIndex())
                    .currency(coin.getIndex())
                    .userId(bossTransferDto.getUserId())
                    .amount(bossTransferDto.getAmount())
                    .fee(ZERO)
                    .brokerId(bossTransferDto.getBrokerId())
                    .transferType(TransferType.TRANSFER.getCode())
                    .status((byte) TransferStatus.WAITING.getCode())
                    .build();
            return this.transferRecordService.createTransferRecord(record);
        } catch (UnsupportedCurrency | UnsupportedBiz e) {
            log.error("transfer error", e);
            return ResultUtils.failure(e);
        } catch (Throwable e) {
            log.error("transfer error ", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @RequestMapping(value = "/withdraw/audit")
    ResponseResult<DataGridPagerResult<TransferRecordAuditResDto>> withdrawAudit(@RequestBody final DataGridPager<TransferRecordAuditResDto> pager){
        final PageInfo pageInfo = pager.toPageInfo();
        final List<TransferRecordAudit> transferRecordAuditList = this.transferRecordAuditService.listByPage(pageInfo,pager.getQueryParameter().getStatus(),pager.getQueryParameter().getTraderNo(),pager.getQueryParameter().getBrokerId());
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), this.replaceSenstiveInfo(transferRecordAuditList)));
    }

    private List<TransferRecordAuditResDto> replaceSenstiveInfo(final List<TransferRecordAudit> transferRecordAuditList) {
        final List<TransferRecordAuditResDto> transferRecordAuditResDtos = Lists.newArrayListWithCapacity(CollectionUtils.size(transferRecordAuditList));
        transferRecordAuditList.forEach(item -> {
            TransferRecordAuditResDto resVO = new TransferRecordAuditResDto();
            resVO.setAuditUserId(item.getAuditUserId());
            resVO.setStatus(item.getStatus().intValue());
            resVO.setTraderNo(item.getTraderNo());
            resVO.setCreateDate(item.getCreateDate());
            resVO.setUpdateDate(item.getUpdateDate());
            resVO.setMsg(item.getMsg());
            TransferRecord transferRecord = transferRecordService.getByTradeNo(item.getTraderNo());
            WithdrawRecordDto withdrawRecordDto = new WithdrawRecordDto();
            BeanConvertUtil.convert(withdrawRecordDto,transferRecord);
            resVO.setWithdrawRecordDto(withdrawRecordDto);
            transferRecordAuditResDtos.add(resVO);
        });
        return transferRecordAuditResDtos;
    }

    @PostMapping(value = "/withdraw/audit/oper")
    ResponseResult withdraw(@RequestBody TransferRecordAuditReqDto transferRecordAuditReqDto){
        try {
            TransferRecordAudit transferRecordAudit = transferRecordAuditService.getByTradeNo(transferRecordAuditReqDto.getTraderNo());
            if(ObjectUtils.isEmpty(transferRecordAudit)){
                return ResultUtils.failure(BizErrorCodeEnum.WITHDRAW_AUDIT_STATUS_FAIL);
            }
            //boss设置状态
            Integer preStatus = transferRecordAuditReqDto.getStatus();
            Byte status =transferRecordAudit.getStatus();

            //审核表中状态为1(审核成功) 3(提现进行中)时，直接返回成功
            if(status == TransferAuditStatus.SUCCEED.getCode() || status == TransferAuditStatus.JOB_OPER.getCode()){
                return ResultUtils.success();
            }

            //审核表中状态为0(等待审核) 2(审核失败)时，只能设置为 1(审核成功) 2(审核失败)
            if(status == TransferAuditStatus.WAITING.getCode() || status == TransferAuditStatus.FAIL.getCode()){
                if(preStatus.equals(TransferAuditStatus.WAITING.getCode()) || preStatus.equals(TransferAuditStatus.JOB_OPER.getCode())){
                    return ResultUtils.failure(BizErrorCodeEnum.WITHDRAW_AUDIT_STATUS_FAIL);
                }
            }
            transferRecordAudit.setStatus(transferRecordAuditReqDto.getStatus().byteValue());
            //当设置成功后，修改TransferRecord状态
            if(preStatus==TransferAuditStatus.SUCCEED.getCode()){
                TransferRecord transferRecord = this.transferRecordService.getByTradeNo(transferRecordAuditReqDto.getTraderNo());
                transferRecord.setStatus((byte)TransferStatus.WAITING.getCode());
                this.transferRecordService.editById(transferRecord);
            }
            transferRecordAudit.setMsg(transferRecordAuditReqDto.getMsg());
            transferRecordAudit.setAuditUserId(transferRecordAuditReqDto.getAuditUserId());
            this.transferRecordAuditService.editById(transferRecordAudit);
        } catch (Throwable e) {
            log.error("withdraw audit oper error ", e);
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_ERROR);
        }
        return ResultUtils.success();
    }
}
