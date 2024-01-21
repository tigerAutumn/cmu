package cc.newex.dax.push.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.RegisterBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.cache.PushCache;
import cc.newex.dax.push.service.RegisterService;

/**
 * 退出登录
 *
 * @author xionghui
 * @date 2018/09/28
 */
@Service("signout")
public class LoginOutRegisterServiceImpl implements RegisterService {

  @Override
  public void register(String ip, WebSocketBean webSocketBean, RegisterBean registerBean) {
    JSONObject result = new JSONObject();
    result.put("channel", "signout");
    JSONObject data = new JSONObject();
    data.put("result", true);
    result.put("data", data);
    PushCache.removeUser(webSocketBean.getSession().getId());
    webSocketBean.getProcessor().onNext(webSocketBean.getSession().textMessage(result.toString()));
  }
}
