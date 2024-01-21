package cc.newex.dax.extra.service.wiki.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.wiki.RtCurrencyDetailExample;
import cc.newex.dax.extra.data.wiki.RtCurrencyDetailRepository;
import cc.newex.dax.extra.domain.wiki.RtCurrencyDetail;
import cc.newex.dax.extra.service.wiki.RtCurrencyDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * rt代币详情信息 服务实现
 *
 * @author mbg.generated
 * @date 2018-11-28 14:55:17
 */
@Slf4j
@Service
public class RtCurrencyDetailServiceImpl extends AbstractCrudService<RtCurrencyDetailRepository, RtCurrencyDetail, RtCurrencyDetailExample, Long> implements RtCurrencyDetailService {
    @Autowired
    private RtCurrencyDetailRepository rtCurrencyDetailRepos;

    @Override
    protected RtCurrencyDetailExample getPageExample(final String fieldName, final String keyword) {
        final RtCurrencyDetailExample example = new RtCurrencyDetailExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    /**
     * 根据cid获取代币详情
     *
     * @param cid
     * @return
     */
    @Override
    public RtCurrencyDetail getDetailByCid(final String cid) {

        final RtCurrencyDetailExample example = new RtCurrencyDetailExample();
        final RtCurrencyDetailExample.Criteria criteria = example.createCriteria();
        criteria.andCidEqualTo(cid);

        return this.getOneByExample(example);
    }
}