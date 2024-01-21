package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.MarkPriceExample;
import cc.newex.dax.perpetual.domain.MarkPrice;

/**
 *
 * @author newex-team
 * @date 2019-01-07 21:55:01
 */
public interface MarkPriceService extends CrudService<MarkPrice, MarkPriceExample, Long> {
}