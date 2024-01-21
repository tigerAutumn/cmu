package cc.newex.dax.perpetual.scheduler.task.position;

import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.client.MessageClient;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.PreLiquidateAlert;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.PreLiquidateAlertService;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 与爆仓报警
 */
@Slf4j
@Component
@JobHandler("PreLiquidateAlertJob")
public class PreLiquidateAlertJob extends IJobHandler {

    private static final int POOL_SIZE = 8;
    private static final long EXPIRE_TIME = 1 * 60 * 60 * 1000L;
    ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("liquidate-alert-%d")
            .setDaemon(false)
            .build();
    private final ExecutorService executorService = new ThreadPoolExecutor(PreLiquidateAlertJob.POOL_SIZE, PreLiquidateAlertJob.POOL_SIZE, 0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), this.threadFactory);

    @Autowired
    private PreLiquidateAlertService preLiquidateAlertService;
    @Autowired
    private UsersClient usersClient;
    @Autowired
    private MessageClient messageClient;
    @Autowired
    private ContractService contractService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        final List<Contract> unExpiredContract = this.contractService.getUnExpiredContract();

        if (CollectionUtils.isEmpty(unExpiredContract)) {
            PreLiquidateAlertJob.log.warn("contract list is empty");
            return ReturnT.SUCCESS;
        }

        final CountDownLatch countDownLatch = new CountDownLatch(unExpiredContract.size());

        for (final Contract c : unExpiredContract) {
            this.executorService.execute(new PreLiquidateAlertJob.Worker(c, countDownLatch));
        }

        countDownLatch.await();
        return ReturnT.SUCCESS;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageParam {
        private String contract;
        private String pairCode;
    }

    private class Worker implements Runnable {
        private static final int size = 1000;
        private final Contract contract;
        private final CountDownLatch countDownLatch;

        private Worker(final Contract contract, final CountDownLatch countDownLatch) {
            this.contract = contract;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {

            try {

                long index = 0L;
                while (true) {
                    final List<PreLiquidateAlert> preLiquidateAlerts =
                            PreLiquidateAlertJob.this.preLiquidateAlertService.alertList(index, Worker.size, this.contract.getContractCode());

                    if (CollectionUtils.isEmpty(preLiquidateAlerts)) {
                        break;
                    }

                    for (final PreLiquidateAlert p : preLiquidateAlerts) {
                        index = Math.max(index, p.getId());
                        this.sendMessage(p, this.contract);
                        p.setTimes(p.getTimes() + 1);
                        p.setExpireTime(new Date(System.currentTimeMillis() + PreLiquidateAlertJob.EXPIRE_TIME));
                    }
                    PreLiquidateAlertJob.this.preLiquidateAlertService.batchEdit(preLiquidateAlerts);
                }
                PreLiquidateAlertJob.this.preLiquidateAlertService.removeExpireList(this.contract.getContractCode());
            } catch (final Exception e) {
                PreLiquidateAlertJob.log.error("unexpected error", e);
            } finally {
                this.countDownLatch.countDown();
            }
        }

        private void sendMessage(final PreLiquidateAlert preLiquidateAlert, final Contract contract) {

            try {
                final ResponseResult<UserInfoResDTO> userInfo = PreLiquidateAlertJob.this.usersClient.getUserInfo(preLiquidateAlert.getUserId(), null);
                if (userInfo == null || userInfo.getCode() != ResultUtils.success().getCode()) {
                    PreLiquidateAlertJob.log.error("get user info failed, userId : {}, brokerId : {}, result : {}", preLiquidateAlert.getUserId(), preLiquidateAlert.getBrokerId(), JSON.toJSONString(userInfo));
                    return;
                }
                final UserInfoResDTO data = userInfo.getData();
                final boolean isRealMobile = !StringUtil.isEmail(data.getMobile());

                final String countryCode = StringUtils.isEmpty(data.getAreaCode()) ? "86" : data.getAreaCode();
                final String email = data.getEmail();
                final String mobile = data.getMobile();
                final MessageParam param = MessageParam.builder()
                        .contract(contract.getContractCode())
                        .pairCode(contract.getPairCode())
                        .build();
                final MessageReqDTO messageReqDTO = MessageReqDTO.builder()
                        .brokerId(preLiquidateAlert.getBrokerId())
                        .countryCode(countryCode)
                        .locale(countryCode.equalsIgnoreCase("86") ? "zh-cn" : "en-us")
                        .mobile(mobile)
                        .email(email)
                        .templateCode(isRealMobile ? "SMS_PERPETUAL_PROSITION_ALERT" : "MAIL_PERPETUAL_PROSITION_ALERT")
                        .params(JSON.toJSONString(param))
                        .build();
                final ResponseResult<String> send = PreLiquidateAlertJob.this.messageClient.send(messageReqDTO);
                if (send == null || send.getCode() != ResultUtils.success().getCode()) {
                    PreLiquidateAlertJob.log.error("send message failed, param : {}, result : {}", messageReqDTO, JSON.toJSONString(send));
                }
            } catch (final Exception e) {
                PreLiquidateAlertJob.log.error("send message failed", e);
            }
        }
    }
}
