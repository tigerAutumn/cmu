package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.MarkPriceExample;
import cc.newex.dax.perpetual.data.MarkPriceRepository;
import cc.newex.dax.perpetual.domain.MarkPrice;
import cc.newex.dax.perpetual.service.MarkPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author newex-team
 * @date 2019-01-07 21:55:01
 */
@Slf4j
@Service
public class MarkPriceServiceImpl extends AbstractCrudService<MarkPriceRepository, MarkPrice, MarkPriceExample, Long> implements MarkPriceService {
    @Autowired
    private MarkPriceRepository markPriceRepository;

    @Override
    protected MarkPriceExample getPageExample(final String fieldName, final String keyword) {
        final MarkPriceExample example = new MarkPriceExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}