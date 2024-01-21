package cc.newex.dax.push.bean;

import lombok.Data;

/**
 * 请求参数bean
 *
 * @author xionghui
 * @date 2018/09/12
 */
@Data
public class RegisterBean {

  private String event;
  private String params;
}
