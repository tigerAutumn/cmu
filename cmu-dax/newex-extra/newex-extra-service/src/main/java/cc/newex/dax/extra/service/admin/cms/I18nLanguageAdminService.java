package cc.newex.dax.extra.service.admin.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.cms.I18nLanguageExample;
import cc.newex.dax.extra.domain.cms.I18nLanguage;

/**
 * 本地化语言表 服务接口
 *
 * @author newex-team
 * @date 2018-05-30
 */
public interface I18nLanguageAdminService
        extends CrudService<I18nLanguage, I18nLanguageExample, Integer> {

    /**
     * 获取当前分页查询的总记录数
     *
     * @param pager
     * @param example 查询条件参数
     * @return 总记录数
     */
    int countByPager(PageInfo pager, I18nLanguageExample example);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param example 查询条件参数
     * @return 总记录数
     */
    int countByExample(I18nLanguageExample example);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param i18nLanguage 查询条件参数
     * @return 总记录数
     */
    int count(I18nLanguage i18nLanguage);

}
