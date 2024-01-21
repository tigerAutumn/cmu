package cc.newex.dax.users.service.subaccount;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.subaccount.TransferRecordExample;
import cc.newex.dax.users.domain.subaccount.TransferRecord;

/**
 * 母子账户划转记录表 服务接口
 *
 * @author newex-team
 * @date 2018-11-05 17:21:17
 */
public interface TransferRecordService extends CrudService<TransferRecord, TransferRecordExample, Long> {
}