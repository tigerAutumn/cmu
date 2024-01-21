package cc.newex.dax.market.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.market.criteria.CoinConfigExample;
import cc.newex.dax.market.domain.CoinConfig;
import org.springframework.stereotype.Repository;
/**
 *  数据访问类
 * @author newex-team
 * @date 2018/03/18
 */
@Repository
public interface CoinConfigRepository
    extends CrudRepository<CoinConfig, CoinConfigExample, Long> {
}