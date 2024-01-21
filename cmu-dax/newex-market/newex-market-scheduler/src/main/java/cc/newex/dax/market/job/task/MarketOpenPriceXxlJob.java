package cc.newex.dax.market.job.task;

import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.job.service.CoinService;
import cc.newex.dax.market.job.service.MarketDataOuterOpenPrice24HService;
import cc.newex.dax.market.service.CoinConfigService;
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
@JobHandler(value = "MarketOpenPriceXxlJob")
@Component
public class MarketOpenPriceXxlJob extends IJobHandler {
    @Autowired
    CoinService coinService;
    @Autowired
    MarketDataOuterOpenPrice24HService marketDataOuterOpenPrice24HService;
    @Autowired
    CoinConfigService coinConfigService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.doExecute();
        return IJobHandler.SUCCESS;
    }

    private void doExecute() {
        final List<CoinConfig> coinConfigList = this.coinService.getCoinConfigList();
        if (CollectionUtils.isEmpty(coinConfigList)) {
            MarketOpenPriceXxlJob.log.info("MarketOpenPriceXxlJob getCoinConfigList is null");
            return;
        }

        //组合指数列表
        final List<CoinConfig> portfolioList = coinConfigService.getAllPortfolioCoinConfigListFromCache();
        if (CollectionUtils.isNotEmpty(portfolioList)) {
            coinConfigList.addAll(portfolioList);
        }

        for (final CoinConfig coin : coinConfigList) {
            this.marketDataOuterOpenPrice24HService.openPriceBefore24H(coin);
        }
    }
}
