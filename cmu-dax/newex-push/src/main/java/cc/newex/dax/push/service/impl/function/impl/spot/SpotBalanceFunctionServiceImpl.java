package cc.newex.dax.push.service.impl.function.impl.spot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.cache.PushRedisCache;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;

public class SpotBalanceFunctionServiceImpl implements FunctionService {
  private final PushRedisCache pushRedisCache;

  public SpotBalanceFunctionServiceImpl(PushRedisCache pushRedisCache) {
    this.pushRedisCache = pushRedisCache;
  }

  @Override
  public List<ReplyBean> replyUserMessage(AskBean askBean, JSONArray data) {
    List<ReplyBean> replyBeanList = new ArrayList<>();
    for (Object obj : data) {
      JSONObject json = (JSONObject) obj;
      JSONObject message = new JSONObject();
      long userId = json.getLong("userId");
      message.put("userId", userId);
      BigDecimal available = json.getBigDecimal("available");
      message.put("available", BigDecimalSerializerUtil.serializer(available));
      message.put("withdrawLimit",
          BigDecimalSerializerUtil.serializer(json.getBigDecimal("withdrawLimit")));
      BigDecimal hold = json.getBigDecimal("hold");
      message.put("hold", BigDecimalSerializerUtil.serializer(hold));
      Integer currencyId = json.getInteger("currencyId");
      message.put("currencyId", currencyId);
      message.put("currencyCode", this.pushRedisCache.getCurrencySymbol(currencyId));
      message.put("balance", BigDecimalSerializerUtil.serializer(available.add(hold)));
      replyBeanList.add(ReplyBean.builder().userId(userId).message(message).build());
    }
    return replyBeanList;
  }
}
