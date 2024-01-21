package cc.newex.dax.market.job.task;

import cc.newex.dax.market.service.CoinConfigService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @date 2018/7/20
 */
@Slf4j
@JobHandler(value = "MarketConfigXxlJob")
@Component
public class MarketConfigXxlJob extends IJobHandler {

    @Autowired
    private CoinConfigService coinConfigService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.coinConfigService.putCoinConfigToRedis();
        return IJobHandler.SUCCESS;
    }
}
