package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.UserFeeExample;
import cc.newex.dax.perpetual.domain.UserFee;
import org.springframework.stereotype.Repository;

/**
 * 用户手续费配置表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-01 09:30:43
 */
@Repository
public interface UserFeeRepository extends CrudRepository<UserFee, UserFeeExample, Long> {
}