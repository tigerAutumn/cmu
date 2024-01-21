package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.CurrencyPairExample;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 合约币对 服务接口
 *
 * @author newex -team
 * @date 2018 -10-30 17:21:53
 */
public interface CurrencyPairService
        extends CrudService<CurrencyPair, CurrencyPairExample, Integer> {

    /**
     * Add currency pair int.
     *
     * @param currencyPair the currency pair
     * @return the int
     */
    int addCurrencyPair(final CurrencyPair currencyPair);

    /**
     * Update currency pair int.
     *
     * @param currencyPair the currency pair
     * @return the int
     */
    int updateCurrencyPair(final CurrencyPair currencyPair);

    /**
     * 检查档位是否合法
     *
     * @param pairCode the pair code
     * @param gear     the gear
     * @return the boolean
     */
    boolean checkGear(final String pairCode, final BigDecimal gear);

    /**
     * 获取开仓保证金率
     *
     * @param pairCode 唯一名称
     * @param gear     档位价值
     * @param lever    杠杆
     * @return the open margin rate
     */
    GearRate getOpenMarginRate(final String pairCode, final BigDecimal gear, final BigDecimal lever);

    /**
     * 获取可用的币对信息
     *
     * @return online currency pair
     */
    List<CurrencyPair> getOnlineCurrencyPair();

    /**
     * 获取币对信息
     *
     * @param pairCode the pair code
     * @return online currency pair
     */
    List<CurrencyPair> getOnlineCurrencyPair(String... pairCode);

    /**
     * Load db.
     */
    void loadDb();

    /**
     * 通过 pairCode 获取币对信息，如果币对不存在返回 null
     *
     * @param pairCode paircode 如 p_btc_usdt
     * @return by pair code
     */
    CurrencyPair getByPairCode(String pairCode);

    /**
     * 获取所有的合约币对的PairCode
     *
     * @return list list
     */
    List<String> listCurrencyPairCode();

    /**
     * List currency pair base code list.
     *
     * @return the list
     */
    List<String> listCurrencyPairBaseCode();

    /**
     * The type Gear rate.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GearRate {
        /**
         * 开仓保证金率
         */
        private BigDecimal entryRate;
        /**
         * 维持保证金率
         */
        private BigDecimal maintainRate;
    }
}
