package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.ContractExample;
import cc.newex.dax.perpetual.domain.Contract;
import org.springframework.stereotype.Repository;

/**
 * 合约表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-01 09:30:28
 */
@Repository
public interface ContractRepository extends CrudRepository<Contract, ContractExample, Long> {
}