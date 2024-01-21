package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.AssetsFeeRateExample;
import cc.newex.dax.perpetual.domain.AssetsFeeRate;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金费率流水表 服务接口
 *
 * @author newex-team
 * @date 2018-11-20 15:55:43
 */
public interface AssetsFeeRateService extends CrudService<AssetsFeeRate, AssetsFeeRateExample, Long> {
    /**
     * 通过溢价指数计算资金费率
     *
     * @param contract
     */
    void scheduleFeeRate(Contract contract, Date start, Date end);

    /**
     * 获取资金费率
     *
     * @param contractCode
     * @return
     */
    BigDecimal takeFeeRate(String contractCode);

    /**
     * 查询资金费率
     *
     * @param contractCode
     * @return
     */
    AssetsFeeRate get(final String contractCode);

    /**
     * 计算资金费率
     *
     * @param contract
     * @param currencyPair
     * @param start
     * @param end
     * @return
     */
    BigDecimal takeFeeRate(Contract contract, CurrencyPair currencyPair, Date start, Date end);

    /**
     * 计算资金费率
     *
     * @param currencyPair
     * @param lastFeeRate   上一次资金费率
     * @param preminumIndex 资金费率加权值
     * @return
     */
    BigDecimal takeFeeRate(CurrencyPair currencyPair, BigDecimal lastFeeRate, BigDecimal preminumIndex);
}