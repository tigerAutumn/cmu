package cc.newex.dax.extra.data.wiki;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.wiki.RtCurrencyDetailExample;
import cc.newex.dax.extra.domain.wiki.RtCurrencyDetail;
import org.springframework.stereotype.Repository;

/**
 * rt代币详情信息 数据访问类
 *
 * @author mbg.generated
 * @date 2018-11-28 14:55:17
 */
@Repository
public interface RtCurrencyDetailRepository extends CrudRepository<RtCurrencyDetail, RtCurrencyDetailExample, Long> {
}