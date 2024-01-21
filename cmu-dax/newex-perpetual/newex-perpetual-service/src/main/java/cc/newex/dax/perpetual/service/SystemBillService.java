package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.SystemBillExample;
import cc.newex.dax.perpetual.domain.SystemBill;

/**
 * 对账流水表 服务接口
 *
 * @author newex -team
 * @date 2018 -11-01 09:32:43
 */
public interface SystemBillService extends CrudService<SystemBill, SystemBillExample, Long> {

    /**
     * 获取最大的账号ID
     *
     * @return the max id
     */
    Long getMaxId();
}
