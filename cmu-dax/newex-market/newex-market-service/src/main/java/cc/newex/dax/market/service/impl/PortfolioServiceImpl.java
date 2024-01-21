package cc.newex.dax.market.service.impl;

import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.domain.MarketIndex;
import cc.newex.dax.market.dto.model.IndexPortfolioConfigDTO;
import cc.newex.dax.market.dto.model.PortfolioInfo;
import cc.newex.dax.market.dto.model.PortfolioProperties;
import cc.newex.dax.market.service.CoinConfigService;
import cc.newex.dax.market.service.MarketIndexService;
import cc.newex.dax.market.service.MarketPropertiesService;
import cc.newex.dax.market.service.PortfolioService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by wj on 2018/7/24.
 *
 * @author fangsheng
 */
@Service("portfolioService")
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {
    @Autowired
    private CoinConfigService coinConfigService;
    @Autowired
    private MarketIndexService marketIndexService;
    @Autowired
    private MarketPropertiesService marketPropertiesService;

    @Override
    public void createCoinConfig(CoinConfig coinConfig) {
        coinConfigService.addCoinConfig(coinConfig);
    }

    @Override
    public void initialPrice(CoinConfig coinConfig) {
        PortfolioInfo portfolioInfo = coinConfig.getPortfolioInfo();
        if (portfolioInfo == null) {
            log.info("Portfolio is null id:{}", coinConfig.getId());
            return;
        }
        if (portfolioInfo.getInitialCompleted()) {
            log.info("Portfolio initial finish id:{}", coinConfig.getId());
            return;
        }

        if (portfolioInfo.getInitialDate() == null) {
            log.info("Portfolio initial date is null id:{}", coinConfig.getId());
            return;
        }

        if (portfolioInfo.getInitialDate() > System.currentTimeMillis()) {
            log.info("Portfolio initial date after now id:{}", coinConfig.getId());
            return;
        }

        List<IndexPortfolioConfigDTO> portfolioConfigList = portfolioInfo.getPortfolioConfigList();
        if (CollectionUtils.isEmpty(portfolioConfigList)) {
            log.info("Portfolio configList is null id:{}", coinConfig.getId());
            return;
        }

        List<CoinConfig> indexCoinConfigList = coinConfigService.getAllCoinConfigListFromCache();
        Map<Integer, CoinConfig> coinConfigMap = indexCoinConfigList.stream()
                .collect(Collectors.toMap(CoinConfig::getSymbol, Function.identity(), (x, y) -> y, HashMap::new));

        for (IndexPortfolioConfigDTO indexPortfolioConfigDTO : portfolioConfigList) {
            MarketIndex marketIndex = marketIndexService.getTickerFromCache(coinConfigMap.get(indexPortfolioConfigDTO.getSymbol()).getSymbolName());
            if (marketIndex == null) {
                log.info("Portfolio marketIndex is null id:{} symbol:{}",
                        coinConfig.getId(), indexPortfolioConfigDTO.getSymbol());
                return;
            }
            indexPortfolioConfigDTO.setPrice(marketIndex.getPrice());
        }
        portfolioInfo.setInitialCompleted(true);

        coinConfigService.updateCoinConfig(CoinConfig.builder().id(coinConfig.getId())
                .marketFrom(JSON.toJSONString(portfolioInfo)).build());
    }

    @Override
    public void createMarketIndex(CoinConfig coinConfig) {
        PortfolioInfo portfolioInfo = coinConfig.getPortfolioInfo();
        if (portfolioInfo == null) {
            log.info("Portfolio is null id:{}", coinConfig.getId());
            return;
        }
        //没有初始化 不生成指数
        if (!portfolioInfo.getInitialCompleted()) {
            return;
        }

        double pts = 0d;

        List<CoinConfig> indexCoinConfigList = coinConfigService.getAllCoinConfigListFromCache();
        Map<Integer, CoinConfig> coinConfigMap = indexCoinConfigList.stream()
                .collect(Collectors.toMap(CoinConfig::getSymbol, Function.identity(), (x, y) -> y, HashMap::new));

        PortfolioProperties portfolioProperties = marketPropertiesService.getPortfolioProperties(coinConfig.getSymbol());

        for (IndexPortfolioConfigDTO portfolioConfig : portfolioInfo.getPortfolioConfigList()) {
            MarketIndex marketIndex = marketIndexService.getTickerFromCache(coinConfigMap.get(portfolioConfig.getSymbol()).getSymbolName());
            if (marketIndex == null) {
                log.info("Portfolio marketIndex is null id:{} symbol:{}",
                        coinConfig.getId(), portfolioConfig.getSymbol());
                return;
            }
            pts = pts + portfolioProperties.getPts() * portfolioConfig.getRatio() * marketIndex.getPrice() / portfolioConfig.getPrice();
        }

        marketIndexService.insertMarketIndex(MarketIndex.builder().symbol(coinConfig.getSymbol())
                .createdDate(new Date()).modifyDate(new Date()).price(pts).build());
    }
}
