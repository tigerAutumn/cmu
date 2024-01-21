package cc.newex.wallet.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.wallet.criteria.BestBlockHeightExample;
import cc.newex.wallet.dao.BestBlockHeightRepository;
import cc.newex.wallet.pojo.BestBlockHeight;
import cc.newex.wallet.service.BestBlockHeightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-05-02
 */
@Slf4j
@Service
public class BestBlockHeightServiceImpl
        extends AbstractCrudService<BestBlockHeightRepository, BestBlockHeight, BestBlockHeightExample, Integer>
        implements BestBlockHeightService {

    @Autowired
    private BestBlockHeightRepository bestBlockHeightRepos;

    @Override
    protected BestBlockHeightExample getPageExample(final String fieldName, final String keyword) {
        final BestBlockHeightExample example = new BestBlockHeightExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}