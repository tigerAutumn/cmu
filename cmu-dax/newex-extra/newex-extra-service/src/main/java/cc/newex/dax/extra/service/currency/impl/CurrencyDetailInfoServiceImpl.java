package cc.newex.dax.extra.service.currency.impl;

import cc.newex.dax.extra.criteria.currency.CurrencyDetailInfoExample;
import cc.newex.dax.extra.data.currency.CurrencyDetailInfoRepository;
import cc.newex.dax.extra.domain.currency.CurrencyDetailInfo;
import cc.newex.dax.extra.service.currency.CurrencyDetailInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
public class CurrencyDetailInfoServiceImpl implements CurrencyDetailInfoService {

    @Autowired
    private CurrencyDetailInfoRepository currencyDetailInfoRepository;

    @Override
    public CurrencyDetailInfo getByCode(final String code, final String locale) {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(locale)) {
            return null;
        }

        final CurrencyDetailInfoExample example = new CurrencyDetailInfoExample();
        example.createCriteria()
                .andCodeEqualTo(code)
                .andLocaleEqualTo(locale);

        return this.currencyDetailInfoRepository.selectOneByExample(example);
    }

    @Override
    public List<CurrencyDetailInfo> getByCode(final String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        final CurrencyDetailInfoExample example = new CurrencyDetailInfoExample();
        example.createCriteria()
                .andCodeEqualTo(code);

        return this.currencyDetailInfoRepository.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrUpdate(final CurrencyDetailInfo currencyDetailInfo) {
        if (currencyDetailInfo == null) {
            return 0;
        }

        int update = 0;

        final Long id = currencyDetailInfo.getId();

        if (id == null || id <= 0) {
            update = this.currencyDetailInfoRepository.insert(currencyDetailInfo);
        } else {
            update = this.currencyDetailInfoRepository.updateById(currencyDetailInfo);
        }

        return update;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrUpdate(final List<CurrencyDetailInfo> currencyDetailInfoList) {
        if (CollectionUtils.isEmpty(currencyDetailInfoList)) {
            return 0;
        }

        int update = 0;

        for (final CurrencyDetailInfo currencyDetailInfo : currencyDetailInfoList) {
            update += this.saveOrUpdate(currencyDetailInfo);
        }

        return update;
    }

}
