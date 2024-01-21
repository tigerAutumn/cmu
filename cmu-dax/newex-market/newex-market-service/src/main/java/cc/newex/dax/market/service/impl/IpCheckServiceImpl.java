package cc.newex.dax.market.service.impl;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.dax.market.common.enums.RedisKeyEnum;
import cc.newex.dax.market.service.IpCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author allen
 * @date 2018/3/29
 * @des
 */
@Service("ipCheckService")
public class IpCheckServiceImpl implements IpCheckService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 查询ip白名单
     *
     * @return
     */
    @Override
    public Boolean IPWhiteCheck(final HttpServletRequest request) {
        final String ip = IpUtil.getRealIPAddress(request);
        if (ip == null) {
            return false;
        }
        try {
            //return this.stringRedisTemplate.opsForSet().isMember(RedisKeyEnum.REDIS_KEY_WHITE_IP.getKey(), ip);
        } catch (final Exception e) {
            return true;
        }
        return true;
    }
}
