package cc.newex.dax.users.service.membership.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserIpRateLimitExample;
import cc.newex.dax.users.data.UserIpRateLimitRepository;
import cc.newex.dax.users.domain.UserIpRateLimit;
import cc.newex.dax.users.service.cache.AppCacheService;
import cc.newex.dax.users.service.membership.UserIpRateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

/**
 * Api流量限制配置表 服务实现
 *
 * @author newex-team
 * @date 2018-06-18
 */
@Slf4j
@Service
public class UserIpRateLimitServiceImpl
        extends AbstractCrudService<UserIpRateLimitRepository, UserIpRateLimit, UserIpRateLimitExample, Integer>
        implements UserIpRateLimitService {

    @Autowired
    private UserIpRateLimitRepository userApiRateLimitRepos;
    @Autowired
    private AppCacheService appCacheService;

    @Override
    protected UserIpRateLimitExample getPageExample(final String fieldName, final String keyword) {
        final UserIpRateLimitExample example = new UserIpRateLimitExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public UserIpRateLimit getUserIpRateLimitByIp(final Long ip) {
        final UserIpRateLimitExample example = new UserIpRateLimitExample();
        final UserIpRateLimitExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(ip)) {
            criteria.andIpEqualTo(ip);
        }
        return this.userApiRateLimitRepos.selectOneByExample(example);
    }

    @PostConstruct
    @Override
    public void loadAllUserIpRateLimitToCache() {
        final List<UserIpRateLimit> list = this.getAll();
        this.appCacheService.loadAllIpRateLimit(list);
        log.info("load all user ip rate limit finished.");
    }

}