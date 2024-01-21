package cc.newex.dax.push.service;

import cc.newex.dax.push.bean.RegisterBean;
import cc.newex.dax.push.bean.WebSocketBean;

/**
 * 消息回应接口
 *
 * @author xionghui
 * @date 2018/09/12
 */
public interface RegisterService {

  void register(String ip, WebSocketBean webSocketBean, RegisterBean registerBean);
}
