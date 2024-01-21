package cc.newex.dax.push.service.impl.function.impl.perpetual;

import java.util.LinkedList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.cache.PushRedisCache;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;
import io.jsonwebtoken.lang.Collections;
import lombok.Builder;
import lombok.Data;

/**
 * kline
 *
 * @author xionghui
 * @date 2018/09/26
 */
public class PerpetualKlineFunctionServiceImpl implements FunctionService {
  private final PushRedisCache pushRedisCache;

  public PerpetualKlineFunctionServiceImpl(PushRedisCache pushRedisCache) {
    this.pushRedisCache = pushRedisCache;
  }

  @Override
  public boolean hasInit() {
    return true;
  }

  @Override
  public Object initReply(WebSocketBean webSocketBean, AskBean askBean, String params) {
    CacheKey cacheKey = CacheKey.builder().contractCode(askBean.getContractCode())
        .granularity(askBean.getGranularity()).build();
    LinkedList<JSONObject> data = new LinkedList<>();
    JSONArray redisData = this.pushRedisCache.getPerpetualKlines(cacheKey.getContractCode(),
        cacheKey.getGranularity());
    if (!Collections.isEmpty(redisData)) {
      for (Object obj : redisData) {
        data.add((JSONObject) obj);
      }
    }
    JSONArray resultData = new JSONArray();
    if (!Collections.isEmpty(data)) {
      JSONObject paramsJson = JSON.parseObject(params);
      long since = paramsJson.getLong("since");
      JSONArray tmpData = new JSONArray();
      synchronized (data) {
        for (int i = data.size() - 1; i >= 0; i--) {
          JSONObject json = data.get(i);
          long time = json.getDate("createdDate").getTime();
          // 最后一分钟数据可能有变动，所以time和since相同时需要更新, 至少传20条
          if (time < since && tmpData.size() >= 20) {
            break;
          }
          tmpData.add(json.clone());
        }
      }
      for (int i = tmpData.size() - 1; i >= 0; i--) {
        JSONObject json = tmpData.getJSONObject(i);
        resultData.add(this.buildObjs(json));
      }
    }
    return resultData;
  }

  private Object[] buildObjs(JSONObject json) {
    return new Object[] {json.getDate("createdDate").getTime(),
        BigDecimalSerializerUtil.serializer(json.getBigDecimal("low")),
        BigDecimalSerializerUtil.serializer(json.getBigDecimal("high")),
        BigDecimalSerializerUtil.serializer(json.getBigDecimal("open")),
        BigDecimalSerializerUtil.serializer(json.getBigDecimal("close")),
        BigDecimalSerializerUtil.serializer(json.getBigDecimal("amount")),
        BigDecimalSerializerUtil.serializer(json.getBigDecimal("size"))};
  }

  @Override
  public Object replyMessage(AskBean askBean, Object data) {
    JSONArray dataArray = (JSONArray) data;
    JSONArray resultData = new JSONArray();
    if (!Collections.isEmpty(dataArray)) {
      for (int i = 0; i < dataArray.size(); i++) {
        JSONObject json = dataArray.getJSONObject(i);
        resultData.add(this.buildObjs(json));
      }
    }
    return resultData;
  }

  /**
   * cache key
   *
   * @author xionghui
   * @date 2018/09/26
   */
  @Data
  @Builder
  private static class CacheKey {

    private String contractCode;
    private String granularity;
  }
}
