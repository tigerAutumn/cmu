package cc.newex.dax.market.job.task;

import cc.newex.commons.dictionary.enums.KlineEnum;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.job.service.CoinService;
import cc.newex.dax.market.job.service.MarketDataKlineService;
import cc.newex.dax.market.service.CoinConfigService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * @author newex-team
 * @date 2018/7/20
 */
@Slf4j
@JobHandler(value = "MarketKlineXxlJob")
@Component
public class MarketKlineXxlJob extends IJobHandler {
    @Resource(name = "threadpoolExecutor")
    Executor threadpoolExecutor;
    @Resource(name = "klineThreadpoolExecutor")
    Executor klineThreadpoolExecutor;
    @Autowired
    CoinService coinService;
    @Autowired
    MarketDataKlineService marketDataKlineService;
    @Autowired
    CoinConfigService coinConfigService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        MarketKlineXxlJob.log.info("MarketKlineXxlJob begin");
        this.doExecute();
        MarketKlineXxlJob.log.info("MarketKlineXxlJob end");
        return IJobHandler.SUCCESS;
    }

    private void doExecute() throws InterruptedException, ExecutionException {
        final List<CoinConfig> coinConfigListCache = this.coinService.getCoinConfigListCache();

        //组合指数列表
        final List<CoinConfig> portfolioList = this.coinConfigService.getAllPortfolioCoinConfigListFromCache();
        if (CollectionUtils.isNotEmpty(portfolioList)) {
            for (final CoinConfig coinConfig : portfolioList) {
                if (coinConfig.getPortfolioInfo() != null && coinConfig.getPortfolioInfo().getInitialCompleted()) {
                    coinConfigListCache.add(coinConfig);
                }
            }
        }

        final CountDownLatch oneMinuteCountDownLatch = new CountDownLatch(coinConfigListCache.size());
        //执行一分钟和多分钟任务
        for (final CoinConfig coinConfig : coinConfigListCache) {
            this.threadpoolExecutor.execute(new KlineBuilderRunnable(coinConfig,
                    oneMinuteCountDownLatch));
        }
        oneMinuteCountDownLatch.await();
    }

    /**
     * 一分钟和多分钟Kline生成任务，顺序执行
     */
    private class KlineBuilderRunnable implements Runnable {
        final CountDownLatch countDownLatch;
        final CoinConfig coinConfig;

        KlineBuilderRunnable(final CoinConfig coinConfig, final CountDownLatch countDownLatch) {
            this.coinConfig = coinConfig;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {

            try {
                //先执行一分钟Kline
                final boolean newKline = MarketKlineXxlJob.this.marketDataKlineService.generateOuterIndex1Minute(this.coinConfig);
                if (newKline) {
                    int count = 0;
                    for (final KlineEnum k : KlineEnum.values()) {
                        if (KlineEnum.MIN1 == k) {
                            continue;
                        }
                        count++;
                    }
                    //再执行多分钟Kline
                    final CountDownLatch subTaskCountDownLatch = new CountDownLatch(count);
                    for (final KlineEnum k : KlineEnum.values()) {
                        if (KlineEnum.MIN1 == k) {
                            continue;
                        }
                        MarketKlineXxlJob.this.klineThreadpoolExecutor.execute(() ->
                        {
                            try {
                                MarketKlineXxlJob.this.marketDataKlineService.marketDataOuterMerge(this.coinConfig, k);
                            } catch (final Exception e) {
                                MarketKlineXxlJob.log.error("KlineBuilderRunnable subTaskCountDownLatch error " + k + " , " + this.coinConfig + " : ", e);
                            } finally {
                                subTaskCountDownLatch.countDown();
                            }
                        });
                    }
                    subTaskCountDownLatch.await();
                } else {
                    MarketKlineXxlJob.log.info("KlineBuilderRunnable has no newKline: " + this.coinConfig);
                }
            } catch (final Exception e) {
                MarketKlineXxlJob.log.error("KlineBuilderRunnable error " + this.coinConfig + " : ", e);
            } finally {
                this.countDownLatch.countDown();
            }
        }
    }
}
