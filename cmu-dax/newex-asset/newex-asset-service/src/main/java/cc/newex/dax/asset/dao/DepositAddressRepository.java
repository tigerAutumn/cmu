package cc.newex.dax.asset.dao;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.asset.criteria.DepositAddressExample;
import cc.newex.dax.asset.domain.DepositAddress;
import org.springframework.stereotype.Repository;

/**
 *  数据访问类
 * @author 
 * @date 2018-04-05
 */
@Repository
public interface DepositAddressRepository
    extends CrudRepository<DepositAddress, DepositAddressExample, Integer> {
}