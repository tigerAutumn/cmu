package cc.newex.dax.push.service.impl.function.impl.spot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;

public class SpotTickerFunctionServiceImpl implements FunctionService {
  private Map<String, JSONObject> cacheMap = new ConcurrentHashMap<>();

  @Override
  public Object replyMessage(AskBean askBean, Object data) {
    JSONObject dataJson = (JSONObject) data;
    boolean change = this.cacheData(dataJson);
    return change ? null
        : new Object[][] {{dataJson.getDate("createdDate").getTime(),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("high")),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("low")),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("last")),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("volume")),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("quoteVolume")),
            dataJson.getString("change"), dataJson.getString("changePercentage"),
            dataJson.getString("symbol"), dataJson.getInteger("currencyId")}};
  }

  private boolean cacheData(JSONObject dataJson) {
    JSONObject data = new JSONObject();
    data.put("high", BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("high")));
    data.put("low", BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("low")));
    data.put("last", BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("last")));
    data.put("volume", BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("volume")));
    data.put("quoteVolume",
        BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("quoteVolume")));
    data.put("change", dataJson.getString("change"));
    data.put("changePercentage", dataJson.getString("changePercentage"));
    JSONObject oldData = this.cacheMap.put(dataJson.getString("symbol"), data);
    return data.equals(oldData);
  }
}
