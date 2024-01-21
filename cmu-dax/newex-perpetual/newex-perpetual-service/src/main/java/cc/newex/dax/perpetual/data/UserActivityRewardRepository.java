package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.UserActivityRewardExample;
import cc.newex.dax.perpetual.domain.UserActivityReward;
import org.springframework.stereotype.Repository;

/**
 * 活动币领取表 数据访问类
 *
 * @author newex-team
 * @date 2018-12-20 20:14:27
 */
@Repository
public interface UserActivityRewardRepository extends CrudRepository<UserActivityReward, UserActivityRewardExample, Long> {
}