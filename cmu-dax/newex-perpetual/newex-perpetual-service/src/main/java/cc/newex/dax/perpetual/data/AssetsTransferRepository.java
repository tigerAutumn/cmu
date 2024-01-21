package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.AssetsTransferExample;
import cc.newex.dax.perpetual.domain.AssetsTransfer;
import org.springframework.stereotype.Repository;

/**
 * 资金划转交易 数据访问类
 *
 * @author newex-team
 * @date 2018-11-01 09:33:04
 */
@Repository
public interface AssetsTransferRepository extends CrudRepository<AssetsTransfer, AssetsTransferExample, Long> {
    AssetsTransfer selectByIdForUpdate(Long id);
}