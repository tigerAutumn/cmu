package cc.newex.dax.perpetual.scheduler.task.markprice;

import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.enums.PushTypeEnum;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.common.MarketService;
import cc.newex.dax.perpetual.service.common.PushService;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@JobHandler("MarketPriceJob")
public class MarketPriceJob extends IJobHandler {

    ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("market-price-%d")
            .setDaemon(false)
            .build();

    private final ExecutorService executorService = new ThreadPoolExecutor(8, 8,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), this.threadFactory);

    @Autowired
    private MarketService marketService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private PushService pushService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        final List<Contract> contractList = this.contractService.getUnExpiredContract();
        if (CollectionUtils.isEmpty(contractList)) {
            log.warn("contract list is empty");

            return new ReturnT<>(ReturnT.FAIL_CODE, "contract list is empty");
        }

        final CountDownLatch downLatch = new CountDownLatch(contractList.size());

        for (final Contract c : contractList) {
            log.info("MarketPrice, Contract: {}", JSON.toJSONString(c));

            this.executorService.execute(new Worker(c, downLatch));
        }

        try {
            downLatch.await();
        } catch (final InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        final Map<String, MarkIndexPriceDTO> priceMap = this.marketService.allMarkIndexPrice();

        final Collection<MarkIndexPriceDTO> values = priceMap.values();

        if (CollectionUtils.isNotEmpty(values)) {
            final List<String> codes = contractList.stream().map(Contract::getContractCode).collect(Collectors.toList());
            final List<MarkIndexPriceDTO> priceValues = priceMap
                    .entrySet()
                    .stream()
                    .filter(entry -> codes.contains(entry.getKey())).map(Map.Entry::getValue)
                    .collect(Collectors.toList());
            this.pushService.pushData(contractList.get(0), PushTypeEnum.FUND_RATES, JSON.toJSONString(priceValues), false,
                    false, false);
        }

        //this.marketService.removeNotInContractMarkIndex(contractList);
        return ReturnT.SUCCESS;
    }

    class Worker implements Runnable {
        private final Contract contract;
        private final CountDownLatch countDownLatch;

        private Worker(final Contract contract, final CountDownLatch countDownLatch) {
            this.contract = contract;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                MarketPriceJob.this.marketService.scheduleMarketPrice(this.contract);
            } catch (final Exception e) {
                log.error("unexpected error: " + e.getMessage(), e);
            } finally {
                this.countDownLatch.countDown();
            }
        }
    }
}
