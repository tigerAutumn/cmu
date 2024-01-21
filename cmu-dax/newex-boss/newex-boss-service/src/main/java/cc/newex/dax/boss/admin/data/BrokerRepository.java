package cc.newex.dax.boss.admin.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.admin.criteria.BrokerExample;
import cc.newex.dax.boss.admin.domain.Broker;
import org.springframework.stereotype.Repository;

/**
 * 后台系统券商表 数据访问类
 *
 * @author mbg.generated
 * @date 2018-09-11 14:44:15
 */
@Repository
public interface BrokerRepository extends CrudRepository<Broker, BrokerExample, Long> {
}