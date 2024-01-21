package cc.newex.dax.users.service.membership;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.DictCountryExample;
import cc.newex.dax.users.domain.DictCountry;
import cc.newex.dax.users.dto.admin.DictCountryReqDTO;

import java.util.List;

/**
 * 全球国家(地区)表 服务接口
 *
 * @author newex-team
 * @date 2018-04-08
 */
public interface DictCountryService
        extends CrudService<DictCountry, DictCountryExample, Integer> {

    /**
     * 获取当前语言所有国家信息列表
     *
     * @param locale 语言(zh_cn/en_us等)
     * @return 国家列表
     */
    List<DictCountry> getAllByLocale(String locale);

    /**
     * 分页查询列表
     *
     * @param pageInfo
     * @param dictCountryReqDTO
     * @return
     */
    List<DictCountry> listByPage(PageInfo pageInfo, DictCountryReqDTO dictCountryReqDTO);

    /**
     * 获取受限制的国家列表
     *
     * @param locale
     * @return
     */
    List<DictCountry> limitCountries(String locale);

}
