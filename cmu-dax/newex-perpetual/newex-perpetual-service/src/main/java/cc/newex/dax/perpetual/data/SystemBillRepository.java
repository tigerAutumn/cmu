package cc.newex.dax.perpetual.data;

import org.springframework.stereotype.Repository;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.SystemBillExample;
import cc.newex.dax.perpetual.domain.SystemBill;

/**
 * 对账流水表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-01 09:32:43
 */
@Repository
public interface SystemBillRepository extends CrudRepository<SystemBill, SystemBillExample, Long> {
}
