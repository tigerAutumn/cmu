package cc.newex.dax.asset.jobs.service;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.redis.REDIS;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.common.consts.RedisKeyCons;
import cc.newex.dax.asset.common.consts.UserInfoCons;
import cc.newex.dax.asset.common.enums.TransferStatus;
import cc.newex.dax.asset.common.util.BizClientUtil;
import cc.newex.dax.asset.criteria.DepositAddressExample;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.domain.AssetCurrency;
import cc.newex.dax.asset.domain.DepositAddress;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.service.DepositAddressService;
import cc.newex.dax.asset.service.NotifyUserService;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.dax.integration.client.MessageClient;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author newex-team
 * @data 06/04/2018
 */
@Slf4j
@Component
public class DepositService {

    @Autowired
    TransferRecordService transferRecordService;
    @Autowired
    DepositAddressService addressService;
    @Autowired
    private AssetCurrencyCompressService currencyCompressService;
    @Autowired
    private UsersClient usersClient;
    @Autowired
    private MessageClient messageClient;
    @Autowired
    private NotifyUserService notifyUserService;

    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public void deposit(TransactionDTO tx) {
        DepositService.log.info("deposit,txId:{},confirm :{} begin", tx.getTxId(), tx.getConfirmNum());
        TransferRecord record = this.convertTxToDeposit(tx);

        if (ObjectUtils.isEmpty(record)) {
            DepositService.log.info("deposit error,txId:{},address :{} end", tx.getTxId(), tx.getAddress());
        } else {
            this.deposit(record);
        }
        DepositService.log.info("deposit,txId:{},confirm :{} end", tx.getTxId(), tx.getConfirmNum());
    }

    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public void deposit(TransferRecord record) {

        if (ObjectUtils.isEmpty(record)) {
            DepositService.log.info("deposit error,record is null");
        } else {
            CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());
            AssetCurrency info = this.currencyCompressService.getCurrency(currency.getName(), record.getBiz(), record.getBrokerId());
            record.setUpdateDate(Date.from(Instant.now()));

            //校验合并最小充值的数
            List<TransferRecord> transferRecordList = transferRecordService.getDepositNotBizList(record.getUserId(), currency.getIndex(), record.getBrokerId(), record.getTo());
            TransferRecord transferRecord = new TransferRecord();
            if (CollectionUtils.isNotEmpty(transferRecordList)) {
                transferRecordList.stream().reduce((tr, x) -> {
                    TransferRecord result = new TransferRecord();
                    result.setAmount(tr.getAmount().add(x.getAmount()));
                    return result;
                }).ifPresent(y -> {
                    transferRecord.setAmount(y.getAmount());
                });

                //未到账的需要+当前大于最小充值数的这笔的数量进行比较
                BigDecimal totalAmount;
                if (transferRecord.getAmount() != null) {
                    if (record.getStatus() < TransferStatus.CONFIRMED.getCode()) {
                        totalAmount = transferRecord.getAmount().add(record.getAmount());
                    } else {
                        totalAmount = transferRecord.getAmount();
                    }

                    if (totalAmount.compareTo(info.getMinDepositAmount()) >= 0) {
                        transferRecordList.stream().forEach(tr -> {
                            depositConfirmed(tr, currency);
                            //通知第三方到账
                            this.notifyUserService.notifyDeposit(tr);
                        });
                    }
                }

            }

            if (record.getStatus() < TransferStatus.CONFIRMED.getCode()
                    && record.getConfirmation() >= currency.getDepositConfirmNum()) {
                depositConfirmed(record, currency);
            }
        }

