package cc.newex.dax.extra.data.currency;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.currency.CurrencyBaseInfoExample;
import cc.newex.dax.extra.domain.currency.CurrencyBaseInfo;
import org.springframework.stereotype.Repository;

/**
 * 全球数字货币基本信息表 数据访问类
 *
 * @author newex-team
 * @date 2018-06-29
 */
@Repository
public interface CurrencyBaseInfoRepository extends CrudRepository<CurrencyBaseInfo, CurrencyBaseInfoExample, Long> {
}