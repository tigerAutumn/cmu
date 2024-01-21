package cc.newex.dax.perpetual.data;

import org.springframework.stereotype.Repository;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.SystemBillTotalExample;
import cc.newex.dax.perpetual.domain.SystemBillTotal;

/**
 * 对账汇总表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-01 09:32:47
 */
@Repository
public interface SystemBillTotalRepository
    extends CrudRepository<SystemBillTotal, SystemBillTotalExample, Long> {
}
