package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.AssetsFeeRateExample;
import cc.newex.dax.perpetual.domain.AssetsFeeRate;
import org.springframework.stereotype.Repository;

/**
 * 资金费率流水表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-20 15:55:43
 */
@Repository
public interface AssetsFeeRateRepository extends CrudRepository<AssetsFeeRate, AssetsFeeRateExample, Long> {
}