package cc.newex.dax.push.service.impl.function.impl.perpetual;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;

public class PerpetualTotalPositionFunctionServiceImpl implements FunctionService {
  private Map<String, BigDecimal> totalPositionMap = new ConcurrentHashMap<>();

  @Override
  public Object replyMessage(AskBean askBean, Object data) {
    JSONObject dataJson = (JSONObject) data;
    BigDecimal totalPosition = dataJson.getBigDecimal("totalPosition");
    BigDecimal oldTotalPosition =
        this.totalPositionMap.put(askBean.getContractCode().toLowerCase(), totalPosition);
    if (oldTotalPosition != null && oldTotalPosition.compareTo(totalPosition) == 0) {
      return null;
    }
    JSONObject result = new JSONObject();
    result.put("totalPosition", BigDecimalSerializerUtil.serializer(totalPosition));
    return result;
  }
}
