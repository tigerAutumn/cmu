package cc.newex.dax.push.service.impl.function.impl.perpetual;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;

public class PerpetualPositionFunctionServiceImpl implements FunctionService {

  @Override
  public List<ReplyBean> replyUserMessage(AskBean askBean, JSONArray data) {
    List<ReplyBean> replyBeanList = new ArrayList<>();
    for (Object obj : data) {
      JSONObject json = (JSONObject) obj;
      JSONObject message = new JSONObject();
      long userId = json.getLong("userId");
      message.put("id", json.getLong("id"));
      message.put("userId", userId);
      message.put("base", json.getString("base"));
      message.put("quote", json.getString("quote"));
      message.put("contractCode", json.getString("contractCode"));
      message.put("type", json.getInteger("type"));
      message.put("lever", BigDecimalSerializerUtil.serializer(json.getBigDecimal("lever")));
      message.put("gear", BigDecimalSerializerUtil.serializer(json.getBigDecimal("gear")));
      message.put("side", json.getString("side"));
      message.put("amount", BigDecimalSerializerUtil.serializer(json.getBigDecimal("amount")));
      message.put("closingAmount",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("closingAmount")));
      message.put("openMargin",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("openMargin")));
      message.put("maintenanceMargin",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("maintenanceMargin")));
      message.put("fee", BigDecimalSerializerUtil.serializer(json.getBigDecimal("fee")));
      message.put("price", BigDecimalSerializerUtil.serializer(json.getBigDecimal("price")));
      message.put("size", BigDecimalSerializerUtil.serializer(json.getBigDecimal("size")));
      message.put("preLiqudatePrice",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("preLiqudatePrice")));
      message.put("liqudatePrice",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("liqudatePrice")));
      message.put("brokerPrice",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("brokerPrice")));
      message.put("realizedSurplus",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("realizedSurplus")));
      message.put("orderMargin",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("orderMargin")));
      message.put("orderFee", BigDecimalSerializerUtil.serializer(json.getBigDecimal("orderFee")));
      message.put("orderLongAmount",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("orderLongAmount")));
      message.put("orderShortAmount",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("orderShortAmount")));
      message.put("orderLongSize",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("orderLongSize")));
      message.put("orderShortSize",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("orderShortSize")));
      message.put("brokerId", json.getInteger("brokerId"));
      message.put("createdDate", json.getDate("createdDate").getTime());
      replyBeanList.add(ReplyBean.builder().userId(userId).message(message).build());
    }
    return replyBeanList;
  }
}
