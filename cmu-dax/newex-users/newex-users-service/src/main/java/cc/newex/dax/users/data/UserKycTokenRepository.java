package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserKycTokenExample;
import cc.newex.dax.users.domain.UserKycToken;
import org.springframework.stereotype.Repository;

/**
 * 用户kyc等级 数据访问类
 *
 * @author newex-team
 * @date 2018-05-11
 */
@Repository
public interface UserKycTokenRepository
        extends CrudRepository<UserKycToken, UserKycTokenExample, Long> {
}