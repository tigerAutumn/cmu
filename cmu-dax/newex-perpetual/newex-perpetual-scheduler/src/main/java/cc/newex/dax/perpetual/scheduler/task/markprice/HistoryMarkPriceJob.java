package cc.newex.dax.perpetual.scheduler.task.markprice;

import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.HistoryMarkPriceService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Component
@JobHandler("HistoryMarkPriceJob")
public class HistoryMarkPriceJob extends IJobHandler {

    ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("history-m-price-%d")
            .setDaemon(false)
            .build();
    private final ExecutorService executorService = new ThreadPoolExecutor(8, 8,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), threadFactory);

    @Autowired
    private HistoryMarkPriceService historyMarkPriceService;
    @Autowired
    private ContractService contractService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {

        final List<Contract> contractList = this.contractService.getUnExpiredContract();
        if (CollectionUtils.isEmpty(contractList)) {
            HistoryMarkPriceJob.log.error("contract list is empty");
            return ReturnT.SUCCESS;
        }

        final CountDownLatch countDownLatch = new CountDownLatch(contractList.size());
        for (final Contract contract : contractList) {
            this.executorService.execute(new Worker(contract, countDownLatch));
        }
        countDownLatch.await();
        return ReturnT.SUCCESS;
    }

    private class Worker implements Runnable {

        private final Contract contract;
        private final CountDownLatch countDownLatch;

        public Worker(final Contract contract, final CountDownLatch countDownLatch) {
            this.contract = contract;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                HistoryMarkPriceJob.this.historyMarkPriceService.scheduleMarketPrice(this.contract);
            } catch (final Exception e) {
                HistoryMarkPriceJob.log.error("unexpected error", e);
            } finally {
                this.countDownLatch.countDown();
            }
        }
    }
}
