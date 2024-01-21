package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.common.config.PerpetualConfig;
import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.DirectionEnum;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.criteria.ContractExample;
import cc.newex.dax.perpetual.criteria.CurrencyPairExample;
import cc.newex.dax.perpetual.criteria.UserBalanceExample;
import cc.newex.dax.perpetual.data.CurrencyPairRepository;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import cc.newex.dax.perpetual.service.MarketDataShardingService;
import cc.newex.dax.perpetual.service.OrderFinishShardingService;
import cc.newex.dax.perpetual.service.OrderHistoryShardingService;
import cc.newex.dax.perpetual.service.OrderShardingService;
import cc.newex.dax.perpetual.service.PendingShardingService;
import cc.newex.dax.perpetual.service.UserBalanceService;
import cc.newex.dax.perpetual.service.cache.CacheService;
import cc.newex.dax.perpetual.util.BigDecimalUtil;
import cc.newex.dax.perpetual.util.SettlementDateUtil;
import cc.newex.dax.perpetual.util.ShardingUtil;
import cc.newex.wallet.currency.CurrencyEnum;
import com.alibaba.fastjson.JSON;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static cc.newex.dax.perpetual.common.constant.PerpetualCacheKeys.CURRENCY_PAIR;

/**
 * 合约币对 服务实现
 *
 * @author newex-team
 * @date 2018-10-30 14:43:47
 */
