package cc.newex.dax.push.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cc.newex.commons.security.jwt.token.JwtTokenProvider;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.push.bean.LoginBean;
import cc.newex.dax.push.bean.RegisterBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.bean.enums.ErrorCodeEnum;
import cc.newex.dax.push.cache.PushCache;
import cc.newex.dax.push.client.FeignUsersClient;
import cc.newex.dax.push.service.RegisterService;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;

/**
 * 登录 signout
 *
 * @author xionghui
 * @date 2018/09/12
 */
@Service("signin")
public class LoginRegisterServiceImpl implements RegisterService {

  @Autowired
  private FeignUsersClient usersClient;
  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Override
  public void register(String ip, WebSocketBean webSocketBean, RegisterBean registerBean) {
    JSONObject result = new JSONObject();
    result.put("channel", "signin");
    JSONObject data = new JSONObject();
    data.put("result", false);
    result.put("data", data);
    LoginBean loginBean = JSON.parseObject(registerBean.getParams(), LoginBean.class);
    // API 登录
    if (loginBean.getApi_key() != null && loginBean.getPassphrase() != null) {
      // api_key 方式登录
      ResponseResult<UserInfoResDTO> userResult =
          this.usersClient.getUserInfoByApiKey(loginBean.getApi_key(), loginBean.getPassphrase());
      if (userResult != null && userResult.getCode() == 0) {
        data.put("result", true);
        PushCache.addUser(webSocketBean.getSession().getId(), userResult.getData().getId());
      } else {
        data.put("error_code", ErrorCodeEnum.INVALID_LOGIN_APIKEY.code());
        data.put("error_msg", ErrorCodeEnum.INVALID_LOGIN_APIKEY.errorMsg());
      }
    } else if (loginBean.getToken() != null) {
      long userId = this.jwtTokenProvider.getUserId(loginBean.getToken());
      if (userId != 0) {
        data.put("result", true);
        PushCache.addUser(webSocketBean.getSession().getId(), userId);
      } else {
        data.put("error_code", ErrorCodeEnum.INVALID_LOGIN.code());
        data.put("error_msg", ErrorCodeEnum.INVALID_LOGIN.errorMsg());
      }
    }
    webSocketBean.getProcessor().onNext(webSocketBean.getSession().textMessage(result.toString()));
  }
}
