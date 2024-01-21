package cc.newex.dax.extra.service.wiki.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.wiki.RtCurrencyScoreExample;
import cc.newex.dax.extra.data.wiki.RtCurrencyScoreRepository;
import cc.newex.dax.extra.domain.wiki.RtCurrencyScore;
import cc.newex.dax.extra.service.wiki.RtCurrencyScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * rt代币评分信息 服务实现
 *
 * @author mbg.generated
 * @date 2018-12-13 16:34:00
 */
@Slf4j
@Service
public class RtCurrencyScoreServiceImpl extends AbstractCrudService<RtCurrencyScoreRepository, RtCurrencyScore, RtCurrencyScoreExample, Long> implements RtCurrencyScoreService {
    @Autowired
    private RtCurrencyScoreRepository rtCurrencyScoreRepos;

    @Override
    protected RtCurrencyScoreExample getPageExample(final String fieldName, final String keyword) {
        final RtCurrencyScoreExample example = new RtCurrencyScoreExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public RtCurrencyScore getByCidAndCurrencyCode(final String currencyCode, final String cid) {

        Assert.hasLength(cid, "cid is not be empty or null");
        Assert.hasLength(currencyCode, "currencyCode is not be empty or null");

        final RtCurrencyScoreExample example = new RtCurrencyScoreExample();
        example.createCriteria().andSymbolEqualTo(currencyCode).andCidEqualTo(cid);

        return this.getOneByExample(example);
    }
}