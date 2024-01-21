package cc.newex.dax.push.service.impl.function.impl.portfolio;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;

public class PortfolioOrderFunctionServiceImpl implements FunctionService {

  @Override
  public List<ReplyBean> replyUserMessage(AskBean askBean, JSONArray data) {
    List<ReplyBean> replyBeanList = new ArrayList<>();
    for (Object obj : data) {
      JSONObject json = (JSONObject) obj;
      JSONObject message = new JSONObject();
      message.put("orderId", json.getLong("id"));
      message.put("portfolioName", json.getString("portfolioName"));
      message.put("quoteCrrency", json.getString("quoteCrrency"));
      message.put("type", json.getString("type"));
      message.put("redeemType", json.getInteger("redeemType"));
      message.put("status", json.getInteger("status"));
      message.put("notes", json.getString("notes"));
      message.put("fee", json.getBigDecimal("fee"));
      message.put("price", BigDecimalSerializerUtil.serializer(json.getBigDecimal("price")));
      message.put("amount", BigDecimalSerializerUtil.serializer(json.getBigDecimal("amount")));
      message.put("dealAmount",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("dealAmount")));
      message.put("details", json.getString("details"));
      message.put("createdDate", json.getDate("createdDate"));
      message.put("modifyDate", json.getDate("modifyDate"));
      replyBeanList
          .add(ReplyBean.builder().userId(json.getLong("userId")).message(message).build());
    }
    return replyBeanList;
  }
}
