package cc.newex.dax.extra.data.currency;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.currency.CurrencyProgressInfoExample;
import cc.newex.dax.extra.domain.currency.CurrencyProgressInfo;
import org.springframework.stereotype.Repository;

/**
 * 项目进展信息表 数据访问类
 *
 * @author mbg.generated
 * @date 2018-08-20 17:32:20
 */
@Repository
public interface CurrencyProgressInfoRepository extends CrudRepository<CurrencyProgressInfo, CurrencyProgressInfoExample, Long> {
}