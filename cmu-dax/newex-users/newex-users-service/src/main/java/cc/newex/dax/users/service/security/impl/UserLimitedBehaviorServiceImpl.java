package cc.newex.dax.users.service.security.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserLimitedBehaviorExample;
import cc.newex.dax.users.data.UserLimitedBehaviorRepository;
import cc.newex.dax.users.domain.UserLimitedBehavior;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.service.security.UserLimitedBehaviorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户受限制行为服务实现
 *
 * @author newex-team
 * @date 2018/03/18åΩ
 */
@Slf4j
@Service
public class UserLimitedBehaviorServiceImpl
        extends AbstractCrudService<UserLimitedBehaviorRepository, UserLimitedBehavior, UserLimitedBehaviorExample, Long>
        implements UserLimitedBehaviorService {

    @Override
    protected UserLimitedBehaviorExample getPageExample(final String fieldName, final String keyword) {
        final UserLimitedBehaviorExample example = new UserLimitedBehaviorExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int updateUserLimitedBehaviorByIp(final long ipAddress, final int type, final int limitNum, final int
            freezeMin) {
        return 0;
    }

    @Override
    public int updateUserLimitedBehaviorByName(final String loginName, final int type, final int limitNum, final int
            freezeMin) {
        return 0;
    }

    @Override
    public int updateUserLimitedBehaviorByName(final long userId, final int type, final int limitNum, final int
            freezeMin) {
        return 0;
    }

    @Override
    public int updateUserLimitedBehaviorByDeviceId(final String deviceId, final BehaviorTheme behaviorTheme) {
        return 0;
    }

    @Override
    public boolean isUserLimitedBehavior(final long ipAddress, final int type, final int limitNum, final int freezeMin) {
        return false;
    }

    @Override
    public boolean isUserLimitedBehavior(final String loginName, final int type, final int limitNum, final int freezeMin) {
        return false;
    }

    @Override
    public boolean isUserLimitedBehaviorByDeviceId(final String deviceId, final BehaviorTheme behaviorTheme) {
        return false;
    }

    @Override
    public boolean resetUserLimitedBehavior(final String loginName, final int codeType) {
        return false;
    }
}
