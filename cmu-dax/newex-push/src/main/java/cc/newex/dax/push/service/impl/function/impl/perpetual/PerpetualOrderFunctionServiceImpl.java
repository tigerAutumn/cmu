package cc.newex.dax.push.service.impl.function.impl.perpetual;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;

public class PerpetualOrderFunctionServiceImpl implements FunctionService {

  @Override
  public List<ReplyBean> replyUserMessage(AskBean askBean, JSONArray data) {
    List<ReplyBean> replyBeanList = new ArrayList<>();
    for (Object obj : data) {
      JSONObject json = (JSONObject) obj;
      JSONObject message = new JSONObject();
      long userId = json.getLong("userId");
      message.put("id", json.getLong("id"));
      message.put("orderId", json.getLong("orderId"));
      message.put("contractCode", json.getString("contractCode"));
      message.put("contractDirection", json.getInteger("contractDirection"));
      message.put("base", json.getString("base"));
      message.put("quote", json.getString("quote"));
      message.put("userId", userId);
      message.put("side", json.getString("side"));
      message.put("detailSide", json.getString("detailSide"));
      message.put("clazz", json.getInteger("clazz"));
      message.put("mustMaker", json.getInteger("mustMaker"));
      message.put("amount", BigDecimalSerializerUtil.serializer(json.getBigDecimal("amount")));
      message.put("price", BigDecimalSerializerUtil.serializer(json.getBigDecimal("price")));
      message.put("avgPrice", BigDecimalSerializerUtil.serializer(json.getBigDecimal("avgPrice")));
      message.put("dealAmount",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("dealAmount")));
      message.put("orderSize", json.getString("size"));
      message.put("dealSize", json.getString("dealSize"));
      message.put("openMargin", json.getString("openMargin"));
      message.put("extraMargin", json.getString("extraMargin"));
      message.put("avgMargin", json.getString("avgMargin"));
      message.put("status", json.getInteger("status"));
      message.put("systemType", json.getInteger("systemType"));
      message.put("profit", BigDecimalSerializerUtil.serializer(json.getBigDecimal("profit")));
      message.put("fee", BigDecimalSerializerUtil.serializer(json.getBigDecimal("fee")));
      message.put("reason", json.getInteger("reason"));
      message.put("createdDate", json.getDate("createdDate").getTime());
      replyBeanList.add(ReplyBean.builder().userId(userId).message(message).build());
    }
    return replyBeanList;
  }
}
