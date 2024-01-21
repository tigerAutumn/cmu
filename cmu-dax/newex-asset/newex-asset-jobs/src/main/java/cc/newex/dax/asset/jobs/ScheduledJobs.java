package cc.newex.dax.asset.jobs;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.redis.REDIS;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.common.consts.RedisKeyCons;
import cc.newex.dax.asset.common.enums.TransferStatus;
import cc.newex.dax.asset.common.util.BizClientUtil;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.domain.AssetCurrencyCompress;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.jobs.service.DepositService;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.service.LockedPositionService;
import cc.newex.dax.asset.service.NotifyUserService;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.dax.extra.client.inner.ProjectInnerClient;
import cc.newex.dax.extra.dto.cgm.ProjectPaymentRecordDTO;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author newex-team
 * @date 2018/5/19
 */
@Component
@Slf4j
public class ScheduledJobs {

    @Autowired
    DepositService depositService;
    @Value("${newex.innerProxy}")
    private String innerProxy;
    @Autowired
    private TransferRecordService transferRecordService;
    @Autowired
    private AssetCurrencyCompressService currencyCompressService;
    @Autowired
    private LockedPositionService lockedPositionService;
    @Autowired
    private NotifyUserService notifyUserService;
    @Value("${newex.payToken.defaultUser}")
    private Long targetUserId;
    @Autowired
    private ProjectInnerClient projectInnerClient;

    /**
     * payToken 失败的数据
     */
    @Scheduled(fixedRate = DateUtil.ONE_MINUTE)
    public void retryPayToken() {
        TransferRecordExample example = new TransferRecordExample();
        example.createCriteria()
                .andStatusEqualTo((byte) TransferStatus.PREPARED.getCode())
                .andTransferTypeEqualTo(TransferType.PAY_TOKEN.getCode());
        List<TransferRecord> records = this.transferRecordService.getByExample(example);
        if (!records.isEmpty()) {
            records.forEach(transferRecord -> {
                String tradeNo = transferRecord.getTraderNo() + "@add";
                ResponseResult addOfficialResult = BizClientUtil.payToken(true, transferRecord.getBiz(), this.targetUserId,
                        CurrencyEnum.parseValue(transferRecord.getCurrency()), transferRecord.getAmount(),
                        tradeNo, transferRecord.getBrokerId());
                if (addOfficialResult.getCode() == 0) {
                    log.info("addOfficial success userId={} targetId={} amount={} result={}", transferRecord.getUserId(), this.targetUserId, transferRecord.getAmount(),
                            addOfficialResult.getMsg());
                    //给官方账户加钱成功以后 设置状态=3
                    transferRecord.setStatus((byte) TransferStatus.CONFIRMED.getCode());
                    this.transferRecordService.editById(transferRecord);
                    ProjectPaymentRecordDTO dto = ProjectPaymentRecordDTO.builder()
                            .amount(transferRecord.getAmount())
                            .createdDate(new Date())
                            .currencyType((byte) 1)
                            .tokenInfoId(Long.valueOf(transferRecord.getRemark()))
                            .userId(transferRecord.getId())
                            .tradeNo(tradeNo)
                            .updatedDate(new Date())
                            .build();
                    this.projectInnerClient.saveProjectPaymentRecord(dto);
                }
            });
        }

    }

    /**
     * 重试失败的划转和提现
     */
    @Scheduled(fixedRate = DateUtil.ONE_MINUTE)
    public void retryTransferFailed() {
        ScheduledJobs.log.info("retryTransferFailed begin");
        List<TransferRecord> records = null;
        try {
            TransferRecordExample example = new TransferRecordExample();
            long now = System.currentTimeMillis();
            example.createCriteria().andTransferTypeIn(Arrays.asList(TransferType.WITHDRAW.getCode()
                    , TransferType.TRANSFER.getCode(), TransferType.TRANSFER_OUT.getCode()))
                    .andStatusEqualTo((byte) Constants.WAITING)
                    .andCreateDateLessThan(Date.from(Instant.ofEpochMilli(now - DateUtil.ONE_MINUTE)))
                    .andCreateDateGreaterThan(Date.from(Instant.ofEpochMilli(now - DateUtil.FIVE_MINUTE)));
            records = this.transferRecordService.getByExample(example);
            records.forEach(this.transferRecordService::retryTransferRecord);
            ScheduledJobs.log.info("retryTransferFailed task size:{}", records.size());
        } catch (Throwable e) {
            ScheduledJobs.log.info("retryTransferFailed error", e);

        }
        ScheduledJobs.log.info("retryTransferFailed end");
    }

    /**
     * 重试失败的充值
     */
    @Scheduled(fixedRate = DateUtil.ONE_MINUTE)
    public void retryDepositFailed() {
        try {
            TransferRecordExample example = new TransferRecordExample();
            long now = System.currentTimeMillis();
            example.createCriteria().andTransferTypeIn(Arrays.asList(TransferType.DEPOSIT.getCode()))
                    .andStatusEqualTo((byte) TransferStatus.SENDING.getCode())
                    .andUpdateDateLessThan(Date.from(Instant.ofEpochMilli(now - DateUtil.FIVE_MINUTE)));
            List<TransferRecord> deposits = this.transferRecordService.getByExample(example);
            ScheduledJobs.log.info("retryDepositFailed size :{} begin", deposits.size());
            if (!CollectionUtils.isEmpty(deposits)) {
                deposits.parallelStream().forEach(this.depositService::deposit);
            }
        } catch (Exception e) {
            ScheduledJobs.log.info("retryDepositFailed error", e);
        }
        ScheduledJobs.log.info("retryDepositFailed end");
    }

