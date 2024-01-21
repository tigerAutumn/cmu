package cc.newex.dax.perpetual.openapi.support.auth;

import cc.newex.commons.openapi.specs.auth.IpRateLimitService;
import cc.newex.commons.openapi.support.auth.AbstractRedisIpRateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author newex-team
 * @date 2018/6/21
 */
@Service
@Slf4j
public class IpRateLimitServiceImpl
        extends AbstractRedisIpRateLimitService implements IpRateLimitService {
}
