package cc.newex.commons.openapi.specs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author newex-team
 * @date 2018/6/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpRateLimitInfo {

    /**
     * ip
     */
    private Long ip;
    /**
     * 请求次数限制(次数/每几秒 如:6/2表示2秒内不超过6次)默认为6/2
     */
    private String rateLimit;
}
