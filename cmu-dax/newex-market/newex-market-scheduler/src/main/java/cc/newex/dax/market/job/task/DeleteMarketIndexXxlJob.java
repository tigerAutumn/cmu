package cc.newex.dax.market.job.task;

import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.job.service.CoinService;
import cc.newex.dax.market.service.CoinConfigService;
import cc.newex.dax.market.service.MarketIndexService;
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
@JobHandler(value = "DeleteMarketIndexXxlJob")
@Component
public class DeleteMarketIndexXxlJob extends IJobHandler {
    @Autowired
    CoinService coinService;
    @Autowired
    MarketIndexService marketIndexService;
    @Autowired
    CoinConfigService coinConfigService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.doExecute();
        return IJobHandler.SUCCESS;
    }

    /**
     * 删除指数数据
     */
    private void doExecute() {
        final List<CoinConfig> coinList = this.coinService.getCoinConfigList();
        if (CollectionUtils.isEmpty(coinList)) {
            DeleteMarketIndexXxlJob.log.info("deleteMarketIndex coinsArray is null");
            return;
        }

        //组合指数列表
        final List<CoinConfig> portfolioList = coinConfigService.getAllPortfolioCoinConfigListFromCache();
        if (CollectionUtils.isNotEmpty(portfolioList)) {
            coinList.addAll(portfolioList);
        }

        for (final CoinConfig coin : coinList) {
            this.marketIndexService.deleteMarketIndex(coin.getSymbol());
        }
    }
}