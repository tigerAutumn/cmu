package cc.newex.dax.push.service;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.WebSocketBean;

/**
 * 回应消息
 *
 * @author xionghui
 * @date 2018/09/13
 */
public interface ReplyService {

  void initReply(WebSocketBean webSocketBean, AskBean askBean, String params);

  void reply(AskBean askBean, Object data);

  void replyUser(AskBean askBean, Object data);
}
