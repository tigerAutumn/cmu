package cc.newex.dax.extra.service.currency;

import cc.newex.dax.extra.domain.currency.CurrencyDetailInfo;

import java.util.List;

/**
 * @author liutiejun
 * @date 2018-08-21
 */
public interface CurrencyDetailInfoService {

    /**
     * 根据币种简称查询
     *
     * @param code
     * @param locale
     * @return
     */
    CurrencyDetailInfo getByCode(String code, String locale);

    /**
     * 根据币种简称查询
     *
     * @param code
     * @return
     */
    List<CurrencyDetailInfo> getByCode(String code);

    /**
     * 新增或者更新
     *
     * @param currencyDetailInfo
     * @return
     */
    int saveOrUpdate(CurrencyDetailInfo currencyDetailInfo);

    /**
     * 新增或者更新
     *
     * @param currencyDetailInfoList
     * @return
     */
    int saveOrUpdate(List<CurrencyDetailInfo> currencyDetailInfoList);

}
