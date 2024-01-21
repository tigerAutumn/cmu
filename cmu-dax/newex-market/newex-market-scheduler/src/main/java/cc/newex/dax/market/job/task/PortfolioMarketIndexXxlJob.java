package cc.newex.dax.market.job.task;

import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.service.CoinConfigService;
import cc.newex.dax.market.service.PortfolioService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@JobHandler(value = "PortfolioMarketIndexXxlJob")
@Component
public class PortfolioMarketIndexXxlJob extends IJobHandler {

    @Autowired
    CoinConfigService coinConfigService;
    @Autowired
    PortfolioService portfolioService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.doExecute();
        return IJobHandler.SUCCESS;
    }

    /**
     * 初始化新币数据
     */
    private void doExecute() {
        final List<CoinConfig> coinList = coinConfigService.getAllPortfolioCoinConfigListFromCache();
        for (final CoinConfig coin : coinList) {
            portfolioService.createMarketIndex(coin);
        }
    }
}
