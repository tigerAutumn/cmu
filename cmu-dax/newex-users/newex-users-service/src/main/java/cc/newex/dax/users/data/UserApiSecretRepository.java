package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserApiSecretExample;
import cc.newex.dax.users.domain.UserApiSecret;
import org.springframework.stereotype.Repository;

/**
 * apikey表 数据访问类
 *
 * @author newex-team
 * @date 2018-04-06
 */
@Repository
public interface UserApiSecretRepository
        extends CrudRepository<UserApiSecret, UserApiSecretExample, Long> {
}