package cc.newex.dax.perpetual.service.common;

import cc.newex.dax.perpetual.domain.bean.Currency;

import java.util.List;

public interface CurrencyService {
    /**
     * 获取所有币种
     *
     * @return
     */
    List<Currency> cacheCurrencyAll();

    Currency getCurrencyByCode(String currencyCode);

    Currency getCurrencyById(long id);

    List<Currency> listCurrencies();

}
