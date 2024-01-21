package cc.newex.dax.perpetual.scheduler.task.position;

import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPairBrokerRelation;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.CurrencyPairBrokerRelationService;
import cc.newex.dax.perpetual.service.UserPositionService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 重新计算强平、破产价，需要满足下面条件
 * 1、用户持有多个 contract.base 相同的全仓
 * 2、用户每个合约的仓位持仓数量大于 0
 * <p>
 * 如 用户持有 p_eth_usdt 100 张, p_eth_btc 200 张，且都为全仓
 * 由于 p_eth_usdt 的保证金为 balance.available + p_eth_btc 的未实现
 * 所以需要为 p_eth_usdt、p_eth_btc 重新计算强平价、破产价
 */
@Slf4j
@Component
@JobHandler("RefreshLiquidatePriceJob")
public class RefreshLiquidatePriceJob extends IJobHandler {

    private static final int POOL_SIZE = 8;
    ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("refresh-l-price-%d")
            .setDaemon(false)
            .build();
    private final ExecutorService executorService = new ThreadPoolExecutor(RefreshLiquidatePriceJob.POOL_SIZE, RefreshLiquidatePriceJob.POOL_SIZE, 0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), this.threadFactory);

    @Autowired
    private ContractService contractService;
    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private CurrencyPairBrokerRelationService currencyPairBrokerRelationService;


    @Override
    public ReturnT<String> execute(final String s) throws Exception {

        final List<Contract> unExpiredContract = this.contractService.getUnExpiredContract();
        if (CollectionUtils.isEmpty(unExpiredContract)) {
            return ReturnT.SUCCESS;
        }
        // 按 contract.base 分组
        final Map<String, List<Contract>> groupContract = unExpiredContract.stream().collect(Collectors.groupingBy(Contract::getBase));
        // 过滤掉 contract.base 组中只有一个 contract 的数据
        Set<Map.Entry<String, List<Contract>>> entrySet = groupContract.entrySet();
        final Iterator<Map.Entry<String, List<Contract>>> it = entrySet.iterator();
        while (it.hasNext()) {
            final Map.Entry<String, List<Contract>> entry = it.next();
            if (entry.getValue().size() <= 1) {
                it.remove();
            }
        }

        entrySet = groupContract.entrySet();
        if (CollectionUtils.isEmpty(entrySet)) {
            return ReturnT.SUCCESS;
        }

        final CountDownLatch countDownLatch = new CountDownLatch(entrySet.size());
        for (final Map.Entry<String, List<Contract>> entry : entrySet) {
            this.executorService.execute(new Worker(entry.getKey(), entry.getValue(), countDownLatch));
        }
        countDownLatch.await();
        return ReturnT.SUCCESS;
    }


    private class Worker implements Runnable {
        private final String base;
        private final List<Contract> contractList;
        private final CountDownLatch countDownLatch;

        private Worker(final String base, final List<Contract> contractList, final CountDownLatch countDownLatch) {
            this.base = base;
            this.contractList = contractList;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {

                final List<CurrencyPairBrokerRelation> allRelation = RefreshLiquidatePriceJob.this.currencyPairBrokerRelationService.getAll();
                if (CollectionUtils.isEmpty(allRelation)) {
                    return;
                }
                final int count = 2;
                final Map<Integer, List<CurrencyPairBrokerRelation>> relationMap = allRelation.stream().collect(Collectors.groupingBy(CurrencyPairBrokerRelation::getBrokerId));
                for (final Integer brokerId : relationMap.keySet()) {

                    // 获取多持仓的用户持仓
                    final List<Long> userIdList = RefreshLiquidatePriceJob.this.userPositionService.getUserIdGroupByBase(this.base, brokerId, count);
                    if (CollectionUtils.isEmpty(userIdList)) {
                        continue;
                    }
                    for (final Long userId : userIdList) {
                        try {
                            RefreshLiquidatePriceJob.this.userPositionService.refreshLiquidatePrice(userId, brokerId, this.base, this.contractList);
                        } catch (final Exception e) {
                            RefreshLiquidatePriceJob.log.error("refresh liquidate price failed", e);
                        }
                    }
                }
            } catch (final Exception e) {
                RefreshLiquidatePriceJob.log.error("unexpected error", e);
            } finally {
                this.countDownLatch.countDown();
            }
        }
    }
}
