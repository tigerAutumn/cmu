package cc.newex.dax.extra.service.admin.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.cms.NewsCategoryExample;
import cc.newex.dax.extra.domain.cms.NewsCategory;

/**
 * 新闻或文章类别表 服务接口
 *
 * @author newex-team
 * @date 2018-05-30
 */
public interface NewsCategoryAdminService
        extends CrudService<NewsCategory, NewsCategoryExample, Integer> {

    /**
     * 获取当前分页查询的总记录数
     *
     * @param pager
     * @param example 查询条件参数
     * @return 总记录数
     */
    int countByPager(PageInfo pager, NewsCategoryExample example);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param example 查询条件参数
     * @return 总记录数
     */
    int countByExample(NewsCategoryExample example);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param newsCategory 查询条件参数
     * @return 总记录数
     */
    int count(NewsCategory newsCategory);

}
