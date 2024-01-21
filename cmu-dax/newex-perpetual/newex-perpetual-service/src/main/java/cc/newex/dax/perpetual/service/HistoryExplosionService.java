package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.HistoryExplosionExample;
import cc.newex.dax.perpetual.domain.HistoryExplosion;

import java.util.List;

/**
 * 爆仓流水表 服务接口
 *
 * @author newex-team
 * @date 2018-11-02 11:19:38
 */
public interface HistoryExplosionService extends CrudService<HistoryExplosion, HistoryExplosionExample, Long> {

    void batchInsertWithId(List<HistoryExplosion> list);
}