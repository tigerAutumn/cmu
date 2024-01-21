package cc.newex.dax.push.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PortfolioShareRequest {

  /**
   * 基准币
   */
  private String base;
  /**
   * 计价币
   */
  private String quote;
}
