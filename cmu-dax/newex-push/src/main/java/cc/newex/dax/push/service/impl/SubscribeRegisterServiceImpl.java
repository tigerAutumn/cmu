package cc.newex.dax.push.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.RegisterBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.service.ReplyService;

/**
 * 订阅服务
 *
 * @author xionghui
 * @date 2018/09/12
 */
@Service("subscribe")
public class SubscribeRegisterServiceImpl extends CommonSubscribeRegisterServiceImpl {
  @Autowired
  private ApplicationContext applicationContext;

  @Override
  public void register(String ip, WebSocketBean webSocketBean, RegisterBean registerBean) {
    AskBean askBean = JSON.parseObject(registerBean.getParams(), AskBean.class);
    JSONObject result = new JSONObject();
    result.put("channel", "subscribe");
    result.put("biz", askBean.getBiz());
    result.put("type", askBean.getType());
    result.put("base", askBean.getBase());
    result.put("quote", askBean.getQuote());
    result.put("contractCode", askBean.getContractCode());
    result.put("granularity", askBean.getGranularity());
    JSONObject data = new JSONObject();
    result.put("data", data);
    boolean valid = this.dealUserId(ip, webSocketBean, askBean, true);
    if (!valid) {
      data.put("result", false);
      webSocketBean.getProcessor()
          .onNext(webSocketBean.getSession().textMessage(result.toString()));
      return;
    }
    data.put("result", true);
    webSocketBean.getProcessor().onNext(webSocketBean.getSession().textMessage(result.toString()));

    // 返回初始化数据
    ReplyService replyService =
        this.applicationContext.getBean(askBean.getBiz(), ReplyService.class);
    replyService.initReply(webSocketBean, askBean, registerBean.getParams());
  }
}
