package cc.newex.dax.push.service.impl.function.impl.portfolio;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;

public class PortfolioBalanceFunctionServiceImpl implements FunctionService {

  @Override
  public List<ReplyBean> replyUserMessage(AskBean askBean, JSONArray data) {
    List<ReplyBean> replyBeanList = new ArrayList<>();
    for (Object obj : data) {
      JSONObject json = (JSONObject) obj;
      JSONObject message = new JSONObject();
      long userId = json.getLong("userId");
      message.put("currencyId", json.getInteger("currencyId"));
      message.put("currencyCode", json.getString("currencyCode"));
      message.put("totalBalance",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("balance")));
      message.put("valuation",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("valuation")));
      replyBeanList.add(ReplyBean.builder().userId(userId).message(message).build());
    }
    return replyBeanList;
  }
}
