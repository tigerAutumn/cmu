package cc.newex.dax.market.rest.controller.outer.v1.common;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.criteria.MarketAllRateExample;
import cc.newex.dax.market.domain.MarketAllRate;
import cc.newex.dax.market.model.RateInfo;
import cc.newex.dax.market.rest.common.aop.ControllerLog;
import cc.newex.dax.market.rest.controller.base.BaseController;
import cc.newex.dax.market.service.MarketAllRateService;
import cc.newex.dax.market.service.RedisService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 对外提供汇率相关的接口。
 *
 * @author newex-team
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/market/indexes")
public class MarketRateController
        extends BaseController<MarketAllRateService, MarketAllRate, MarketAllRateExample, Long> {

    @Autowired
    RedisService redisService;

    /**
     * 获取汇率平均值
     */
    @ControllerLog(isLog = true)
    @GetMapping(value = "/exchange-rate")
    public ResponseResult exchangeRate() {
        //从缓存查询
        final String object = this.redisService.getInfo(IndexConst.MARKET_RATE_LIST);
        final JSONObject result = new JSONObject();
        if (object != null) {
            final List<RateInfo> rateInfoList = JSONObject.parseArray(object, RateInfo.class);
            rateInfoList.stream().forEach(rateInfo -> {
                result.put(rateInfo.getRateType(), rateInfo.getRate());
            });
        }
        return ResultUtils.success(result);
    }

}
