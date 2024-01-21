package cc.newex.dax.perpetual.scheduler.task.position;

import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.UserPositionService;
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
@JobHandler("SortUserPositionJob")
public class SortUserPositionJob extends IJobHandler {

    private static final int POOL_SIZE = 8;
    ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("sort-pst-%d")
            .setDaemon(false)
            .build();
    private final ExecutorService executorService = new ThreadPoolExecutor(SortUserPositionJob.POOL_SIZE,
            SortUserPositionJob.POOL_SIZE, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), this.threadFactory);

    @Autowired
    private ContractService contractService;
    @Autowired
    private UserPositionService userPositionService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {

        final List<Contract> unExpiredContract = this.contractService.getUnExpiredContract();
        if (CollectionUtils.isEmpty(unExpiredContract)) {
            return ReturnT.SUCCESS;
        }


        final CountDownLatch countDownLatch = new CountDownLatch(unExpiredContract.size());
        for (final Contract c : unExpiredContract) {
            this.executorService.execute(new Worker(c, countDownLatch));
        }
        countDownLatch.await();
        return ReturnT.SUCCESS;
    }

    private class Worker implements Runnable {
        private final Contract contract;
        private final CountDownLatch countDownLatch;

        private Worker(final Contract contract, final CountDownLatch countDownLatch) {
            this.contract = contract;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                if (this.contract.getType().equalsIgnoreCase("perpetual")) {
                    // 永续合约实时计算头部用户
                    SortUserPositionJob.this.userPositionService.sortUserPosition(this.contract.getContractCode());
                } else if (this.contract.getSettlementDate() != null && this.contract.getSettlementDate().getTime() > System.currentTimeMillis()) {
                    // 定期合约只对未结算的合约计算头部用户
                    SortUserPositionJob.this.userPositionService.sortUserPosition(this.contract.getContractCode());
                }
            } catch (final Exception e) {
                SortUserPositionJob.log.error("unexpected error", e);
            } finally {
                this.countDownLatch.countDown();
            }
        }
    }
}
