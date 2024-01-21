package cc.newex.dax.market.job.task;

import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.job.service.CoinService;
import cc.newex.dax.market.service.MarketIndexRecordService;
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
@JobHandler(value = "DeleteMarketIndexRecordXxlJob")
@Component
public class DeleteMarketIndexRecordXxlJob extends IJobHandler {
    @Autowired
    CoinService coinService;
    @Autowired
    MarketIndexRecordService marketIndexRecordService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.doExecute();
        return IJobHandler.SUCCESS;
    }

    /**
     * 删除market_index_record一天前数据
     */
    private void doExecute() {
        final List<CoinConfig> coinList = this.coinService.getCoinConfigList();
        if (CollectionUtils.isEmpty(coinList)) {
            DeleteMarketIndexRecordXxlJob.log.info("deleteMarketIndexRecord coinsArray is null");
            return;
        }
        for (final CoinConfig coin : coinList) {
            this.marketIndexRecordService.deleteMarketIndexRecordBefore24Hour(coin);
        }
    }
}