package cc.newex.dax.push.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import cc.newex.commons.dictionary.consts.CacheKeys;
import cc.newex.commons.dictionary.enums.KlineEnum;
import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.CurrencyPairBean;
import cc.newex.dax.push.bean.FunctionType;
import cc.newex.dax.push.bean.IndexCurrencyCodeBean;
import cc.newex.dax.push.bean.PerpetualCurrencyCodeBean;
import cc.newex.dax.push.bean.PortfolioCurrencyCodeBean;
import cc.newex.dax.push.bean.enums.BusinessTypeEnum;
import cc.newex.dax.push.service.ReplyService;
import cc.newex.dax.push.utils.ZipUtil;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;

/**
 * redis相关操作，包括接受redis订阅数据和查询redis缓存
 *
 * @author xionghui
 * @date 2018/09/20
 */
@Slf4j
@Service
public class PushRedisCache {
  // 订单和资产需要登录信息
  private static final Set<String> LOGIN_SET =
      new HashSet<>(Arrays.asList(FunctionType.ORDERS, FunctionType.CONDITION_ORDERS,
          FunctionType.ASSETS, FunctionType.POSITION, FunctionType.RANK));

  // 币种的id
  private final Map<Integer, JSONObject> currencyIdMap = new ConcurrentHashMap<>();
  // 币种的code
  private final Map<String, JSONObject> currencySymbolMap = new ConcurrentHashMap<>();

  // spot币对订阅信息
  private final Map<String, Topic> spotTopicMap = new ConcurrentHashMap<>();
  // 组合币对订阅信息
  private final Map<String, Topic> portfolioTopicMap = new ConcurrentHashMap<>();
  // 合约币对订阅信息
  private final Map<String, Topic> perpetualTopicMap = new ConcurrentHashMap<>();

  @Autowired
  private RedisMessageListenerContainer redisMessageListenerContainer;
  @Autowired
  private StringRedisTemplate redisTemplate;
  @Autowired
  private ApplicationContext applicationContext;

  /**
   * 接受消息的入口
   */
  private final MessageListener listener = (message, pattern) -> {
    String body = new String(message.getBody());
    JSONObject bodyJson = JSON.parseObject(body);
    AskBean askBean = AskBean.builder().biz(bodyJson.getString("biz"))
        .type(bodyJson.getString("type")).contractCode(bodyJson.getString("contractCode"))
        .base(bodyJson.getString("base")).quote(bodyJson.getString("quote"))
        .granularity(bodyJson.getString("granularity")).build();
    if (askBean.getBiz() != null) {
      askBean.setBiz(askBean.getBiz().toLowerCase());
    }
    if (askBean.getType() != null) {
      askBean.setType(askBean.getType().toLowerCase());
      if (FunctionType.TICKER.equals(askBean.getType())
          && BusinessTypeEnum.SPOT.getType().equals(askBean.getBiz())) {
        askBean.setType("tickers");
        askBean.setBase(null);
        askBean.setQuote(null);
        askBean.setGranularity(null);
      } else if ("order".equals(askBean.getType())) {
        askBean.setType("orders");
      } else if ("condition_order".equals(askBean.getType())) {
        askBean.setType("condition_orders");
      } else if ("asset".equals(askBean.getType())) {
        askBean.setType("assets");
      }
    }
    if (BusinessTypeEnum.INDEXES.getType().equalsIgnoreCase(askBean.getBiz())
        && FunctionType.CANDLES.equalsIgnoreCase(askBean.getType())
        && askBean.getGranularity() == null) {
      askBean.setGranularity("15min");
    }
    if (askBean.getContractCode() != null) {
      askBean.setContractCode(askBean.getContractCode().toLowerCase());
    }
    if (askBean.getBase() != null) {
      askBean.setBase(askBean.getBase().toLowerCase());
    }
    if (askBean.getQuote() != null) {
      askBean.setQuote(askBean.getQuote().toLowerCase());
    }
    if (askBean.getGranularity() != null) {
      askBean.setGranularity(askBean.getGranularity().toLowerCase());
    }
    Object data = bodyJson.get("data");
    Boolean zip = bodyJson.getBoolean("zip");
    if (zip != null && zip) {
      data = JSON.parseObject(ZipUtil.unzip(String.valueOf(data)), Object.class);
    }
    log.info("RedisCache onMessage askBean: {}, data: {}", askBean, data);
    ReplyService replyService =
        this.applicationContext.getBean(askBean.getBiz(), ReplyService.class);
    if (loginCheck(askBean.getType())) {
      replyService.replyUser(askBean, data);
    } else {
      replyService.reply(askBean, data);
    }
  };

