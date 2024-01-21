package cc.newex.dax.push.service.impl.function.impl.perpetual;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;

public class PerpetualBalanceFunctionServiceImpl implements FunctionService {

  @Override
  public List<ReplyBean> replyUserMessage(AskBean askBean, JSONArray data) {
    List<ReplyBean> replyBeanList = new ArrayList<>();
    for (Object obj : data) {
      JSONObject json = (JSONObject) obj;
      JSONObject message = new JSONObject();
      long userId = json.getLong("userId");
      message.put("userId", userId);
      BigDecimal availableBalance = json.getBigDecimal("availableBalance");
      message.put("availableBalance", BigDecimalSerializerUtil.serializer(availableBalance));
      BigDecimal frozenBalance = json.getBigDecimal("frozenBalance");
      message.put("frozenBalance", BigDecimalSerializerUtil.serializer(frozenBalance));
      BigDecimal orderMargin = json.getBigDecimal("orderMargin");
      message.put("orderMargin", BigDecimalSerializerUtil.serializer(orderMargin));
      BigDecimal orderFee = json.getBigDecimal("orderFee");
      message.put("orderFee", BigDecimalSerializerUtil.serializer(orderFee));
      BigDecimal positionMargin = json.getBigDecimal("positionMargin");
      message.put("positionMargin", BigDecimalSerializerUtil.serializer(positionMargin));
      BigDecimal positionFee = json.getBigDecimal("positionFee");
      message.put("positionFee", BigDecimalSerializerUtil.serializer(positionFee));
      BigDecimal realizedSurplus = json.getBigDecimal("realizedSurplus");
      message.put("realizedSurplus", BigDecimalSerializerUtil.serializer(realizedSurplus));
      message.put("total_balance",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("totalBalance")));
      message.put("currencyCode", json.getString("currencyCode"));
      replyBeanList.add(ReplyBean.builder().userId(userId).message(message).build());
    }
    return replyBeanList;
  }
}
