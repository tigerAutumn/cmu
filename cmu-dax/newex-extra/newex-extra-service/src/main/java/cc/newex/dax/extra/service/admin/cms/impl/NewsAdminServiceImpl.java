package cc.newex.dax.extra.service.admin.cms.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.cms.NewsExample;
import cc.newex.dax.extra.data.cms.NewsRepository;
import cc.newex.dax.extra.domain.cms.News;
import cc.newex.dax.extra.service.admin.cms.NewsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 新闻文章表 服务实现
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@Service
public class NewsAdminServiceImpl
        extends AbstractCrudService<NewsRepository, News, NewsExample, Integer>
        implements NewsAdminService {

    @Autowired
    private NewsRepository newsRepos;

    @Override
    protected NewsExample getPageExample(final String fieldName, final String keyword) {
        final NewsExample example = new NewsExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    @Override
    public int countByPager(final PageInfo pager, final NewsExample example) {
        if (example == null) {
            return 0;
        }

        return this.newsRepos.countByPager(pager, example);
    }

    @Override
    public int countByExample(final NewsExample example) {
        if (example == null) {
            return 0;
        }

        return this.newsRepos.countByExample(example);
    }

    @Override
    public int count(final News news) {
        if (news == null) {
            return 0;
        }

        final NewsExample example = NewsExample.toExample(news);

        return this.countByExample(example);
    }

    @Override
    public List<News> getByUid(String locale, final String uid) {
        if (StringUtils.isBlank(uid)) {
            return null;
        }

        if (StringUtils.isBlank(locale)) {
            locale = "en-us";
        }

        final NewsExample example = new NewsExample();
        final NewsExample.Criteria criteria = example.createCriteria();

        criteria.andLocaleEqualTo(locale);
        criteria.andUidEqualTo(uid);

        return this.newsRepos.selectByExample(example);
    }
}