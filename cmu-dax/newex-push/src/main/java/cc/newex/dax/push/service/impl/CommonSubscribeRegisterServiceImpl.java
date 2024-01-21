package cc.newex.dax.push.service.impl;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.cache.PushCache;
import cc.newex.dax.push.cache.PushRedisCache;
import cc.newex.dax.push.service.RegisterService;

/**
 * 处理注册时候的登录信息
 *
 * @author xionghui
 * @date 2018/09/17
 */
public abstract class CommonSubscribeRegisterServiceImpl implements RegisterService {

  /**
   * 处理登录信息
   */
  protected boolean dealUserId(String ip, WebSocketBean webSocketBean, AskBean originAskBean,
      boolean add) {
    AskBean askBean = AskBean.builder().build();
    if (originAskBean.getBiz() != null) {
      askBean.setBiz(originAskBean.getBiz().toLowerCase());
    }
    if (originAskBean.getType() != null) {
      askBean.setType(originAskBean.getType().toLowerCase());
    }
    if (originAskBean.getContractCode() != null) {
      askBean.setContractCode(originAskBean.getContractCode().toLowerCase());
    }
    if (originAskBean.getBase() != null) {
      askBean.setBase(originAskBean.getBase().toLowerCase());
    }
    if (originAskBean.getQuote() != null) {
      askBean.setQuote(originAskBean.getQuote().toLowerCase());
    }
    if (originAskBean.getGranularity() != null) {
      askBean.setGranularity(originAskBean.getGranularity().toLowerCase());
    }
    // session处理
    if (add) {
      PushCache.addIpSession(ip, webSocketBean.getSession().getId(), askBean);
    } else {
      PushCache.removeIpSession(ip, webSocketBean.getSession().getId(), askBean);
    }
    if (PushRedisCache.loginCheck(askBean.getType())) {
      Long userId = PushCache.getUser(webSocketBean.getSession().getId());
      if (userId == null) {
        return false;
      }
      if (add) {
        PushCache.addUserSession(askBean, userId, webSocketBean);
      } else {
        PushCache.removeUserSession(askBean, userId, webSocketBean);
      }
    } else {
      if (add) {
        PushCache.addSession(askBean, webSocketBean);
      } else {
        PushCache.removeSession(askBean, webSocketBean);
      }
    }
    return true;
  }
}
