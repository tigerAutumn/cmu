package cc.newex.dax.push.bean;

import lombok.Data;

/**
 * 请求参数bean
 *
 * @author xionghui
 * @date 2018/09/12
 */
@Data
public class LoginBean {

  private String token;
  private String api_key;
  private String passphrase;
}
