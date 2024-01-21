package cc.newex.commons.ratelimiter.model;

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
@AllArgsConstructor
@NoArgsConstructor
public class RateLimitInfo {

    private int maxRequestTimes;

    private int seconds;

}
