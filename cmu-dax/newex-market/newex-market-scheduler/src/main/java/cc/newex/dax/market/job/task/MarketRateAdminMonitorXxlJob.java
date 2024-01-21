package cc.newex.dax.market.job.task;

import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.dto.enums.RateConvertEnum;
import cc.newex.dax.market.common.util.JSONUtil;
import cc.newex.dax.market.domain.MarketAllRate;
import cc.newex.dax.market.service.MarketAllRateService;
import cc.newex.dax.market.service.RedisService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @date 2018/7/23
 */
@Slf4j
@JobHandler(value = "MarketRateAdminMonitorXxlJob")
@Component
public class MarketRateAdminMonitorXxlJob extends IJobHandler {

    @Autowired
    private MarketAllRateService marketAllRateService;
    @Autowired
    RedisService redisService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.doExecute();
        return IJobHandler.SUCCESS;
    }

    /**
     * 取得过去两周汇率明细
     */
    private void doExecute() {
        final List<MarketAllRate> list = this.marketAllRateService.getMarketAllRateTwoWeekListUSD_CNY(RateConvertEnum.USD_CNY.getCode());
        final List<Map<String, Object>> lastRates = new ArrayList<>();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                final Map<String, Object> mapModel = new HashMap<>();
                mapModel.put("id", list.get(i).getId());
                mapModel.put("usd_cny_rate", list.get(i).getRateName());
                mapModel.put("usd_avg", list.get(i).getPairAvg());
                mapModel.put("created_date", list.get(i).getCreateTime());
                lastRates.add(mapModel);
            }
        }
        if (!CollectionUtils.isEmpty(lastRates)) {
            this.redisService.setInfo(IndexConst.LASTTWOWEEKRATE, JSONUtil.toJSONString(lastRates));
        }
    }
}