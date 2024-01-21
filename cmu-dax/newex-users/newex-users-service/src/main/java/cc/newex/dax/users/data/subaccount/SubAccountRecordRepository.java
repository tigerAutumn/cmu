package cc.newex.dax.users.data.subaccount;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.subaccount.SubAccountRecordExample;
import cc.newex.dax.users.domain.subaccount.SubAccountRecord;
import org.springframework.stereotype.Repository;

/**
 * 子账户绑定、解锁的操作记录表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-06 17:06:44
 */
@Repository
public interface SubAccountRecordRepository extends CrudRepository<SubAccountRecord, SubAccountRecordExample, Long> {
}