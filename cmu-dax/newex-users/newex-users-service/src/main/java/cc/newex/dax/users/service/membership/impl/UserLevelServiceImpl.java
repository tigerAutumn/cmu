package cc.newex.dax.users.service.membership.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserLevelExample;
import cc.newex.dax.users.data.UserLevelRecordRepository;
import cc.newex.dax.users.data.UserLevelRepository;
import cc.newex.dax.users.domain.UserLevel;
import cc.newex.dax.users.domain.UserLevelRecord;
import cc.newex.dax.users.dto.common.UserLevelEnum;
import cc.newex.dax.users.service.membership.UserLevelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户等级表 服务实现
 *
 * @author newex-team
 * @date 2018-07-07
 */
@Slf4j
@Service
public class UserLevelServiceImpl
        extends AbstractCrudService<UserLevelRepository, UserLevel, UserLevelExample, Long>
        implements UserLevelService {

    @Autowired
    private UserLevelRepository userLevelRepos;

    @Autowired
    private UserLevelRecordRepository userLevelRecordRepository;

    @Override
    protected UserLevelExample getPageExample(final String fieldName, final String keyword) {
        final UserLevelExample example = new UserLevelExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public UserLevel getByUserId(final Long userId) {
        final UserLevelExample example = new UserLevelExample();
        example.createCriteria().andUserIdEqualTo(userId);
        UserLevel userLevel = this.userLevelRepos.selectOneByExample(example);
        if (ObjectUtils.isEmpty(userLevel)) {
            userLevel = UserLevel.getInstance();
            userLevel.setUserId(userId);
            this.userLevelRepos.insert(userLevel);
        }

        return userLevel;
    }

    @Override
    public List<UserLevel> getListByLevel(final String level, final Long lastUserId, final Integer limit) {
        final UserLevelExample example = new UserLevelExample();
        final UserLevelExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(level)) {
            criteria.andUserLevelEqualTo(level);
        }
        if (lastUserId != null && lastUserId > 0) {
            criteria.andUserIdGreaterThan(lastUserId);
        }
        example.setOrderByClause(" user_id asc limit " + limit);
        return this.userLevelRepos.selectByExample(example);
    }

    @Override
    public UserLevelEnum getUserLevelEnum(final Long userId) {
        final UserLevelExample example = new UserLevelExample();
        example.createCriteria().andUserIdEqualTo(userId);
        final UserLevel userLevel = this.userLevelRepos.selectOneByExample(example);
        if (ObjectUtils.isEmpty(userLevel)) {
            final UserLevel model = UserLevel.getInstance();
            model.setUserId(userId);
            this.userLevelRepos.insert(model);
            return UserLevelEnum.getInstance(model.getUserLevel());
        }
        final UserLevelEnum userLevelEnum = UserLevelEnum.getInstance(userLevel.getUserLevel());
        return userLevelEnum;
    }

    @Override
    @Transactional
    public int updateUserLevel(final Long userId, final String level) {
        final UserLevel userLevel = this.getByUserId(userId);
        final UserLevelRecord userLevelRecord = UserLevelRecord.builder().preUserLevel(userLevel.getUserLevel())
                .userLevel(level).userId(userId).build();
        this.userLevelRecordRepository.insert(userLevelRecord);

        final UserLevelEnum userLevelEnum = UserLevelEnum.getInstance(level);
        userLevel.setUserLevel(userLevelEnum.getCode());
        userLevel.setWeight(userLevelEnum.getWeight());
        return this.userLevelRepos.updateById(userLevel);
    }
}