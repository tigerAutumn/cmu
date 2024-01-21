package cc.newex.dax.extra.data.wiki;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.wiki.RtCurrencyRankExample;
import cc.newex.dax.extra.domain.wiki.RtCurrencyRank;
import org.springframework.stereotype.Repository;

/**
 * rt代币排名信息 数据访问类
 *
 * @author mbg.generated
 * @date 2018-11-28 14:55:21
 */
@Repository
public interface RtCurrencyRankRepository extends CrudRepository<RtCurrencyRank, RtCurrencyRankExample, Long> {
}