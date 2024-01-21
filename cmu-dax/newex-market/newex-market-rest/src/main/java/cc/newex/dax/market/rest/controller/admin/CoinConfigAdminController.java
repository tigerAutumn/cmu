package cc.newex.dax.market.rest.controller.admin;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.dto.enums.CoinConfigStatusEnum;
import cc.newex.dax.market.dto.result.CoinConfigDto;
import cc.newex.dax.market.service.CoinConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wj on 2018/7/26.
 */
@Slf4j
@RestController
@RequestMapping("/admin/v1/market/coin/config")
public class CoinConfigAdminController {
    @Autowired
    private CoinConfigService coinConfigService;

    @GetMapping(value = "list")
    public ResponseResult<List<CoinConfigDto>> list() {
        List<CoinConfig> coinConfigs = coinConfigService.getAllCoinConfigList();
        if (CollectionUtils.isEmpty(coinConfigs)) {
            return ResultUtils.success(new ArrayList<>());
        }

        List<CoinConfigDto> coinConfigDtos = new ArrayList<>();
        for (CoinConfig coinConfig : coinConfigs) {
            coinConfigDtos.add(CoinConfigDto.builder()
                    .id(coinConfig.getId())
                    .symbol(coinConfig.getSymbol())
                    .symbolName(coinConfig.getSymbolName())
                    .status(coinConfig.getStatus())
                    .statusEnum(CoinConfigStatusEnum.getByCode(coinConfig.getStatus()))
                    .build());
        }
        return ResultUtils.success(coinConfigDtos);
    }

    @PostMapping(value = "update")
    public ResponseResult create(@RequestBody CoinConfigDto coinConfigDto) {
        coinConfigService.updateCoinConfig(
                CoinConfig.builder()
                        .id(coinConfigDto.getId())
                        .status(coinConfigDto.getStatusEnum().getCode())
                        .build()
        );
        return ResultUtils.success();
    }
}
