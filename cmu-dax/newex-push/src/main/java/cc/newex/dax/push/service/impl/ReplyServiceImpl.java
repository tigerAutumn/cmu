package cc.newex.dax.push.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.cache.PushCache;
import cc.newex.dax.push.service.ReplyService;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.service.impl.function.FunctionService.ReplyBean;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;

/**
 * 回应消息实现类，使用模板方法
 *
 * @author xionghui
 * @date 2018/09/13
 */
@Slf4j
public abstract class ReplyServiceImpl implements ReplyService {
  protected final Map<String, FunctionService> functionServiceMap = new ConcurrentHashMap<>();

  @Override
  public void initReply(WebSocketBean webSocketBean, AskBean askBean, String params) {
    FunctionService functionService = this.functionServiceMap.get(askBean.getType());
    if (functionService == null) {
      log.error("SpotReplyServiceImpl initReply get functionService null: {}", askBean);
      return;
    }
    if (functionService.hasInit()) {
      Object data = functionService.initReply(webSocketBean, askBean, params);
      if (data != null) {
        webSocketBean.getProcessor()
            .onNext(webSocketBean.getSession().textMessage(this.buildResult(askBean, data)));
      }
    }
  }

  private String buildResult(AskBean askBean, Object data) {
    JSONObject result = new JSONObject();
    result.put("data", data);
    result.put("biz", askBean.getBiz());
    result.put("type", askBean.getType());
    result.put("base", askBean.getBase());
    result.put("quote", askBean.getQuote());
    result.put("contractCode", askBean.getContractCode());
    result.put("granularity", askBean.getGranularity());
    return result.toJSONString();
  }

  @Override
  public void reply(AskBean askBean, Object data) {
    FunctionService functionService = this.functionServiceMap.get(askBean.getType());
    if (functionService == null) {
      log.error("ReplyServiceImpl get functionService null: {}", askBean);
      return;
    }
    Object messageData = functionService.replyMessage(askBean, data);
    if (messageData == null) {
      return;
    }
    List<WebSocketBean> webSocketBeanList = PushCache.getSession(askBean);
    if (Collections.isEmpty(webSocketBeanList)) {
      log.debug("ReplyServiceImpl has no webSocketBeanList");
      return;
    }
    String message = this.buildResult(askBean, messageData);
    for (WebSocketBean webSocketBean : webSocketBeanList) {
      webSocketBean.getProcessor().onNext(webSocketBean.getSession().textMessage(message));
    }
  }

  @Override
  public void replyUser(AskBean askBean, Object data) {
    FunctionService functionService = this.functionServiceMap.get(askBean.getType());
    if (functionService == null) {
      log.error("SpecialReplyServiceImpl get functionService null: {}", askBean);
      return;
    }
    List<ReplyBean> replyBeanList = functionService.replyUserMessage(askBean, (JSONArray) data);
    if (Collections.isEmpty(replyBeanList)) {
      log.debug("SpecialReplyServiceImpl replyUserMessage empty webSocketBeanList");
      return;
    }
    for (ReplyBean replyBean : replyBeanList) {
      List<WebSocketBean> webSocketBeanList =
          PushCache.getUserSession(askBean, replyBean.getUserId());
      if (Collections.isEmpty(webSocketBeanList)) {
        log.debug("SpecialReplyServiceImpl has no webSocketBeanList");
        continue;
      }
      for (WebSocketBean webSocketBean : webSocketBeanList) {
        if (replyBean.getMessage() != null) {
          webSocketBean.getProcessor().onNext(webSocketBean.getSession()
              .textMessage(this.buildResult(askBean, replyBean.getMessage())));
        }
      }
    }
  }
}
