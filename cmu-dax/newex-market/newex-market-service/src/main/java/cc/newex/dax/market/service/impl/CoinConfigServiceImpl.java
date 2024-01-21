package cc.newex.dax.market.service.impl;

import cc.newex.dax.market.common.enums.CoinConfigTypeEnum;
import cc.newex.dax.market.common.enums.RedisKeyEnum;
import cc.newex.dax.market.criteria.CoinConfigExample;
import cc.newex.dax.market.data.CoinConfigRepository;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.dto.model.PortfolioInfo;
import cc.newex.dax.market.service.CoinConfigService;
import cc.newex.dax.market.service.RedisService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Component("coinConfigService")
public class CoinConfigServiceImpl implements CoinConfigService, ApplicationListener<ContextRefreshedEvent> {
    private static Map<String, CoinConfig> stringCoinConfigMap;
    private static Map<Integer, CoinConfig> integerCoinConfigMap;
    private static List<CoinConfig> cacheCoinConfigList;
    private static volatile List<CoinConfig> cachePortfolioCoinConfigList;

    @Autowired
    private CoinConfigRepository coinConfigRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CoinConfigService coinConfigService;

    @Override
    public List<CoinConfig> getAllCoinConfigList() {

        final CoinConfigExample marketPropertiesExample = new CoinConfigExample();
        CoinConfigExample.Criteria criteria = marketPropertiesExample.createCriteria();
        criteria.andTypeEqualTo(CoinConfigTypeEnum.MARKET.getCode());

        final List<CoinConfig> list = this.coinConfigRepository.selectByExample(marketPropertiesExample);

        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }

        for (final CoinConfig coinConfig : list) {
            final JSONArray jsonArray = JSONArray.parseArray(coinConfig.getMarketFrom());
            if (jsonArray != null) {
                final int[] returnArr = new int[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); i++) {
                    returnArr[i] = Integer.parseInt(jsonArray.get(i).toString());
                }
                coinConfig.setMarketFromArray(returnArr);
            }
        }
        return list;
    }

    @Override
    public List<CoinConfig> getAllCoinConfigListFromCache() {
        final List<CoinConfig> coinConfigList = new ArrayList<>();
        coinConfigList.addAll(CoinConfigServiceImpl.cacheCoinConfigList);
        return coinConfigList;
    }

    @Override
    public List<CoinConfig> getAllPortfolioCoinConfigList() {
        final CoinConfigExample marketPropertiesExample = new CoinConfigExample();
        CoinConfigExample.Criteria criteria = marketPropertiesExample.createCriteria();
        criteria.andTypeEqualTo(CoinConfigTypeEnum.PORTFOLIO.getCode());

        List<CoinConfig> list = this.coinConfigRepository.selectByExample(marketPropertiesExample);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }

        for (CoinConfig coinConfig : list) {
            coinConfig.setPortfolioInfo(JSON.parseObject(coinConfig.getMarketFrom(), PortfolioInfo.class));
        }
        return list;
    }

    @Override
    public List<CoinConfig> getAllPortfolioCoinConfigListFromCache() {
        return new ArrayList<>(cachePortfolioCoinConfigList);
    }

    @Override
    public List<CoinConfig> getCompletedPortfolioListFromCache() {
        final List<CoinConfig> list = this.getAllPortfolioCoinConfigListFromCache();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream().filter(l -> l.getPortfolioInfo().getInitialCompleted())
                .collect(toList());
    }

    @Scheduled(initialDelay = 100, fixedRate = 1000 * 10)
    private void refresh() {
        refreshCoinList();
    }

    @PostConstruct
    public void refreshCoinList() {
        cacheCoinConfigList = this.getAllCoinConfigList();
        cachePortfolioCoinConfigList = this.getAllPortfolioCoinConfigList();

        CoinConfigServiceImpl.stringCoinConfigMap = new HashMap<>(16);
        CoinConfigServiceImpl.integerCoinConfigMap = new HashMap<>(16);

        for (final CoinConfig coinConfig : cacheCoinConfigList) {
            CoinConfigServiceImpl.stringCoinConfigMap.put(coinConfig.getSymbolName().toLowerCase(), coinConfig);
            CoinConfigServiceImpl.integerCoinConfigMap.put(coinConfig.getSymbol(), coinConfig);
        }
        for (final CoinConfig coinConfig : cachePortfolioCoinConfigList) {
            CoinConfigServiceImpl.stringCoinConfigMap.put(coinConfig.getSymbolName().toLowerCase(), coinConfig);
            CoinConfigServiceImpl.integerCoinConfigMap.put(coinConfig.getSymbol(), coinConfig);
        }
    }

    @Override
    public CoinConfig getAllCoinConfigBySymbol(final Integer symbol) {
        final CoinConfigExample coinConfigExample = new CoinConfigExample();
        final CoinConfigExample.Criteria criteria = coinConfigExample.createCriteria();
        criteria.andSymbolEqualTo(symbol);
        final List<CoinConfig> list = this.coinConfigRepository.selectByExample(coinConfigExample);
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public CoinConfig getCoinConfigBySymbolCode(final Integer symbol) {
        return CoinConfigServiceImpl.integerCoinConfigMap.get(symbol);
    }


    @Override
    public CoinConfig getAllCoinConfigById(final Long id) {
        return this.coinConfigRepository.selectById(id);
    }

    @Override
    public int deleteCoinConfig(final Long id) {
        return this.coinConfigRepository.deleteById(id);
    }

    @Override
    public int addCoinConfig(final CoinConfig model) {
        return this.coinConfigRepository.insert(model);
    }

    @Override
    public int updateCoinConfig(final CoinConfig model) {
        return this.coinConfigRepository.updateById(model);
    }

    @Override
    public CoinConfig getCoinConfigBySymbolName(final String symbolName) {
        return CoinConfigServiceImpl.stringCoinConfigMap.get(symbolName.toLowerCase());
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        refreshCoinList();
    }

    /**
     * 将币对写入redis缓存
     */
    @Override
    public void putCoinConfigToRedis() {
        final List<CoinConfig> coinConfigList = this.getAllCoinConfigList();
        if (coinConfigList.isEmpty()) {
            return;
        }

        //组合指数列表
        final List<CoinConfig> portfolioList = coinConfigService.getAllPortfolioCoinConfigListFromCache();
        if (CollectionUtils.isNotEmpty(portfolioList)) {
            coinConfigList.addAll(portfolioList);
        }

        final String jsonData = JSON.toJSONString(coinConfigList);
        //添加 redis
        this.redisService.setInfo(RedisKeyEnum.REDIS_KEY_COIN_CONFIG.getKey(), jsonData);
    }
}