  @PostConstruct
  public void init() {
    // 初始化币种信息
    this.refreshCurrency();
    // 现货，按照币对来订阅
    this.updateSpotCurrencypairMessageListener();
    // 指数
    final List<Topic> indexTopicList = this.fetchIndexCurrencyCodeTopic();
    if (!Collections.isEmpty(indexTopicList)) {
      this.redisMessageListenerContainer.addMessageListener(this.listener, indexTopicList);
    }
    // 组合
    this.updatePortfolioCurrencypairMessageListener();
    // 合约
    this.updatePerpetualMessageListener();
  }

  /**
   * 检查是否需要区分登录
   */
  public static boolean loginCheck(final String type) {
    return LOGIN_SET.contains(type);
  }

  /**
   * 更新spot币对订阅信息
   */
  public boolean updateSpotCurrencypairMessageListener() {
    boolean changed = false;
    final Map<String, Topic> newTopicMap = this.fetchSpotTopic();
    final Map<String, Topic> topicMap = new HashMap<>(this.spotTopicMap);
    for (Map.Entry<String, Topic> entry : topicMap.entrySet()) {
      if (newTopicMap.remove(entry.getKey()) == null) {
        this.redisMessageListenerContainer.removeMessageListener(null, entry.getValue());
        this.spotTopicMap.remove(entry.getKey());
        changed = true;
      }
    }
    if (newTopicMap.size() > 0) {
      this.redisMessageListenerContainer.addMessageListener(this.listener, newTopicMap.values());
      this.spotTopicMap.putAll(newTopicMap);
      changed = true;
    }
    return changed;
  }

  /**
   * 获取币对
   */
  private Map<String, Topic> fetchSpotTopic() {
    final Map<String, Topic> topicMap = new HashMap<>();
    final Object currencyPair = this.redisTemplate.opsForValue().get("CURRENCYPAIR");
    final List<CurrencyPairBean> currencyPairBeanList =
        JSONObject.parseObject(currencyPair == null ? null : currencyPair.toString(),
            new TypeReference<List<CurrencyPairBean>>() {});
    if (!Collections.isEmpty(currencyPairBeanList)) {
      for (final CurrencyPairBean currencyPairBean : currencyPairBeanList) {
        if (currencyPairBean.getOnline() == null || currencyPairBean.getOnline() != ((byte) 1)) {
          continue;
        }
        final String channel = String.format("%s_%s", BusinessTypeEnum.SPOT.getType().toUpperCase(),
            currencyPairBean.getCode().toUpperCase());
        topicMap.put(channel, new ChannelTopic(channel));
      }
    }
    log.info("RedisCache fetchSpotTopic topicMap: {}", topicMap);
    return topicMap;
  }

  /**
   * 获取指数的币种
   */
  private List<Topic> fetchIndexCurrencyCodeTopic() {
    final List<Topic> topicList = new ArrayList<>();
    final Object indexCurrencyCode = this.redisTemplate.opsForValue().get("redis_key_coin_config");
    final List<IndexCurrencyCodeBean> indexCurrencyCodeList =
        JSONObject.parseObject(indexCurrencyCode == null ? null : indexCurrencyCode.toString(),
            new TypeReference<List<IndexCurrencyCodeBean>>() {});
    if (!Collections.isEmpty(indexCurrencyCodeList)) {
      for (final IndexCurrencyCodeBean indexCurrencyCodeBean : indexCurrencyCodeList) {
        final String channel =
            String.format("%s_%s", BusinessTypeEnum.INDEXES.getType().toUpperCase(),
                indexCurrencyCodeBean.getSymbolName().toUpperCase());
        topicList.add(new ChannelTopic(channel));
      }
    }
    log.info("RedisCache fetchIndexCurrencyCodeTopic topicList: {}", topicList);
    return topicList;
  }

  /**
   * 刷新组合的订阅
   */
  public boolean updatePortfolioCurrencypairMessageListener() {
    boolean changed = false;
    final Map<String, Topic> newTopicMap = this.fetchPortfolioTopic();
    final Map<String, Topic> topicMap = new HashMap<>(this.portfolioTopicMap);
    for (Map.Entry<String, Topic> entry : topicMap.entrySet()) {
      if (newTopicMap.remove(entry.getKey()) == null) {
        this.redisMessageListenerContainer.removeMessageListener(null, entry.getValue());
        this.portfolioTopicMap.remove(entry.getKey());
        changed = true;
      }
    }
    if (newTopicMap.size() > 0) {
      this.redisMessageListenerContainer.addMessageListener(this.listener, newTopicMap.values());
      this.portfolioTopicMap.putAll(newTopicMap);
      changed = true;
    }
    return changed;
  }

