package cc.newex.dax.asset.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.asset.criteria.ReconciliationConfExample;
import cc.newex.dax.asset.domain.ReconciliationConf;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-06-26
 */
public interface ReconciliationConfService
        extends CrudService<ReconciliationConf, ReconciliationConfExample, Long> {
}
