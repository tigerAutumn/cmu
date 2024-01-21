package cc.newex.dax.users.service.admin.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserInvitationChannelExample;
import cc.newex.dax.users.data.UserInvitationChannelRepository;
import cc.newex.dax.users.domain.UserInvitationChannel;
import cc.newex.dax.users.service.admin.UserInvitationChannelAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 渠道推广表 服务实现
 *
 * @author mbg.generated
 * @date 2018-07-25 15:34:50
 */
@Slf4j
@Service
public class UserInvitationChannelAdminServiceImpl extends AbstractCrudService<UserInvitationChannelRepository, UserInvitationChannel, UserInvitationChannelExample, Long> implements UserInvitationChannelAdminService {
    @Autowired
    private UserInvitationChannelRepository userInvitationChannelRepos;

    @Override
    protected UserInvitationChannelExample getPageExample(final String fieldName, final String keyword) {
        final UserInvitationChannelExample example = new UserInvitationChannelExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}