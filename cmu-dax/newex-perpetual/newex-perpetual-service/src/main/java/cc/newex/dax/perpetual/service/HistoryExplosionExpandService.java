package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.HistoryExplosionExpandExample;
import cc.newex.dax.perpetual.domain.HistoryExplosionExpand;

import java.util.List;

/**
 * 爆仓流水扩展表 服务接口
 *
 * @author newex-team
 * @date 2018-11-02 11:33:46
 */
public interface HistoryExplosionExpandService extends CrudService<HistoryExplosionExpand, HistoryExplosionExpandExample, Long> {


    /**
     * 通过 爆仓流水表主键 获取扩展数据列表
     * @param historyExplosionIds 爆仓流水表主键
     * @return
     */
    List<HistoryExplosionExpand> listByHistoryExplosionId(Long ... historyExplosionIds);
}