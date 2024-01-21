package cc.newex.dax.push.service.impl.function.impl.spot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.newex.commons.dictionary.enums.SideTypeEnum;
import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.cache.PushRedisCache;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;
import io.jsonwebtoken.lang.Collections;
import lombok.Builder;
import lombok.Data;

/**
 * 最新成交
 *
 * @author xionghui
 * @date 2018/09/18
 */
public class SpotDealFunctionServiceImpl implements FunctionService {
  /**
   * 保存最后一条last
   */
  private final Map<CacheKey, Integer> lastMap = new ConcurrentHashMap<>();
  private final PushRedisCache pushRedisCache;

  public SpotDealFunctionServiceImpl(PushRedisCache pushRedisCache) {
    this.pushRedisCache = pushRedisCache;
  }

  @Override
  public boolean hasInit() {
    return true;
  }

  @Override
  public Object initReply(WebSocketBean webSocketBean, AskBean askBean, String params) {
    CacheKey cacheKey =
        CacheKey.builder().base(askBean.getBase()).quote(askBean.getQuote()).build();
    JSONArray data = this.pushRedisCache.getSpotDealList(cacheKey.getBase(), cacheKey.getQuote());
    if (!Collections.isEmpty(data)) {
      Integer last = this.lastMap.get(cacheKey);
      for (int i = 0; i < data.size(); i++) {
        JSONObject json = data.getJSONObject(i);
        int id = json.getInteger("id");
        if (last == null || id > last) {
          this.updateLast(cacheKey, id);
          last = id;
        }
      }
    }
    JSONArray resultData = new JSONArray();
    if (!Collections.isEmpty(data)) {
      for (int i = 0; i < data.size(); i++) {
        resultData.add(this.buildObjs(data.getJSONObject(i)));
      }
    }
    return resultData;
  }

  /**
   * 缓存最新的id
   */
  private void updateLast(CacheKey cacheKey, int id) {
    synchronized (this.lastMap) {
      Integer old = this.lastMap.get(cacheKey);
      if (old == null || old < id) {
        this.lastMap.put(cacheKey, id);
      }
    }
  }

  private Object[] buildObjs(JSONObject json) {
    return new Object[] {BigDecimalSerializerUtil.serializer(json.getBigDecimal("price")),
        BigDecimalSerializerUtil.serializer(json.getBigDecimal("amount")),
        SideTypeEnum.getTypeName(json.getByte("side")), json.getDate("createdDate").getTime(),
        json.getInteger("id")};
  }

  @Override
  public Object replyMessage(AskBean askBean, Object data) {
    JSONArray resultData = new JSONArray();
    JSONArray dataArray = (JSONArray) data;
    if (Collections.isEmpty(dataArray)) {
      return null;
    }
    CacheKey cacheKey =
        CacheKey.builder().base(askBean.getBase()).quote(askBean.getQuote()).build();
    Integer last = this.lastMap.get(cacheKey);
    for (Object obj : dataArray) {
      JSONObject json = (JSONObject) obj;
      int id = json.getInteger("id");
      if (last == null || id > last) {
        if (last == null || id > last) {
          this.updateLast(cacheKey, id);
          last = id;
        }
        resultData.add(this.buildObjs(json));
      }
    }
    return resultData;
  }

  /**
   * cache key
   *
   * @author xionghui
   * @date 2018/09/18
   */
  @Data
  @Builder
  private static class CacheKey {
    private String base;
    private String quote;
  }
}
