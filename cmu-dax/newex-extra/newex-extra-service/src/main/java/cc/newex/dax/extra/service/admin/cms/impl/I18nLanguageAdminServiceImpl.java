package cc.newex.dax.extra.service.admin.cms.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.cms.I18nLanguageExample;
import cc.newex.dax.extra.data.cms.I18nLanguageRepository;
import cc.newex.dax.extra.domain.cms.I18nLanguage;
import cc.newex.dax.extra.service.admin.cms.I18nLanguageAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 本地化语言表 服务实现
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@Service
public class I18nLanguageAdminServiceImpl
        extends AbstractCrudService<I18nLanguageRepository, I18nLanguage, I18nLanguageExample, Integer>
        implements I18nLanguageAdminService {

    @Autowired
    private I18nLanguageRepository i18nLanguageRepos;

    @Override
    protected I18nLanguageExample getPageExample(final String fieldName, final String keyword) {
        final I18nLanguageExample example = new I18nLanguageExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int countByPager(final PageInfo pager, final I18nLanguageExample example) {
        if (example == null) {
            return 0;
        }

        return this.i18nLanguageRepos.countByPager(pager, example);
    }

    @Override
    public int countByExample(final I18nLanguageExample example) {
        if (example == null) {
            return 0;
        }

        return this.i18nLanguageRepos.countByExample(example);
    }

    @Override
    public int count(final I18nLanguage i18nLanguage) {
        if (i18nLanguage == null) {
            return 0;
        }

        final I18nLanguageExample example = I18nLanguageExample.toExample(i18nLanguage);

        return this.countByExample(example);
    }

}