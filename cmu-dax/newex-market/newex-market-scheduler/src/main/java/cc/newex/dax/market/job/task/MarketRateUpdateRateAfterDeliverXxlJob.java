package cc.newex.dax.market.job.task;

import cc.newex.dax.market.model.RateInfo;
import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.common.consts.MarketAllRateConst;
import cc.newex.dax.market.common.util.JSONUtil;
import cc.newex.dax.market.domain.MarketAllRate;
import cc.newex.dax.market.service.MarketAllRateService;
import cc.newex.dax.market.service.RedisService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/7/23
 */
@Slf4j
@JobHandler(value = "MarketRateUpdateRateAfterDeliverXxlJob")
@Component
public class MarketRateUpdateRateAfterDeliverXxlJob extends IJobHandler {

    @Autowired
    private MarketAllRateService marketAllRateService;
    @Autowired
    RedisService redisService;

    @PostConstruct
    public void initRate() {
        this.putRateCnyCache();
    }

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.doExecute();
        return IJobHandler.SUCCESS;
    }

    /**
     * 更新汇率，推送汇率
     */
    private void doExecute() {
        putRateCnyCache();
    }

    private void putRateCnyCache() {

        List<RateInfo> rateInfoList = new ArrayList<>();
        MarketAllRateConst.RATE_LIST.stream().forEach(s -> {
            final MarketAllRate marketAllRate = this.marketAllRateService.getRateByNameOrderBy(s);
            if (marketAllRate != null) {
                double rate = Double.parseDouble(marketAllRate.getPairAvg().toString());
                rateInfoList.add(RateInfo.builder()
                        .rate(rate)
                        .rateType(s)
                        .build());
            }

        });
        this.redisService.setInfo(IndexConst.MARKET_RATE_LIST, JSONUtil.toJSONString(rateInfoList));

    }
}