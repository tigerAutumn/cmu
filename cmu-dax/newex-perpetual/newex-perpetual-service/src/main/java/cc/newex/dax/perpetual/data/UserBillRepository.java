package cc.newex.dax.perpetual.data;

import org.springframework.stereotype.Repository;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.UserBillExample;
import cc.newex.dax.perpetual.domain.UserBill;

/**
 * 账单 数据访问类
 *
 * @author newex-team
 * @date 2018-11-01 09:32:34
 */
@Repository
public interface UserBillRepository extends CrudRepository<UserBill, UserBillExample, Long> {
}
