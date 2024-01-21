package cc.newex.dax.extra.service.currency;

import cc.newex.dax.extra.domain.currency.CurrencyProgressInfo;

import java.util.List;

/**
 * @author liutiejun
 * @date 2018-08-21
 */
public interface CurrencyProgressInfoService {

    /**
     * 根据币种简称查询
     *
     * @param code
     * @param locale
     * @return
     */
    List<CurrencyProgressInfo> getByCode(String code, String locale);

    /**
     * 新增
     *
     * @param currencyProgressInfo
     * @return
     */
    int save(CurrencyProgressInfo currencyProgressInfo);

}
