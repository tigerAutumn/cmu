package cc.newex.dax.market.service;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.market.domain.CoinConfig;
import com.alibaba.fastjson.JSONObject;

/**
 * @author allen
 * @date 2018/5/21
 * @des
 */
public interface MarketPublicService {

    /**
     * ticker convert
     *
     * @param coinConfig
     * @return
     */
    JSONObject buildTicker(final CoinConfig coinConfig);

    /**
     * get kline
     *
     * @param currencyCode
     * @param granularity
     * @param limitStr
     * @param sinceStr
     * @return
     */
    ResponseResult kline(final String currencyCode, final String granularity, String limitStr, String sinceStr);
}