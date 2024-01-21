package cc.newex.dax.extra.service.currency;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.dax.extra.criteria.currency.CurrencyTrendInfoExample;
import cc.newex.dax.extra.domain.currency.CurrencyTrendInfo;

import java.util.List;

/**
 * The interface Currency trend info service.
 *
 * @author liutiejun
 * @date 2018 -08-21
 */
public interface CurrencyTrendInfoService {

    /**
     * 分页查询
     *
     * @param pageInfo 分页参数
     * @param example  where条件参数
     * @return 分页记录列表 by page
     */
    List<CurrencyTrendInfo> getByPage(PageInfo pageInfo, CurrencyTrendInfoExample example);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param example 查询条件参数
     * @return 总记录数 int
     */
    int countByExample(CurrencyTrendInfoExample example);

    /**
     * 新增
     *
     * @param currencyTrendInfo the currency trend info
     * @return int
     */
    int save(CurrencyTrendInfo currencyTrendInfo);

    /**
     * 根据币种Code和语言和状态查询
     *
     * @param currencyCode the currency code
     * @param locale       the locale
     * @param status       the status
     * @param pageInfo     the page info
     * @return list
     */
    List<CurrencyTrendInfo> listByCodeAndLocaleAndStatus(String currencyCode, String locale, Integer status, PageInfo pageInfo);
}
