package cc.newex.commons.openapi.specs.auth;

import cc.newex.commons.openapi.specs.model.IpRateLimitInfo;

/**
 * @author newex-team
 * @date 2018/6/21
 */
public interface IpRateLimitService {

    /**
     * search limit by local ip
     * @param ip
     * @return
     */
    IpRateLimitInfo getRateLimitByIp(Long ip);
}
