package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.HistoryLiquidateExample;
import cc.newex.dax.perpetual.data.HistoryLiquidateRepository;
import cc.newex.dax.perpetual.domain.HistoryLiquidate;
import cc.newex.dax.perpetual.service.HistoryLiquidateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 预爆仓备份表(不会被任务删除) 服务实现
 *
 * @author newex-team
 * @date 2018-11-02 11:19:47
 */
@Slf4j
@Service
public class HistoryLiquidateServiceImpl extends AbstractCrudService<HistoryLiquidateRepository, HistoryLiquidate, HistoryLiquidateExample, Long> implements HistoryLiquidateService {
    @Autowired
    private HistoryLiquidateRepository historyLiquidateRepository;

    @Override
    protected HistoryLiquidateExample getPageExample(final String fieldName, final String keyword) {
        final HistoryLiquidateExample example = new HistoryLiquidateExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}