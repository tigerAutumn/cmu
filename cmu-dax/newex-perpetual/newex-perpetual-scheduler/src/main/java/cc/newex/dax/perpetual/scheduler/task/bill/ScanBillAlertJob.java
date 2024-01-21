package cc.newex.dax.perpetual.scheduler.task.bill;

import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.client.MessageClient;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import cc.newex.dax.perpetual.common.constant.PerpetualCacheKeys;
import cc.newex.dax.perpetual.common.enums.BillTypeEnum;
import cc.newex.dax.perpetual.domain.UserBill;
import cc.newex.dax.perpetual.service.UserBillService;
import cc.newex.dax.perpetual.service.cache.CacheService;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Functions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 扫描订单报警
 */
@Slf4j
@Component
@JobHandler("ScanBillAlertJob")
public class ScanBillAlertJob extends IJobHandler {

    private static final int POOL_SIZE = 4;
    private static final int RECORD_SIZE = 1000;
    ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("scan-userbill-%d")
            .setDaemon(false)
            .build();
    private final ExecutorService executorService = new ThreadPoolExecutor(ScanBillAlertJob.POOL_SIZE,
            ScanBillAlertJob.POOL_SIZE, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), this.threadFactory);

    @Autowired
    private MessageClient messageClient;
    @Autowired
    private UsersClient usersClient;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserBillService userBillService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {

        final String key = PerpetualCacheKeys.getScanUserBillKey();
        final String value = this.cacheService.getCacheValue(key);
        Long id = StringUtils.isNotEmpty(value) ? Long.valueOf(value) : 0L;

        final List<BaseWorker> workers = new LinkedList<>();
        // 如果需要添加其他账单处理任务在 wokers 中添加 BaseWorker 子类
        workers.add(new ClosePositionNoticeWorker());


        while (true) {
            final List<UserBill> bills = this.userBillService.getBillList(id, new Date(System.currentTimeMillis() - 30 * 1000L), ScanBillAlertJob.RECORD_SIZE);
            if (bills.isEmpty()) {
                return ReturnT.SUCCESS;
            }
            final Long maxId = bills.stream().max(Comparator.comparing(UserBill::getId)).get().getId();
            id = Math.max(id, maxId);
            this.cacheService.setCacheValue(key, id.toString());

            final CountDownLatch countDownLatch = new CountDownLatch(workers.size());
            for (final BaseWorker worker : workers) {
                worker.setBills(bills);
                worker.setCountDownLatch(countDownLatch);
                this.executorService.execute(worker);
            }
            countDownLatch.await();
        }
    }

    private abstract class BaseWorker implements Runnable {
        protected List<UserBill> bills;
        protected CountDownLatch countDownLatch;

        protected void setBills(final List<UserBill> bills) {
            this.bills = bills;
        }

        protected void setCountDownLatch(final CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }
    }

    private class ClosePositionNoticeWorker extends BaseWorker implements Runnable {

        private final List<Integer> CARE_TYPE = Arrays.asList(
                BillTypeEnum.CONTRA_TRADE.getBillType(),
                BillTypeEnum.LIQUIDATE.getBillType(),
                BillTypeEnum.EXPLOSION.getBillType());

        @Override
        public void run() {
            try {
                if (CollectionUtils.isEmpty(this.bills)) {
                    return;
                }

                final Map<Long, UserBill> billMap = this.bills.stream().filter(b -> this.CARE_TYPE.contains(b.getType()))
                        .sorted(Comparator.comparing(UserBill::getId).reversed())
                        .collect(Collectors.toMap(UserBill::getUserId, Functions.identity(), (x, y) -> x));

                if (MapUtils.isEmpty(billMap)) {
                    ScanBillAlertJob.log.info("bill map is empty");
                    return;
                }

                final Map<Long, UserInfoResDTO> userInfoMap = this.getUserInfoMap(new ArrayList<>(billMap.keySet()));

                final Set<Map.Entry<Long, UserInfoResDTO>> entries = userInfoMap.entrySet();

                for (final Map.Entry<Long, UserInfoResDTO> entry : entries) {
                    this.retrySendMessage(entry.getValue(), billMap.get(entry.getKey()), 5);
                }
            } catch (final Exception e) {
                ScanBillAlertJob.log.error("unexpected error", e);
            } finally {
                this.countDownLatch.countDown();
            }
        }


        private Map<Long, UserInfoResDTO> getUserInfoMap(final List<Long> userIdList) {
            if (CollectionUtils.isEmpty(userIdList)) {
                return new HashMap<>();
            }
            final Map<Long, UserInfoResDTO> result = new HashMap<>(userIdList.size());
            for (final Long userId : userIdList) {
                final UserInfoResDTO userInfo = this.retryGetUserInfo(userId, 5);
                if (userInfo != null) {
                    result.put(userId, userInfo);
                }
            }
            return result;
        }

        private UserInfoResDTO retryGetUserInfo(final Long userId, final int times) {
            for (int i = 0; i < times; i++) {
                try {
                    final ResponseResult<UserInfoResDTO> userInfo = ScanBillAlertJob.this.usersClient.getUserInfo(userId, null);
                    if (userInfo == null || userInfo.getCode() != ResultUtils.success().getCode()) {
                        ScanBillAlertJob.log.error("get user info failed, userId : {}, result : {}", userId, JSON.toJSONString(userInfo));
                        continue;
                    }
                    return userInfo.getData();
                } catch (final Exception e) {
                    ScanBillAlertJob.log.error("unexpected error", e);
                }
            }
            return null;
        }

        private void retrySendMessage(final UserInfoResDTO userInfo, final UserBill userBill, final int times) {
            if (userInfo == null || userBill == null) {
                ScanBillAlertJob.log.error("userInfo or userBill is null, userInfo : {}, userBill : {}", userInfo, userBill);
                return;
            }

            final String param = this.buildMessageParam(userBill);
            final String countryCode = StringUtils.isEmpty(userInfo.getAreaCode()) ? "86" : userInfo.getAreaCode();
            final boolean isRealMobile = !StringUtil.isEmail(userInfo.getMobile());
            final MessageReqDTO msgReq = MessageReqDTO.builder()
                    .params(param)
                    .templateCode(this.getMessageTemplateCode(isRealMobile, userBill))
                    .email(userInfo.getEmail())
                    .mobile(userInfo.getMobile())
                    .brokerId(userBill.getBrokerId())
                    .countryCode(countryCode)
                    .locale(countryCode.equalsIgnoreCase("86") ? "zh-cn" : "en-us")
                    .build();

            for (int i = 0; i < times; i++) {
                try {
                    final ResponseResult<String> result = ScanBillAlertJob.this.messageClient.send(msgReq);
                    if (result == null || result.getCode() != ResultUtils.success().getCode()) {
                        ScanBillAlertJob.log.error("send message failed, message : {}, result : {}", msgReq, result);
                        continue;
                    }
                    return;
                } catch (final Exception e) {
                    ScanBillAlertJob.log.error("unexpected error", e);
                }
            }
        }

        private String getMessageTemplateCode(final boolean useSms, final UserBill userBill) {
            if (userBill.getType().equals(BillTypeEnum.LIQUIDATE.getBillType())) {
                return useSms ? "SMS_PERPETUAL_POSITION_LIQUIDATE" : "MAIL_PERPETUAL_POSITION_LIQUIDATE";
            } else if (userBill.getType().equals(BillTypeEnum.EXPLOSION.getBillType())) {
                return useSms ? "SMS_PERPETUAL_POSITION_EXPLOSION" : "MAIL_PERPETUAL_POSITION_EXPLOSION";
            } else if (userBill.getType().equals(BillTypeEnum.CONTRA_TRADE.getBillType())) {
                return useSms ? "SMS_PERPETUAL_POSITION_CONTRA_TRADE" : "MAIL_PERPETUAL_POSITION_CONTRA_TRADE";
            }
            return "";
        }

        private String buildMessageParam(final UserBill userBill) {
            final MessageParam messageParam = new MessageParam(userBill.getContractCode(), userBill.getAmount(), userBill.getPrice());
            return JSON.toJSONString(messageParam);
        }
    }


    class MessageParam {
        private String contract;
        private BigDecimal amount;
        private BigDecimal price;

        public MessageParam(final String contract, final BigDecimal amount, final BigDecimal price) {
            this.contract = contract;
            this.amount = amount;
            this.price = price;
        }

        public String getContract() {
            return this.contract;
        }

        public void setContract(final String contract) {
            this.contract = contract;
        }

        public BigDecimal getAmount() {
            return this.amount;
        }

        public void setAmount(final BigDecimal amount) {
            this.amount = amount;
        }

        public BigDecimal getPrice() {
            return this.price;
        }

        public void setPrice(final BigDecimal price) {
            this.price = price;
        }
    }

}
