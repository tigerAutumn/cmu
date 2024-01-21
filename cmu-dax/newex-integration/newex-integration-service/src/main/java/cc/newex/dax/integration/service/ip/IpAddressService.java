package cc.newex.dax.integration.service.ip;

import cc.newex.dax.integration.domain.ip.IpLocation;

/**
 * @author newex-team
 * @date 2018-06-05
 */
public interface IpAddressService {

    /**
     * @param ipAddress
     * @return
     */
    IpLocation getIpLocation(final String ipAddress);
}
