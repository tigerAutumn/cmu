package cc.newex.dax.market.service.impl;

import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.common.util.CacheableObject;
import cc.newex.dax.market.model.RateInfo;
import cc.newex.dax.market.service.MonitorService;
import cc.newex.dax.market.service.RedisService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author newex-team
 */
@Service("monitorService")
@Slf4j
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    RedisService redisService;


    public CacheableObject<List<RateInfo>> cachedAdminMonitor = new CacheableObject<List<RateInfo>>(5000) {
        @Override
        protected List<RateInfo> buildObject() {
            return MonitorServiceImpl.this.getAdminMonitorFromCache();
        }
    };

    @Override
    public List<RateInfo> getCachedAdminMonitor() {
        return this.cachedAdminMonitor.getObject();
    }

    /**
     * 按照汇率类型获取汇率
     *
     * @param rateType
     * @return
     */
    @Override
    public RateInfo getCacheRateInfo(String rateType) {
        List<RateInfo> list = getAdminMonitorFromCache();
        if (CollectionUtils.isNotEmpty(list)) {
            return list.stream().filter(rateInfo -> rateInfo.getRateType().equalsIgnoreCase(rateType)).findFirst().orElse(null);
        }
        return null;
    }

    public List<RateInfo> getAdminMonitorFromCache() {
        final String object = this.redisService.getInfo(IndexConst.MARKET_RATE_LIST);
        if (object != null) {
            return JSONObject.parseArray(object, RateInfo.class);
        } else {
            return null;
        }
    }

}
