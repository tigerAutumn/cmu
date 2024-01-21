package cc.newex.wallet.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.wallet.criteria.CurrencyBalanceExample;
import cc.newex.wallet.dao.CurrencyBalanceRepository;
import cc.newex.wallet.pojo.CurrencyBalance;
import cc.newex.wallet.service.CurrencyBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-04-26
 */
@Slf4j
@Service
public class CurrencyBalanceServiceImpl
        extends AbstractCrudService<CurrencyBalanceRepository, CurrencyBalance, CurrencyBalanceExample, Integer>
        implements CurrencyBalanceService {

    @Autowired
    private CurrencyBalanceRepository currencyBalanceRepos;

    @Override
    protected CurrencyBalanceExample getPageExample(final String fieldName, final String keyword) {
        final CurrencyBalanceExample example = new CurrencyBalanceExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}