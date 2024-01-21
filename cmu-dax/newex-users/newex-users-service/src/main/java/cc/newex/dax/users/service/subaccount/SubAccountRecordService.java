package cc.newex.dax.users.service.subaccount;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.subaccount.SubAccountRecordExample;
import cc.newex.dax.users.domain.subaccount.SubAccountRecord;

/**
 * 子账户绑定、解锁的操作记录表 服务接口
 *
 * @author newex-team
 * @date 2018-11-06 17:06:44
 */
public interface SubAccountRecordService extends CrudService<SubAccountRecord, SubAccountRecordExample, Long> {
}