        DepositService.log.info("deposit,txId:{},confirm :{} end", record.getFrom(), record.getConfirmation());
    }

    /**
     * 充值确认
     */
    private void depositConfirmed(TransferRecord record, CurrencyEnum currency) {
        record.setStatus((byte) TransferStatus.SENDING.getCode());
        this.transferRecordService.editById(record);
        Long userId = record.getUserId();
        if (userId > 0) {
            ResponseResult responseResult = BizClientUtil.deposit(record.getBiz(), userId, currency, record.getAmount(), record.getTraderNo(), record.getBrokerId());
            if (responseResult != null && responseResult.getCode() == 0) {
                if (currency == CurrencyEnum.IOTA) {
                    //iota的地址只能用一次，所以充值一次之后删除掉
                    DepositAddressExample example = new DepositAddressExample();
                    example.createCriteria().andAddressEqualTo(record.getTo());
                    DepositAddress iotaAddr = DepositAddress.builder().address(record.getTo()).status((byte) 1).build();
                    this.addressService.editByExample(iotaAddr, example);
                }
                record.setStatus((byte) TransferStatus.CONFIRMED.getCode());
                record.setUpdateDate(Date.from(Instant.now()));
                this.transferRecordService.editById(record);
                //充值成功,发送短信与邮件
                final ResponseResult<UserInfoResDTO> result = this.usersClient.getUserInfo(userId, record.getBrokerId());
                if (!ObjectUtils.isEmpty(result)) {
                    UserInfoResDTO userInfo = result.getData();
                    this.sendDepositEmailOrSMS(userInfo, record, MessageTemplateConsts.MAIL_ASSET_DEPOSIT);
                    this.sendDepositEmailOrSMS(userInfo, record, MessageTemplateConsts.SMS_ASSET_DEPOSIT);
                }
            }
        } else {
            record.setStatus((byte) TransferStatus.CONFIRMED.getCode());
            record.setUpdateDate(Date.from(Instant.now()));
            this.transferRecordService.editById(record);
        }

    }

    private void sendDepositEmailOrSMS(UserInfoResDTO userInfo, TransferRecord record, String messageTemplate) {
        try {
            if (ObjectUtils.isEmpty(userInfo) || ObjectUtils.isEmpty(record)) {
                log.info("deposit sendEmail error,userInfoResDTO is null");
                return;
            }
            CurrencyEnum currencyEnum = CurrencyEnum.parseValue(record.getCurrency());

            final JSONObject params = new JSONObject();
            params.put("amount", record.getAmount().stripTrailingZeros().toPlainString());
            params.put("coin", currencyEnum.name());

            MessageReqDTO messageReqDTO = MessageReqDTO.builder()
                    .countryCode(userInfo.getAreaCode())
                    .email(userInfo.getEmail())
                    .mobile(userInfo.getMobile())
                    .templateCode(messageTemplate)
                    .params(params.toJSONString())
                    .brokerId(record.getBrokerId())
                    .build();
            if (UserInfoCons.USER_AREA_CODE_CN_KEY.equals(userInfo.getAreaCode())) {
                messageReqDTO.setLocale(Locale.CHINA.toLanguageTag());
            } else {
                messageReqDTO.setLocale(Locale.US.toLanguageTag());
            }

            this.messageClient.send(messageReqDTO);
        } catch (Throwable e) {
            log.error("sendDepositEmailOrSMS error,txid:{}, userId:{},", record.getFrom(), record.getUserId(), e);
        }
    }

    private TransferRecord convertTxToDeposit(TransactionDTO tx) {

        CurrencyEnum currency = CurrencyEnum.parseValue(tx.getCurrency());

        TransferRecordExample example = new TransferRecordExample();
        example.createCriteria().andFromEqualTo(tx.getTxId()).andTransferTypeEqualTo(TransferType.DEPOSIT.getCode());
        TransferRecord record = this.transferRecordService.getOneByExample(example);
        AssetCurrency info;
        if (ObjectUtils.isEmpty(record)) {
            DepositAddressExample addressExample = new DepositAddressExample();
            //available
            addressExample.createCriteria().andAddressEqualTo(tx.getAddress()).andStatusEqualTo((byte) 0);
            DepositAddress address = this.addressService.getOneByExample(addressExample);
            if (ObjectUtils.isEmpty(address)) {
                log.error("convertTxToDeposit error,can not find {} in database", tx.getAddress());
                return null;
            }
            //check currency
            info = this.currencyCompressService.getCurrency(currency.getName(), tx.getBiz(), address.getBrokerId());
            if (ObjectUtils.isEmpty(info)) {
                log.error("Asset not support : {}", currency.name());
                return null;
            }
            /* stop recharge */
            if (info.getRechargeable().intValue() == 0) {
                return null;
            }
            //校验最小的充值数
            if (tx.getBalance().compareTo(info.getMinDepositAmount()) < 0) {
                record = convertTransferRecord(tx, address, TransferStatus.DEPOSIT_NOT_BIZ.getCode());
            } else {
                //充值记录用txid当做tradeNo，防止给用户重复加钱
                record = convertTransferRecord(tx, address, TransferStatus.WAITING.getCode());
            }
            this.transferRecordService.add(record);

        } else {
            //如果记录已经存在，直接更新确认数
            info = this.currencyCompressService.getCurrency(currency.getName(), tx.getBiz(), record.getBrokerId());
            record.setConfirmation(tx.getConfirmNum().intValue());
            record.setUpdateDate(Date.from(Instant.now()));
            this.transferRecordService.editByExample(record, example);

        }

        if (tx.getConfirmNum() >= CurrencyEnum.parseValue(tx.getCurrency()).getWithdrawConfirmNum()) {
            REDIS.zRem(RedisKeyCons.ASSET_RECORD_WITHDRAW_UNCONFIRM_KEY, record.getId().toString());
        } else {
            REDIS.zAdd(RedisKeyCons.ASSET_RECORD_WITHDRAW_UNCONFIRM_KEY, record.getId(), record.getId().toString());
        }
        if (tx.getBalance().compareTo(info.getMinDepositAmount()) >= 0) {
            //通知第三方到账
            this.notifyUserService.notifyDeposit(record);
        }

        return record;
    }

    /**
     * convert to transfer record
     *
     * @param tx
     * @param address
     * @param status
     * @return
     */
    private TransferRecord convertTransferRecord(TransactionDTO tx, DepositAddress address, int status) {

        return TransferRecord.builder()
                .from(tx.getTxId())
                .to(tx.getAddress())
                .amount(tx.getBalance())
                .biz(tx.getBiz())
                .userId(address.getUserId())
                .currency(tx.getCurrency())
                .confirmation(tx.getConfirmNum().intValue())
                .transferType(TransferType.DEPOSIT.getCode())
                .traderNo(tx.getTxId())
                .brokerId(address.getBrokerId())
                .status((byte) status)
                .build();
    }

}