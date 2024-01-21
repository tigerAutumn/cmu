package cc.newex.dax.extra.service.admin.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.cms.I18nMessageCodeExample;
import cc.newex.dax.extra.domain.cms.I18nMessageCode;

/**
 * 本地化文本编码表 服务接口
 *
 * @author newex-team
 * @date 2018-05-30
 */
public interface I18nMessageCodeAdminService
        extends CrudService<I18nMessageCode, I18nMessageCodeExample, Integer> {

    /**
     * 获取当前分页查询的总记录数
     *
     * @param pager
     * @param example 查询条件参数
     * @return 总记录数
     */
    int countByPager(PageInfo pager, I18nMessageCodeExample example);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param example 查询条件参数
     * @return 总记录数
     */
    int countByExample(I18nMessageCodeExample example);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param i18nMessageCode 查询条件参数
     * @return 总记录数
     */
    int count(I18nMessageCode i18nMessageCode);

}
