package cc.newex.dax.push.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.newex.dax.push.bean.FunctionType;
import cc.newex.dax.push.cache.PushRedisCache;
import cc.newex.dax.push.client.FeignPortfolioConfigClient;
import cc.newex.dax.push.service.impl.function.impl.portfolio.PortfolioBalanceFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.portfolio.PortfolioOrderFunctionServiceImpl;
import cc.newex.dax.push.service.impl.function.impl.portfolio.PortfolioShareFunctionServiceImpl;

/**
 * portfolio的reply
 *
 * @author xionghui
 * @date 2018/09/14
 */
@Service("portfolio")
public class PortfolioReplyServiceImpl extends ReplyServiceImpl {
  @Autowired
  private FeignPortfolioConfigClient portfolioConfigClient;
  @Autowired
  private PushRedisCache pushRedisCache;

  @PostConstruct
  protected void init() {
    this.functionServiceMap.put(FunctionType.SHARE,
        new PortfolioShareFunctionServiceImpl(this.portfolioConfigClient, this.pushRedisCache));
    this.functionServiceMap.put(FunctionType.ASSETS, new PortfolioBalanceFunctionServiceImpl());
    this.functionServiceMap.put(FunctionType.ORDERS, new PortfolioOrderFunctionServiceImpl());
  }
}
