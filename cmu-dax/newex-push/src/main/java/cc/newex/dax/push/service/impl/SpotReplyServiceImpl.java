package cc.newex.dax.push.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.newex.dax.push.bean.FunctionType;
import cc.newex.dax.push.cache.PushRedisCache;
import cc.newex.dax.push.service.impl.function.impl.spot.SpotBalanceFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.spot.SpotCurrencyPairFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.spot.SpotDealFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.spot.SpotDepthFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.spot.SpotKlineFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.spot.SpotOrderFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.spot.SpotTickerFunctionServiceImpl;

/**
 * spot的reply
 *
 * @author xionghui
 * @date 2018/09/14
 */
@Service("spot")
public class SpotReplyServiceImpl extends ReplyServiceImpl {
  @Autowired
  private PushRedisCache pushRedisCache;

  @PostConstruct
  protected void init() {
    this.functionServiceMap.put(FunctionType.CANDLES,
        new SpotKlineFunctionServiceImpl(this.pushRedisCache));
    this.functionServiceMap.put(FunctionType.DEPTH,
        new SpotDepthFunctionServiceImpl(this.pushRedisCache));
    this.functionServiceMap.put(FunctionType.TICKERS, new SpotTickerFunctionServiceImpl());
    this.functionServiceMap.put(FunctionType.FILLS,
        new SpotDealFunctionServiceImpl(this.pushRedisCache));
    this.functionServiceMap.put(FunctionType.CURRENCY_PAIR,
        new SpotCurrencyPairFunctionServiceImpl());
    this.functionServiceMap.put(FunctionType.ASSETS,
        new SpotBalanceFunctionServiceImpl(this.pushRedisCache));
    this.functionServiceMap.put(FunctionType.ORDERS, new SpotOrderFunctionServiceImpl());
  }
}
