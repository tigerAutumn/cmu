package cc.newex.dax.extra.data.vlink;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.vlink.ContractInfoExample;
import cc.newex.dax.extra.domain.vlink.ContractInfo;
import org.springframework.stereotype.Repository;

/**
 * vlink合约购买记录 数据访问类
 *
 * @author mbg.generated
 * @date 2018-10-27 22:42:17
 */
@Repository
public interface ContractInfoRepository extends CrudRepository<ContractInfo, ContractInfoExample, Long> {
}