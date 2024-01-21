package cc.newex.dax.perpetual.domain.bean;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务方和用户
 *
 * @author xionghui
 * @date 2018/11/07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BrokerUserBean {

  /**
   * 业务方ID
   */
  private Integer brokerId;

  /**
   * 用户id
   */
  private Long userId;
}
