package cc.newex.dax.asset.jobs;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.commons.redis.REDIS;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.consts.RedisKeyCons;
import cc.newex.dax.asset.common.enums.TransferAuditStatus;
import cc.newex.dax.asset.common.enums.TransferStatus;
import cc.newex.dax.asset.common.util.BeanConvertUtil;
import cc.newex.dax.asset.common.util.BizClientUtil;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.domain.TransferRecordAudit;
import cc.newex.dax.asset.dto.TransferRecordResDto;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.service.TransferRecordAuditService;
import cc.newex.dax.asset.service.TransferRecordService;
import cc.newex.wallet.annotation.StartThread;
import cc.newex.wallet.currency.CurrencyEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @author newex-team
 * 扫描审核通过的提现
 */
@Component
@Slf4j
@StartThread
public class DepositAuditJobs implements Runnable {

    private final long WAIT_TIME = 10 * 1000L;


    @Autowired
    private TransferRecordAuditService transferRecordAuditService;
    @Autowired
    private TransferRecordService transferRecordService;
    @Autowired
    private AssetCurrencyCompressService currencyCompressService;
    private static final int NOT_ENOUGH_BALANCE = 1223;
    private static final long ONE_DAY = 24 * 60 * 60;

    @Override
    public void run() {
        DepositAuditJobs.log.info("DepositAuditJobs begin");
        while (true) {
            try {
                //查询审核成功的提现记录
                List<TransferRecordAudit> transferRecordAuditList = transferRecordAuditService.getAuditSuccess();
                for (TransferRecordAudit transferRecordAudit: transferRecordAuditList) {
                    TransferRecord record = this.transferRecordService.getByTradeNo(transferRecordAudit.getTraderNo());
                    if(!ObjectUtils.isEmpty(record)){
                        //发起提现操作
                        CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());
                        ResponseResult result = BizClientUtil.withdraw(record.getBiz(), record.getUserId(), currency, record.getAmount().add(record.getFee()), record.getTraderNo(), record.getBrokerId());
                        if (result.getCode() == NOT_ENOUGH_BALANCE) {
                            transferRecordService.removeById(record.getId());
                            log.error("DepositAudit traderNo={} user={} currency={} createTransferRecord error:balance is not enough",record.getTraderNo(), record.getUserId(), record.getCurrency());
                        } else {
                            if (TransferType.WITHDRAW.getCode() == record.getTransferType()) {
                                Date createDate = new Date();
                                final String key = MessageFormat.format(RedisKeyCons.ASSET_RECORD_WITHDRAW_KEY_PRE_NEW, record.getUserId(), record.getBrokerId());
                                final JSONObject content = new JSONObject();
                                content.put("transferType", record.getTransferType());
                                content.put("currency", record.getCurrency());
                                content.put("amountBTC", record.getAmount().multiply(this.currencyCompressService.coinConverseBTCRate(record.getCurrency(), null)));
                                content.put("traderNo", record.getTraderNo());
                                REDIS.zAdd(key, createDate.getTime(), content.toJSONString());
                                REDIS.expire(key, ONE_DAY);

                                //将审核表的状态改为3
                                transferRecordAudit.setStatus((byte)TransferAuditStatus.JOB_OPER.getCode());
                                transferRecordAuditService.editById(transferRecordAudit);
                            }
                        }
                        if (result.getCode() == 0) {
                            log.info("DepositAudit success, traderNo={} user={} currency={} ",record.getTraderNo(), record.getUserId(), record.getCurrency());
                        } else {
                            log.error("DepositAudit fail, traderNo={} user={} currency={} ",record.getTraderNo(), record.getUserId(), record.getCurrency());
                        }
                    }
                }
                Thread.sleep(this.WAIT_TIME);
            } catch (InterruptedException e) {
                log.info("DepositAuditJobs quit", e);
                break;
            }

        }


    }
}
