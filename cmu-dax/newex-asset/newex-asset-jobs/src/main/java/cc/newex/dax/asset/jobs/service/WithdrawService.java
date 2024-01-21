package cc.newex.dax.asset.jobs.service;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.common.consts.UserInfoCons;
import cc.newex.dax.asset.common.enums.TransferStatus;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.domain.AssetCurrency;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.service.NotifyUserService;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.dax.integration.client.MessageClient;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.WithdrawRecord;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.Date;
import java.util.Locale;

/**
 * @author newex-team
 * @data 06/04/2018
 */
@Slf4j
@Component
public class WithdrawService {
    @Autowired
    TransferRecordService recordService;
    @Autowired
    private AssetCurrencyCompressService currencyCompressService;

    @Autowired
    private UsersClient usersClient;
    @Autowired
    private MessageClient messageClient;

    @Autowired
    private NotifyUserService notifyUserService;

    public void fillWithdrawTxId(WithdrawRecord record) {
        log.info("fillWithdrawTxId currency : {}, txid:{} begin", record.getCurrency(), record.getTxId());
        TransferRecordExample example = new TransferRecordExample();
        example.createCriteria().andTraderNoEqualTo(record.getWithdrawId());
        TransferRecord transferRecord = this.recordService.getOneByExample(example);
        if (ObjectUtils.isEmpty(transferRecord)) {
            log.error("tradeNo:{} does not exist", record.getWithdrawId());
        } else {
            transferRecord.setStatus((byte) TransferStatus.CONFIRMED.getCode());
            transferRecord.setFrom(record.getTxId());
            transferRecord.setUpdateDate(Date.from(Instant.now()));
            this.recordService.editById(transferRecord);
            //提现成功,发送短信与邮件
            Long userId = record.getUserId();
            ResponseResult<UserInfoResDTO> result = this.usersClient.getUserInfo(userId, transferRecord.getBrokerId());
            if (!ObjectUtils.isEmpty(result.getData())) {
                UserInfoResDTO userInfo = result.getData();
                String mailAssetWithdrawSuccess = MessageTemplateConsts.MAIL_ASSET_WITHDRAW_SUCCESS;
                String smsAssetWithdrawSuccess = MessageTemplateConsts.SMS_ASSET_WITHDRAW_SUCCESS;
                //luckyWin special detail
                if (record.getCurrency().equals(CurrencyEnum.LUCKYWIN.getIndex())
                        && StringUtils.isNotEmpty(transferRecord.getRemark())
                        && transferRecord.getRemark().startsWith("winExchange")) {
                    mailAssetWithdrawSuccess = MessageTemplateConsts.MAIL_ASSET_EXCHANGE_SUCCESS;
                    smsAssetWithdrawSuccess = MessageTemplateConsts.SMS_ASSET_EXCHANGE_SUCCESS;
                    String remark = transferRecord.getRemark();
                    userId = Long.valueOf(remark.split("#")[1]);
                    result = this.usersClient.getUserInfo(userId, transferRecord.getBrokerId());
                    if (ObjectUtils.isEmpty(result.getData())) {
                        log.error("withdraw user info query fail userId={}",userId);
                        return;
                    }
                    userInfo = result.getData();
                    record.setAddress(transferRecord.getTo());
                    log.info("luckywin record build sucess to notify record={}", record);
                }
                this.sendWithdrawEmailOrSMS(userInfo, record, mailAssetWithdrawSuccess, transferRecord.getBrokerId());
                this.sendWithdrawEmailOrSMS(userInfo, record, smsAssetWithdrawSuccess, transferRecord.getBrokerId());

                this.notifyUserService.notifyWithdraw(transferRecord);
            }
        }

        log.info("fillWithdrawTxId currency : {}, txid:{} end", record.getCurrency(), record.getTxId());
    }

    //发送提现成功邮件与短信
    public void sendWithdrawEmailOrSMS(UserInfoResDTO userInfo, WithdrawRecord record, String messageTemplate, Integer brokerId) {
        try {
            if (ObjectUtils.isEmpty(userInfo) || ObjectUtils.isEmpty(record)) {
                log.info("deposit sendEmail error,userInfoResDTO is null");
                return;
            }
            CurrencyEnum currencyEnum = CurrencyEnum.parseValue(record.getCurrency());
            AssetCurrency assetCurrency = this.currencyCompressService.getCurrency(currencyEnum.getName(), record.getBiz(), brokerId);
            final JSONObject params = new JSONObject();
            params.put("amount", record.getBalance().stripTrailingZeros().toPlainString());
            params.put("coin", currencyEnum.name());
            params.put("toAddress", record.getAddress());
            params.put("txurl", assetCurrency.getTxExplorerUrl() + record.getTxId());

            MessageReqDTO messageReqDTO = MessageReqDTO.builder()
                    .countryCode(userInfo.getAreaCode())
                    .email(userInfo.getEmail())
                    .mobile(userInfo.getMobile())
                    .templateCode(messageTemplate)
                    .params(params.toJSONString())
                    .brokerId(brokerId)
                    .build();
            if (UserInfoCons.USER_AREA_CODE_CN_KEY.equals(userInfo.getAreaCode())) {
                messageReqDTO.setLocale(Locale.CHINA.toLanguageTag());
            } else {
                messageReqDTO.setLocale(Locale.US.toLanguageTag());
            }
            ResponseResult<String> result = this.messageClient.send(messageReqDTO);
            log.info("message send result : {}, {}, {}, {}", result.getCode(), result.getData(), result.getMsg());
        } catch (Throwable e) {
            log.error("sendWithdrawEmailOrSMS error,txid:{}, userId:{},tradeNo", record.getTxId(), record.getUserId(), record.getWithdrawId(), e);

        }
    }
}
