package cc.newex.dax.extra.service.admin.currency.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.currency.CurrencyProgressInfoExample;
import cc.newex.dax.extra.data.currency.CurrencyProgressInfoRepository;
import cc.newex.dax.extra.domain.currency.CurrencyProgressInfo;
import cc.newex.dax.extra.service.admin.currency.CurrencyProgressInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 项目进展信息表 服务实现
 *
 * @author mbg.generated
 * @date 2018-08-20 17:32:20
 */
@Slf4j
@Service
public class CurrencyProgressInfoAdminServiceImpl
        extends AbstractCrudService<CurrencyProgressInfoRepository, CurrencyProgressInfo, CurrencyProgressInfoExample, Long>
        implements CurrencyProgressInfoAdminService {

    @Autowired
    private CurrencyProgressInfoRepository currencyProgressInfoRepos;

    @Override
    protected CurrencyProgressInfoExample getPageExample(final String fieldName, final String keyword) {
        final CurrencyProgressInfoExample example = new CurrencyProgressInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

}