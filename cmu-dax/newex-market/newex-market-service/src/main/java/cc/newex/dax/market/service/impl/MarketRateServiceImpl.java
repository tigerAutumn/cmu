package cc.newex.dax.market.service.impl;


import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.criteria.MarketRateExample;
import cc.newex.dax.market.data.MarketRateRepository;
import cc.newex.dax.market.domain.MarketRate;
import cc.newex.dax.market.service.MarketRateService;
import cc.newex.dax.market.service.RedisService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service
public class MarketRateServiceImpl
        extends AbstractCrudService<MarketRateRepository, MarketRate, MarketRateExample, Long>
        implements MarketRateService {

    @Autowired
    private MarketRateRepository marketRateRepos;

    @Autowired
    private RedisService redisService;
    

    @Override
    protected MarketRateExample getPageExample(final String fieldName, final String keyword) {
        final MarketRateExample example = new MarketRateExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public MarketRate getRateByName(final String name) {
        return this.marketRateRepos.getRateByName(name);
    }

    @Override
    public List getLastFourWeekUsdCnyRate_cache() {
        final String str = this.redisService.getRandom(IndexConst.LASTTWOWEEKRATE);
        return JSON.parseArray(str, HashMap.class);
    }

    /**
     * 写入缓存
     */
    @Override
    public void putRateCnyCache(final double twoWeekRateAvg) {

//        //从缓存中取得 AdminMonitor  重新设置usd_cny_rate, eur_cny_rate
//        final String adminMonitorStr = this.redisService.getInfo(IndexConst.ADMINMONITOR);
//        //反序列化
//        final AdminMonitor adminMonitor;
//        if (StringUtils.isEmpty(adminMonitorStr)) {
//            adminMonitor = new AdminMonitor();
//        } else {
//            adminMonitor = JSON.parseObject(adminMonitorStr, AdminMonitor.class);
//        }
//        adminMonitor.setUsdCnyRate(twoWeekRateAvg);
//        adminMonitor.setEurCnyRate(0);
//        this.redisService.setInfo(IndexConst.ADMINMONITOR, JSONUtil.toJSONString(adminMonitor));
//
//        //新的汇率信息写入缓存(平台汇率)
//        final List<MarketRate> marketRateList = this.getAll();
//        this.redisService.setInfo(IndexConst.SYS_MARKET_RATE, JSONUtil.toJSONString(marketRateList));
//
//        //添加新的缓存
//        final RateInfo adminMonitorNew = new RateInfo();
//        adminMonitorNew.setUsdCnyRate(twoWeekRateAvg);
//        adminMonitorNew.setEurCnyRate(0);
//
//        this.redisService.setInfo(IndexConst.ADMINMONITOR_NEW, JSONUtil.toJSONString(adminMonitorNew));
    }
}