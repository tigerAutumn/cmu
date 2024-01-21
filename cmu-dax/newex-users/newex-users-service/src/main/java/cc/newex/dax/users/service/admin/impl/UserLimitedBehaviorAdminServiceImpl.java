package cc.newex.dax.users.service.admin.impl;

import cc.newex.dax.users.criteria.UserLimitedBehaviorExample;
import cc.newex.dax.users.data.UserLimitedBehaviorRepository;
import cc.newex.dax.users.domain.UserLimitedBehavior;
import cc.newex.dax.users.service.admin.UserLimitedBehaviorAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户受限制行为服务实现
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service
public class UserLimitedBehaviorAdminServiceImpl
        implements UserLimitedBehaviorAdminService {

    @Autowired
    private UserLimitedBehaviorRepository behaviorRepository;

    @Override
    public boolean resetUserLimitedBehavior(final long ipAddress, final int type) {
        final UserLimitedBehaviorExample ipLimitExample = new UserLimitedBehaviorExample();
        ipLimitExample.createCriteria()
                .andIpAddressEqualTo(ipAddress)
                .andTypeEqualTo(type);

        final UserLimitedBehavior ipLimit = this.behaviorRepository.selectOneByExample(ipLimitExample);
        if (ipLimit == null) {
            return true;
        }
        final UserLimitedBehavior update = new UserLimitedBehavior();
        update.setId(ipLimit.getId());
        update.setMaximum(0);
        final int result = this.behaviorRepository.updateById(update);
        return result > 0;
    }

}
