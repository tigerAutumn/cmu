package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserActivityConfigExample;
import cc.newex.dax.users.domain.UserActivityConfig;
import org.springframework.stereotype.Repository;

/**
 * 活动配置表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-29
 */
@Repository
public interface UserActivityConfigRepository
        extends CrudRepository<UserActivityConfig, UserActivityConfigExample, Long> {
}