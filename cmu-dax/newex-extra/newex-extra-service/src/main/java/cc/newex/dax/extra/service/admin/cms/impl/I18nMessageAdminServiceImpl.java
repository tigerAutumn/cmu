package cc.newex.dax.extra.service.admin.cms.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.cms.I18nMessageExample;
import cc.newex.dax.extra.data.cms.I18nMessageRepository;
import cc.newex.dax.extra.domain.cms.I18nMessage;
import cc.newex.dax.extra.service.admin.cms.I18nMessageAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 本地化文本表 服务实现
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@Service
public class I18nMessageAdminServiceImpl
        extends AbstractCrudService<I18nMessageRepository, I18nMessage, I18nMessageExample, Integer>
        implements I18nMessageAdminService {

    @Autowired
    private I18nMessageRepository i18nMessageRepos;

    @Override
    protected I18nMessageExample getPageExample(final String fieldName, final String keyword) {
        final I18nMessageExample example = new I18nMessageExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    @Override
    public int countByPager(final PageInfo pager, final I18nMessageExample example) {
        if (example == null) {
            return 0;
        }

        return this.i18nMessageRepos.countByPager(pager, example);
    }

    @Override
    public int countByExample(final I18nMessageExample example) {
        if (example == null) {
            return 0;
        }

        return this.i18nMessageRepos.countByExample(example);
    }

    @Override
    public int count(final I18nMessage i18nMessage) {
        if (i18nMessage == null) {
            return 0;
        }

        final I18nMessageExample example = I18nMessageExample.toExample(i18nMessage);

        return this.countByExample(example);
    }

}