@Slf4j
@Service
public class CurrencyPairServiceImpl
        extends AbstractCrudService<CurrencyPairRepository, CurrencyPair, CurrencyPairExample, Integer>
        implements CurrencyPairService {
    /**
     * 缓存的合约和gear
     */
    private static final Map<String, CurrencyPairGear> currencyPairMap = new ConcurrentHashMap<>();

    @Autowired
    private CurrencyPairRepository currencyPairRepository;
    @Autowired
    private PerpetualConfig perpetualConfig;
    @Autowired
    private OrderShardingService orderShardingService;
    @Autowired
    private OrderFinishShardingService orderFinishShardingService;
    @Autowired
    private OrderHistoryShardingService orderHistoryShardingService;
    @Autowired
    private PendingShardingService pendingShardingService;
    @Autowired
    private MarketDataShardingService marketDataShardingService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private UserBalanceService userBalanceService;
    @Autowired
    private CacheService cacheService;

    /**
     * 构建档位对应的维持保证金率
     */
    private static NavigableMap<BigDecimal, BigDecimal> buildGear(final CurrencyPair currencyPair) {
        final NavigableMap<BigDecimal, BigDecimal> gearMap = new TreeMap<>();
        // 最小仓位价值 如 200
        final BigDecimal minGear = currencyPair.getMinGear();
        // 最大仓位价值 如 1100
        final BigDecimal maxGear = currencyPair.getMaxGear();
        // 仓位价值间隔 如 100
        final BigDecimal diffGear = currencyPair.getDiffGear();
        // 起始保证金率（保证金率差额）
        final BigDecimal maintainRate = currencyPair.getMaintainRate();
        BigDecimal tempGear = minGear;
        BigDecimal tempRate = maintainRate;

        while (tempGear.compareTo(maxGear) <= 0) {
            gearMap.put(tempGear, tempRate);
            tempGear = tempGear.add(diffGear);
            tempRate = tempRate.add(maintainRate);
        }
        return gearMap;
    }

    private static Contract buildContract(final CurrencyPair currencyPair) {
        return Contract.builder()
                .biz(currencyPair.getBiz())
                .indexBase(currencyPair.getIndexBase())
                .base(currencyPair.getBase())
                .quote(currencyPair.getQuote())
                .direction(currencyPair.getDirection())
                .unitAmount(currencyPair.getUnitAmount())
                .pairCode(currencyPair.getPairCode())
                .insuranceAccount(currencyPair.getInsuranceAccount())
                .minTradeDigit(currencyPair.getMinTradeDigit())
                .minQuoteDigit(currencyPair.getMinQuoteDigit())
                .type(currencyPair.getType())
                .marketPriceDigit(currencyPair.getMarketPriceDigit())
                .preLiqudatePriceThreshold(currencyPair.getPreLiqudatePriceThreshold())
                .expired(0)
                .status(0)
                .env(currencyPair.getEnv())
                .interestRate(currencyPair.getInterestRate())
                .build();
    }

    @Override
    protected CurrencyPairExample getPageExample(final String fieldName, final String keyword) {
        final CurrencyPairExample example = new CurrencyPairExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @PostConstruct
    public void init() {
        this.loadDb();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addCurrencyPair(final CurrencyPair currencyPair) {
        final Contract contract = buildContract(currencyPair);

        if ("perpetual".equals(contract.getBiz())) {
            final Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 12);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            final Date now = new Date();
            while (calendar.getTime().compareTo(now) < 0) {
                calendar.add(Calendar.HOUR_OF_DAY, currencyPair.getLiquidationHour());
            }

            contract.setLiquidationDate(calendar.getTime());

            final String contractCode = contract.getBase() + contract.getQuote();

//            if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
//                contractCode = contractCode + "(" + contract.getQuote() + ")";
//            } else {
//                contractCode = contractCode + "(" + contract.getBase() + ")";
//            }

            contract.setContractCode(contractCode.toLowerCase());
        } else {
            SettlementDateUtil.initSettlementDateAndContract(currencyPair.getType(), contract);
        }

        this.initData(currencyPair, contract);

        this.contractService.add(contract);

        final int addResult = this.add(currencyPair);

        this.sendCurrencyPairDataToRedis();

        return addResult;
    }

    @Override
    public int updateCurrencyPair(final CurrencyPair currencyPair) {
        final Contract contract =
                this.contractService.getContractWithNoCacheByPairCode(currencyPair.getPairCode());
        final Contract contractTarget = CurrencyPairServiceImpl.buildContract(currencyPair);

        if (Objects.isNull(contract)) {
            throw new BizException("query contract is null with pairCode => " + currencyPair.getPairCode());
        }
        contractTarget.setId(contract.getId());
        final ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andIdEqualTo(contract.getId()).andStatusEqualTo(0)
                .andExpiredEqualTo(0);
        final int count = this.contractService.editByExample(contractTarget, contractExample);
        if (count == 0) {
            throw new BizException("update error");
        }
        this.initData(currencyPair, contractTarget);
        final int editResult = this.editById(currencyPair);
        this.sendCurrencyPairDataToRedis();
        return editResult;
    }

    /**
     * 初始化表和资产数据
     *
     * @param currencyPair
     * @param contract
     */
    private void initData(final CurrencyPair currencyPair, final Contract contract) {
        this.orderShardingService
                .createOrderIfNotExists(ShardingUtil.buildContractOrderShardTable(contract));
        this.orderFinishShardingService
                .createOrderFinishIfNotExists(ShardingUtil.buildContractOrderFinishShardTable(contract));
        this.orderHistoryShardingService
                .createOrderHistoryIfNotExists(ShardingUtil.buildContractOrderHistoryShardTable(contract));
        this.pendingShardingService
                .createPendingIfNotExists(ShardingUtil.buildContractPendingShardTable(contract));
        this.marketDataShardingService
                .createMarketDataIfNotExists(ShardingUtil.buildContractMarketDataShardTable(contract));

        final int env = CurrencyEnum.parseName(currencyPair.getBase()).isContractFakeCurrency() ? 1 : 0;
        this.userBalanceService.checkUserIdExistsAndCreate(
                DirectionEnum.POSITIVE.getDirection() == currencyPair.getDirection() ? currencyPair.getQuote() : currencyPair.getBase(),
                env, currencyPair.getInsuranceAccount());
        this.userBalanceService.checkUserIdExistsAndCreate(
                DirectionEnum.POSITIVE.getDirection() == currencyPair.getDirection() ? currencyPair.getQuote() : currencyPair.getBase(),
                env, PerpetualConstants.FEE_COUNT);
        // CMDK
        final String dkCode = this.perpetualConfig.getDkCurrency();
        this.userBalanceService.checkUserIdExistsAndCreate(dkCode, 0, PerpetualConstants.FEE_COUNT);
        final UserBalanceExample userBalanceExample = new UserBalanceExample();
        userBalanceExample.createCriteria().andCurrencyCodeEqualTo(contract.getBase());
        this.userBalanceService.updateEnv(UserBalance.builder().env(env).build(), userBalanceExample);
        // 测试盘
        if (env == 1) {
            this.userBalanceService.checkUserIdExistsAndCreate(
                    DirectionEnum.POSITIVE.getDirection() == currencyPair.getDirection() ? currencyPair.getQuote() : currencyPair.getBase(),
                    env, PerpetualConstants.MONEY_COUNT);
        }
    }

    @Override
    @Scheduled(fixedDelay = 3000, initialDelay = 1000)
    public void loadDb() {
        final CurrencyPairExample currencyPairExample = new CurrencyPairExample();
        currencyPairExample.createCriteria().andOnlineIn(this.availableOnlineValue());

        final List<CurrencyPair> currencyPairList =
                this.currencyPairRepository.selectByExample(currencyPairExample);

        final Map<String, CurrencyPairGear> currencyPairGearMap = new ConcurrentHashMap<>();

        if (CollectionUtils.isNotEmpty(currencyPairList)) {
            for (final CurrencyPair currencyPair : currencyPairList) {
                final String pairCode = currencyPair.getPairCode().toLowerCase();
                currencyPairGearMap.put(pairCode.toLowerCase(),
                        CurrencyPairGear.builder().currencyPair(currencyPair)
                                .gearMap(CurrencyPairServiceImpl.buildGear(currencyPair)).build());
            }
        }

        CurrencyPairServiceImpl.currencyPairMap.putAll(currencyPairGearMap);
    }

    @Override
    public boolean checkGear(final String pairCode, final BigDecimal gear) {
        final CurrencyPairGear currencyPairGear = CurrencyPairServiceImpl.currencyPairMap.get(pairCode.toLowerCase());
        if (currencyPairGear == null) {
            return false;
        }
        return currencyPairGear.gearMap.containsKey(gear);
    }

    @Override
    public GearRate getOpenMarginRate(final String pairCode, final BigDecimal gear,
                                      final BigDecimal lever) {
        final CurrencyPairGear currencyPairGear = CurrencyPairServiceImpl.currencyPairMap.get(pairCode.toLowerCase());
        if (currencyPairGear == null) {
            return null;
        }
        final CurrencyPair currencyPair = currencyPairGear.getCurrencyPair();
        final NavigableMap<BigDecimal, BigDecimal> gearMap = currencyPairGear.getGearMap();

        // 从 gearMap 截取 minGear < value < maxGear 的所有数据
        final boolean enableInclusive = false;
        final BigDecimal minGear = BigDecimal.ZERO;
        final BigDecimal maxGear = gear.add(currencyPair.getDiffGear());
        final BigDecimal maintainRate = currencyPair.getMaintainRate();

        final NavigableMap<BigDecimal, BigDecimal> subMap =
                gearMap.subMap(minGear, enableInclusive, maxGear, enableInclusive);

        final BigDecimal mRate =
                MapUtils.isNotEmpty(subMap) ? subMap.lastEntry().getValue() : maintainRate;

        // 取 用户设置杠杆对应保证金率、仓位最大杠杆保证金率 的最大值，并且最大的保证金率为 1
        final BigDecimal entryRate =
                BigDecimalUtil.divide(BigDecimal.ONE, lever).max(mRate.add(maintainRate));
        return GearRate.builder().entryRate(entryRate).maintainRate(mRate).build();
    }

    @Override
    public List<CurrencyPair> getOnlineCurrencyPair() {
        final CurrencyPairExample currencyPairExample = new CurrencyPairExample();
        // 下线币对不处理
        currencyPairExample.createCriteria().andOnlineIn(this.availableOnlineValue());
        return this.currencyPairRepository.selectByExample(currencyPairExample);
    }

    @Override
    public List<CurrencyPair> getOnlineCurrencyPair(final String... pairCode) {
        final CurrencyPairExample currencyPairExample = new CurrencyPairExample();
        // 下线币对不处理
        final CurrencyPairExample.Criteria criteria = currencyPairExample.createCriteria();
        criteria.andOnlineIn(this.availableOnlineValue());
        if (pairCode != null && pairCode.length > 0) {
            criteria.andPairCodeIn(Arrays.asList(pairCode));
        }
        return this.currencyPairRepository.selectByExample(currencyPairExample);
    }

    @Override
    public CurrencyPair getByPairCode(final String pairCode) {
        if (StringUtils.isBlank(pairCode)) {
            return null;
        }
        final CurrencyPairGear currencyPairGear = CurrencyPairServiceImpl.currencyPairMap.get(pairCode.toLowerCase());
        if (currencyPairGear != null) {
            return currencyPairGear.currencyPair;
        }
        final CurrencyPairExample example = new CurrencyPairExample();
        example.createCriteria().andPairCodeEqualTo(pairCode);
        return this.currencyPairRepository.selectOneByExample(example);
    }

    /**
     * 获取所有的合约币对的PairCode
     *
     * @return list
     */
    @Override
    public List<String> listCurrencyPairCode() {
        return Optional.ofNullable(this.getAll()).map(
                itemList -> itemList.stream().map(CurrencyPair::getPairCode).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    /**
     * List currency pair base code list.
     *
     * @return the list
     */
    @Override
    public List<String> listCurrencyPairBaseCode() {
        return Optional.ofNullable(this.getAll()).map(
                itemList -> itemList.stream().map(CurrencyPair::getIndexBase).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private List<Integer> availableOnlineValue() {
        return this.perpetualConfig.availableOnlineValue();
    }

    /**
     * 将数据库中的数据缓存到redis中
     */
    private void sendCurrencyPairDataToRedis() {
        final String jsonResult = Optional.ofNullable(this.getAll()).map(JSON::toJSONString).orElse("");

        if (StringUtils.isNotEmpty(jsonResult)) {
            this.cacheService.setCacheValue(CURRENCY_PAIR, jsonResult);
            return;
        }

        log.info("queryData jsonResult is empty");
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class CurrencyPairGear {
        /**
         * 币对
         */
        private CurrencyPair currencyPair;
        /**
         * 风险档位 -> 维持保证金
         */
        private NavigableMap<BigDecimal, BigDecimal> gearMap;
    }
}
