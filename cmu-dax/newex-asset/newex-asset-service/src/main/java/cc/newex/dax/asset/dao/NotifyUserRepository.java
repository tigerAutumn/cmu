package cc.newex.dax.asset.dao;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.asset.criteria.NotifyUserExample;
import cc.newex.dax.asset.domain.NotifyUser;
import org.springframework.stereotype.Repository;

/**
 * 充值、提现通知 数据访问类
 *
 * @author newex-team
 * @date 2018-08-01 11:17:17
 */
@Repository
public interface NotifyUserRepository extends CrudRepository<NotifyUser, NotifyUserExample, Integer> {
}