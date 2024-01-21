package cc.newex.dax.extra.service.currency.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.dax.extra.criteria.currency.CurrencyTrendInfoExample;
import cc.newex.dax.extra.data.currency.CurrencyTrendInfoRepository;
import cc.newex.dax.extra.domain.currency.CurrencyTrendInfo;
import cc.newex.dax.extra.service.currency.CurrencyTrendInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liutiejun
 * @date 2018-08-21
 */
@Slf4j
@Service
public class CurrencyTrendInfoServiceImpl implements CurrencyTrendInfoService {

    @Autowired
    private CurrencyTrendInfoRepository currencyTrendInfoRepository;

    @Override
    public List<CurrencyTrendInfo> getByPage(final PageInfo pageInfo, final CurrencyTrendInfoExample example) {
        pageInfo.setTotals(this.currencyTrendInfoRepository.countByPager(pageInfo, example));

        return this.currencyTrendInfoRepository.selectByPager(pageInfo, example);
    }

    @Override
    public int countByExample(final CurrencyTrendInfoExample example) {
        if (example == null) {
            return 0;
        }

        return this.currencyTrendInfoRepository.countByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(final CurrencyTrendInfo currencyTrendInfo) {
        if (currencyTrendInfo == null) {
            return 0;
        }

        this.setDefaultValue(currencyTrendInfo);

        return this.currencyTrendInfoRepository.insert(currencyTrendInfo);
    }

    private void setDefaultValue(final CurrencyTrendInfo currencyTrendInfo) {
        if (currencyTrendInfo == null) {
            return;
        }

        final String content = StringUtils.defaultString(currencyTrendInfo.getContent(), "");

        currencyTrendInfo.setContent(content);
    }

    /**
     * 根据币种Code和语言和状态查询
     *
     * @param currencyCode
     * @param locale
     * @param status
     * @return
     */
    @Override
    public List<CurrencyTrendInfo> listByCodeAndLocaleAndStatus(final String currencyCode, final String locale, final Integer status, final PageInfo pageInfo) {
        final CurrencyTrendInfoExample example = new CurrencyTrendInfoExample();
        example.createCriteria().andCodeEqualTo(currencyCode).andLocaleEqualTo(locale).andStatusEqualTo(status);
        pageInfo.setTotals(this.currencyTrendInfoRepository.countByPager(pageInfo, example));
        return this.currencyTrendInfoRepository.selectByPager(pageInfo, example);
    }
}
