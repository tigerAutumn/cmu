package cc.newex.dax.extra.service.admin.currency.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.currency.CurrencyTagExample;
import cc.newex.dax.extra.criteria.currency.ProductTagExample;
import cc.newex.dax.extra.criteria.currency.TagInfoExample;
import cc.newex.dax.extra.data.currency.CurrencyTagRepository;
import cc.newex.dax.extra.data.currency.ProductTagRepository;
import cc.newex.dax.extra.data.currency.TagInfoRepository;
import cc.newex.dax.extra.domain.currency.TagInfo;
import cc.newex.dax.extra.service.admin.currency.TagInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 标签表 服务实现
 *
 * @author liutiejun
 * @date 2018-07-19
 */
@Slf4j
@Service
public class TagInfoAdminServiceImpl
        extends AbstractCrudService<TagInfoRepository, TagInfo, TagInfoExample, Long>
        implements TagInfoAdminService {

    @Autowired
    private TagInfoRepository tagInfoRepository;

    @Autowired
    private ProductTagRepository productTagRepository;

    @Autowired
    private CurrencyTagRepository currencyTagRepository;

    @Override
    protected TagInfoExample getPageExample(final String fieldName, final String keyword) {
        final TagInfoExample example = new TagInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    @Override
    public List<TagInfo> getByTagCategoryCode(final String tagCategoryCode, final String locale) {
        if (StringUtils.isAllBlank(tagCategoryCode, locale)) {
            return null;
        }

        final TagInfoExample example = new TagInfoExample();
        final TagInfoExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(tagCategoryCode)) {
            criteria.andTagCategoryCodeEqualTo(tagCategoryCode);
        }

        if (StringUtils.isNotBlank(locale)) {
            criteria.andLocaleEqualTo(locale);
        }
        example.setOrderByClause("sort");

        return this.tagInfoRepository.selectByExample(example);
    }

    @Override
    public List<TagInfo> getByTagCode(final String tagCode, final String locale) {
        final TagInfoExample example = new TagInfoExample();
        final TagInfoExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(tagCode)){
            criteria.andCodeEqualTo(tagCode);
        }
        if(StringUtils.isNotBlank(locale)){
            criteria.andLocaleEqualTo(locale);
        }
        return this.tagInfoRepository.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeTagInfoById(final Long id,final String code) {
        if(id == null || StringUtils.isBlank(code)){
            return 0;
        }
        List<TagInfo> list = this.getByTagCode(code,null);
        if(CollectionUtils.isNotEmpty(list) && list.size() == 1 && id.intValue() == list.get(0).getId().longValue()){

            CurrencyTagExample currencyTagExample = new CurrencyTagExample();
            CurrencyTagExample.Criteria criteria1 = currencyTagExample.createCriteria();
            criteria1.andTagCategoryCodeEqualTo(list.get(0).getTagCategoryCode());
            criteria1.andTagInfoCodeEqualTo(code);
            currencyTagRepository.deleteByExample(currencyTagExample);

            ProductTagExample productTagExample = new ProductTagExample();
            ProductTagExample.Criteria criteria2 = productTagExample.createCriteria();
            criteria2.andTagInfoCodeEqualTo(code);
            criteria2.andTagCategoryCodeEqualTo(list.get(0).getTagCategoryCode());
            productTagRepository.deleteByExample(productTagExample);

        }
        return this.tagInfoRepository.deleteById(id);
    }

}
