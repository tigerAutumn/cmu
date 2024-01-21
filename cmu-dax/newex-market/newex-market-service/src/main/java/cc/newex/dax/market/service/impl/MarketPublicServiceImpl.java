package cc.newex.dax.market.service.impl;

import cc.newex.commons.dictionary.enums.KlineEnum;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.common.enums.BizErrorCodeEnum;
import cc.newex.dax.market.common.util.StringUtil;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.service.CoinConfigService;
import cc.newex.dax.market.service.MarketDataService;
import cc.newex.dax.market.service.MarketPublicService;
import cc.newex.dax.market.service.RedisService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author allen
 * @date 2018/5/21
 * @des
 */
@Service
public class MarketPublicServiceImpl implements MarketPublicService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private CoinConfigService configService;
    @Autowired
    private MarketDataService marketDataService;

    /**
     * ticker convert
     *
     * @param coinConfig
     * @return
     */
    @Override
    public JSONObject buildTicker(final CoinConfig coinConfig) {
        final String key = IndexConst.KEY_INDEX_TICKER;
        final String str = this.redisService.getInfo(String.format(key, coinConfig.getSymbol()));
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return JSON.parseObject(str);
    }

    /**
     * get kline
     *
     * @return
     */
    @Override
    public ResponseResult kline(final String currencyCode, final String granularity, final String limitStr, final String sinceStr) {
        final CoinConfig coinConfig = this.configService.getCoinConfigBySymbolName(currencyCode);
        if (coinConfig == null) {
            return ResultUtils.failure(BizErrorCodeEnum.SYMBOL_ERROR);
        }
        final String typeStr = StringUtils.isBlank(granularity) ? KlineEnum.MIN1.getTypeStr() : granularity;
        final Integer type = KlineEnum.getTypeInteger(typeStr);
        if (type == null) {
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_PARAM_EMPTY);
        }
        final int limit = StringUtil.toInteger(limitStr, 0);
        final long since = StringUtil.toLong(sinceStr, 0L);

        final List<String[]> list = this.marketDataService.getMarketDataFromCacheArray(coinConfig.getIndexMarketFrom(), type, limit, since);
        final JSONArray outerArray = new JSONArray();
        if (CollectionUtils.isNotEmpty(list)) {
            for (final String[] outer : list) {
                final JSONArray innerArray = new JSONArray();
                for (int i = 0; i < outer.length; i++) {
                    String tmp = outer[i];
                    if (StringUtils.isBlank(tmp)) {
                        tmp = "0";
                    }
                    if ("null".equalsIgnoreCase(tmp)) {
                        tmp = "0";
                    }
                    if (i == 0) {
                        innerArray.add(Long.parseLong(tmp));
                    } else if (i == 6) {
                        innerArray.add(tmp);
                    } else {
                        innerArray.add(Double.parseDouble(tmp));
                    }
                }
                outerArray.add(innerArray);
            }
        }
        return ResultUtils.success(outerArray);
    }
}
