package cc.newex.dax.extra.service.admin.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.cms.NewsExample;
import cc.newex.dax.extra.domain.cms.News;

import java.util.List;

/**
 * 新闻文章表 服务接口
 *
 * @author newex-team
 * @date 2018-05-30
 */
public interface NewsAdminService
        extends CrudService<News, NewsExample, Integer> {

    /**
     * 获取当前分页查询的总记录数
     *
     * @param pager
     * @param example 查询条件参数
     * @return 总记录数
     */
    int countByPager(PageInfo pager, NewsExample example);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param example 查询条件参数
     * @return 总记录数
     */
    int countByExample(NewsExample example);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param news 查询条件参数
     * @return 总记录数
     */
    int count(News news);

    /**
     * 条件查询
     *
     * @param locale
     * @param uid
     * @return
     */
    List<News> getByUid(String locale, String uid);

}
