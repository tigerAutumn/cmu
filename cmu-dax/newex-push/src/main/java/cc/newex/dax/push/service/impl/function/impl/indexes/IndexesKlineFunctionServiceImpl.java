package cc.newex.dax.push.service.impl.function.impl.indexes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.client.FeignMarketServiceClient;
import cc.newex.dax.push.service.impl.function.FunctionService;
import io.jsonwebtoken.lang.Collections;

public class IndexesKlineFunctionServiceImpl implements FunctionService {
  private static final int CACHE_SET_SIZE = 120;

  private final Map<String, LinkedList<JSONArray>> klineCache = new ConcurrentHashMap<>();
  private final FeignMarketServiceClient marketServiceClient;

  public IndexesKlineFunctionServiceImpl(final FeignMarketServiceClient marketServiceClient) {
    this.marketServiceClient = marketServiceClient;
  }

  @Override
  public boolean hasInit() {
    return true;
  }

  @Override
  public Object initReply(WebSocketBean webSocketBean, AskBean askBean, String params) {
    final String key = this.formatKey(askBean);
    final JSONObject jsonObject = JSON.parseObject(params);
    final Long since = jsonObject.getLong("since");
    List<JSONArray> data = this.klineCache.get(key);
    if (data == null) {
      synchronized (this) {
        data = this.klineCache.get(key);
        if (data == null) {
          ResponseResult<?> responseResult = this.marketServiceClient.candles(askBean.getBase(),
              askBean.getGranularity(), since, CACHE_SET_SIZE);
          if (responseResult != null && responseResult.getCode() == 0) {
            if (responseResult.getData() != null) {
              data = new ArrayList<>();
            } else {
              data = JSON.parseObject(JSON.toJSONString(responseResult.getData()),
                  new TypeReference<List<JSONArray>>() {});
            }
            // 防止并发
            this.klineCache.put(key, new LinkedList<>(data));
          }
        }
      }
    }

    if (since == null || Collections.isEmpty(data)) {
      return data;
    }

    return data.stream().filter(l -> l.getLong(0).longValue() >= since)
        .collect(Collectors.toList());
  }

  @Override
  public Object replyMessage(final AskBean askBean, final Object data) {
    final JSONArray dataArray = (JSONArray) data;
    if (dataArray == null || dataArray.isEmpty()) {
      return null;
    }

    final String key = this.formatKey(askBean);
    LinkedList<JSONArray> jsonArrays = this.klineCache.get(key);
    if (jsonArrays == null) {
      // initReply会补充数据
      return null;
    }

    boolean sign = true;
    for (JSONArray array : jsonArrays) {
      if (!array.getLong(0).equals(dataArray.getLong(0))) {
        continue;
      }
      sign = false;
      for (int i = 0; i < dataArray.size(); i++) {
        array.set(i, dataArray.get(i));
      }
      break;
    }
    if (sign) {
      synchronized (this) {
        jsonArrays.add(dataArray);
        while (jsonArrays.size() > IndexesKlineFunctionServiceImpl.CACHE_SET_SIZE) {
          jsonArrays.removeFirst();
        }
      }
    }
    return new JSONArray[] {dataArray};
  }

  private String formatKey(final AskBean askBean) {
    return String.format("%s_%s", askBean.getBase(), askBean.getGranularity()).toUpperCase();
  }
}
