package cc.newex.dax.push.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订阅与取消订阅参数bean
 *
 * @see MessageBean
 *
 * @author xionghui
 * @date 2018/09/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AskBean {

  private String biz;
  private String type;
  private String contractCode;
  private String base;
  private String quote;
  private String granularity;
}
