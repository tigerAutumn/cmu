package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.criteria.SystemBillTotalExample;
import cc.newex.dax.perpetual.domain.SystemBillTotal;
import cc.newex.dax.perpetual.dto.SystemBillTotalDTO;

import java.util.List;

/**
 * 对账汇总表 服务接口
 *
 * @author newex -team
 * @date 2018 -11-01 09:32:47
 */
public interface SystemBillTotalService
        extends CrudService<SystemBillTotal, SystemBillTotalExample, Long> {

    /**
     * 查询总账单
     *
     * @param currencyCode  the currency code
     * @param endDateMillis the end date millis
     * @return the system bill total
     */
    SystemBillTotal getSystemBillTotal(final String currencyCode, final Long endDateMillis);

    /**
     * 增量统计总账单对账
     */
    void buildSystemBillTotal();

    /**
     * Save systemo bill total response result.
     *
     * @param target the target
     * @return the response result
     */
    ResponseResult<?> saveSystemBillTotal(SystemBillTotal target);

    /**
     * List by condition list.
     *
     * @param currencyCode the currency code
     * @param start        the start
     * @param end          the end
     * @return the list
     */
    List<SystemBillTotalDTO> listByCondition(String currencyCode, Long start, Long end);
}
