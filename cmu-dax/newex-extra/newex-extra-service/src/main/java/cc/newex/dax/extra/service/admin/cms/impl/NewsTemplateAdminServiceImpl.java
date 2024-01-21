package cc.newex.dax.extra.service.admin.cms.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.cms.NewsTemplateExample;
import cc.newex.dax.extra.data.cms.NewsTemplateRepository;
import cc.newex.dax.extra.domain.cms.NewsTemplate;
import cc.newex.dax.extra.service.admin.cms.NewsTemplateAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 新闻模板表 服务实现
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@Service
public class NewsTemplateAdminServiceImpl
        extends AbstractCrudService<NewsTemplateRepository, NewsTemplate, NewsTemplateExample, Integer>
        implements NewsTemplateAdminService {

    @Autowired
    private NewsTemplateRepository newsTemplateRepos;

    @Override
    protected NewsTemplateExample getPageExample(final String fieldName, final String keyword) {
        final NewsTemplateExample example = new NewsTemplateExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    @Override
    public int countByPager(final PageInfo pager, final NewsTemplateExample example) {
        if (example == null) {
            return 0;
        }

        return this.newsTemplateRepos.countByPager(pager, example);
    }

    @Override
    public int countByExample(final NewsTemplateExample example) {
        if (example == null) {
            return 0;
        }

        return this.newsTemplateRepos.countByExample(example);
    }

    @Override
    public int count(final NewsTemplate newsTemplate) {
        if (newsTemplate == null) {
            return 0;
        }

        final NewsTemplateExample example = NewsTemplateExample.toExample(newsTemplate);

        return this.countByExample(example);
    }

}