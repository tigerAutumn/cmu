package cc.newex.dax.extra.data.wiki;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.wiki.RtCurrencyScoreExample;
import cc.newex.dax.extra.domain.wiki.RtCurrencyScore;
import org.springframework.stereotype.Repository;

/**
 * rt代币评分信息 数据访问类
 *
 * @author mbg.generated
 * @date 2018-12-13 16:34:00
 */
@Repository
public interface RtCurrencyScoreRepository extends CrudRepository<RtCurrencyScore, RtCurrencyScoreExample, Long> {
}