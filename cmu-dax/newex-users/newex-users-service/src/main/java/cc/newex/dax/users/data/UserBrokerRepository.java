package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserBrokerExample;
import cc.newex.dax.users.domain.UserBroker;
import org.springframework.stereotype.Repository;

/**
 * 券商表 数据访问类
 *
 * @author newex-team
 * @date 2018-09-11 10:57:27
 */
@Repository
public interface UserBrokerRepository extends CrudRepository<UserBroker, UserBrokerExample, Integer> {
}