    /**
     * 重试充值和提现的通知，主要针对钱包用户
     */
    @Scheduled(fixedRate = DateUtil.ONE_MINUTE)
    public void retryNotifyFailed() {
        try {
            long now = System.currentTimeMillis();
            long fiveMinutesAgo = now - DateUtil.FIVE_MINUTE;


            Set<String> records = REDIS.zRangeByScore(RedisKeyCons.NOTIFY_DEPOSIT_WITHDRAW_FAILED, 0, fiveMinutesAgo);
            ScheduledJobs.log.info("retryNotifyFailed size :{} begin", records.size());
            if (!CollectionUtils.isEmpty(records)) {
                records.parallelStream().forEach((record) -> {
                    JSONObject json = JSONObject.parseObject(record);
                    TransferRecord transferRecord = json.toJavaObject(TransferRecord.class);
                    if (transferRecord.getTransferType() == TransferType.DEPOSIT.getCode()) {
                        this.notifyUserService.notifyDeposit(transferRecord);
                    } else {
                        this.notifyUserService.notifyWithdraw(transferRecord);
                    }
                });
            }
            REDIS.zRemRangeByScore(RedisKeyCons.NOTIFY_DEPOSIT_WITHDRAW_FAILED, 0, fiveMinutesAgo);
            ScheduledJobs.log.info("retryNotifyFailed end");
        } catch (Exception e) {
            ScheduledJobs.log.info("retryNotifyFailed error", e);
        }
    }

    /**
     * 从其他网站抓取交易费
     */

    @Scheduled(fixedRate = DateUtil.FIVE_MINUTE)
    public void getTxFee() {

        ScheduledJobs.log.info("getTxFee begin");
        try {

            //抓取btc的手续费
            String feeUrl = "https://btc.com/service/fees/distribution";
            JSONObject response = this.curlGet(feeUrl);
            int feePerByte = response.getJSONObject("fees_recommended").getIntValue("one_block_fee");
            //链上手续费
            int chainFee = feePerByte * 10;
            //收取用户的手续费
            int userFee = feePerByte * 40 * (325 + 35 * 2 + 15);
            userFee = userFee < 40000 ? 40000 : userFee;
            this.setCurrencyFee(CurrencyEnum.BTC, chainFee, userFee);

            //抓取usdt的手续费
            int usdtFee = feePerByte * 30 * (325 + 35 * 2 + 15);
            usdtFee = usdtFee > 60000 ? 60000 : usdtFee;
            REDIS.set(Constants.WALLET_FEE + CurrencyEnum.USDT.getIndex(), usdtFee);

            //抓取bch的手续费
            feeUrl = "https://bch.btc.com/service/fees/distribution";
            response = this.curlGet(feeUrl);

            feePerByte = response.getJSONObject("fees_recommended").getIntValue("one_block_fee");
            //链上手续费
            chainFee = feePerByte * 10;
            //收取用户的手续费
            userFee = feePerByte * 40 * (325 + 35 * 2 + 15);
            userFee = userFee < 80000 ? 80000 : userFee;
            this.setCurrencyFee(CurrencyEnum.BCH, chainFee, userFee);
            this.setCurrencyFee(CurrencyEnum.BSV, chainFee, userFee);

            //抓取ltc的手续费
            feeUrl = "https://api.blockcypher.com/v1/ltc/main";
            response = this.curlGet(feeUrl);

            feePerByte = response.getIntValue("high_fee_per_kb") / 1000;
            //链上手续费
            chainFee = feePerByte * 2;
            //收取用户的手续费
            userFee = feePerByte * 10 * (325 + 35 * 2 + 15);
            userFee = userFee < 800000 ? 800000 : userFee;
            this.setCurrencyFee(CurrencyEnum.LTC, chainFee, userFee);


        } catch (Exception e) {
            ScheduledJobs.log.info("getTxFee error", e);

        }
        ScheduledJobs.log.info("getTxFee end");
    }

    private void setCurrencyFee(CurrencyEnum currency, int chainFee, int userFee) {
        try {
            REDIS.set(Constants.WALLET_FEE + currency.getIndex(), chainFee);
            AssetCurrencyCompress assetCurrency = new AssetCurrencyCompress();
            assetCurrency.setId(currency.getIndex());
            assetCurrency.setWithdrawFee(new BigDecimal(userFee).divide(currency.getDecimal()));
            ShardTable table = ShardTable.builder().prefix("spot").build();
            this.currencyCompressService.editById(assetCurrency, table);
        } catch (Exception e) {
            ScheduledJobs.log.info("setCurrencyFee error", e);
        }
    }

    private JSONObject curlGet(String url) {
        try {
            String httpStart = "http";
            if (!url.startsWith(httpStart)) {
                url = httpStart + "://" + url;
            }

            int uriIndex = url.indexOf("/", 8);
            String command = "curl -H Referer:" + url.substring(0, uriIndex) + " " + this.innerProxy + url
                    .substring(uriIndex);
            //command = "curl -H Referer:https://btc.com http://innerproxy.cmx.com/service/fees/distribution";
            Process p = Runtime.getRuntime().exec(command);
            String line;
            String res = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = br.readLine()) != null) {
                res = res + line;
            }
            br.close();
            return JSONObject.parseObject(res);
        } catch (Throwable e) {
            ScheduledJobs.log.error("curlGet {} error", url, e);
        }
        return null;
    }

}
