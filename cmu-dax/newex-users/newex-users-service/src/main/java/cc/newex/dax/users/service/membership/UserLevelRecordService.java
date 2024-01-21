package cc.newex.dax.users.service.membership;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserLevelRecordExample;
import cc.newex.dax.users.domain.UserLevelRecord;

/**
 * 用户等级变更记录表 服务接口
 *
 * @author newex-team
 * @date 2018-07-07
 */
public interface UserLevelRecordService
        extends CrudService<UserLevelRecord, UserLevelRecordExample, Long> {
}
