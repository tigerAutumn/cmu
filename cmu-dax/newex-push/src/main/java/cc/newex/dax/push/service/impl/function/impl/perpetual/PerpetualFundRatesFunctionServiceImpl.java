package cc.newex.dax.push.service.impl.function.impl.perpetual;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;

public class PerpetualFundRatesFunctionServiceImpl implements FunctionService {

  @Override
  public Object replyMessage(AskBean askBean, Object data) {
    JSONArray resultData = new JSONArray();
    JSONArray dataArray = (JSONArray) data;
    for (Object obj : dataArray) {
      JSONObject dataJson = (JSONObject) obj;
      resultData.add(new Object[] {dataJson.getString("contractCode"),
          BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("markPrice")),
          BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("indexPrice")),
          BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("feeRate")),
          BigDecimalSerializerUtil.serializer(dataJson.getBigDecimal("estimateFeeRate"))});
    }
    return resultData;
  }
}
