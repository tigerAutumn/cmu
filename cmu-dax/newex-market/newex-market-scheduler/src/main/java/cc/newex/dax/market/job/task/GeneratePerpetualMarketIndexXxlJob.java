package cc.newex.dax.market.job.task;

import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.job.service.CoinService;
import cc.newex.dax.market.job.service.MarketIndexGeneratorService;
import cc.newex.dax.market.job.service.MsgSendService;
import com.alibaba.fastjson.JSON;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author newex-team
 * @date 2018/7/23
 */
@Slf4j
@JobHandler(value = "GeneratePerpetualMarketIndexXxlJob")
@Component
public class GeneratePerpetualMarketIndexXxlJob extends IJobHandler {

    private static final Map<Long, Long> TIME_MAP = new HashMap<>();

    @Autowired
    CoinService coinService;
    @Autowired
    MarketIndexGeneratorService marketIndexGeneratorService;
    @Autowired
    private MsgSendService msgSendService;

    private final ExecutorService executorService =
            new ThreadPoolExecutor(64, 64, 0L,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.doExecute(s);
        return IJobHandler.SUCCESS;
    }

    /**
     * 生成MarketIndex指数
     */
    private void doExecute(final String s) throws InterruptedException {
        final List<CoinConfig> coinConfigList = this.coinService.getCoinConfigList();
        if (CollectionUtils.isEmpty(coinConfigList)) {
            log.info("generateMarketIndex coinsArray is null");
            return;
        }

        final CountDownLatch downLatch = new CountDownLatch(coinConfigList.size());
        for (final CoinConfig coinConfig : coinConfigList) {
            this.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        log.info("generatePerpetualMarketIndexAndCached : {}", JSON.toJSONString(coinConfig));

                        final boolean success = GeneratePerpetualMarketIndexXxlJob.this.marketIndexGeneratorService.generatePerpetualMarketIndexAndCached(coinConfig);
                        if (success) {
                            TIME_MAP.put(coinConfig.getId(), System.currentTimeMillis());
                        }
                        final boolean outTime = (System.currentTimeMillis() - Optional.ofNullable(TIME_MAP.get(coinConfig.getId())).orElse(0L)) > (10 * 60 * 1000L);
                        if (!success && StringUtils.isNotBlank(s) && outTime) {
                            GeneratePerpetualMarketIndexXxlJob.this.msgSendService.sendSMS("[MarketData] 生成合约指数价格失败, currencyCode : " + coinConfig.getSymbolName(), s);
                            TIME_MAP.put(coinConfig.getId(), System.currentTimeMillis());
                        }
                    } catch (final Exception e) {
                        log.error("unexpected error", e);
                    } finally {
                        downLatch.countDown();
                    }
                }
            });
        }
        downLatch.await();
    }
}