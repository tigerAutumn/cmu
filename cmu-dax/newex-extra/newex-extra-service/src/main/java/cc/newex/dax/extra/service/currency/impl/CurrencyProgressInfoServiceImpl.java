package cc.newex.dax.extra.service.currency.impl;

import cc.newex.dax.extra.criteria.currency.CurrencyProgressInfoExample;
import cc.newex.dax.extra.data.currency.CurrencyProgressInfoRepository;
import cc.newex.dax.extra.domain.currency.CurrencyProgressInfo;
import cc.newex.dax.extra.enums.currency.CurrencyStatusEnum;
import cc.newex.dax.extra.service.currency.CurrencyProgressInfoService;
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
public class CurrencyProgressInfoServiceImpl implements CurrencyProgressInfoService {

    @Autowired
    private CurrencyProgressInfoRepository currencyProgressInfoRepository;

    @Override
    public List<CurrencyProgressInfo> getByCode(final String code, final String locale) {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(locale)) {
            return null;
        }

        final CurrencyProgressInfoExample example = new CurrencyProgressInfoExample();
        example.setOrderByClause("publish_date desc");
        example.createCriteria()
                .andCodeEqualTo(code)
                .andLocaleEqualTo(locale)
                .andStatusEqualTo(CurrencyStatusEnum.PUBLISH.getCode());

        return this.currencyProgressInfoRepository.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(final CurrencyProgressInfo currencyProgressInfo) {
        if (currencyProgressInfo == null) {
            return 0;
        }

        return this.currencyProgressInfoRepository.insert(currencyProgressInfo);
    }

}
