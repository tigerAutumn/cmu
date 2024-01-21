package cc.newex.dax.push.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.newex.dax.push.bean.FunctionType;
import cc.newex.dax.push.cache.PushRedisCache;
import cc.newex.dax.push.service.impl.function.impl.perpetual.PerpetualBalanceFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.perpetual.PerpetualConditionOrderFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.perpetual.PerpetualCurrencyPairFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.perpetual.PerpetualDealFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.perpetual.PerpetualDepthFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.perpetual.PerpetualFundRateFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.perpetual.PerpetualFundRatesFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.perpetual.PerpetualKlineFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.perpetual.PerpetualOrderFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.perpetual.PerpetualPositionFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.perpetual.PerpetualRankFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.perpetual.PerpetualTickerFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.perpetual.PerpetualTotalPositionFunctionServiceImpl;

/**
 * perpetual的reply
 *
 * @author xionghui
 * @date 2018/09/25
 */
@Service("perpetual")
public class PerpetualReplyServiceImpl extends ReplyServiceImpl {
  @Autowired
  private PushRedisCache pushRedisCache;

  @PostConstruct
  protected void init() {
    this.functionServiceMap.put(FunctionType.CANDLES,
        new PerpetualKlineFunctionServiceImpl(this.pushRedisCache));
    this.functionServiceMap.put(FunctionType.DEPTH,
        new PerpetualDepthFunctionServiceImpl(this.pushRedisCache));
    this.functionServiceMap.put(FunctionType.TICKER, new PerpetualTickerFunctionServiceImpl());
    this.functionServiceMap.put(FunctionType.TICKERS, new PerpetualTickerFunctionServiceImpl());
    this.functionServiceMap.put(FunctionType.FILLS,
        new PerpetualDealFunctionServiceImpl(this.pushRedisCache));
    this.functionServiceMap.put(FunctionType.ASSETS, new PerpetualBalanceFunctionServiceImpl());
    this.functionServiceMap.put(FunctionType.ORDERS, new PerpetualOrderFunctionServiceImpl());
    this.functionServiceMap.put(FunctionType.CONDITION_ORDERS,
        new PerpetualConditionOrderFunctionServiceImpl());
    this.functionServiceMap.put(FunctionType.POSITION, new PerpetualPositionFunctionServiceImpl());
    this.functionServiceMap.put(FunctionType.FUND_RATE, new PerpetualFundRateFunctionServiceImpl());
    this.functionServiceMap.put(FunctionType.FUND_RATES,
        new PerpetualFundRatesFunctionServiceImpl());
    this.functionServiceMap.put(FunctionType.RANK, new PerpetualRankFunctionServiceImpl());
    this.functionServiceMap.put(FunctionType.TOTAL_POSITION,
        new PerpetualTotalPositionFunctionServiceImpl());
    this.functionServiceMap.put(FunctionType.CURRENCY_PAIR,
        new PerpetualCurrencyPairFunctionServiceImpl());
  }
}