  /**
   * 获取组合投资
   */
  private Map<String, Topic> fetchPortfolioTopic() {
    final Map<String, Topic> topicMap = new HashMap<>();
    final Object portfolioCurrencyCode =
        this.redisTemplate.opsForValue().get("redis_key_portfolio_coin_config");
    final List<PortfolioCurrencyCodeBean> portfolioCurrencyCodeList = JSONObject.parseObject(
        portfolioCurrencyCode == null ? null : portfolioCurrencyCode.toString(),
        new TypeReference<List<PortfolioCurrencyCodeBean>>() {});
    if (!Collections.isEmpty(portfolioCurrencyCodeList)) {
      for (final PortfolioCurrencyCodeBean portfolioCurrencyCodeBean : portfolioCurrencyCodeList) {
        final String channel =
            String.format("%s_%s", BusinessTypeEnum.PORTFOLIO.getType().toUpperCase(),
                portfolioCurrencyCodeBean.getSymbolName().toUpperCase());
        topicMap.put(channel, new ChannelTopic(channel));
      }
    }
    log.info("RedisCache fetchPortfolioTopic topicMap: {}", topicMap);
    return topicMap;
  }

  /**
   * 刷新合约的订阅
   */
  public boolean updatePerpetualMessageListener() {
    boolean changed = false;
    final Map<String, Topic> newTopicMap = this.fetchPerpetualTopic();
    final Map<String, Topic> topicMap = new HashMap<>(this.perpetualTopicMap);
    for (Map.Entry<String, Topic> entry : topicMap.entrySet()) {
      if (newTopicMap.remove(entry.getKey()) == null) {
        this.redisMessageListenerContainer.removeMessageListener(null, entry.getValue());
        this.perpetualTopicMap.remove(entry.getKey());
        changed = true;
      }
    }
    if (newTopicMap.size() > 0) {
      this.redisMessageListenerContainer.addMessageListener(this.listener, newTopicMap.values());
      this.perpetualTopicMap.putAll(newTopicMap);
      changed = true;
    }
    return changed;
  }

  /**
   * 获取合约币对
   */
  private Map<String, Topic> fetchPerpetualTopic() {
    final Map<String, Topic> topicMap = new HashMap<>();
    final Object portfolioCurrencyCode =
        this.redisTemplate.opsForValue().get("PERPETUAL_CURRENCY_PAIR");
    final List<PerpetualCurrencyCodeBean> portfolioCurrencyCodeList = JSONObject.parseObject(
        portfolioCurrencyCode == null ? null : portfolioCurrencyCode.toString(),
        new TypeReference<List<PerpetualCurrencyCodeBean>>() {});
    if (!Collections.isEmpty(portfolioCurrencyCodeList)) {
      for (final PerpetualCurrencyCodeBean portfolioCurrencyCodeBean : portfolioCurrencyCodeList) {
        final String channel =
            String.format("%s_%s", BusinessTypeEnum.PERPETUAL.getType().toUpperCase(),
                portfolioCurrencyCodeBean.getPairCode().toUpperCase());
        topicMap.put(channel, new ChannelTopic(channel));
      }
    }
    log.info("RedisCache fetchPerpetualTopic topicMap: {}", topicMap);
    return topicMap;
  }

  /**
   * 刷新币种信息，会移除删除的数据
   */
  public void refreshCurrency() {
    final Object currencyData = this.redisTemplate.opsForValue().get("CURRENCY");
    log.info("RedisCache refreshCurrency currencyData: {}", currencyData);
    final JSONArray dataArray =
        JSON.parseArray(currencyData == null ? null : currencyData.toString());
    if (Collections.isEmpty(dataArray)) {
      this.currencyIdMap.clear();
      this.currencySymbolMap.clear();
      return;
    }
    Map<Integer, JSONObject> idMap = new HashMap<>();
    Map<String, JSONObject> symbolMap = new HashMap<>();
    for (final Object obj : dataArray) {
      final JSONObject json = (JSONObject) obj;
      idMap.put(json.getInteger("id"), json);
      symbolMap.put(json.getString("symbol"), json);
    }
    for (Integer key : this.currencyIdMap.keySet()) {
      if (idMap.remove(key) == null) {
        this.currencyIdMap.remove(key);
      }
    }
    for (String key : this.currencySymbolMap.keySet()) {
      if (symbolMap.remove(key) == null) {
        this.currencySymbolMap.remove(key);
      }
    }
    this.currencyIdMap.putAll(idMap);
    this.currencySymbolMap.putAll(symbolMap);
  }

  /**
   * 根据币种id查询币种编码
   */
  public String getCurrencySymbol(final Integer currencyId) {
    JSONObject json = this.currencyIdMap.get(currencyId);
    if (json == null) {
      log.warn("PushRedisCache getCurrencySymbol null by {}", currencyId);
      // refresh
      this.refreshCurrency();
      json = this.currencyIdMap.get(currencyId);
    }
    return json == null ? null : json.getString("symbol");
  }

