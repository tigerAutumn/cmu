package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.MatchingCacheExample;
import cc.newex.dax.perpetual.data.MatchingCacheRepository;
import cc.newex.dax.perpetual.domain.MatchingCache;
import cc.newex.dax.perpetual.service.MatchingCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 撮合缓存表 服务实现
 *
 * @author newex-team
 * @date 2018-12-26 14:27:56
 */
@Slf4j
@Service
public class MatchingCacheServiceImpl extends AbstractCrudService<MatchingCacheRepository, MatchingCache, MatchingCacheExample, Long> implements MatchingCacheService {
    @Autowired
    private MatchingCacheRepository matchingCacheRepository;

    @Override
    protected MatchingCacheExample getPageExample(final String fieldName, final String keyword) {
        final MatchingCacheExample example = new MatchingCacheExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<String> getContractCode() {
        return this.matchingCacheRepository.selectContractCode();
    }

    @Override
    public List<MatchingCache> getBatch(final String contractCode) {
        final MatchingCacheExample matchingCacheExample = new MatchingCacheExample();
        matchingCacheExample.createCriteria().andContractCodeEqualTo(contractCode);
        matchingCacheExample.setOrderByClause("id asc limit 10");
        return this.matchingCacheRepository.selectByExample(matchingCacheExample);
    }

    @Override
    public List<MatchingCache> getBatchForUpdate(final String contractCode) {
        return this.matchingCacheRepository.selectBatchForUpdate(contractCode);
    }
}