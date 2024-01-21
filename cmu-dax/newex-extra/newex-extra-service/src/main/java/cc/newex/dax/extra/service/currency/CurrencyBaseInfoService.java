package cc.newex.dax.extra.service.currency;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.dax.extra.criteria.currency.CurrencyBaseInfoExample;
import cc.newex.dax.extra.domain.currency.CurrencyBaseInfo;

import java.util.List;

/**
 * @author liutiejun
 * @date 2018-08-21
 */
public interface CurrencyBaseInfoService {

    /**
     * 根据条件查询零条及多条数据
     *
     * @param example 查询条件参数
     * @return 记录列表
     */
    List<CurrencyBaseInfo> getByExample(CurrencyBaseInfoExample example);

    /**
     * 分页查询
     *
     * @param pageInfo 分页参数
     * @param example  where条件参数
     * @return 分页记录列表
     */
    List<CurrencyBaseInfo> getByPage(PageInfo pageInfo, CurrencyBaseInfoExample example);

    /**
     * 根据币种简称查询
     *
     * @param code
     * @return
     */
    CurrencyBaseInfo getByCode(String code);

    /**
     * 新增或者更新
     *
     * @param currencyBaseInfo
     * @return
     */
    int saveOrUpdate(CurrencyBaseInfo currencyBaseInfo);

}
