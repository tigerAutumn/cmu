package cc.newex.dax.extra.service.admin.currency.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.currency.CurrencyTrendInfoExample;
import cc.newex.dax.extra.data.currency.CurrencyTrendInfoRepository;
import cc.newex.dax.extra.domain.currency.CurrencyTrendInfo;
import cc.newex.dax.extra.service.admin.currency.CurrencyTrendInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 项目动态信息表 服务实现
 *
 * @author mbg.generated
 * @date 2018-08-21 11:44:03
 */
@Slf4j
@Service
public class CurrencyTrendInfoAdminServiceImpl
        extends AbstractCrudService<CurrencyTrendInfoRepository, CurrencyTrendInfo, CurrencyTrendInfoExample, Long>
        implements CurrencyTrendInfoAdminService {

    @Autowired
    private CurrencyTrendInfoRepository currencyTrendInfoRepos;

    @Override
    protected CurrencyTrendInfoExample getPageExample(final String fieldName, final String keyword) {
        final CurrencyTrendInfoExample example = new CurrencyTrendInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
    
}