package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.HistoryMarkPriceExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.HistoryMarkPrice;

import java.util.Date;
import java.util.List;

/**
 * 溢价指数流水表 服务接口
 *
 * @author newex-team
 * @date 2018-11-15 19:24:22
 */
public interface HistoryMarkPriceService extends CrudService<HistoryMarkPrice, HistoryMarkPriceExample, Long> {

    /**
     * 计算溢价指数
     *
     * @param contract
     */
    void scheduleMarketPrice(Contract contract);

    List<HistoryMarkPrice> listHistoryMarkPrice(Contract contract, Date start, Date end);
}