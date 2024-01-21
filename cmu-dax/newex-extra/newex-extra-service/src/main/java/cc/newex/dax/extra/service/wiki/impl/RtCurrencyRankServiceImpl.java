package cc.newex.dax.extra.service.wiki.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.wiki.RtCurrencyRankExample;
import cc.newex.dax.extra.data.wiki.RtCurrencyRankRepository;
import cc.newex.dax.extra.domain.wiki.RtCurrencyRank;
import cc.newex.dax.extra.service.wiki.RtCurrencyRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * rt代币排名信息 服务实现
 *
 * @author mbg.generated
 * @date 2018 -11-28 14:55:21
 */
@Slf4j
@Service
public class RtCurrencyRankServiceImpl extends AbstractCrudService<RtCurrencyRankRepository, RtCurrencyRank, RtCurrencyRankExample, Long> implements RtCurrencyRankService {
    @Autowired
    private RtCurrencyRankRepository rtCurrencyRankRepos;

    @Override
    protected RtCurrencyRankExample getPageExample(final String fieldName, final String keyword) {
        final RtCurrencyRankExample example = new RtCurrencyRankExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public RtCurrencyRank getCidByCurrencyCode(final String currencyCode) {
        final RtCurrencyRankExample example = new RtCurrencyRankExample();
        final RtCurrencyRankExample.Criteria criteria = example.createCriteria();
        criteria.andSymbolEqualTo(currencyCode);
        return this.getOneByExample(example);
    }
}