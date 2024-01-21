package cc.newex.dax.market.job.task;

import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.job.service.CoinService;
import cc.newex.dax.market.job.service.MarketIndexGeneratorService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author newex-team
 * @date 2018/7/23
 */
@Slf4j
@JobHandler(value = "GenerateMarketIndexXxlJob")
@Component
public class GenerateMarketIndexXxlJob extends IJobHandler {
    @Autowired
    CoinService coinService;
    @Autowired
    MarketIndexGeneratorService marketIndexGeneratorService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.doExecute();
        return IJobHandler.SUCCESS;
    }

    /**
     * 生成MarketIndex指数
     */
    private void doExecute() {
        final List<CoinConfig> coinConfigList = this.coinService.getCoinConfigList();
        if (CollectionUtils.isEmpty(coinConfigList)) {
            GenerateMarketIndexXxlJob.log.info("generateMarketIndex coinsArray is null");
            return;
        }
        for (final CoinConfig coinConfig : coinConfigList) {
            this.marketIndexGeneratorService.generateMarketIndexAndCached(coinConfig);
        }
    }
}