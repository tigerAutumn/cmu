package cc.newex.dax.push.service.impl.function;

import java.util.List;

import com.alibaba.fastjson.JSONArray;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.WebSocketBean;
import lombok.Builder;
import lombok.Data;

public interface FunctionService {

  default boolean hasInit() {
    return false;
  }

  default Object initReply(WebSocketBean webSocketBean, AskBean askBean, String params) {
    return null;
  }

  default Object replyMessage(AskBean askBean, Object data) {
    return null;
  }

  default List<ReplyBean> replyUserMessage(AskBean askBean, JSONArray data) {
    return null;
  }

  @Data
  @Builder
  public static class ReplyBean {
    private long userId;
    private Object message;
  }
}
