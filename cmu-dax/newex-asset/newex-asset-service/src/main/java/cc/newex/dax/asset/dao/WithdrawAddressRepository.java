package cc.newex.dax.asset.dao;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.asset.criteria.WithdrawAddressExample;
import cc.newex.dax.asset.domain.WithdrawAddress;
import org.springframework.stereotype.Repository;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018-04-09
 */
@Repository
public interface WithdrawAddressRepository
        extends CrudRepository<WithdrawAddress, WithdrawAddressExample, Integer> {
}