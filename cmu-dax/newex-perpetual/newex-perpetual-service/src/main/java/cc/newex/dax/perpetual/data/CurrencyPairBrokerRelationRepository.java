package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.CurrencyPairBrokerRelationExample;
import cc.newex.dax.perpetual.domain.CurrencyPairBrokerRelation;
import org.springframework.stereotype.Repository;

/**
 * 币对 券商 对应关系表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-22 17:21:12
 */
@Repository
public interface CurrencyPairBrokerRelationRepository extends CrudRepository<CurrencyPairBrokerRelation, CurrencyPairBrokerRelationExample, Long> {
}