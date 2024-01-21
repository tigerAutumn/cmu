package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.HistoryLiquidateExample;
import cc.newex.dax.perpetual.domain.HistoryLiquidate;

/**
 * 预爆仓备份表(不会被任务删除) 服务接口
 *
 * @author newex-team
 * @date 2018-11-02 11:19:47
 */
public interface HistoryLiquidateService extends CrudService<HistoryLiquidate, HistoryLiquidateExample, Long> {
}