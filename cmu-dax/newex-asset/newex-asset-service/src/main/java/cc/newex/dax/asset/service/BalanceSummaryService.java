package cc.newex.dax.asset.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.asset.criteria.BalanceSummaryExample;
import cc.newex.dax.asset.domain.BalanceSummary;

import java.util.Date;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-06-26
 */
public interface BalanceSummaryService
        extends CrudService<BalanceSummary, BalanceSummaryExample, Long> {

    BalanceSummaryExample getBalanceSummaryExample(final String currency, final Date createDate);

    void getAndCheckBalanceSummary();

    void removeDataBeforeTime(Date time);

}
