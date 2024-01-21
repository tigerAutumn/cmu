package cc.newex.dax.users.service.kyc.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserKycEventExample;
import cc.newex.dax.users.data.UserKycEventRepository;
import cc.newex.dax.users.domain.UserKycEvent;
import cc.newex.dax.users.service.kyc.UserKycEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户kyc审核操作记录 服务实现
 *
 * @author newex-team
 * @date 2018-05-21
 */
@Slf4j
@Service
public class UserKycEventServiceImpl
        extends AbstractCrudService<UserKycEventRepository, UserKycEvent, UserKycEventExample, Long>
        implements UserKycEventService {

    @Autowired
    private UserKycEventRepository userKycEventRepos;

    @Override
    protected UserKycEventExample getPageExample(final String fieldName, final String keyword) {
        final UserKycEventExample example = new UserKycEventExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public void deleteByUserId(final Long userId) {
        if (userId == null) {
            return;
        }

        final UserKycEventExample example = new UserKycEventExample();
        example.createCriteria().andUserIdEqualTo(userId);

        this.userKycEventRepos.deleteByExample(example);
    }

}