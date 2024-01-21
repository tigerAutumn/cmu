package cc.newex.dax.asset.dao;

import cc.newex.commons.mybatis.sharding.data.CrudRepository;
import cc.newex.dax.asset.criteria.AssetCurrencyCompressExample;
import cc.newex.dax.asset.domain.AssetCurrencyCompress;
import org.springframework.stereotype.Repository;

/**
 * 币种表 数据访问类
 *
 * @author newex-team
 * @date 2018-09-17 17:20:24
 */
@Repository
public interface AssetCurrencyCompressRepository extends CrudRepository<AssetCurrencyCompress, AssetCurrencyCompressExample, Integer> {
}