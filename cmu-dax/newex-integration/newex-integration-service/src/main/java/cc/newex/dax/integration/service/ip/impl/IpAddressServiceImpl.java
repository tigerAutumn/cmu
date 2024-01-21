package cc.newex.dax.integration.service.ip.impl;

import cc.newex.dax.integration.domain.ip.IpLocation;
import cc.newex.dax.integration.service.conf.enums.RegionEnum;
import cc.newex.dax.integration.service.ip.IpAddressService;
import cc.newex.dax.integration.service.ip.provider.IpAddressProviderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author newex-team
 * @date 2018-06-05
 */
@Slf4j
@Service
public class IpAddressServiceImpl implements IpAddressService {
    @Resource
    private IpAddressProviderFactory factory;

    @Override
    public IpLocation getIpLocation(final String ipAddress) {
        return this.factory.getIpAddressProvider(RegionEnum.CHINA.getName()).getIpLocation(ipAddress);
    }
}
