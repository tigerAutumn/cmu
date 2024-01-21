package cc.newex.wallet.service;

import cc.newex.commons.mybatis.sharding.service.CrudService;
import cc.newex.wallet.criteria.WithdrawRecordExample;
import cc.newex.wallet.pojo.WithdrawRecord;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-04-02
 */
public interface WithdrawRecordService
        extends CrudService<WithdrawRecord, WithdrawRecordExample, Integer> {
}
