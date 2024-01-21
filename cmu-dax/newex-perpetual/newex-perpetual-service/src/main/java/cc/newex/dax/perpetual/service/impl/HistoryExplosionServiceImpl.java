package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.HistoryExplosionExample;
import cc.newex.dax.perpetual.data.HistoryExplosionRepository;
import cc.newex.dax.perpetual.domain.HistoryExplosion;
import cc.newex.dax.perpetual.service.HistoryExplosionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 爆仓流水表 服务实现
 *
 * @author newex-team
 * @date 2018-11-02 11:19:38
 */
@Slf4j
@Service
public class HistoryExplosionServiceImpl extends AbstractCrudService<HistoryExplosionRepository, HistoryExplosion, HistoryExplosionExample, Long> implements HistoryExplosionService {
    @Autowired
    private HistoryExplosionRepository historyExplosionRepository;

    @Override
    protected HistoryExplosionExample getPageExample(final String fieldName, final String keyword) {
        final HistoryExplosionExample example = new HistoryExplosionExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }


    @Override
    public void batchInsertWithId(final List<HistoryExplosion> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            this.historyExplosionRepository.batchInsertWithId(list);
        }
    }
}