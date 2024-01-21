package cc.newex.dax.extra.service.admin.cms.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.cms.I18nMessageCodeExample;
import cc.newex.dax.extra.data.cms.I18nMessageCodeRepository;
import cc.newex.dax.extra.domain.cms.I18nMessageCode;
import cc.newex.dax.extra.service.admin.cms.I18nMessageCodeAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 本地化文本编码表 服务实现
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@Service
public class I18nMessageCodeAdminServiceImpl
        extends AbstractCrudService<I18nMessageCodeRepository, I18nMessageCode, I18nMessageCodeExample, Integer>
        implements I18nMessageCodeAdminService {

    @Autowired
    private I18nMessageCodeRepository i18nMessageCodeRepos;

    @Override
    protected I18nMessageCodeExample getPageExample(final String fieldName, final String keyword) {
        final I18nMessageCodeExample example = new I18nMessageCodeExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int countByPager(final PageInfo pager, final I18nMessageCodeExample example) {
        if (example == null) {
            return 0;
        }

        return this.i18nMessageCodeRepos.countByPager(pager, example);
    }

    @Override
    public int countByExample(final I18nMessageCodeExample example) {
        if (example == null) {
            return 0;
        }

        return this.i18nMessageCodeRepos.countByExample(example);
    }

    @Override
    public int count(final I18nMessageCode i18nMessageCode) {
        if (i18nMessageCode == null) {
            return 0;
        }

        final I18nMessageCodeExample example = I18nMessageCodeExample.toExample(i18nMessageCode);

        return this.countByExample(example);
    }

}