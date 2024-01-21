package cc.newex.dax.push.service.impl;

import org.springframework.stereotype.Service;

import cc.newex.dax.push.bean.RegisterBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.service.RegisterService;

/**
 * ping pong
 *
 * @author xionghui
 * @date 2018/09/12
 */
@Service("ping")
public class PingRegisterServiceImpl implements RegisterService {

  @Override
  public void register(String ip, WebSocketBean webSocketBean, RegisterBean registerBean) {
    webSocketBean.getProcessor()
        .onNext(webSocketBean.getSession().textMessage("{\"event\":\"pong\"}"));
  }
}
