package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.LatestTickerExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.LatestTicker;

/**
 * 最新报价 服务接口
 *
 * @author newex-team
 * @date 2018-11-16 13:24:53
 */
public interface LatestTickerService extends CrudService<LatestTicker, LatestTickerExample, Long> {
  /**
   * 放入 Ticker redis 缓存
   *
   * @param latestTicker
   */
  void putTickerRedis(LatestTicker latestTicker);

  /**
   * 获取最新报价
   *
   * @param contract
   * @return
   */
  LatestTicker getTickerRedis(Contract contract);

  /**
   * 放入 Push Ticker redis 缓存
   *
   * @param latestTicker
   */
  void putPushTickerRedis(LatestTicker latestTicker);

  /**
   * 推送redis
   *
   * @param contract
   * @param latestTicker
   */
  void pushRedisTicker(Contract contract, LatestTicker latestTicker);
}
