package cc.newex.dax.users.data.subaccount;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.subaccount.TransferRecordExample;
import cc.newex.dax.users.domain.subaccount.TransferRecord;
import org.springframework.stereotype.Repository;

/**
 * 母子账户划转记录表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-05 17:21:17
 */
@Repository
public interface TransferRecordRepository extends CrudRepository<TransferRecord, TransferRecordExample, Long> {
}