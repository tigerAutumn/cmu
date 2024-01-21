package cc.newex.dax.push.bean;

import lombok.Data;

/**
 * 监听消息的bean
 *
 * @see AskBean
 *
 * @author xionghui
 * @date 2018/09/13
 */
@Data
public class MessageBean {

  private String biz;
  private String type;
  private String base;
  private String quote;
  private String granularity;

  private String data;

  public AskBean buildAsk() {
    return AskBean.builder().biz(this.biz).type(this.base).base(this.base).quote(this.quote)
        .granularity(this.granularity).build();
  }
}
