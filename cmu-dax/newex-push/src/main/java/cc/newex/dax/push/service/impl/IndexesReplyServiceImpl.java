package cc.newex.dax.push.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.newex.dax.push.bean.FunctionType;
import cc.newex.dax.push.client.FeignMarketServiceClient;
import cc.newex.dax.push.service.impl.function.impl.indexes.IndexesKlineFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.indexes.IndexesTickerFunctionServiceImpl;

/**
 * indexes的reply
 *
 * @author xionghui
 * @date 2018/09/14
 */
@Service("indexes")
public class IndexesReplyServiceImpl extends ReplyServiceImpl {
  @Autowired
  private FeignMarketServiceClient marketServiceClient;

  @PostConstruct
  protected void init() {
    this.functionServiceMap.put(FunctionType.TICKER,
        new IndexesTickerFunctionServiceImpl(this.marketServiceClient));
    this.functionServiceMap.put(FunctionType.CANDLES,
        new IndexesKlineFunctionServiceImpl(this.marketServiceClient));
  }
}
