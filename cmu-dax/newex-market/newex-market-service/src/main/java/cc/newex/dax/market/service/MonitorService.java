package cc.newex.dax.market.service;

import cc.newex.dax.market.model.RateInfo;

import java.util.List;

public interface MonitorService {

    /**
     * 从本地缓存中获取
     *
     * @return
     */
    List<RateInfo> getCachedAdminMonitor();

    /**
     * 按照汇率类型获取汇率
     *
     * @param rateType
     * @return
     */
    RateInfo getCacheRateInfo(String rateType);


}