package cc.newex.dax.extra.service.admin.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.cms.NewsTemplateExample;
import cc.newex.dax.extra.domain.cms.NewsTemplate;

/**
 * 新闻模板表 服务接口
 *
 * @author newex-team
 * @date 2018-05-30
 */
public interface NewsTemplateAdminService
        extends CrudService<NewsTemplate, NewsTemplateExample, Integer> {

    /**
     * 获取当前分页查询的总记录数
     *
     * @param pager
     * @param example 查询条件参数
     * @return 总记录数
     */
    int countByPager(PageInfo pager, NewsTemplateExample example);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param example 查询条件参数
     * @return 总记录数
     */
    int countByExample(NewsTemplateExample example);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param newsTemplate 查询条件参数
     * @return 总记录数
     */
    int count(NewsTemplate newsTemplate);

}
