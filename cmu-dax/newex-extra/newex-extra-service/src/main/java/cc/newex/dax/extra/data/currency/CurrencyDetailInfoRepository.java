package cc.newex.dax.extra.data.currency;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.currency.CurrencyDetailInfoExample;
import cc.newex.dax.extra.domain.currency.CurrencyDetailInfo;
import org.springframework.stereotype.Repository;

/**
 * 全球数字货币详细信息表 数据访问类
 *
 * @author newex-team
 * @date 2018-06-29
 */
@Repository
public interface CurrencyDetailInfoRepository extends CrudRepository<CurrencyDetailInfo, CurrencyDetailInfoExample, Long> {
}