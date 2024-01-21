package cc.newex.dax.market.service;

import java.util.List;
import java.util.Optional;

import cc.newex.dax.market.domain.Currency;

/**
 * 币种表 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface CurrencyService {

    List<Currency> selectCurrencyToRedis();

    Optional<Currency> getCurrencyById(byte id);
}
