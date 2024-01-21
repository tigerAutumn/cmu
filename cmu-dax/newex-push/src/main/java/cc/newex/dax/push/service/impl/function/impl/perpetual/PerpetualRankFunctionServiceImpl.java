package cc.newex.dax.push.service.impl.function.impl.perpetual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.cache.PushCache;
import cc.newex.dax.push.service.impl.function.FunctionService;

public class PerpetualRankFunctionServiceImpl implements FunctionService {
  private volatile Map<String, Map<Long, Integer>> cacheMap = new ConcurrentHashMap<>();

  @Override
  public boolean hasInit() {
    return true;
  }

  @Override
  public Object initReply(WebSocketBean webSocketBean, AskBean askBean, String params) {
    Long userId = PushCache.getUser(webSocketBean.getSession().getId());
    Map<Long, Integer> tmpMap = this.cacheMap.get(askBean.getContractCode().toLowerCase());
    JSONObject message = new JSONObject();
    Integer score = null;
    if (tmpMap != null) {
      score = tmpMap.get(userId);
    }
    message.put("score", score == null ? 0 : score);
    return message;
  }

  @Override
  public List<ReplyBean> replyUserMessage(AskBean askBean, JSONArray data) {
    List<ReplyBean> replyBeanList = new ArrayList<>();
    Map<Long, Integer> newCacheMap = new ConcurrentHashMap<>();
    for (Object obj : data) {
      JSONObject json = (JSONObject) obj;
      Long userId = json.getLong("userId");
      Integer score = json.getInteger("score");
      newCacheMap.put(userId, score);
    }
    Map<Long, Integer> tmpMap = this.cacheMap.get(askBean.getContractCode());
    tmpMap = tmpMap == null ? new HashMap<>() : tmpMap;
    this.cacheMap.put(askBean.getContractCode().toLowerCase(), newCacheMap);
    for (Map.Entry<Long, Integer> entry : newCacheMap.entrySet()) {
      Integer oldScore = tmpMap.remove(entry.getKey());
      if (entry.getValue().equals(oldScore)) {
        continue;
      }
      JSONObject message = new JSONObject();
      message.put("score", entry.getValue());
      replyBeanList.add(ReplyBean.builder().userId(entry.getKey()).message(message).build());
    }
    for (Map.Entry<Long, Integer> entry : tmpMap.entrySet()) {
      JSONObject message = new JSONObject();
      message.put("score", 0);
      replyBeanList.add(ReplyBean.builder().userId(entry.getKey()).message(message).build());
    }
    return replyBeanList;
  }
}
