package cc.newex.dax.extra.data.cms;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cms.NewsExample;
import cc.newex.dax.extra.domain.cms.News;
import org.springframework.stereotype.Repository;

/**
 * 新闻文章表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Repository
public interface NewsRepository
        extends CrudRepository<News, NewsExample, Integer> {
}