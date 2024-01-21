package cc.newex.dax.asset.dao;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.asset.criteria.TransferRecordAuditExample;
import cc.newex.dax.asset.domain.TransferRecordAudit;
import org.springframework.stereotype.Repository;

/**
 *
 * @author newex-team
 * @date 2019-04-23 18:58:22
 */
@Repository
public interface TransferRecordAuditRepository extends CrudRepository<TransferRecordAudit, TransferRecordAuditExample, Long> {
}