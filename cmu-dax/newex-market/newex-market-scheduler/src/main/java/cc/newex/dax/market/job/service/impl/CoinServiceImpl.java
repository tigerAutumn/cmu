package cc.newex.dax.market.job.service.impl;

import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.common.util.JSONUtil;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.job.service.CoinService;
import cc.newex.dax.market.service.CoinConfigService;
import cc.newex.dax.market.service.CreatorTableService;
import cc.newex.dax.market.service.MarketPropertiesService;
import cc.newex.dax.market.service.RedisService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Service("coinService")
@Slf4j
public class CoinServiceImpl implements CoinService, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    CoinConfigService coinConfigService;
    @Autowired
    RedisService redisService;
    @Autowired
    private CreatorTableService creatorTableService;

    private static List<CoinConfig> coinConfigList;
    private static final Map<String, String> indexCacheKeyMap = new ConcurrentHashMap<>();
    private static final Map<String, String> lastIndexCacheKeyMap = new ConcurrentHashMap<>();

    @Override
    public List<CoinConfig> getCoinConfigListCache() {
        return this.coinConfigService.getAllCoinConfigListFromCache();
    }

    @Override
    public List<CoinConfig> getCoinConfigList() {
        CoinServiceImpl.coinConfigList = this.coinConfigService.getAllCoinConfigListFromCache();
        return CoinServiceImpl.coinConfigList;
    }

    @Override
    public String getIndexCacheKey(final CoinConfig coinConfig) {
        return this.getString(coinConfig, CoinServiceImpl.indexCacheKeyMap, IndexConst.KEY_INDEX);
    }

    @Override
    public String getLastIndexCacheKey(final CoinConfig coinConfig) {
        return this.getString(coinConfig, CoinServiceImpl.lastIndexCacheKeyMap, IndexConst.FUTURE_KEY_LAST_INDEX);
    }


    private String getString(final CoinConfig coinConfig, final Map<String, String> map, final String memKey) {
        if (CollectionUtils.isEmpty(map)) {
            CoinServiceImpl.log.error("ConcurrentHashMap is null,coinConfig:{}", coinConfig.toString());
        }
        final String key = map.get(String.valueOf(coinConfig.getSymbol()));
        if (StringUtils.isNotBlank(key)) {
            return key;
        }
        final String tempStr = String.format(memKey, coinConfig.getSymbolName().toLowerCase());
        CoinServiceImpl.log.error("ConcurrentHashMap key is null,key:{},coinConfig:{}", tempStr, coinConfig.toString());
        map.put(String.valueOf(coinConfig.getSymbol()), tempStr);
        return tempStr;
    }


    @Override
    public void reloadCache() {
        CoinServiceImpl.coinConfigList = this.coinConfigService.getAllCoinConfigList();
        try {
            if (CollectionUtils.isEmpty(CoinServiceImpl.coinConfigList)) {
                return;
            }
            for (final CoinConfig coinConfig : CoinServiceImpl.coinConfigList) {
                this.creatorTableService.checkMarketDataOrCreate(coinConfig);
                this.creatorTableService.checkMarketIndexRecordOrCreate(coinConfig);
                CoinServiceImpl.lastIndexCacheKeyMap.put(String.valueOf(coinConfig.getSymbol()), String.format(IndexConst.FUTURE_KEY_LAST_INDEX, coinConfig.getSymbolName().toLowerCase()));
                CoinServiceImpl.indexCacheKeyMap.put(String.valueOf(coinConfig.getSymbol()), String.format(IndexConst.KEY_INDEX, coinConfig.getSymbolName().toLowerCase()));

            }
        } catch (final Exception e) {
            CoinServiceImpl.log.error("checkMarketDataOrCreate error", e);
        }

        final List<CoinConfig> coinConfigs = this.coinConfigService.getAllPortfolioCoinConfigListFromCache();
        if (CollectionUtils.isEmpty(coinConfigs)) {
            return;
        }
        try {
            for (final CoinConfig coinConfig : coinConfigs) {
                if (coinConfig.getPortfolioInfo().getInitialCompleted()) {
                    this.creatorTableService.checkMarketDataOrCreate(coinConfig);
                }
            }
        } catch (final Exception e) {
            CoinServiceImpl.log.error("Portfolio checkMarketDataOrCreate error", e);
        }
    }

    /**
     * Spring容器启动后刷新缓存数据
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        this.reloadCache();
        CoinServiceImpl.log.info("market cache reload on startup.");
    }
}
