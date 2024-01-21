package cc.newex.dax.push.task;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.FunctionType;
import cc.newex.dax.push.cache.PushCache;
import cc.newex.dax.push.cache.PushRedisCache;
import cc.newex.dax.push.service.ReplyService;
import lombok.extern.slf4j.Slf4j;

/**
 * 币种、币对刷新和监听服务
 */
@Slf4j
@Component
public class CurrencyAndPairTask {
  @Autowired
  private PushRedisCache pushRedisCache;
  @Autowired
  private ApplicationContext applicationContext;

  @PostConstruct
  public void init() {
    this.refreshMaxConfig();
  }

  /**
   * 刷新组合币对监听
   */
  private void refreshMaxConfig() {
    log.info("CurrencyAndPairTask refreshMaxConfig begin");
    try {
      String maxIpStr = this.pushRedisCache.getMaxIpCount();
      Integer maxIp = null;
      if (maxIpStr != null) {
        try {
          maxIp = Integer.valueOf(maxIpStr);
        } catch (final NumberFormatException ex) {
          // ignore it
        }
      }
      String maxSessionStr = this.pushRedisCache.getMaxSessionCount();
      Integer maxSession = null;
      if (maxSessionStr != null) {
        try {
          maxSession = Integer.valueOf(maxSessionStr);
        } catch (final NumberFormatException ex) {
          // ignore it
        }
      }
      PushCache.setIpSessionAtomic(maxIp, maxSession);
      log.info("CurrencyAndPairTask refreshMaxConfig end");
    } catch (final Exception e) {
      log.error("CurrencyAndPairTask refreshMaxConfig error: ", e);
    }
  }

  /**
   * 更新组合币对监听
   */
  @Scheduled(initialDelay = 60_000, fixedDelay = 60_000)
  public void updateMaxConfig() {
    this.refreshMaxConfig();
  }

  /**
   * 刷新币种
   */
  @Scheduled(initialDelay = 60_000, fixedDelay = 60_000)
  private void updateCurrency() {
    log.info("CurrencyAndPairTask updateCurrency begin");
    try {
      this.pushRedisCache.refreshCurrency();
    } catch (Exception e) {
      log.error("CurrencyAndPairTask updateCurrency error: ", e);
    }
    log.info("CurrencyAndPairTask updateCurrency end");
  }

  /**
   * 刷新spot币对监听
   */
  @Scheduled(initialDelay = 1_000, fixedDelay = 1_000)
  private void updateSpotCurrencyPairChannel() {
    log.info("CurrencyAndPairTask updateSpotCurrencyPairChannel begin");
    try {
      boolean changed = this.pushRedisCache.updateSpotCurrencypairMessageListener();
      if (changed) {
        JSONObject data = new JSONObject();
        data.put("data", true);

        AskBean askBean = AskBean.builder().biz("spot").type(FunctionType.CURRENCY_PAIR).build();
        ReplyService replyService =
            this.applicationContext.getBean(askBean.getBiz(), ReplyService.class);
        replyService.reply(askBean, data);
      }
      log.info("CurrencyAndPairTask updateSpotCurrencyPairChannel end");
    } catch (Exception e) {
      log.error("CurrencyAndPairTask updateSpotCurrencyPairChannel error: ", e);
    }
  }

  /**
   * 刷新组合币对监听
   */
  @Scheduled(initialDelay = 1_000, fixedDelay = 1_000)
  private void updatePortfolioCurrencyPairChannel() {
    log.info("CurrencyAndPairTask updatePortfolioCurrencyPairChannel begin");
    try {
      this.pushRedisCache.updatePortfolioCurrencypairMessageListener();
      log.info("CurrencyAndPairTask updatePortfolioCurrencyPairChannel end");
    } catch (Exception e) {
      log.error("CurrencyAndPairTask updatePortfolioCurrencyPairChannel error: ", e);
    }
  }

  /**
   * 刷新合约币对监听
   */
  @Scheduled(initialDelay = 1_000, fixedDelay = 1_000)
  private void updatePerpetualCurrencyPairChannel() {
    log.info("CurrencyAndPairTask updatePerpetualCurrencyPairChannel begin");
    try {
      this.pushRedisCache.updatePerpetualMessageListener();
      log.info("CurrencyAndPairTask updatePerpetualCurrencyPairChannel end");
    } catch (Exception e) {
      log.error("CurrencyAndPairTask updatePerpetualCurrencyPairChannel error: ", e);
    }
  }
}
