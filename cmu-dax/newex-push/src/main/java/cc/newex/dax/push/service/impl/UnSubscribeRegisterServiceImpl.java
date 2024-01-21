package cc.newex.dax.push.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.RegisterBean;
import cc.newex.dax.push.bean.WebSocketBean;

/**
 * 取消订阅服务
 *
 * @author xionghui
 * @date 2018/09/12
 */
@Service("unsubscribe")
public class UnSubscribeRegisterServiceImpl extends CommonSubscribeRegisterServiceImpl {

  @Override
  public void register(String ip, WebSocketBean webSocketBean, RegisterBean registerBean) {
    AskBean askBean = JSON.parseObject(registerBean.getParams(), AskBean.class);

    JSONObject result = new JSONObject();
    result.put("channel", "unsubscribe");
    result.put("biz", askBean.getBiz());
    result.put("type", askBean.getType());
    result.put("base", askBean.getBase());
    result.put("quote", askBean.getQuote());
    result.put("granularity", askBean.getGranularity());
    JSONObject data = new JSONObject();
    result.put("data", data);
    boolean valid = this.dealUserId(ip, webSocketBean, askBean, false);
    if (!valid) {
      data.put("result", false);
      webSocketBean.getProcessor()
          .onNext(webSocketBean.getSession().textMessage(result.toString()));
      return;
    }

    data.put("result", true);
    webSocketBean.getProcessor().onNext(webSocketBean.getSession().textMessage(result.toString()));
  }
}
