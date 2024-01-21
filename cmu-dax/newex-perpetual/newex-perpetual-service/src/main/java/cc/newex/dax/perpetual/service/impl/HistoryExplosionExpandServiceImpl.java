package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.HistoryExplosionExpandExample;
import cc.newex.dax.perpetual.data.HistoryExplosionExpandRepository;
import cc.newex.dax.perpetual.domain.HistoryExplosionExpand;
import cc.newex.dax.perpetual.service.HistoryExplosionExpandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 爆仓流水扩展表 服务实现
 *
 * @author newex-team
 * @date 2018-11-02 11:33:46
 */
@Slf4j
@Service
public class HistoryExplosionExpandServiceImpl extends AbstractCrudService<HistoryExplosionExpandRepository, HistoryExplosionExpand, HistoryExplosionExpandExample, Long> implements HistoryExplosionExpandService {
    @Autowired
    private HistoryExplosionExpandRepository historyExplosionExpandRepository;

    @Override
    protected HistoryExplosionExpandExample getPageExample(final String fieldName, final String keyword) {
        final HistoryExplosionExpandExample example = new HistoryExplosionExpandExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<HistoryExplosionExpand> listByHistoryExplosionId(Long ... historyExplosionIds) {

        if (historyExplosionIds == null || historyExplosionIds.length == 0) {
            return new ArrayList<>(0);
        }

        final HistoryExplosionExpandExample example = new HistoryExplosionExpandExample();
        example.createCriteria().andHistoryExplosionIdIn(Arrays.asList(historyExplosionIds));
        return historyExplosionExpandRepository.selectByExample(example);
    }
}