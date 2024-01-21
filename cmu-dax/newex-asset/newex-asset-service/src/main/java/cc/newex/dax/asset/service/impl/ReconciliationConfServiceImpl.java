package cc.newex.dax.asset.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.asset.criteria.ReconciliationConfExample;
import cc.newex.dax.asset.dao.ReconciliationConfRepository;
import cc.newex.dax.asset.domain.ReconciliationConf;
import cc.newex.dax.asset.service.ReconciliationConfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-06-19
 */
@Slf4j
@Service
public class ReconciliationConfServiceImpl
        extends AbstractCrudService<ReconciliationConfRepository, ReconciliationConf, ReconciliationConfExample, Long>
        implements ReconciliationConfService {

    @Autowired
    private ReconciliationConfRepository reconciliationConfRepos;

    @Override
    protected ReconciliationConfExample getPageExample(final String fieldName, final String keyword) {
        final ReconciliationConfExample example = new ReconciliationConfExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}