package cc.newex.dax.perpetual.scheduler.task.bill;

import cc.newex.dax.perpetual.common.enums.PositionClearEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.PositionClear;
import cc.newex.dax.perpetual.scheduler.service.ClearUserPositionService;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.PositionClearService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Component
@JobHandler(value = "BuildPositionClearBillJob")
public class BuildPositionClearBillJob extends IJobHandler {

    private static final int POOL_SIZE = 4;
    private static final int RECORD_SIZE = 1000;
    ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("build-userbill-%d")
            .setDaemon(false)
            .build();
    private final ExecutorService executorService = new ThreadPoolExecutor(BuildPositionClearBillJob.POOL_SIZE,
            BuildPositionClearBillJob.POOL_SIZE, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), this.threadFactory);

    @Autowired
    private ClearUserPositionService clearUserPositionService;
    @Autowired
    private PositionClearService positionClearService;
    @Autowired
    private ContractService contractService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        final List<Contract> contracts = this.contractService.getUnExpiredContract();
        if (CollectionUtils.isEmpty(contracts)) {
            return ReturnT.SUCCESS;
        }

        final CountDownLatch countDownLatch = new CountDownLatch(contracts.size());

        for (final Contract c : contracts) {
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
            Long startId = 0L;

            try {
                while (true) {

                    final List<PositionClear> positionClear = BuildPositionClearBillJob.this.positionClearService.getPositionClear(this.contract.getContractCode(),
                            PositionClearEnum.INIT, startId, BuildPositionClearBillJob.RECORD_SIZE);
                    if (CollectionUtils.isEmpty(positionClear)) {
                        break;
                    }
                    BuildPositionClearBillJob.this.clearUserPositionService.buildUserBill(positionClear);
                    startId = positionClear.stream().max(Comparator.comparing(PositionClear::getId)).get().getId();
                }
            } catch (final Exception e) {
                BuildPositionClearBillJob.log.error("unexpected error", e);
            } finally {
                this.countDownLatch.countDown();
            }
        }
    }
}
