package cc.newex.dax.perpetual.scheduler.task;

import cc.newex.commons.dictionary.enums.KlineEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.scheduler.service.KlineService;
import cc.newex.dax.perpetual.service.ContractService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * K线计算
 *
 * @author xionghui
 * @date 2018/11/16
 */
@Slf4j
@Component
@JobHandler("KLineJob")
public class KLineJob extends IJobHandler {
    private static final int MAX_SIZE = 64;

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(KLineJob.MAX_SIZE,
            KLineJob.MAX_SIZE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    private final ThreadPoolExecutor klineThreadPoolExecutor = new ThreadPoolExecutor(
            KLineJob.MAX_SIZE, KLineJob.MAX_SIZE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    @Autowired
    KlineService klineService;
    @Autowired
    ContractService contractService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        KLineJob.log.info("KLineJob begin");
        final List<Contract> contractList = this.contractService.getUnExpiredContract();
        if (CollectionUtils.isEmpty(contractList)) {
            KLineJob.log.info("KLineJob contractList is empty");
            return new ReturnT<>(ReturnT.SUCCESS_CODE, "contractList is empty.");
        }
        final CountDownLatch countDownLatch = new CountDownLatch(contractList.size());
        for (final Contract contract : contractList) {
            this.threadPoolExecutor.execute(new KlineBuilder(countDownLatch, contract));
        }
        countDownLatch.await();
        KLineJob.log.info("KLineJob end");
        return ReturnT.SUCCESS;
    }

    /**
     * KlineBuilder
     *
     * @author xionghui
     * @date 2018/11/16
     */
    private class KlineBuilder implements Runnable {
        private final CountDownLatch latch;
        private final Contract contract;

        CountDownLatch subTaskLatch;

        public KlineBuilder(final CountDownLatch latch, final Contract contract) {
            this.latch = latch;
            this.contract = contract;
            this.subTaskLatch = new CountDownLatch(KlineEnum.values().length - 1);
        }

        @Override
        public void run() {
            try {
                KLineJob.this.klineService.bulidOneMinuteKline(this.contract, KlineEnum.MIN1);
                for (final KlineEnum k : KlineEnum.values()) {
                    if (!KlineEnum.MIN1.equals(k)) {
                        KLineJob.this.klineThreadPoolExecutor.execute(() -> {
                            try {
                                if (KlineEnum.WEEK.equals(k)) {
                                    KLineJob.this.klineService.bulidWeekKline(this.contract, k);
                                } else {
                                    KLineJob.this.klineService.bulidKline(this.contract, k);
                                }
                            } catch (final Exception e) {
                                KLineJob.log.error("build sub job kline failed", e);
                            } finally {
                                this.subTaskLatch.countDown();
                            }
                        });
                    }
                }
                this.subTaskLatch.await();
            } catch (final Exception e) {
                KLineJob.log.error("KLineJob KlineBuilder failed: ", e);
            } finally {
                this.latch.countDown();
            }
        }
    }
}
