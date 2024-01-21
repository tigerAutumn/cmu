package cc.newex.dax.extra.service.currency.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.dax.extra.criteria.currency.CurrencyBaseInfoExample;
import cc.newex.dax.extra.data.currency.CurrencyBaseInfoRepository;
import cc.newex.dax.extra.domain.currency.CurrencyBaseInfo;
import cc.newex.dax.extra.enums.currency.CurrencyStatusEnum;
import cc.newex.dax.extra.service.currency.CurrencyBaseInfoService;
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
public class CurrencyBaseInfoServiceImpl implements CurrencyBaseInfoService {

    @Autowired
    private CurrencyBaseInfoRepository currencyBaseInfoRepository;

    @Override
    public List<CurrencyBaseInfo> getByExample(final CurrencyBaseInfoExample example) {
        return this.currencyBaseInfoRepository.selectByExample(example);
    }

    @Override
    public List<CurrencyBaseInfo> getByPage(final PageInfo pageInfo, final CurrencyBaseInfoExample example) {
        pageInfo.setTotals(this.currencyBaseInfoRepository.countByPager(pageInfo, example));

        return this.currencyBaseInfoRepository.selectByPager(pageInfo, example);
    }

    @Override
    public CurrencyBaseInfo getByCode(final String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        final CurrencyBaseInfoExample example = new CurrencyBaseInfoExample();
        example.createCriteria()
                .andCodeEqualTo(code)
                .andStatusEqualTo(CurrencyStatusEnum.PUBLISH.getCode());

        return this.currencyBaseInfoRepository.selectOneByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrUpdate(final CurrencyBaseInfo currencyBaseInfo) {
        if (currencyBaseInfo == null) {
            return 0;
        }

        int update = 0;

        final Long id = currencyBaseInfo.getId();

        if (id == null || id <= 0) {
            update = this.currencyBaseInfoRepository.insert(currencyBaseInfo);
        } else {
            update = this.currencyBaseInfoRepository.updateById(currencyBaseInfo);
        }

        return update;
    }
}
