package cc.newex.dax.extra.service.admin.currency.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.currency.CurrencyBaseInfoExample;
import cc.newex.dax.extra.criteria.currency.CurrencyDetailInfoExample;
import cc.newex.dax.extra.criteria.currency.CurrencyProgressInfoExample;
import cc.newex.dax.extra.criteria.currency.CurrencyTrendInfoExample;
import cc.newex.dax.extra.data.currency.CurrencyBaseInfoRepository;
import cc.newex.dax.extra.data.currency.CurrencyDetailInfoRepository;
import cc.newex.dax.extra.data.currency.CurrencyProgressInfoRepository;
import cc.newex.dax.extra.data.currency.CurrencyTrendInfoRepository;
import cc.newex.dax.extra.domain.currency.CurrencyBaseInfo;
import cc.newex.dax.extra.service.admin.currency.CurrencyBaseInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 全球数字货币基本信息表 服务实现
 *
 * @author newex-team
 * @date 2018-06-29
 */
@Slf4j
@Service
public class CurrencyBaseInfoAdminServiceImpl
        extends AbstractCrudService<CurrencyBaseInfoRepository, CurrencyBaseInfo, CurrencyBaseInfoExample, Long>
        implements CurrencyBaseInfoAdminService {

    @Autowired
    private CurrencyBaseInfoRepository currencyBaseInfoRepository;

    @Autowired
    private CurrencyDetailInfoRepository currencyDetailInfoRepository;

    @Autowired
    private CurrencyProgressInfoRepository currencyProgressInfoRepository;

    @Autowired
    private CurrencyTrendInfoRepository currencyTrendInfoRepository;

    @Override
    protected CurrencyBaseInfoExample getPageExample(final String fieldName, final String keyword) {
        final CurrencyBaseInfoExample example = new CurrencyBaseInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    /**
     * 按币种删除数据
     *
     * @param code
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByCode(final String code) {
        final int result = this.removeBaseInfo(code);
        this.removeDetailInfo(code);
        this.removeProgressInfo(code);
        this.removeTrendInfo(code);

        return result;
    }

    private int removeBaseInfo(final String code) {
        final CurrencyBaseInfoExample example = new CurrencyBaseInfoExample();
        final CurrencyBaseInfoExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }

        return this.currencyBaseInfoRepository.deleteByExample(example);
    }

    private int removeDetailInfo(final String code) {
        final CurrencyDetailInfoExample example = new CurrencyDetailInfoExample();
        final CurrencyDetailInfoExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }

        return this.currencyDetailInfoRepository.deleteByExample(example);
    }

    private int removeProgressInfo(final String code) {
        final CurrencyProgressInfoExample example = new CurrencyProgressInfoExample();
        final CurrencyProgressInfoExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }

        return this.currencyProgressInfoRepository.deleteByExample(example);
    }

    private int removeTrendInfo(final String code) {
        final CurrencyTrendInfoExample example = new CurrencyTrendInfoExample();
        final CurrencyTrendInfoExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }

        return this.currencyTrendInfoRepository.deleteByExample(example);
    }

    @Override
    public List<CurrencyBaseInfo> getByCode(final String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        final CurrencyBaseInfoExample example = new CurrencyBaseInfoExample();
        final CurrencyBaseInfoExample.Criteria criteria = example.createCriteria();

        criteria.andCodeEqualTo(code);

        return this.currencyBaseInfoRepository.selectByExample(example);
    }

}