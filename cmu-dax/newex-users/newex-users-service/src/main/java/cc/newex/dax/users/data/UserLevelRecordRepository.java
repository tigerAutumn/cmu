package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserLevelRecordExample;
import cc.newex.dax.users.domain.UserLevelRecord;
import org.springframework.stereotype.Repository;

/**
 * 用户等级变更记录表 数据访问类
 *
 * @author newex-team
 * @date 2018-07-07
 */
@Repository
public interface UserLevelRecordRepository
        extends CrudRepository<UserLevelRecord, UserLevelRecordExample, Long> {
}