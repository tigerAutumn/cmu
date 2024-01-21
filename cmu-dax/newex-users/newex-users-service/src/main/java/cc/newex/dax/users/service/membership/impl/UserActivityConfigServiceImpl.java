package cc.newex.dax.users.service.membership.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.criteria.UserActivityConfigExample;
import cc.newex.dax.users.data.UserActivityConfigRepository;
import cc.newex.dax.users.domain.UserActivityConfig;
import cc.newex.dax.users.dto.membership.UserActivityConfigDTO;
import cc.newex.dax.users.service.membership.UserActivityConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 活动配置表 服务实现
 *
 * @author newex-team
 * @date 2018-05-29
 */
@Slf4j
@Service
public class UserActivityConfigServiceImpl
        extends AbstractCrudService<UserActivityConfigRepository, UserActivityConfig, UserActivityConfigExample, Long>
        implements UserActivityConfigService {

    @Autowired
    private UserActivityConfigRepository activityConfigRepos;

    @Override
    protected UserActivityConfigExample getPageExample(final String fieldName, final String keyword) {
        final UserActivityConfigExample example = new UserActivityConfigExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int offline(final String activityCode) {
        final UserActivityConfig userActivityConfig = UserActivityConfig.builder().activityCode(activityCode).online(0).build();
        final UserActivityConfigExample example = new UserActivityConfigExample();
        example.createCriteria().andActivityCodeEqualTo(activityCode);
        log.info("offline activityCode={}", activityCode);
        return this.activityConfigRepos.updateByExample(userActivityConfig, example);
    }

    @Override
    public UserActivityConfigDTO getActivityConfig(final String activityCode, final Integer brokerId) {
        final UserActivityConfigExample example = new UserActivityConfigExample();

        if (StringUtils.isNotBlank(activityCode)) {
            example.createCriteria().andActivityCodeEqualTo(activityCode);
        }
        if (null != brokerId) {
            example.createCriteria().andBrokerIdEqualTo(brokerId);
        }
        example.setOrderByClause("  online desc,id desc");

        final List<UserActivityConfig> userActivityConfigList = this.activityConfigRepos.selectByExample(example);
        if (CollectionUtils.isEmpty(userActivityConfigList)) {
            return null;
        }

        final UserActivityConfig userActivityConfig = userActivityConfigList.get(0);

        final UserActivityConfigDTO dto = ObjectCopyUtils.map(userActivityConfig, UserActivityConfigDTO.class);

        return dto;
    }

}