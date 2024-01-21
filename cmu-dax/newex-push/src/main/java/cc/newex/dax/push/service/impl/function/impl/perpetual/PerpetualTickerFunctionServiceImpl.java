package cc.newex.dax.push.service.impl.function.impl.perpetual;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;

public class PerpetualTickerFunctionServiceImpl implements FunctionService {
  private Map<String, JSONObject> cacheMap = new ConcurrentHashMap<>();

  @Override
  public Object replyMessage(AskBean askBean, Object data) {
    JSONObject dataJson = (JSONObject) data;
    boolean change = this.cacheData(dataJson);
    return change ? null
        : new Object[][] {{dataJson.getDate("createdDate").getTime(),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("high")),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("low")),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("amount24")),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("size24")),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("first")),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("last")),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("change24")),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("changePercentage")),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("buy")),
            BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("sell")),
            dataJson.getString("contractCode")}};
  }

  private boolean cacheData(JSONObject dataJson) {
    JSONObject data = new JSONObject();
    data.put("high", BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("high")));
    data.put("low", BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("low")));
    data.put("amount24", BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("amount24")));
    data.put("size24", BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("size24")));
    data.put("first", BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("first")));
    data.put("last", BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("last")));
    data.put("change24", BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("change24")));
    data.put("changePercentage",
        BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("changePercentage")));
    data.put("buy", BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("buy")));
    data.put("sell", BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("sell")));
    JSONObject oldData = this.cacheMap.put(dataJson.getString("contractCode"), data);
    return data.equals(oldData);
  }
}
