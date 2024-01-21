package cc.newex.dax.users.service.membership.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserLevelRecordExample;
import cc.newex.dax.users.data.UserLevelRecordRepository;
import cc.newex.dax.users.domain.UserLevelRecord;
import cc.newex.dax.users.service.membership.UserLevelRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户等级变更记录表 服务实现
 *
 * @author newex-team
 * @date 2018-07-07
 */
@Slf4j
@Service
public class UserLevelRecordServiceImpl
        extends AbstractCrudService<UserLevelRecordRepository, UserLevelRecord, UserLevelRecordExample, Long>
        implements UserLevelRecordService {

    @Autowired
    private UserLevelRecordRepository userLevelRecordRepos;

    @Override
    protected UserLevelRecordExample getPageExample(final String fieldName, final String keyword) {
        final UserLevelRecordExample example = new UserLevelRecordExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}