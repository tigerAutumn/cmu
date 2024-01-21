package cc.newex.dax.push.service.impl.function.impl.spot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.newex.commons.dictionary.enums.OrderStatusEnEnum;
import cc.newex.commons.dictionary.enums.OrderTypeEnum;
import cc.newex.commons.dictionary.enums.SideTypeEnum;
import cc.newex.commons.dictionary.enums.SourceEnum;
import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;

public class SpotOrderFunctionServiceImpl implements FunctionService {

  @Override
  public List<ReplyBean> replyUserMessage(AskBean askBean, JSONArray data) {
    List<ReplyBean> replyBeanList = new ArrayList<>();
    for (Object obj : data) {
      JSONObject json = (JSONObject) obj;
      JSONObject message = new JSONObject();
      long userId = json.getLong("userId");
      String productId = json.getString("productId");
      message.put("id", productId != null ? Integer.valueOf(productId) : -1);
      message.put("code", json.getString("symbol"));
      message.put("orderId", json.getLong("id"));
      message.put("userId", userId);
      Byte side = json.getByte("side");
      message.put("side", SideTypeEnum.getTypeName(side));
      message.put("price", json.getString("price"));
      BigDecimal size = BigDecimal.ZERO;
      try {
        size = new BigDecimal(OrderTypeEnum.MARKET.getType().equals(json.getByte("orderType"))
            && SideTypeEnum.BUY.getType().equals(side) ? json.getString("quoteSize")
                : json.getString("size"));
      } catch (final Exception e) {
        // ignore
      }
      message.put("volume", BigDecimalSerializerUtil.serializer(size));
      String filledSizeStr = json.getString("filledSize");
      BigDecimal filledSize =
          filledSizeStr == null ? BigDecimal.ZERO : new BigDecimal(filledSizeStr);
      message.put("filledVolume",
          filledSizeStr == null ? null : BigDecimalSerializerUtil.serializer(filledSize));
      message.put("orderType", OrderTypeEnum.getTypeName(json.getByte("orderType")));
      message.put("status", OrderStatusEnEnum.getTypeName(json.getByte("status")));
      Byte source = json.getByte("source");
      message.put("source", SourceEnum.getTypeName(source != null ? source : (byte) 0));
      String executedValueStr = json.getString("executedValue");
      BigDecimal executedValue =
          executedValueStr == null ? BigDecimal.ZERO : new BigDecimal(executedValueStr);
      message.put("executedVolume",
          executedValueStr == null ? null : BigDecimalSerializerUtil.serializer(executedValue));
      Date createTime = json.getDate("createTime");
      message.put("createdDate", createTime == null ? 0 : createTime.getTime());
      BigDecimal averagePrice = BigDecimal.ZERO;
      BigDecimal trunOver = BigDecimal.ZERO;
      if (filledSize != null && executedValue != null && filledSize.compareTo(BigDecimal.ZERO) != 0
          && executedValue.compareTo(BigDecimal.ZERO) != 0) {
        averagePrice = executedValue.divide(filledSize, 8, BigDecimal.ROUND_HALF_UP);
        trunOver = filledSize.multiply(executedValue);
      }
      message.put("trunoverVolume", BigDecimalSerializerUtil.serializer(trunOver));
      message.put("openVolume", BigDecimalSerializerUtil.serializer(size.subtract(filledSize)));
      message.put("averagePrice", BigDecimalSerializerUtil.serializer(averagePrice));
      replyBeanList.add(ReplyBean.builder().userId(userId).message(message).build());
    }
    return replyBeanList;
  }
}
