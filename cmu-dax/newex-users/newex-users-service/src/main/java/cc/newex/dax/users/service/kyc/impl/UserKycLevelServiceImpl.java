package cc.newex.dax.users.service.kyc.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserKycLevelExample;
import cc.newex.dax.users.data.UserKycLevelRepository;
import cc.newex.dax.users.domain.UserKycLevel;
import cc.newex.dax.users.service.kyc.UserKycLevelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户kyc等级 服务实现
 *
 * @author newex-team
 * @date 2018-05-03
 */
@Slf4j
@Service
public class UserKycLevelServiceImpl extends AbstractCrudService<UserKycLevelRepository, UserKycLevel, UserKycLevelExample, Long>
        implements UserKycLevelService {

    @Autowired
    private UserKycLevelRepository userKycLevelRepos;

    @Override
    protected UserKycLevelExample getPageExample(final String fieldName, final String keyword) {
        final UserKycLevelExample example = new UserKycLevelExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public Optional<UserKycLevel> getByUserid(final Long userId) {
        final UserKycLevelExample userKycLevelExample = this.getPageExample("user_id", userId + "");
        userKycLevelExample.setOrderByClause("level desc");
        return this.userKycLevelRepos.selectByExample(userKycLevelExample).stream().findFirst();
    }

    @Override
    public int countByExample(final UserKycLevelExample example) {
        return this.userKycLevelRepos.countByExample(example);
    }

    @Override
    public void deleteByUserId(final Long userId) {
        if (userId == null) {
            return;
        }

        final UserKycLevelExample example = new UserKycLevelExample();
        example.createCriteria().andUserIdEqualTo(userId);

        this.userKycLevelRepos.deleteByExample(example);
    }

}