package cc.newex.dax.extra.service.admin.currency.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.currency.CurrencyDetailInfoExample;
import cc.newex.dax.extra.data.currency.CurrencyDetailInfoRepository;
import cc.newex.dax.extra.domain.currency.CurrencyDetailInfo;
import cc.newex.dax.extra.service.admin.currency.CurrencyDetailInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 全球数字货币详细信息表 服务实现
 *
 * @author newex-team
 * @date 2018-06-29
 */
@Slf4j
@Service
public class CurrencyDetailInfoAdminServiceImpl
        extends AbstractCrudService<CurrencyDetailInfoRepository, CurrencyDetailInfo, CurrencyDetailInfoExample, Long>
        implements CurrencyDetailInfoAdminService {

    @Autowired
    private CurrencyDetailInfoRepository currencyDetailInfoRepository;

    @Override
    protected CurrencyDetailInfoExample getPageExample(final String fieldName, final String keyword) {
        final CurrencyDetailInfoExample example = new CurrencyDetailInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    @Override
    public List<CurrencyDetailInfo> getByCode(final String code, String locale) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        if (StringUtils.isBlank(locale)) {
            locale = "en-us";
        }

        CurrencyDetailInfoExample example = this.toExample(code, locale);
        List<CurrencyDetailInfo> currencyDetailInfoList = this.currencyDetailInfoRepository.selectByExample(example);

        if (CollectionUtils.isEmpty(currencyDetailInfoList)) {
            if (!locale.equals("en-us")) {
                // 如果当前语言不支持，默认为en-us
                locale = "en-us";

                example = this.toExample(code, locale);
                currencyDetailInfoList = this.currencyDetailInfoRepository.selectByExample(example);
            }
        }

        return currencyDetailInfoList;
    }

    @Override
    public List<CurrencyDetailInfo> getAllByCode(final String code, final String locale) {
        final CurrencyDetailInfoExample example = this.toExample(code, locale);

        final List<CurrencyDetailInfo> currencyDetailInfoList = this.currencyDetailInfoRepository.selectByExample(example);

        return currencyDetailInfoList;
    }

    private CurrencyDetailInfoExample toExample(final String code, final String locale) {
        final CurrencyDetailInfoExample example = new CurrencyDetailInfoExample();
        final CurrencyDetailInfoExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeEqualTo(code);
        }

        if (StringUtils.isNotBlank(locale)) {
            criteria.andLocaleEqualTo(locale);
        }

        return example;
    }

}