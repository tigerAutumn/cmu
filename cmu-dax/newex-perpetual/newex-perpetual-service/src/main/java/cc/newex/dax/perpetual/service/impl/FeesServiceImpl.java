package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.market.client.MarketServiceClient;
import cc.newex.dax.market.dto.result.TickerDto;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.Fee;
import cc.newex.dax.perpetual.domain.UserFee;
import cc.newex.dax.perpetual.dto.enums.MakerEnum;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import cc.newex.dax.perpetual.service.FeeService;
import cc.newex.dax.perpetual.service.FeesService;
import cc.newex.dax.perpetual.service.UserFeeService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手续费服务实现
 *
 * @author xionghui
 * @date 2018/10/24
 */
@Slf4j
@Service
public class FeesServiceImpl implements FeesService {
    /**
     * 默认手续费费率
     */
    private final BigDecimal defaultRate = BigDecimal.ZERO;
    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private UserFeeService userFeeService;
    @Autowired
    private FeeService feeService;
    @Autowired
    private MarketServiceClient marketServiceClient;
    /**
     * 用户手续费配置
     */
    private volatile Map<FeeUserKey, BigDecimal> userConfig;
    /**
     * 手续费配置
     */
    private volatile Map<FeeKey, BigDecimal> globalConfig;
    /**
     * 手续费抵扣配置
     */
    private volatile Map<String, BigDecimal> marketIndexPriceConfig;

    @PostConstruct
    public void init() {
        this.refresh();
    }

    @Override
    @Scheduled(fixedDelay = 60_000, initialDelay = 1000)
    public void refresh() {
        final List<CurrencyPair> currencyPairList = this.currencyPairService.getOnlineCurrencyPair();
        this.userConfig = this.getUserFeeConfig();
        this.globalConfig = this.getFeeConfig();
        this.marketIndexPriceConfig = this.getCurrencyIndexPrice(currencyPairList);
    }

    /**
     * 用户手续费配置
     */
    private Map<FeeUserKey, BigDecimal> getUserFeeConfig() {
        final List<UserFee> configList = this.userFeeService.getAll();
        final Map<FeeUserKey, BigDecimal> userFeeMap = new HashMap<>();
        for (final UserFee config : configList) {
            userFeeMap.put(FeeUserKey.builder().pairCode(config.getPairCode().toLowerCase())
                            .userId(config.getUserId()).side(config.getSide()).brokerId(config.getBrokerId()).build(),
                    config.getRate());
        }
        return userFeeMap;
    }

    /**
     * 全局配置
     */
    private Map<FeeKey, BigDecimal> getFeeConfig() {
        final List<Fee> configList = this.feeService.getAll();
        final Map<FeeKey, BigDecimal> feeMap = new HashMap<>();
        for (final Fee config : configList) {
            feeMap.put(FeeKey.builder().pairCode(config.getPairCode().toLowerCase())
                    .side(config.getSide()).brokerId(config.getBrokerId()).build(), config.getRate());
        }
        return feeMap;
    }

    /**
     * 获取计价币指数价格
     */
    private Map<String, BigDecimal> getCurrencyIndexPrice(final List<CurrencyPair> currencyPairList) {
        final Map<String, BigDecimal> currencyIndexPriceMap = new HashMap<>();
        currencyPairList.forEach((cp) -> {
            // 线上盘并使用点卡抵扣配置
            if (cp.getEnv() == 0 && cp.getDkFee() == 1) {
                final String currencyCode = cp.getIndexBase().toLowerCase();
                // 一点卡抵扣一usdt
                if (currencyCode.equals("usdt")) {
                    currencyIndexPriceMap.put(cp.getPairCode().toLowerCase(), BigDecimal.ONE);
                    return;
                }
                final ResponseResult<TickerDto> response = this.marketServiceClient.ticker(currencyCode);
                if (response == null || response.getCode() != 0 || response.getData() == null) {
                    return;
                }
                currencyIndexPriceMap.put(cp.getPairCode().toLowerCase(),
                        new BigDecimal(response.getData().getLast()));
            }
        });

        return currencyIndexPriceMap;
    }

    @Override
    public BigDecimal getFeeRate(final Long userId, final String pairCode, final MakerEnum makerEnum,
                                 final Integer brokerId) {
        final Map<FeeUserKey, BigDecimal> tmpUserConfig = this.userConfig;
        final Map<FeeKey, BigDecimal> tmpGlobalConfig = this.globalConfig;
        final FeeUserKey feeUserKey = FeeUserKey.builder().pairCode(pairCode.toLowerCase())
                .userId(userId).side(makerEnum.getCode()).brokerId(brokerId).build();
        BigDecimal rate = tmpUserConfig.get(feeUserKey);
        if (rate == null) {
            feeUserKey.setSide(MakerEnum.BOTH.getCode());
            rate = tmpUserConfig.get(feeUserKey);
            if (rate == null) {
                final FeeKey feeKey = FeeKey.builder().pairCode(pairCode.toLowerCase())
                        .side(makerEnum.getCode()).brokerId(brokerId).build();
                rate = tmpGlobalConfig.get(feeKey);
                if (rate == null) {
                    feeKey.setSide(MakerEnum.BOTH.getCode());
                    rate = tmpGlobalConfig.get(feeKey);
                    if (rate == null) {
                        rate = this.defaultRate;
                    }
                }
            }
        }
        if (rate.abs().compareTo(BigDecimal.ONE) >= 0) {
            throw new BizException(rate + " is illegal");
        }
        return rate;
    }

    @Override
    public BigDecimal getUsdPrice(final String pairCode) {
        final BigDecimal price = this.marketIndexPriceConfig.get(pairCode.toLowerCase());
        return price == null ? BigDecimal.ZERO : price;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FeeUserKey {
        /**
         * pair_code的base走手续费
         */
        private String pairCode;
        /**
         * 用户id
         */
        private Long userId;
        /**
         * 0:both 1:maker 2:taker
         */
        private Integer side;
        /**
         * 业务方ID
         */
        private Integer brokerId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FeeKey {
        /**
         * pair_code的base走手续费
         */
        private String pairCode;
        /**
         * 0:both 1:maker 2:taker
         */
        private Integer side;
        /**
         * 业务方ID
         */
        private Integer brokerId;
    }
}
