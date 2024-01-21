package cc.newex.dax.extra.data.currency;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.currency.CurrencyTrendInfoExample;
import cc.newex.dax.extra.domain.currency.CurrencyTrendInfo;
import org.springframework.stereotype.Repository;

/**
 * 项目动态信息表 数据访问类
 *
 * @author mbg.generated
 * @date 2018-08-21 11:44:03
 */
@Repository
public interface CurrencyTrendInfoRepository extends CrudRepository<CurrencyTrendInfo, CurrencyTrendInfoExample, Long> {
}