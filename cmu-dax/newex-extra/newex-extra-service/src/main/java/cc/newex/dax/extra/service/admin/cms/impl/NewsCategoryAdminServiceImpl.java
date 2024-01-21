package cc.newex.dax.extra.service.admin.cms.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.cms.NewsCategoryExample;
import cc.newex.dax.extra.data.cms.NewsCategoryRepository;
import cc.newex.dax.extra.domain.cms.NewsCategory;
import cc.newex.dax.extra.service.admin.cms.NewsCategoryAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 新闻或文章类别表 服务实现
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@Service
public class NewsCategoryAdminServiceImpl
        extends AbstractCrudService<NewsCategoryRepository, NewsCategory, NewsCategoryExample, Integer>
        implements NewsCategoryAdminService {

    @Autowired
    private NewsCategoryRepository newsCategoryRepos;

    @Override
    protected NewsCategoryExample getPageExample(final String fieldName, final String keyword) {
        final NewsCategoryExample example = new NewsCategoryExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    @Override
    public int countByPager(final PageInfo pager, final NewsCategoryExample example) {
        if (example == null) {
            return 0;
        }

        return this.newsCategoryRepos.countByPager(pager, example);
    }

    @Override
    public int countByExample(final NewsCategoryExample example) {
        if (example == null) {
            return 0;
        }

        return this.newsCategoryRepos.countByExample(example);
    }

    @Override
    public int count(final NewsCategory newsCategory) {
        if (newsCategory == null) {
            return 0;
        }

        final NewsCategoryExample example = NewsCategoryExample.toExample(newsCategory);

        return this.countByExample(example);
    }

}