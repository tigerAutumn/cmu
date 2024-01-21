package cc.newex.dax.integration.data.conf;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.integration.criteria.conf.BrokerSignConfigExample;
import cc.newex.dax.integration.domain.conf.BrokerSignConfig;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mbg.generated
 * @date 2018-09-12 15:04:54
 */
@Repository
public interface BrokerSignConfigRepository extends CrudRepository<BrokerSignConfig, BrokerSignConfigExample, Long> {
}