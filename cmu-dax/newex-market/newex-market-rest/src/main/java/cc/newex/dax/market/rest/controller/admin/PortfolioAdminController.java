package cc.newex.dax.market.rest.controller.admin;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.market.common.enums.BizErrorCodeEnum;
import cc.newex.dax.market.common.enums.CoinConfigTypeEnum;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.dto.model.IndexPortfolioConfigDTO;
import cc.newex.dax.market.dto.model.PortfolioInfo;
import cc.newex.dax.market.dto.request.PortfolioParam;
import cc.newex.dax.market.dto.result.CoinConfigDto;
import cc.newex.dax.market.service.CoinConfigService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by wj on 2018/7/26.
 */
@Slf4j
@RestController
@RequestMapping("/admin/v1/market/portfolio")
public class PortfolioAdminController {
    @Autowired
    private CoinConfigService coinConfigService;

    @GetMapping(value = "list")
    public ResponseResult<List<CoinConfigDto>> list() {
        List<CoinConfig> coinConfigs = coinConfigService.getAllPortfolioCoinConfigList();
        if (CollectionUtils.isEmpty(coinConfigs)) {
            return ResultUtils.success(new ArrayList<>());
        }

        List<CoinConfig> indexCoinConfigList = coinConfigService.getAllCoinConfigListFromCache();
        Map<Integer, CoinConfig> coinConfigMap = indexCoinConfigList.stream()
                .collect(Collectors.toMap(CoinConfig::getSymbol, Function.identity(), (x, y) -> y, HashMap::new));

        List<CoinConfigDto> coinConfigDtos = new ArrayList<>();
        for (CoinConfig coinConfig : coinConfigs) {

            //赋值币名字
            if (coinConfig != null && CollectionUtils.isNotEmpty(coinConfig.getPortfolioInfo().getPortfolioConfigList())) {
                for (IndexPortfolioConfigDTO indexPortfolioConfigDTO : coinConfig.getPortfolioInfo().getPortfolioConfigList()) {
                    indexPortfolioConfigDTO.setSymbolName(coinConfigMap.get(indexPortfolioConfigDTO.getSymbol()).getSymbolName());
                }
            }

            coinConfigDtos.add(CoinConfigDto.builder()
                    .id(coinConfig.getId())
                    .symbol(coinConfig.getSymbol())
                    .symbolName(coinConfig.getSymbolName())
                    .initialDate(coinConfig.getPortfolioInfo().getInitialDate())
                    .status(coinConfig.getStatus())
                    .portfolioConfigList(coinConfig.getPortfolioInfo().getPortfolioConfigList())
                    .build());
        }
        return ResultUtils.success(coinConfigDtos);
    }


    @GetMapping(value = "coinList")
    public ResponseResult<List<CoinConfigDto>> coinList() {
        List<CoinConfig> coinConfigs = coinConfigService.getAllCoinConfigList();
        if (CollectionUtils.isEmpty(coinConfigs)) {
            return ResultUtils.success(new ArrayList<>());
        }

        List<CoinConfigDto> coinConfigDtos = new ArrayList<>();
        for (CoinConfig coinConfig : coinConfigs) {
            coinConfigDtos.add(CoinConfigDto.builder().symbol(coinConfig.getSymbol())
                    .symbolName(coinConfig.getSymbolName()).build());
        }
        return ResultUtils.success(coinConfigDtos);
    }

    @PostMapping(value = "create")
    public ResponseResult create(@RequestBody PortfolioParam portfolioParam) {
        PortfolioInfo portfolioInfo = PortfolioInfo.builder()
                .initialCompleted(false)
                .initialDate(portfolioParam.getInitialDate())
                .portfolioConfigList(portfolioParam.getPortfolioConfigList())
                .build();

        CoinConfig coinConfig = CoinConfig.builder()
                .symbol(portfolioParam.getSymbol())
                .symbolName(portfolioParam.getSymbolName())
                .indexMarketFrom(portfolioParam.getSymbol())
                .symbolMark(StringUtils.EMPTY)
                .marketFrom(JSON.toJSONString(portfolioInfo))
                .pricePoint(2)
                .invalidSwitch((byte) 0)
                .type(CoinConfigTypeEnum.PORTFOLIO.getCode())
                .status(portfolioParam.getStatus())
                .createdDate(new Date())
                .modifyDate(new Date())
                .build();

        coinConfigService.addCoinConfig(coinConfig);

        return ResultUtils.success();
    }

    @PostMapping(value = "update")
    public ResponseResult update(@RequestBody PortfolioParam portfolioParam) {
        CoinConfig coinConfig = coinConfigService.getAllCoinConfigById(portfolioParam.getId());
        if (coinConfig == null) {
            return ResultUtils.failure(BizErrorCodeEnum.PORTFOLIO_NOT_EXIST);
        }

        //赋值 PortfolioInfo 对象
        coinConfig.setPortfolioInfo(JSON.parseObject(coinConfig.getMarketFrom(), PortfolioInfo.class));

        CoinConfig coinConfigUpdate = CoinConfig.builder()
                .id(portfolioParam.getId())
                .status(portfolioParam.getStatus())
                .modifyDate(new Date())
                .build();

        PortfolioInfo portfolioInfo = coinConfig.getPortfolioInfo();
        //修改初始化时间
        if (!portfolioInfo.getInitialCompleted()) {
            portfolioInfo.setInitialDate(portfolioParam.getInitialDate());
        }

        //前端传入指数列表对象
        Map<Integer, IndexPortfolioConfigDTO> map = portfolioParam.getPortfolioConfigList().stream()
                .collect(Collectors.toMap(IndexPortfolioConfigDTO::getSymbol, Function.identity(), (x, y) -> y, HashMap::new));

        //修改比列
        for (IndexPortfolioConfigDTO portfolioConfig : portfolioInfo.getPortfolioConfigList()) {
            portfolioConfig.setRatio(map.get(portfolioConfig.getSymbol()).getRatio());
        }

        coinConfigUpdate.setMarketFrom(JSON.toJSONString(portfolioInfo));

        coinConfigService.updateCoinConfig(coinConfigUpdate);

        return ResultUtils.success();
    }
}
