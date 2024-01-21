package cc.newex.wallet.dao;

import cc.newex.commons.mybatis.sharding.data.CrudRepository;
import cc.newex.wallet.criteria.WithdrawRecordExample;
import cc.newex.wallet.pojo.WithdrawRecord;
import org.springframework.stereotype.Repository;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018-04-02
 */
@Repository
public interface WithdrawRecordRepository
        extends CrudRepository<WithdrawRecord, WithdrawRecordExample, Integer> {
}