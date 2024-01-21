package cc.newex.dax.users.service.membership;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserActivityConfigExample;
import cc.newex.dax.users.domain.UserActivityConfig;
import cc.newex.dax.users.dto.membership.UserActivityConfigDTO;

/**
 * 活动配置表 服务接口
 *
 * @author newex-team
 * @date 2018-05-29
 */
public interface UserActivityConfigService
        extends CrudService<UserActivityConfig, UserActivityConfigExample, Long> {
    /**
     * 活动下线
     *
     * @param activityCode
     * @return
     */
    int offline(String activityCode);

    /**
     * 获取最新活动
     *
     * @return
     */
    UserActivityConfigDTO getActivityConfig(String activityCode, Integer brokerId);
}
