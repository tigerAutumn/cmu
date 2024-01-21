package cc.newex.dax.users.service.admin.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserIpRateLimitExample;
import cc.newex.dax.users.data.UserIpRateLimitRepository;
import cc.newex.dax.users.domain.UserIpRateLimit;
import cc.newex.dax.users.service.admin.UserIpRateLimitAdminService;
import cc.newex.dax.users.service.membership.UserIpRateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Api流量限制配置表 服务实现
 *
 * @author newex-team
 * @date 2018-06-18
 */
@Slf4j
@Service
public class UserIpRateLimitAdminServiceImpl
        extends AbstractCrudService<UserIpRateLimitRepository, UserIpRateLimit, UserIpRateLimitExample, Integer>
        implements UserIpRateLimitAdminService {
    @Autowired
    private UserIpRateLimitService userIpRateLimitService;

    @Override
    protected UserIpRateLimitExample getPageExample(final String fieldName, final String keyword) {
        final UserIpRateLimitExample example = new UserIpRateLimitExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public void refreshCache() {
        this.userIpRateLimitService.loadAllUserIpRateLimitToCache();
    }

    @Override
    public List<UserIpRateLimit> getByPage(final PageInfo pageInfo, final String fieldName, final String keyword, final Integer brokerId) {
        final UserIpRateLimitExample example = this.getPageExample(fieldName, keyword);
        final UserIpRateLimitExample.Criteria criteria = example.createCriteria();
        if (null != brokerId) {
            criteria.andBrokerIdEqualTo(brokerId);
        }
        if (StringUtils.isBlank(fieldName)) {
            return this.getByPage(pageInfo, null);
        }
        return this.getByPage(pageInfo, example);
    }
}