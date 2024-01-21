package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.HistoryMarkPriceExample;
import cc.newex.dax.perpetual.data.HistoryMarkPriceRepository;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.HistoryMarkPrice;
import cc.newex.dax.perpetual.domain.redis.DepthCacheBean;
import cc.newex.dax.perpetual.domain.redis.DepthLine;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import cc.newex.dax.perpetual.service.HistoryMarkPriceService;
import cc.newex.dax.perpetual.service.MarketDataShardingService;
import cc.newex.dax.perpetual.service.common.MarketService;
import cc.newex.dax.perpetual.util.BigDecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 溢价指数流水表 服务实现
 *
 * @author newex-team
 * @date 2018-11-15 19:24:22
 */
@Slf4j
@Service
public class HistoryMarkPriceServiceImpl extends AbstractCrudService<HistoryMarkPriceRepository, HistoryMarkPrice, HistoryMarkPriceExample, Long> implements HistoryMarkPriceService {
    @Autowired
    private HistoryMarkPriceRepository historyMarkPriceRepository;

    @Autowired
    private MarketDataShardingService marketDataShardingService;
    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private MarketService marketService;

    @Override
    protected HistoryMarkPriceExample getPageExample(final String fieldName, final String keyword) {
        final HistoryMarkPriceExample example = new HistoryMarkPriceExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public void scheduleMarketPrice(final Contract contract) {
        // 指数价格
        final MarkIndexPriceDTO markIndexPriceDTO = this.marketService.getMarkIndex(contract);
        if (markIndexPriceDTO == null) {
            HistoryMarkPriceServiceImpl.log.error("not found market price, contractCode : {}", contract.getContractCode());
            return;
        }

        final BigDecimal indexPrice = (BigDecimal) markIndexPriceDTO.getIndexPrice();

        final MarkIndexPriceDTO cacheObject = this.marketService.getMarkIndex(contract);
        final BigDecimal marketPriceCache = cacheObject.getMarkPrice();
        // 标记价格
        final BigDecimal marketPrice = marketPriceCache == null ? indexPrice : marketPriceCache;
        final CurrencyPair currencyPair = this.currencyPairService.getByPairCode(contract.getPairCode());
        if (currencyPair == null) {
            HistoryMarkPriceServiceImpl.log.error("not found currencyPair, pairCode : {}", contract.getPairCode());
            return;
        }
        // 深度加权值最大限额
        final BigDecimal premiumDepth = currencyPair.getPremiumDepth();
        // 深度值
        final DepthCacheBean depthCacheBean = this.marketDataShardingService.getDepthCacheBean(contract.getContractCode());
        // 卖方向
        final List<DepthLine> asks = depthCacheBean.getAsks().stream()
                .sorted(Comparator.comparing(DepthLine::getPrice)).collect(Collectors.toList());
        // 买方向
        final List<DepthLine> bids = depthCacheBean.getBids().stream()
                .sorted(Comparator.comparing(DepthLine::getPrice).reversed()).collect(Collectors.toList());
        // 卖方向价值
        final BigDecimal askAmount = this.sumValueDepthLineLise(asks);
        // 买方向价值
        final BigDecimal bidAmount = this.sumValueDepthLineLise(bids);
        // 有效的深度限额
        final BigDecimal minAmount = askAmount.min(bidAmount).min(premiumDepth);
        // 深度加权卖价
        final BigDecimal askPrem = this.premiumDepthPrice(asks, minAmount);
        // 深度加权买价
        final BigDecimal bidPrem = this.premiumDepthPrice(bids, minAmount);
        // 溢价指数 = （max(0, 深度加权买-标记价格) - max(0, 标记价格-深度加权卖)）/指数价格
        final BigDecimal premiumIndex = BigDecimalUtil.divide(BigDecimal.ZERO.max(bidPrem.subtract(marketPrice))
                .subtract(BigDecimal.ZERO.max(marketPrice.subtract(askPrem))), indexPrice).add(markIndexPriceDTO.getFeeRate());

        final Date date = new Date();
        this.add(HistoryMarkPrice.builder()
                .createdDate(date)
                .indexPrice(indexPrice)
                .markPrice(marketPrice)
                .modifyDate(date)
                .contractCode(contract.getContractCode())
                .premiumIndex(premiumIndex)
                .timeIndex(date)
                .askPrice(askPrem)
                .bidPrice(bidPrem)
                .build());
    }

    @Override
    public List<HistoryMarkPrice> listHistoryMarkPrice(final Contract contract, final Date start, final Date end) {
        final HistoryMarkPriceExample example = new HistoryMarkPriceExample();
        final HistoryMarkPriceExample.Criteria criteria = example.createCriteria();
        criteria.andContractCodeEqualTo(contract.getContractCode());
        criteria.andTimeIndexGreaterThanOrEqualTo(start);
        criteria.andTimeIndexLessThan(end);
        return this.getByExample(example);
    }

    private BigDecimal sumValueDepthLineLise(final List<DepthLine> depthLines) {
        // 价值
        BigDecimal size = BigDecimal.ZERO;
        if (CollectionUtils.isNotEmpty(depthLines)) {
            for (final DepthLine d : depthLines) {
                size = size.add(BigDecimalUtil.divide(d.getTotalAmount(), d.getPrice()));
            }
        }
        return size;
    }

    private BigDecimal premiumDepthPrice(final List<DepthLine> depthLines, final BigDecimal size) {

        if (CollectionUtils.isEmpty(depthLines) || size.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal availableSize = size;
        BigDecimal total = BigDecimal.ZERO;
        for (final DepthLine d : depthLines) {
            // 价值
            final BigDecimal value = BigDecimalUtil.divide(d.getTotalAmount(), d.getPrice());

            if (value.compareTo(availableSize) <= 0) {
                availableSize = availableSize.subtract(value);
                total = total.add(d.getTotalAmount());
            } else {
                total = total.add(BigDecimalUtil.multiply(availableSize, d.getPrice()));
                availableSize = BigDecimal.ZERO;
            }
            if (availableSize.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
        }
        return BigDecimalUtil.divide(total, size);
    }
}