  /**
   * 根据币种code查询币种信息
   */
  public JSONObject getCurrency(final String symbol) {
    JSONObject jsonObject = this.currencySymbolMap.get(symbol);
    if (jsonObject == null) {
      log.warn("PushRedisCache getCurrency null by {}", symbol);
      // refresh
      this.refreshCurrency();
      jsonObject = this.currencySymbolMap.get(symbol);
    }
    return jsonObject == null ? null : (JSONObject) jsonObject.clone();
  }

  /**
   * 查询kline
   */
  public JSONArray getSpotKlines(String base, String quote, String period) {
    final Integer marketFrom = this.getMarketFrom(base, quote);
    final Integer typeInteger = KlineEnum.getTypeInteger(period);
    final Object marketData =
        this.redisTemplate.opsForValue().get("MARKET_" + marketFrom + "_" + typeInteger);
    log.info("RedisCache getSpotKlines marketData: {}", marketData);
    return JSON.parseArray(marketData == null ? null : marketData.toString());
  }

  private Integer getMarketFrom(String base, String quote) {
    final Object currencyPairData = this.redisTemplate.opsForValue().get("CURRENCYPAIR");
    log.info("RedisCache getMarketFrom currencyPairData: {}", currencyPairData);
    final JSONArray dataArray =
        JSON.parseArray(currencyPairData == null ? null : currencyPairData.toString());
    if (!Collections.isEmpty(dataArray)) {
      final String symbol = String.format("%s_%s", base, quote);
      for (final Object obj : dataArray) {
        final JSONObject json = (JSONObject) obj;
        if (((Byte) ((byte) 1)).equals(json.getByte("online"))
            && symbol.equals(json.getString("code"))) {
          return json.getInteger("marketId");
        }
      }
    }
    return null;
  }

  /**
   * 查询永续的kline
   */
  public JSONArray getPerpetualKlines(String contractCode, String period) {
    String klineKey = new StringBuilder().append(BusinessTypeEnum.PERPETUAL.getType())
        .append(CacheKeys.DELIMITER).append(CacheKeys.MARKET_PREFIX).append(CacheKeys.DELIMITER)
        .append(contractCode).append(CacheKeys.DELIMITER).append(period).toString().toUpperCase();
    final String datas = this.redisTemplate.opsForValue().get(klineKey);
    log.info("RedisCache getPerpetualKlines datas: {}", datas);
    return JSON.parseArray(datas);
  }

  /**
   * 查询spot最新成交
   */
  public JSONArray getSpotDealList(String base, String quote) {
    final String symbol = String.format("%s_%s", base, quote);
    final Object dealData = this.redisTemplate.opsForValue().get("DEAL_" + symbol);
    log.info("RedisCache getSpotDealList dealData: {}", dealData);
    return JSON.parseArray(dealData == null ? null : dealData.toString());
  }

  /**
   * 查询perpetual最新成交
   */
  public JSONArray getPerpetualDealList(String contractCode) {
    final Object dealData =
        this.redisTemplate.opsForValue().get("PERPETUAL_DEAL_" + contractCode.toUpperCase());
    log.info("RedisCache getSpotDealList dealData: {}", dealData);
    return JSON.parseArray(dealData == null ? null : dealData.toString());
  }

  /**
   * 查询spot深度
   */
  public JSONObject getSpotDepth(String base, String quote) {
    final String symbol = String.format("%s_%s", base, quote);
    final Object dealData = this.redisTemplate.opsForValue().get("DEPTH_" + symbol);
    log.info("RedisCache getSpotDepth dealData: {}", dealData);
    return JSON.parseObject(dealData == null ? null : dealData.toString());
  }

  /**
   * 查询perpetual深度
   */
  public JSONObject getPerpetualDepth(String contractCode) {
    final Object dealData = this.redisTemplate.opsForValue().get("PERPETUAL_DEPTH_" + contractCode);
    log.info("RedisCache getPerpetualDepth dealData: {}", dealData);
    return JSON.parseObject(dealData == null ? null : dealData.toString());
  }

  /**
   * 单个ip最大的连接数
   */
  public String getMaxIpCount() {
    final Object ipData = this.redisTemplate.opsForValue().get("PUSH_MAX_IP");
    log.info("RedisCache getMaxIpCount ipData: {}", ipData);
    return (String) ipData;
  }

  /**
   * 单个session最大的订阅数
   */
  public String getMaxSessionCount() {
    final Object sessionData = this.redisTemplate.opsForValue().get("PUSH_MAX_SESSION");
    log.info("RedisCache getMaxSessionCount sessionData: {}", sessionData);
    return (String) sessionData;
  }
}
