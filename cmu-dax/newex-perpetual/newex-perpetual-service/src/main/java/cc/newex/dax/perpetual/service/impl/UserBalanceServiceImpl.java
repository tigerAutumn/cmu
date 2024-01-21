package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.common.config.PerpetualConfig;
import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.*;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.criteria.UserBalanceExample;
import cc.newex.dax.perpetual.data.UserBalanceRepository;
import cc.newex.dax.perpetual.domain.*;
import cc.newex.dax.perpetual.domain.bean.Currency;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.enums.MakerEnum;
import cc.newex.dax.perpetual.dto.enums.UserBalanceFrozenStatus;
import cc.newex.dax.perpetual.dto.enums.UserBalanceStatusEnum;
import cc.newex.dax.perpetual.service.*;
import cc.newex.dax.perpetual.service.common.CurrencyService;
import cc.newex.dax.perpetual.service.common.MarketService;
import cc.newex.dax.perpetual.util.ContractSizeUtil;
import cc.newex.dax.perpetual.util.FormulaUtil;
import cc.newex.wallet.currency.CurrencyEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 账户资产 服务实现
 *
 * @author newex-team
 * @date 2018-11-01 09:31:54
 */
@Slf4j
@Service
public class UserBalanceServiceImpl
        extends AbstractCrudService<UserBalanceRepository, UserBalance, UserBalanceExample, Long>
        implements UserBalanceService {
    @Autowired
    private UserBalanceRepository userBalanceRepository;
    @Autowired
    private UserBillService userBillService;
    @Autowired
    private SystemBillService systemBillService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private PerpetualConfig perpetualConfig;
    @Autowired
    private OrderShardingService orderShardingService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private UserActivityRewardService userActivityRewardService;

    private static UserBill buildUserBill(final UserBalance userBalance,
                                          final BillTypeEnum userBillTypeEnum, final BigDecimal size, final String tradeNo) {
        final Date nowTime = new Date();
        final BigDecimal beforeBalance =
                userBalance.getAvailableBalance().add(userBalance.getFrozenBalance())
                        .add(userBalance.getPositionMargin()).add(userBalance.getPositionFee())
                        .add(userBalance.getOrderMargin()).add(userBalance.getOrderFee());
        return UserBill.builder().currencyCode(userBalance.getCurrencyCode()).uId(tradeNo)
                .brokerId(userBalance.getBrokerId()).contractCode("").feeCurrencyCode("").tradeNo(tradeNo)
                .detailSide("").makerTaker(0).referId(0L).beforeBalance(beforeBalance)
                .afterBalance(beforeBalance.add(size)).createdDate(nowTime).userId(userBalance.getUserId())
                .type(userBillTypeEnum.getBillType()).price(BigDecimal.ZERO).amount(BigDecimal.ZERO)
                .fee(BigDecimal.ZERO).size(BigDecimal.ZERO).modifyDate(nowTime).profit(size)
                .tradeNo(tradeNo).build();
    }

    private static SystemBill buildSystemBill(final UserBalance userBalance, final BigDecimal size,
                                              final String tradeNo) {
        final Date nowTime = new Date();
        return SystemBill.builder().brokerId(userBalance.getBrokerId()).createdDate(nowTime)
                .feeCurrencyCode("").currencyCode(userBalance.getCurrencyCode())
                .brokerId(userBalance.getBrokerId()).fee(BigDecimal.ZERO).modifyDate(nowTime).profit(size)
                .uId(tradeNo).userId(userBalance.getUserId()).build();
    }

    @Override
    protected UserBalanceExample getPageExample(final String fieldName, final String keyword) {
        final UserBalanceExample example = new UserBalanceExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserBalance getForUpdate(final Long userId, final Integer brokerId,
                                    final String currencyCode) {

        UserBalance userBalance =
                this.userBalanceRepository.selectForUpdate(userId, brokerId, currencyCode);
        if (userBalance == null) {
            final Date nowTime = new Date();
            this.userBalanceRepository.insert(UserBalance.builder().userId(userId).brokerId(brokerId)
                    .currencyCode(currencyCode).availableBalance(BigDecimal.ZERO)
                    .frozenBalance(BigDecimal.ZERO).orderFee(BigDecimal.ZERO).orderMargin(BigDecimal.ZERO)
                    .status(UserBalanceStatusEnum.NORMAL.getCode()).positionFee(BigDecimal.ZERO)
                    .positionMargin(BigDecimal.ZERO).frozenStatus(UserBalanceFrozenStatus.UNFROZEN.getCode())
                    .createdDate(nowTime).modifyDate(nowTime).build());
            userBalance = this.userBalanceRepository.selectForUpdate(userId, brokerId, currencyCode);
        }
        return userBalance;
    }

    @Override
    public List<UserBalance> selectBatchForUpdate(final String currencyCode, final Integer brokerId,
                                                  final Set<Long> userIds) {
        return this.userBalanceRepository.selectBatchForUpdate(currencyCode, brokerId, userIds);
    }

    @Override
    public List<UserBalance> selectBatch(final String currencyCode, final Integer brokerId, final List<Long> userIds, final PageInfo pageInfo) {
        final UserBalanceExample userBalanceExample = new UserBalanceExample();
        final UserBalanceExample.Criteria criteria = userBalanceExample.createCriteria();
        if (StringUtils.isNotBlank(currencyCode)) {
            criteria.andCurrencyCodeEqualTo(currencyCode);
        }
        if (!CollectionUtils.isEmpty(userIds)) {
            criteria.andUserIdIn(userIds);
        }
        if (brokerId != null) {
            criteria.andBrokerIdEqualTo(brokerId);
        }
        if (pageInfo != null) {
            return this.userBalanceRepository.selectByPager(pageInfo, userBalanceExample);
        }
        return this.userBalanceRepository.selectByExample(userBalanceExample);
    }

    @Override
    public List<UserBalance> selectAllBaseInfoForUpdate(final String currencyCode,
                                                        final Integer brokerId) {
        return this.userBalanceRepository.selectAllUserIdForUpdate(currencyCode, brokerId);
    }

    @Override
    public List<UserBalance> selectAll(final String currencyCode, final Integer brokerId) {
        final UserBalanceExample userBalanceExample = new UserBalanceExample();
        userBalanceExample.createCriteria().andCurrencyCodeEqualTo(currencyCode).andBrokerIdEqualTo(brokerId);
        return this.userBalanceRepository.selectByExample(userBalanceExample);
    }

    @Override
    public List<Long> selectAllUserId(final String currencyCode, final Integer brokerId) {
        return this.userBalanceRepository.selectAllUserId(currencyCode, brokerId);
    }

    @Override
    public UserBalance get(final String currencyCode, final Long userId, final Integer brokerId) {
        final UserBalanceExample userBalanceExample = new UserBalanceExample();
        userBalanceExample.createCriteria().andCurrencyCodeEqualTo(currencyCode)
                .andUserIdEqualTo(userId).andBrokerIdEqualTo(brokerId);
        return this.userBalanceRepository.selectOneByExample(userBalanceExample);
    }

    @Override
    public List<UserBalance> get(final Long userId, final Integer brokerId) {
        final UserBalanceExample userBalanceExample = new UserBalanceExample();
        userBalanceExample.createCriteria().andUserIdEqualTo(userId).andBrokerIdEqualTo(brokerId);

        return this.userBalanceRepository.selectByExample(userBalanceExample);
    }

    /**
     * 结算转入
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferInUserBalance(final long userId, final String currencyCode,
                                      final Integer brokerId, final BigDecimal amount, final BillTypeEnum billTypeEnum) {

        final UserBalance userBalance = this.getTransferBalance(userId, currencyCode, brokerId, amount);
        final UserBalanceStatusEnum status = UserBalanceStatusEnum.fromInteger(userBalance.getStatus());
        if (UserBalanceStatusEnum.EXPLOSION.equals(status)) {
            UserBalanceServiceImpl.log.error("transfer failed, status : {}", status);
            throw new BizException(BizErrorCodeEnum.USER_LIQUIDATION_FREEZED);
        }
        final UserBill userBill =
                UserBalanceServiceImpl.buildUserBill(userBalance, billTypeEnum, amount, "");
        final SystemBill systemBill = UserBalanceServiceImpl.buildSystemBill(userBalance, amount, "");
        final Map<String, MarkIndexPriceDTO> indexPriceDTOMap = this.marketService.allMarkIndexPrice();
        final List<UserPosition> userPosition = this.userPositionService
                .getUserPositionByBase(userBalance.getUserId(), userBalance.getBrokerId(), currencyCode);

        userBalance.setAvailableBalance(userBalance.getAvailableBalance().add(amount));
        final List<Contract> unExpiredContract = this.contractService.getUnExpiredContract();
        for (final UserPosition u : userPosition) {
            if (u.getType() == PositionTypeEnum.PART_IN.getType()) {
                continue;
            }
            FormulaUtil.calculationBrokerForcedLiquidationPrice(unExpiredContract, indexPriceDTOMap, u,
                    userPosition, userBalance);
        }
        if (!CollectionUtils.isEmpty(userPosition)) {
            this.userPositionService.batchEdit(userPosition);
        }
        this.editById(userBalance);
        this.userBillService.add(userBill);
        this.systemBillService.add(systemBill);
    }

    /**
     * 结算转出
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferOutUserBalance(final long userId, final String currencyCode,
                                       final Integer brokerId, final BigDecimal amount, final BillTypeEnum billTypeEnum) {
        final UserBalance userBalance = this.getTransferBalance(userId, currencyCode, brokerId, amount);

        final UserBill userBill =
                UserBalanceServiceImpl.buildUserBill(userBalance, billTypeEnum, amount.negate(), "");
        final SystemBill systemBill =
                UserBalanceServiceImpl.buildSystemBill(userBalance, amount.negate(), "");

        final UserBalanceStatusEnum status = UserBalanceStatusEnum.fromInteger(userBalance.getStatus());
        if (!UserBalanceStatusEnum.NORMAL.equals(status)) {
            UserBalanceServiceImpl.log.error("transfer failed, status : {}", status);
            throw new BizException(BizErrorCodeEnum.USER_LIQUIDATION_FREEZED);
        }

        final Map<String, MarkIndexPriceDTO> indexPriceDTOMap = this.marketService.allMarkIndexPrice();

        BigDecimal size = userBalance.getAvailableBalance();
        final List<UserPosition> userPosition = this.userPositionService
                .getUserPositionByBase(userBalance.getUserId(), userBalance.getBrokerId(), currencyCode);
        for (final UserPosition u : userPosition) {

            if (u.getType() == PositionTypeEnum.PART_IN.getType()) {
                continue;
            }

            if (u.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            final MarkIndexPriceDTO markIndexPriceDTO = indexPriceDTOMap.get(u.getContractCode());

            if (markIndexPriceDTO == null) {
                UserBalanceServiceImpl.log.error("not found market price, contractCode : {}",
                        u.getContractCode());
                throw new BizException(BizErrorCodeEnum.SYSTEM_ERROR);
            }
            final Contract contract = this.contractService.getContract(u.getContractCode());
            // 开仓价值
            final BigDecimal openSize = ContractSizeUtil.countSize(contract, u.getAmount(), u.getPrice());
            // 当前价值
            final BigDecimal currentSize =
                    ContractSizeUtil.countSize(contract, u.getAmount(), markIndexPriceDTO.getMarkPrice());

            if (OrderSideEnum.LONG.getSide().equals(u.getSide()) && currentSize.compareTo(openSize) > 0) {
                size = size.subtract((openSize.subtract(currentSize)).abs());
            }

            if ((!OrderSideEnum.LONG.getSide().equals(u.getSide())) && currentSize.compareTo(openSize) < 0) {
                size = size.subtract((openSize.subtract(currentSize)).abs());
            }
        }

        if (size.compareTo(amount) < 0) {
            UserBalanceServiceImpl.log.error(
                    "user balance not enough, userId : {}, currencyCode : {}, size : {}", userId,
                    currencyCode, size);
            throw new BizException(BizErrorCodeEnum.AVAILABLE_BALANCE_NOT_ENOUGH);
        }

        final List<Contract> unExpiredContract = this.contractService.getUnExpiredContract();
        userBalance.setAvailableBalance(userBalance.getAvailableBalance().subtract(amount));
        for (final UserPosition u : userPosition) {
            if (u.getType() == PositionTypeEnum.PART_IN.getType()) {
                continue;
            }
            FormulaUtil.calculationBrokerForcedLiquidationPrice(unExpiredContract, indexPriceDTOMap, u,
                    userPosition, userBalance);
            final OrderSideEnum orderSideEnum;
            BigDecimal min = null;
            BigDecimal max = null;
            if (PositionSideEnum.LONG.getSide().equalsIgnoreCase(u.getSide())) {
                orderSideEnum = OrderSideEnum.SHORT;
                min = u.getBrokerPrice();
            } else {
                orderSideEnum = OrderSideEnum.LONG;
                max = u.getBrokerPrice();
            }
            this.orderShardingService.cancelOverPriceOrder(userBalance, u.getContractCode(), min, max,
                    orderSideEnum);
        }

        if (!CollectionUtils.isEmpty(userPosition)) {
            this.userPositionService.batchEdit(userPosition);
        }
        this.editById(userBalance);
        this.userBillService.add(userBill);
        this.systemBillService.add(systemBill);
    }

    @Override
    public List<UserBalance> selectByBalanceGreaterThanZero(final Long userId,
                                                            final Integer brokerId) {
        return this.userBalanceRepository.selectByBalanceGreaterThanZero(userId, brokerId);
    }

    @Override
    public void checkUserIdExistsAndCreate(final String base, final Integer env, final Long userId) {
        // 检查base和userId是否存在
        final Boolean exists = this.checkUserIdExists(base, userId);
        if (exists) {
            UserBalanceServiceImpl.log.info(
                    "userId => {} and baseCode => {} is exists in the user_balance table", userId, base);
            return;
        }
        final UserBalance userBalance = new UserBalance();
        userBalance.setCurrencyCode(base);
        userBalance.setUserId(userId);
        userBalance.setEnv(env);
        this.add(userBalance);
    }

    @Override
    public void checkUserAccountByContract(final Long userId, final Integer brokerId) {
        final List<Currency> currencyList = this.currencyService.listCurrencies();
        if (CollectionUtils.isEmpty(currencyList)) {
            UserBalanceServiceImpl.log.error("checkUserAccountByContract 币种列表为空");
            return;
        }
        final List<Currency> filterResult = currencyList.stream().filter(item -> item.getOnline() == 1).collect(Collectors.toList());

        for (final Currency currency : filterResult) {
            UserBalance userBalance = this.get(currency.getSymbol().toLowerCase(), userId, brokerId);
            if (Objects.isNull(userBalance)) {
                userBalance = UserBalance.builder().userId(userId)
                        .env(CurrencyEnum.parseName(currency.getSymbol()).isContractFakeCurrency() ? 1 : 0)
                        .brokerId(brokerId)
                        .currencyCode(currency.getSymbol().toLowerCase()).build();
                this.add(userBalance);
                UserBalanceServiceImpl.log.info("添加contract={}合约的资金账户base={}", currency.getSymbol(),
                        currency.getSymbol());
            }
        }

        // 判断CMDK账号是否存在
        final String dkCode = this.perpetualConfig.getDkCurrency();
        UserBalance userBalance = this.get(dkCode, userId, brokerId);
        if (Objects.isNull(userBalance)) {
            userBalance = new UserBalance();
            userBalance.setCurrencyCode(dkCode);
            userBalance.setUserId(userId);
            userBalance.setEnv(0);
            userBalance.setBrokerId(brokerId);
            this.add(userBalance);
        }
    }

    /**
     * 检查用户是否存在
     *
     * @param userId
     * @return
     */
    @Override
    public Boolean checkUserIdExists(final String base, final Long userId) {

        if (StringUtils.isEmpty(base) || Objects.isNull(userId)) {
            throw new IllegalArgumentException("baseCode or userId is not be empty or null, baseCode => "
                    + base + "and userId => " + userId);
        }
        final UserBalanceExample example = new UserBalanceExample();
        example.createCriteria().andUserIdEqualTo(userId).andCurrencyCodeEqualTo(base);
        return Optional.ofNullable(this.getByExample(example))
                .map(itemList -> !CollectionUtils.isEmpty(itemList)).orElse(Boolean.FALSE);
    }

    /**
     * Check user id exists boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    @Override
    public Boolean checkUserIdExists(final Long userId) {
        if (Objects.isNull(userId)) {
            throw new IllegalArgumentException("userId is not be null");
        }
        final UserBalanceExample example = new UserBalanceExample();
        example.createCriteria().andUserIdEqualTo(userId);
        final UserBalance queryData = this.getOneByExample(example);
        // 获取查询的数据 看是否为CMDK和9号账号
        if (Objects.equals(queryData.getCurrencyCode(), this.perpetualConfig.getDkCurrency())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private UserBalance getTransferBalance(final long userId, final String currencyCode,
                                           final Integer brokerId, final BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            UserBalanceServiceImpl.log.error("transfer in amount should be greater then 0");
            throw new BizException(BizErrorCodeEnum.ILLEGAL_PARAM);
        }

        final UserBalance userBalance = this.getForUpdate(userId, brokerId, currencyCode);
        if (userBalance == null) {
            UserBalanceServiceImpl.log.error(
                    "no user balance, userId : {}, brokerId : {}, currencyCode : {}", userId, brokerId,
                    currencyCode);
            throw new BizException(BizErrorCodeEnum.USER_NOT_EXIST);
        }
        return userBalance;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int bossReward(final String currencyCode, final Long userId, final Integer brokerId, BigDecimal value) {
        final Set<Long> userIds = new HashSet<>();
        userIds.add(userId);
        userIds.add(PerpetualConstants.MONEY_COUNT);
        final List<UserBalance> userBalanceList = this.selectBatchForUpdate(currencyCode, brokerId, userIds);

        UserBalance userBalance = null, moneyBalance = null;
        for (final UserBalance ub : userBalanceList) {
            if (ub.getUserId().equals(userId)) {
                userBalance = ub;
            } else if (ub.getUserId().equals(PerpetualConstants.MONEY_COUNT)) {
                moneyBalance = ub;
            }
        }
        if (Objects.isNull(userBalance)) {
            UserBalanceServiceImpl.log.error(
                    "bossReward no user balance, userId : {}, brokerId : {}, currencyCode : {}", userId, brokerId,
                    currencyCode);
            throw new BizException(BizErrorCodeEnum.USER_NOT_EXIST);
        }
        // 非测试币不能领取
        if (userBalance.getEnv() == 0) {
            throw new BizException(BizErrorCodeEnum.ILLEGAL_REWARD_CURRENCY);
        }


        // 数据太小
        if (value.compareTo(BigDecimal.ZERO) == 0) {
            value = new BigDecimal(10);
        }
        // moneyBalance可能为负数，不管，因为是假币
        moneyBalance.setAvailableBalance(moneyBalance.getAvailableBalance().subtract(value));
        userBalance.setAvailableBalance(userBalance.getAvailableBalance().add(value));
        userBalance.setRewardTime(System.currentTimeMillis());
        this.batchEdit(userBalanceList);

        final UserActivityReward reward = UserActivityReward.builder().userId(userId).brokerId(brokerId).currencyCode(currencyCode)
                .amount(value).createdDate(new Date()).type(1).build();
        this.userActivityRewardService.add(reward);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal reward(final String currencyCode, final Long userId, final Integer brokerId) {
        final Set<Long> userIds = new HashSet<>();
        userIds.add(userId);
        userIds.add(PerpetualConstants.MONEY_COUNT);
        final List<UserBalance> userBalanceList = this.selectBatchForUpdate(currencyCode, brokerId, userIds);
        UserBalance userBalance = null, moneyBalance = null;
        for (final UserBalance ub : userBalanceList) {
            if (ub.getUserId().equals(userId)) {
                userBalance = ub;
            } else if (ub.getUserId().equals(PerpetualConstants.MONEY_COUNT)) {
                moneyBalance = ub;
            }
        }
        // 非测试币不能领取
        if (userBalance.getEnv() == 0) {
            throw new BizException(BizErrorCodeEnum.ILLEGAL_REWARD_CURRENCY);
        }
        final long rewardTime = userBalance.getRewardTime();
        final Calendar calendar = Calendar.getInstance();
        final long now = calendar.getTimeInMillis();
        // 24小时领一次
        if (now - rewardTime < 24L * 60 * 60 * 1000) {
            final String msg = "" + (24 - (now - rewardTime) / (60 * 60 * 1000));
            throw new BizException(BizErrorCodeEnum.ILLEGAL_REWARD_INTERVAL, msg);
        }
        double maxReward = 1.0;
        // 2019-2
        if (calendar.get(Calendar.YEAR) < 2019 || calendar.get(Calendar.MONTH) + 1 < 3) {
            maxReward = 10.0;
        }
        BigDecimal value = new BigDecimal(Math.random() * maxReward).setScale(PerpetualConstants.SCALE,
                BigDecimal.ROUND_UP);
        // 数据太小
        if (value.compareTo(BigDecimal.ZERO) == 0) {
            value = new BigDecimal(10);
        }
        value = BigDecimal.ONE;

        // 待插入user账单
        final List<UserBill> userBillList = new ArrayList<>();
        // 待插入系统账单
        final List<SystemBill> systemBillList = new ArrayList<>();
        final UserBill moneyUserBill = this.buildUserBill(moneyBalance, "",
                BillTypeEnum.REWARD, "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, "", BigDecimal.ZERO,
                value.negate(), BigDecimal.ZERO, BigDecimal.ZERO, MakerEnum.BOTH,
                0L);
        userBillList.add(moneyUserBill);
        systemBillList.add(this.buildSystemBill(moneyBalance, "", BigDecimal.ZERO,
                value.negate(), moneyUserBill.getUId()));

        final UserBill userBill = this.buildUserBill(userBalance, "",
                BillTypeEnum.REWARD, "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, "", BigDecimal.ZERO,
                value, BigDecimal.ZERO, BigDecimal.ZERO, MakerEnum.BOTH,
                0L);
        userBillList.add(userBill);
        systemBillList.add(this.buildSystemBill(userBalance, "", BigDecimal.ZERO,
                value, userBill.getUId()));
        this.userBillService.batchAdd(userBillList);
        this.systemBillService.batchAdd(systemBillList);

        // moneyBalance可能为负数，不管，因为是假币
        moneyBalance.setAvailableBalance(moneyBalance.getAvailableBalance().subtract(value));
        userBalance.setAvailableBalance(userBalance.getAvailableBalance().add(value));
        userBalance.setRewardTime(now);
        this.batchEdit(userBalanceList);

        final UserActivityReward reward = UserActivityReward.builder().userId(userId).brokerId(brokerId).currencyCode(currencyCode)
                .amount(value).createdDate(new Date()).type(1).build();
        this.userActivityRewardService.add(reward);
        return value;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal rewardFbtc(final Long userId, final Integer brokerId) {
        // 默认分fbtc
        final String currencyCode = "fbtc";
        // 默认分10个FBTC
        final BigDecimal value = BigDecimal.TEN;
        final Set<Long> userIds = new HashSet<>();
        userIds.add(userId);
        userIds.add(PerpetualConstants.MONEY_COUNT);
        final List<UserBalance> userBalanceList = this.selectBatchForUpdate(currencyCode, brokerId, userIds);
        // 没有测试币
        if (userBalanceList == null || userBalanceList.size() != 2) {
            return BigDecimal.ZERO;
        }
        UserBalance userBalance = null, moneyBalance = null;
        for (final UserBalance ub : userBalanceList) {
            if (ub.getUserId().equals(userId)) {
                userBalance = ub;
            } else if (ub.getUserId().equals(PerpetualConstants.MONEY_COUNT)) {
                moneyBalance = ub;
            }
        }
        // 非测试币不能领取
        if (userBalance.getEnv() == 0) {
            throw new BizException(BizErrorCodeEnum.ILLEGAL_REWARD_CURRENCY);
        }

        // 待插入user账单
        final List<UserBill> userBillList = new ArrayList<>();
        // 待插入系统账单
        final List<SystemBill> systemBillList = new ArrayList<>();
        final UserBill moneyUserBill = this.buildUserBill(moneyBalance, "",
                BillTypeEnum.REWARD, "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, "", BigDecimal.ZERO,
                value.negate(), BigDecimal.ZERO, BigDecimal.ZERO, MakerEnum.BOTH,
                0L);
        userBillList.add(moneyUserBill);
        systemBillList.add(this.buildSystemBill(moneyBalance, "", BigDecimal.ZERO,
                value.negate(), moneyUserBill.getUId()));

        final UserBill userBill = this.buildUserBill(userBalance, "",
                BillTypeEnum.REWARD, "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, "", BigDecimal.ZERO,
                value, BigDecimal.ZERO, BigDecimal.ZERO, MakerEnum.BOTH,
                0L);
        userBillList.add(userBill);
        systemBillList.add(this.buildSystemBill(userBalance, "", BigDecimal.ZERO,
                value, userBill.getUId()));
        this.userBillService.batchAdd(userBillList);
        this.systemBillService.batchAdd(systemBillList);

        // moneyBalance可能为负数，不管，因为是假币
        moneyBalance.setAvailableBalance(moneyBalance.getAvailableBalance().subtract(value));
        userBalance.setAvailableBalance(userBalance.getAvailableBalance().add(value));
        this.batchEdit(userBalanceList);

        final UserActivityReward reward = UserActivityReward.builder().userId(userId).brokerId(brokerId).currencyCode(currencyCode)
                .amount(value).createdDate(new Date()).type(1).build();
        this.userActivityRewardService.add(reward);
        return value;
    }

    /**
     * 构建userBill
     */
    private UserBill buildUserBill(final UserBalance balance, final String contractCode,
                                   final BillTypeEnum billTypeEnum, final String detailSide, final BigDecimal amount,
                                   final BigDecimal size, final BigDecimal price, final String feeCurrencyCode,
                                   final BigDecimal fee, final BigDecimal profit, final BigDecimal beforePosition,
                                   final BigDecimal afterPosition, final MakerEnum makerEnum, final Long orderId) {
        final BigDecimal beforeBalance = balance.getAvailableBalance().add(balance.getFrozenBalance())
                .add(balance.getPositionMargin()).add(balance.getPositionFee())
                .add(balance.getOrderMargin()).add(balance.getOrderFee());
        BigDecimal afterBalance = beforeBalance.add(profit);
        if (balance.getCurrencyCode().equals(feeCurrencyCode)) {
            afterBalance = afterBalance.add(fee);
        }
        return UserBill.builder().uId(UUID.randomUUID().toString()).userId(balance.getUserId())
                .contractCode(contractCode).currencyCode(balance.getCurrencyCode())
                .type(billTypeEnum.getBillType()).detailSide(detailSide).amount(amount).price(price)
                .size(size).feeCurrencyCode(feeCurrencyCode).fee(fee).profit(profit)
                .beforePosition(beforePosition).afterPosition(afterPosition).beforeBalance(beforeBalance)
                .afterBalance(afterBalance).makerTaker(makerEnum.getCode()).referId(orderId)
                .brokerId(balance.getBrokerId()).build();
    }

    /**
     * 构建systemBill
     */
    private SystemBill buildSystemBill(final UserBalance balance, final String feeCurrencyCode,
                                       final BigDecimal fee, final BigDecimal profit, final String uId) {
        return SystemBill.builder().userId(balance.getUserId()).currencyCode(balance.getCurrencyCode())
                .feeCurrencyCode(feeCurrencyCode).fee(fee).profit(profit).uId(uId)
                .brokerId(balance.getBrokerId()).build();
    }

    @Override
    public Map<Long, BigDecimal> unrealizedSurplus(final Integer brokerId,
                                                   final List<Long> userId,
                                                   final String currencyCode,
                                                   final Map<String, MarkIndexPriceDTO> priceMap) {
        final List<UserPosition> positions = this.userPositionService.selectBatchPositionByBase(currencyCode, brokerId, new HashSet<>(userId));
        if (CollectionUtils.isEmpty(positions)) {
            return new HashMap<>();
        }
        final Map<Long, BigDecimal> result = new HashMap<>();
        for (final UserPosition u : positions) {
            if (!result.containsKey(u.getUserId())) {
                result.put(u.getUserId(), BigDecimal.ZERO);
            }
            result.put(u.getUserId(), result.get(u.getUserId()).add(FormulaUtil.calcProfit(u, priceMap.get(u.getContractCode()).getMarkPrice())));
        }
        return result;
    }

    @Override
    public int updateEnv(final UserBalance userBalance, final UserBalanceExample userBalanceExample) {
        return this.userBalanceRepository.updateEnvByExample(userBalance, userBalanceExample);
    }
}
