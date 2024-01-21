package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserKycEventExample;
import cc.newex.dax.users.domain.UserKycEvent;
import org.springframework.stereotype.Repository;

/**
 * 用户kyc审核操作记录 数据访问类
 *
 * @author newex-team
 * @date 2018-05-21
 */
@Repository
public interface UserKycEventRepository
        extends CrudRepository<UserKycEvent, UserKycEventExample, Long> {
}