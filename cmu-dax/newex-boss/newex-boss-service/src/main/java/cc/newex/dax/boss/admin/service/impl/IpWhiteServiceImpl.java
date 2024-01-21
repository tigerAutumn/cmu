package cc.newex.dax.boss.admin.service.impl;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.boss.admin.criteria.IpWhiteExample;
import cc.newex.dax.boss.admin.data.IpWhiteRepository;
import cc.newex.dax.boss.admin.domain.IpWhite;
import cc.newex.dax.boss.admin.service.IpWhiteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后台系统ip白名单表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class IpWhiteServiceImpl
        extends AbstractCrudService<IpWhiteRepository, IpWhite, IpWhiteExample, Integer>
        implements IpWhiteService {

    @Autowired
    private IpWhiteRepository ipWhiteRepos;

    @Override
    protected IpWhiteExample getPageExample(final String fieldName, final String keyword) {
        final IpWhiteExample example = new IpWhiteExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public boolean isIpInWhite(final String ip) {
        if (StringUtils.isBlank(ip)) {
            return false;
        }
        final IpWhiteExample example = new IpWhiteExample();
        example.createCriteria().andIpAddressEqualTo(IpUtil.toLong(ip));
        return (this.dao.countByExample(example) > 0);
    }

    @Override
    public IpWhite getByIpAddress(final String ip) {
        final IpWhiteExample example = new IpWhiteExample();
        example.createCriteria()
                .andIpAddressEqualTo(IpUtil.toLong(ip));
        return this.ipWhiteRepos.selectOneByExample(example);
    }
}