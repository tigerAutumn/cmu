package cc.newex.dax.push.service.impl.function.impl.perpetual;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;

public class PerpetualFundRateFunctionServiceImpl implements FunctionService {
  /**
   * 保存最后一条fundRate
   */
  private final Map<String, Object[]> fundRateMap = new ConcurrentHashMap<>();

  @Override
  public boolean hasInit() {
    return true;
  }

  @Override
  public Object initReply(WebSocketBean webSocketBean, AskBean askBean, String params) {
    Object[] data = this.fundRateMap.get(askBean.getContractCode().toLowerCase());
    return data == null ? new Object[] {askBean.getContractCode().toLowerCase(), "0", "0", "0", "0"}
        : data;
  }

  @Override
  public Object replyMessage(AskBean askBean, Object data) {
    JSONObject dataJson = (JSONObject) data;
    Object[] result = new Object[] {dataJson.getString("contractCode"),
        BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("markPrice")),
        BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("indexPrice")),
        BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("feeRate")),
        BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("estimateFeeRate"))};
    Object[] old = this.fundRateMap.put(askBean.getContractCode().toLowerCase(), result);
    if (old != null && old[1].equals(result[1]) && old[2].equals(result[2])
        && old[3].equals(result[3]) && old[4].equals(result[4])) {
      return null;
    }
    return result;
  }
}
