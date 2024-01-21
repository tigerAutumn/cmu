package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.dictionary.consts.BrokerIdConsts;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.AssetsFeeRateExample;
import cc.newex.dax.perpetual.data.AssetsFeeRateRepository;
import cc.newex.dax.perpetual.domain.*;
import cc.newex.dax.perpetual.service.*;
import cc.newex.dax.perpetual.util.BigDecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 资金费率流水表 服务实现
 *
 * @author newex-team
 * @date 2018-11-20 15:55:43
 */
@Slf4j
@Service
public class AssetsFeeRateServiceImpl extends AbstractCrudService<AssetsFeeRateRepository, AssetsFeeRate, AssetsFeeRateExample, Long> implements AssetsFeeRateService {
    @Autowired
    private AssetsFeeRateRepository assetsFeeRateRepository;

    @Autowired
    private HistoryMarkPriceService historyMarkPriceService;

    @Autowired
    private CurrencyPairService currencyPairService;

    @Autowired
    private UserBalanceService userBalanceService;

    @Autowired
    private UserPositionService userPositionService;

    @Override
    protected AssetsFeeRateExample getPageExample(final String fieldName, final String keyword) {
        final AssetsFeeRateExample example = new AssetsFeeRateExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public void scheduleFeeRate(final Contract contract, final Date start, final Date end) {
        final CurrencyPair currencyPair = this.currencyPairService.getByPairCode(contract.getPairCode());
        if (currencyPair == null) {
            AssetsFeeRateServiceImpl.log.warn("currency not found, contractCode : {}", contract.getContractCode());
            return;
        }
        final BigDecimal feeRate = this.takeFeeRate(contract, currencyPair, start, end);
        final UserBalance userBalance = this.userBalanceService.get(contract.getBase(), currencyPair.getInsuranceAccount(), BrokerIdConsts.COIN_MEX);
        final BigDecimal insuranceSize = userBalance == null ? BigDecimal.ZERO : userBalance.getAvailableBalance();
        final BigDecimal amount = this.userPositionService.sumTotalUserPosition(contract.getContractCode());
        final Date date = new Date();
        this.add(AssetsFeeRate.builder().createdDate(date).feeRate(feeRate).modifyDate(date)
                .insuranceSize(insuranceSize)
                .userPositionAmount(amount)
                .contractCode(contract.getContractCode()).timeIndex(date).build());
    }

    @Override
    public BigDecimal takeFeeRate(final String contractCode) {
        final AssetsFeeRateExample example = new AssetsFeeRateExample();
        final AssetsFeeRateExample.Criteria criteria = example.createCriteria();
        criteria.andContractCodeEqualTo(contractCode);
        example.setOrderByClause("id desc");
        final AssetsFeeRate feeRate = this.getOneByExample(example);
        if (feeRate == null) {
            return BigDecimal.ZERO;
        }
        return feeRate.getFeeRate();
    }


    @Override
    public AssetsFeeRate get(final String contractCode) {
        final AssetsFeeRateExample example = new AssetsFeeRateExample();
        final AssetsFeeRateExample.Criteria criteria = example.createCriteria();
        criteria.andContractCodeEqualTo(contractCode);
        example.setOrderByClause("id desc");
        return this.getOneByExample(example);
    }

    @Override
    public BigDecimal takeFeeRate(final Contract contract, final CurrencyPair currencyPair, final Date start, final Date end) {
        final List<HistoryMarkPrice> historyMarkPrices = this.historyMarkPriceService.listHistoryMarkPrice(contract, start, end);
        if (CollectionUtils.isEmpty(historyMarkPrices)) {
            AssetsFeeRateServiceImpl.log.warn("history mark price is empty");
            return BigDecimal.ZERO;
        }

        BigDecimal sum = BigDecimal.ZERO;

        for (final HistoryMarkPrice h : historyMarkPrices) {
            sum = sum.add(h.getPremiumIndex());
        }

        // 溢价指数加权值
        final BigDecimal preminumIndex = BigDecimalUtil.divide(sum, new BigDecimal(historyMarkPrices.size()));
        final BigDecimal record = this.takeFeeRate(contract.getContractCode());
        return this.takeFeeRate(currencyPair, record, preminumIndex);
    }

    @Override
    public BigDecimal takeFeeRate(final CurrencyPair currencyPair, final BigDecimal lastFeeRate, final BigDecimal preminumIndex) {
        // 资金费率 需要在配置的最大值与最小值之间
        BigDecimal feeRate = preminumIndex.add(BigDecimalUtil.clamp(currencyPair.getInterestRate().subtract(preminumIndex),
                currencyPair.getPremiumMinRange(), currencyPair.getPremiumMaxRange()));
        // 资金费率在 维持保证金率 *（绝对的资金费率上限为 起始保证金 - 维持保证金 的 百分比）
        feeRate = BigDecimalUtil.clamp(feeRate, currencyPair.getMaintainRate().multiply(currencyPair.getFundingCeiling()).negate(),
                currencyPair.getMaintainRate().multiply(currencyPair.getFundingCeiling()));

        // 资金费率在变化率在 维持保证金率*0.0075 之间
        final BigDecimal temp = currencyPair.getMaintainRate().multiply(new BigDecimal("0.75"));
        return BigDecimalUtil.clamp(feeRate, lastFeeRate.subtract(temp), lastFeeRate.add(temp)).setScale(6, BigDecimal.ROUND_DOWN);
    }
}