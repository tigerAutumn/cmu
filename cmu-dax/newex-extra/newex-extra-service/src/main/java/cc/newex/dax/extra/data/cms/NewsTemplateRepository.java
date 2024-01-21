package cc.newex.dax.extra.data.cms;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cms.NewsTemplateExample;
import cc.newex.dax.extra.domain.cms.NewsTemplate;
import org.springframework.stereotype.Repository;

/**
 * 新闻模板表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Repository
public interface NewsTemplateRepository
        extends CrudRepository<NewsTemplate, NewsTemplateExample, Integer> {
}