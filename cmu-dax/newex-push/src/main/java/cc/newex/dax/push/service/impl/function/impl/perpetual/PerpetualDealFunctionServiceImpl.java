package cc.newex.dax.push.service.impl.function.impl.perpetual;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.cache.PushRedisCache;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;
import io.jsonwebtoken.lang.Collections;

/**
 * 最新成交
 *
 * @author xionghui
 * @date 2018/09/18
 */
public class PerpetualDealFunctionServiceImpl implements FunctionService {
  /**
   * 保存最后一条last
   */
  private final Map<String, Integer> lastMap = new ConcurrentHashMap<>();
  private final PushRedisCache pushRedisCache;

  public PerpetualDealFunctionServiceImpl(PushRedisCache pushRedisCache) {
    this.pushRedisCache = pushRedisCache;
  }

  @Override
  public boolean hasInit() {
    return true;
  }

  @Override
  public Object initReply(WebSocketBean webSocketBean, AskBean askBean, String params) {
    String contractCode = askBean.getContractCode().toLowerCase();
    JSONArray data = this.pushRedisCache.getPerpetualDealList(contractCode);
    if (!Collections.isEmpty(data)) {
      Integer last = this.lastMap.get(contractCode);
      for (int i = 0; i < data.size(); i++) {
        JSONObject json = data.getJSONObject(i);
        int id = json.getInteger("id");
        if (last == null || id > last) {
          this.updateLast(contractCode, id);
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
  private void updateLast(String contractCode, int id) {
    synchronized (this.lastMap) {
      Integer old = this.lastMap.get(contractCode);
      if (old == null || old < id) {
        this.lastMap.put(contractCode, id);
      }
    }
  }

  private Object[] buildObjs(JSONObject json) {
    return new Object[] {BigDecimalSerializerUtil.serializer(json.getBigDecimal("price")),
        BigDecimalSerializerUtil.serializer(json.getBigDecimal("amount")), json.getString("side"),
        json.getDate("createdDate").getTime(), json.getLong("id")};
  }

  @Override
  public Object replyMessage(AskBean askBean, Object data) {
    JSONArray resultData = new JSONArray();
    JSONArray dataArray = (JSONArray) data;
    if (Collections.isEmpty(dataArray)) {
      return null;
    }
    String contractCode = askBean.getContractCode().toLowerCase();
    Integer last = this.lastMap.get(contractCode);
    for (int i = dataArray.size() - 1; i >= 0; i--) {
      JSONObject json = dataArray.getJSONObject(i);
      int id = json.getInteger("id");
      if (last == null || id > last) {
        if (last == null || id > last) {
          this.updateLast(contractCode, id);
          last = id;
        }
        resultData.add(this.buildObjs(json));
      }
    }
    return resultData;
  }
}
