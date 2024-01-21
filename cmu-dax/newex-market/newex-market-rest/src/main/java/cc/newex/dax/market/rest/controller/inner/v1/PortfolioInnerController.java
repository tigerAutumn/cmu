package cc.newex.dax.market.rest.controller.inner.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.market.common.enums.BizErrorCodeEnum;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.domain.MarketIndex;
import cc.newex.dax.market.dto.model.IndexPortfolioConfigDTO;
import cc.newex.dax.market.dto.model.PortfolioInfo;
import cc.newex.dax.market.dto.model.PortfolioProperties;
import cc.newex.dax.market.dto.result.PortfolioResultDto;
import cc.newex.dax.market.service.CoinConfigService;
import cc.newex.dax.market.service.MarketIndexService;
import cc.newex.dax.market.service.MarketPropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by wj on 2018/7/26.
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/market/portfolio")
public class PortfolioInnerController {
    @Autowired
    private CoinConfigService coinConfigService;
    @Autowired
    private MarketIndexService marketIndexService;
    @Autowired
    private MarketPropertiesService marketPropertiesService;

    @GetMapping(value = "detail")
    public ResponseResult<PortfolioResultDto> portfolioDetail(@RequestParam(value = "name", required = false) final String name) {
        List<CoinConfig> coinConfigs = coinConfigService.getAllPortfolioCoinConfigListFromCache();
        if (CollectionUtils.isEmpty(coinConfigs)) {
            return ResultUtils.failure(BizErrorCodeEnum.PORTFOLIO_NOT_EXIST, PortfolioResultDto.builder().build());
        }
        CoinConfig coinConfig = coinConfigs.stream().filter(c -> c.getSymbolName().equalsIgnoreCase(name)).findFirst()
                .orElse(null);
        if (coinConfig == null) {
            return ResultUtils.failure(BizErrorCodeEnum.PORTFOLIO_NOT_EXIST, PortfolioResultDto.builder().build());
        }

        PortfolioInfo portfolioInfo = coinConfig.getPortfolioInfo();
        if (portfolioInfo == null) {
            return ResultUtils.failure(BizErrorCodeEnum.PORTFOLIO_NOT_EXIST, PortfolioResultDto.builder().build());
        }

        //没有初始化数据
        //spot 要比列
        if (!portfolioInfo.getInitialCompleted()) {
            return ResultUtils.success(PortfolioResultDto.builder().pts(0D).usdt(1D)
                    .portfolioConfigList(portfolioInfo.getPortfolioConfigList()).build());
        }

        List<CoinConfig> indexCoinConfigList = coinConfigService.getAllCoinConfigListFromCache();
        Map<Integer, CoinConfig> coinConfigMap = indexCoinConfigList.stream()
                .collect(Collectors.toMap(CoinConfig::getSymbol, Function.identity(), (x, y) -> y, HashMap::new));

        PortfolioProperties portfolioProperties = marketPropertiesService.getPortfolioProperties(coinConfig.getSymbol());

        double pts = 0d;
        for (IndexPortfolioConfigDTO portfolioConfig : portfolioInfo.getPortfolioConfigList()) {
            MarketIndex marketIndex = marketIndexService.getTickerFromCache(coinConfigMap.get(portfolioConfig.getSymbol()).getSymbolName());
            portfolioConfig.setLastPrice(marketIndex.getPrice());
            portfolioConfig.setSymbolName(coinConfigMap.get(portfolioConfig.getSymbol()).getSymbolName());
            //实时指数价格
            pts = pts + portfolioProperties.getPts() * portfolioConfig.getRatio() * marketIndex.getPrice() / portfolioConfig.getPrice();
        }

        return ResultUtils.success(PortfolioResultDto.builder().pts(pts).usdt(pts / portfolioProperties.getPts())
                .portfolioConfigList(portfolioInfo.getPortfolioConfigList()).build());
    }
}
