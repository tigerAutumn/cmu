package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.common.config.PerpetualConfig;
import cc.newex.dax.perpetual.common.constant.PerpetualCacheKeys;
import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.BillTypeEnum;
import cc.newex.dax.perpetual.common.enums.BizErrorCodeEnum;
import cc.newex.dax.perpetual.common.enums.DirectionEnum;
import cc.newex.dax.perpetual.common.enums.OrderSideEnum;
import cc.newex.dax.perpetual.common.enums.OrderSystemTypeEnum;
import cc.newex.dax.perpetual.common.enums.PositionSideEnum;
import cc.newex.dax.perpetual.common.enums.PositionTypeEnum;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.common.push.PushData;
import cc.newex.dax.perpetual.criteria.UserPositionExample;
import cc.newex.dax.perpetual.data.UserPositionRepository;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.SystemBill;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserBill;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.domain.bean.BrokerUserBean;
import cc.newex.dax.perpetual.domain.bean.CurrentPosition;
import cc.newex.dax.perpetual.domain.bean.PositionPubConfig;
import cc.newex.dax.perpetual.domain.bean.ShortOrder;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.enums.MakerEnum;
import cc.newex.dax.perpetual.dto.enums.PushTypeEnum;
import cc.newex.dax.perpetual.dto.enums.UserBalanceStatusEnum;
import cc.newex.dax.perpetual.service.AssetsFeeRateService;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import cc.newex.dax.perpetual.service.CurrencyPairService.GearRate;
import cc.newex.dax.perpetual.service.FeesService;
import cc.newex.dax.perpetual.service.OrderShardingService;
import cc.newex.dax.perpetual.service.OrderShardingService.ContractCodeUserIdBrokerIdBean;
import cc.newex.dax.perpetual.service.UserBalanceService;
import cc.newex.dax.perpetual.service.UserPositionService;
import cc.newex.dax.perpetual.service.cache.CacheService;
import cc.newex.dax.perpetual.service.common.MarketService;
import cc.newex.dax.perpetual.service.common.PushService;
import cc.newex.dax.perpetual.util.BigDecimalUtil;
import cc.newex.dax.perpetual.util.FormulaUtil;
import cc.newex.dax.perpetual.util.OrderUtil;
import cc.newex.dax.perpetual.util.PushDataUtil;
import cc.newex.wallet.currency.CurrencyEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 用户合约持仓数据表 服务实现
 *
 * @author newex-team
 * @date 2018-11-01 09:32:15
 */
@Slf4j
@Service
public class UserPositionServiceImpl
        extends AbstractCrudService<UserPositionRepository, UserPosition, UserPositionExample, Long>
        implements UserPositionService {
    private static final int MAX_LEN = 30000;
    @Autowired
    PerpetualConfig perpetualConfig;
    @Autowired
    private UserPositionRepository userPositionRepository;
    @Autowired
    private UserBalanceService userBalanceService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private FeesService feesService;
    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private OrderShardingService orderShardingService;
    @Autowired
    private PushService pushService;
    @Autowired
    private AssetsFeeRateService assetsFeeRateService;

    /**
     * 扣除、得到手续费或者点卡
     */
    private void transferFee(final BigDecimal oppsiteFeeRate, final FeeBean feeBean,
                             final FeeBean oppsiteFeeBean, final UserPosition userPosition, final UserPosition oppsiteUserPosition,
                             final UserBalance userBalance, final UserBalance oppsiteUserBalance, final Map<BrokerUserBean, UserBalance> userBalanceMap,
                             final BigDecimal pointsCardPrice, final UserBalance pointsCardBalance,
                             final UserBalance oppsitePointsCardBalance,
                             final Map<BrokerUserBean, UserBalance> pointsCardBalanceMap, final UserBill userBill,
                             final SystemBill systemBill, final UserBill oppsiteUserBill,
                             final SystemBill oppsiteSystemBill, final List<UserBill> userBillList,
                             final List<SystemBill> systemBillList) {
        userBill.setFeeCurrencyCode(userBalance.getCurrencyCode());
        systemBill.setFeeCurrencyCode(userBalance.getCurrencyCode());
        BigDecimal fee = feeBean.getFee();
        final BigDecimal oppsiteFee = oppsiteFeeBean.getFee();
        if (fee.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        final BrokerUserBean brokerUserBean = BrokerUserBean.builder()
                .brokerId(userBalance.getBrokerId()).userId(PerpetualConstants.FEE_COUNT).build();
        boolean usePointsCard = false;
        // 尝试点卡抵扣，不支持部分抵扣，即要不全抵扣，要不不抵扣
        if (pointsCardPrice.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal pointsCardFee = BigDecimalUtil.multiply(fee, pointsCardPrice);
            if (pointsCardBalance.getAvailableBalance().compareTo(pointsCardFee) >= 0) {
                usePointsCard = true;
                userBill.setFeeCurrencyCode(pointsCardBalance.getCurrencyCode());
                userBill.setFee(pointsCardFee.negate());
                systemBill.setFeeCurrencyCode(pointsCardBalance.getCurrencyCode());
                systemBill.setFee(pointsCardFee.negate());
                pointsCardBalance
                        .setAvailableBalance(pointsCardBalance.getAvailableBalance().subtract(pointsCardFee));
                userBalance.setAvailableBalance(userBalance.getAvailableBalance().add(fee));
                // 补偿对手点卡
                if (oppsiteFeeRate.compareTo(BigDecimal.ZERO) < 0) {
                    final BigDecimal oppsitePointsCardFee =
                            BigDecimalUtil.multiply(oppsiteFee, pointsCardPrice);
                    oppsiteUserBill.setFeeCurrencyCode(pointsCardBalance.getCurrencyCode());
                    oppsiteUserBill.setFee(oppsitePointsCardFee.negate());
                    oppsiteSystemBill.setFeeCurrencyCode(pointsCardBalance.getCurrencyCode());
                    oppsiteSystemBill.setFee(oppsitePointsCardFee.negate());
                    pointsCardFee = pointsCardFee.add(oppsitePointsCardFee);
                    oppsitePointsCardBalance.setAvailableBalance(
                            oppsitePointsCardBalance.getAvailableBalance().add(oppsitePointsCardFee.negate()));
                }
                if (pointsCardFee.compareTo(BigDecimal.ZERO) > 0) {
                    final UserBalance pointsCardFeeBalance = pointsCardBalanceMap.get(brokerUserBean);
                    final UserBill feeUserBill = UserPositionService.buildUserBill(pointsCardFeeBalance,
                            userPosition.getContractCode(), UUID.randomUUID().toString(), BillTypeEnum.SYSTEM_FEE,
                            "", BigDecimal.ZERO,
                            BigDecimal.ZERO, BigDecimal.ZERO, pointsCardFeeBalance.getCurrencyCode(),
                            BigDecimal.ZERO, pointsCardFee, BigDecimal.ZERO, BigDecimal.ZERO, MakerEnum.BOTH, 0L);
                    userBillList.add(feeUserBill);
                    final SystemBill feeSystemBill = UserPositionService.buildSystemBill(pointsCardFeeBalance,
                            pointsCardFeeBalance.getCurrencyCode(), BigDecimal.ZERO, pointsCardFee,
                            feeUserBill.getUId());
                    systemBillList.add(feeSystemBill);

                    pointsCardFeeBalance
                            .setAvailableBalance(pointsCardFeeBalance.getAvailableBalance().add(pointsCardFee));
                }
            }
        }
        // 不走抵扣
        if (!usePointsCard) {
            if (userPosition.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                userPosition.setRealizedSurplus(userPosition.getRealizedSurplus()
                        .subtract(fee.min(feeBean.getFeeRealizedSurplus())));
            }
            userBill.setFeeCurrencyCode(userBalance.getCurrencyCode());
            userBill.setFee(fee.negate());
            systemBill.setFee(fee.negate());
            // 补偿对手手续费
            if (oppsiteFeeRate.compareTo(BigDecimal.ZERO) < 0) {
                oppsiteUserBill.setFeeCurrencyCode(userBalance.getCurrencyCode());
                oppsiteUserBill.setFee(oppsiteFee.negate());
                oppsiteSystemBill.setFeeCurrencyCode(userBalance.getCurrencyCode());
                oppsiteSystemBill.setFee(oppsiteFee.negate());
                fee = fee.add(oppsiteFee);
                oppsiteUserBalance
                        .setAvailableBalance(oppsiteUserBalance.getAvailableBalance().add(oppsiteFee.negate()));
                if (oppsiteUserPosition.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                    oppsiteUserPosition.setRealizedSurplus(oppsiteUserPosition.getRealizedSurplus()
                            .subtract(BigDecimalUtil.multiply(oppsiteFee, oppsiteFeeBean.getFeePercent())));
                }
            }

            if (fee.compareTo(BigDecimal.ZERO) > 0) {
                final UserBalance feeBalance = userBalanceMap.get(brokerUserBean);
                final UserBill feeUserBill = UserPositionService.buildUserBill(feeBalance,
                        userPosition.getContractCode(), UUID.randomUUID().toString(),
                        BillTypeEnum.SYSTEM_FEE, "", BigDecimal.ZERO,
                        BigDecimal.ZERO, BigDecimal.ZERO, feeBalance.getCurrencyCode(), BigDecimal.ZERO, fee,
                        BigDecimal.ZERO, BigDecimal.ZERO, MakerEnum.BOTH, 0L);
                userBillList.add(feeUserBill);
                final SystemBill feeSystemBill = UserPositionService.buildSystemBill(feeBalance,
                        feeBalance.getCurrencyCode(), BigDecimal.ZERO, fee, feeUserBill.getUId());
                systemBillList.add(feeSystemBill);

                feeBalance.setAvailableBalance(feeBalance.getAvailableBalance().add(fee));
            }
        }
    }

    /**
     * 重新计算资产
     *
     * @param order
     * @param userBill
     */
    private void reCountUserBill(final Order order, final UserBill userBill) {
        BigDecimal afterBalance = userBill.getBeforeBalance().add(userBill.getProfit());
        if (userBill.getCurrencyCode().equals(userBill.getFeeCurrencyCode())) {
            order.setFee(order.getFee() == null ? userBill.getFee() : userBill.getFee().add(order.getFee()));
            afterBalance = afterBalance.add(userBill.getFee());
        }
        userBill.setAfterBalance(afterBalance);
    }

    private List<Map<String, Object>> buildPushRankData(final BigDecimal max,
                                                        final BigDecimal min, final List<UserRank> ranks) {
        final BigDecimal size = new BigDecimal(ranks.size());
        BigDecimal index = BigDecimal.ZERO;
        final BigDecimal maxScore = new BigDecimal("100");
        final List<Map<String, Object>> result = new LinkedList<>();
        for (final UserRank u : ranks) {
            final int score =
                    BigDecimalUtil.divide(size.subtract(index), size).multiply(maxScore).setScale(0, BigDecimal.ROUND_UP).intValue();
            final Map<String, Object> map = new HashMap<>();
            map.put("userId", u.getUserId());
            map.put("score", score);
            index = index.add(BigDecimal.ONE);
            result.add(map);
        }
        return result;
    }

    /**
     * 计算手续费
     */
    private void dealFeeTransfer(final BigDecimal takerFeeRate, final BigDecimal makerFeeRate,
                                 final FeeBean takerFeeBean, final FeeBean makerFeeBean, final BigDecimal currentSize,
                                 final UserPosition takerUserPosition, final UserPosition makerUserPosition,
                                 final UserBalance takerUserBalance, final UserBalance makerUserBalance,
                                 final Map<BrokerUserBean, UserBalance> userBalanceMap, final BigDecimal pointsCardPrice,
                                 final UserBalance takerPointsCardBalance, final UserBalance makerPointsCardBalance,
                                 final Map<BrokerUserBean, UserBalance> pointsCardBalanceMap,
                                 final Order takerOrder, final Order makerOrder, final UserBill takerUserBill,
                                 final SystemBill takerSystemBill, final List<UserBill> userBillList,
                                 final UserBill makerUserBill, final SystemBill makerSystemBill,
                                 final List<SystemBill> systemBillList) {
        // 小于0不扣手续费，还给自己
        if (takerFeeRate.compareTo(BigDecimal.ZERO) < 0) {
            if (takerFeeBean.getFee().compareTo(BigDecimal.ZERO) > 0) {
                takerUserBalance
                        .setAvailableBalance(takerUserBalance.getAvailableBalance().add(takerFeeBean.getFee()));
            }
            takerFeeBean.setFee(BigDecimalUtil.multiply(takerFeeRate.negate(), currentSize)
                    .min(makerFeeBean.getFee().max(BigDecimal.ZERO)).negate());
        }
        if (makerFeeRate.compareTo(BigDecimal.ZERO) < 0) {
            if (makerFeeBean.getFee().compareTo(BigDecimal.ZERO) > 0) {
                makerUserBalance
                        .setAvailableBalance(makerUserBalance.getAvailableBalance().add(makerFeeBean.getFee()));
            }
            makerFeeBean.setFee(BigDecimalUtil.multiply(makerFeeRate.negate(), currentSize)
                    .min(takerFeeBean.getFee().max(BigDecimal.ZERO)).negate());
        }
        // 双方都收取手续费时都不得对手手续费
        this.transferFee(makerFeeRate, takerFeeBean, makerFeeBean, takerUserPosition, makerUserPosition,
                takerUserBalance, makerUserBalance, userBalanceMap, pointsCardPrice, takerPointsCardBalance,
                makerPointsCardBalance, pointsCardBalanceMap, takerUserBill, takerSystemBill, makerUserBill,
                makerSystemBill, userBillList, systemBillList);
        this.transferFee(takerFeeRate, makerFeeBean, takerFeeBean, makerUserPosition, takerUserPosition,
                makerUserBalance, takerUserBalance, userBalanceMap, pointsCardPrice, makerPointsCardBalance,
                takerPointsCardBalance, pointsCardBalanceMap, makerUserBill, makerSystemBill, takerUserBill,
                takerSystemBill, userBillList, systemBillList);
        this.reCountUserBill(takerOrder, takerUserBill);
        //计算总收益
        takerUserBalance.setRealizedSurplus(takerUserBalance.getRealizedSurplus().add(takerUserBill.getProfit()));
        if (takerUserBill.getCurrencyCode().equals(takerUserBill.getFeeCurrencyCode())) {
            takerUserBalance.setRealizedSurplus(takerUserBalance.getRealizedSurplus().add(takerUserBill.getFee()));
        } else {
            takerPointsCardBalance.setRealizedSurplus(takerPointsCardBalance.getRealizedSurplus().add(takerUserBill.getFee()));
        }
        this.reCountUserBill(makerOrder, makerUserBill);
        //计算总收益
        makerUserBalance.setRealizedSurplus(makerUserBalance.getRealizedSurplus().add(makerUserBill.getProfit()));
        if (makerUserBill.getCurrencyCode().equals(makerUserBill.getFeeCurrencyCode())) {
            makerUserBalance.setRealizedSurplus(makerUserBalance.getRealizedSurplus().add(makerUserBill.getFee()));
        } else {
            makerPointsCardBalance.setRealizedSurplus(makerPointsCardBalance.getRealizedSurplus().add(makerUserBill.getFee()));
        }
    }

    @Override
    protected UserPositionExample getPageExample(final String fieldName, final String keyword) {
        final UserPositionExample example = new UserPositionExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserPosition updateLeverGear(final boolean isLever, final Long userId,
                                        final Integer brokerId, final String contractCode, Integer type, final BigDecimal lever,
                                        BigDecimal gear) {
        final Contract contract = this.contractService.getContract(contractCode);
        if (contract == null) {
            throw new BizException(BizErrorCodeEnum.NO_CONTRACT);
        }
        final CurrencyPair currencyPair = this.currencyPairService.getByPairCode(contract.getPairCode());
        if (currencyPair == null) {
            log.error("not found currencyPair, contractCode : {}", contractCode);
            throw new BizException(BizErrorCodeEnum.NO_CURRENCY_PAIR);
        }
        /**
         * 逐仓设置风险限额不能小于最小范围也不能大于最大范围
         */
        if (!isLever && (gear.compareTo(currencyPair.getMinGear()) < 0
                || gear.compareTo(currencyPair.getMaxGear()) > 0)) {
            log.error("updateLeverGear 风险限额不在设置范围之内, userId={}", userId);
            throw new BizException(BizErrorCodeEnum.RISK_OUT_LIMIT_ERROR);
        }

        // 锁定数据
        final UserBalance userBalance =
                this.userBalanceService.getForUpdate(userId, brokerId, contract.getBase());
        final UserPosition userPosition = this.getUserPosition(userId, brokerId, contractCode);
        if (isLever) {
            gear = userPosition.getGear();
            final BigDecimal leverage = OrderUtil.getLeverage(currencyPair, gear);
            // 不能大于最大杠杆
            if (lever.compareTo(leverage) > 0) {
                log.error("调整失败-1, userId={}", userId);
                throw new BizException(BizErrorCodeEnum.LEVER_UPDATE_ILLEGAL);
            }
        } else {
            BigDecimal longTotalSize = userPosition.getOrderLongSize();
            if (userPosition.getSide().equals(OrderSideEnum.LONG.getSide())) {
                longTotalSize = longTotalSize.add(userPosition.getSize());
            }
            BigDecimal shortTotalSize = userPosition.getOrderShortSize();
            if (userPosition.getSide().equals(OrderSideEnum.SHORT.getSide())) {
                shortTotalSize = shortTotalSize.add(userPosition.getSize());
            }
            if (gear.compareTo(longTotalSize.max(shortTotalSize)) < 0) {
                log.error("调整失败-2, userId={}", userId);
                throw new BizException(BizErrorCodeEnum.LEVER_UPDATE_ILLEGAL);
            }
        }
        if (!isLever) {
            type = userPosition.getType();
        }
        if (type == PositionTypeEnum.PART_IN.getType()) {
            userPosition.setType(type);
            if (!isLever) {
                final BigDecimal leverage = OrderUtil.getLeverage(currencyPair, gear);
                userPosition.setLever(leverage.min(userPosition.getLever()));
            } else {
                userPosition.setLever(lever.setScale(PerpetualConstants.LEVER_SCALE, BigDecimal.ROUND_DOWN));
            }
        } else {
            userPosition.setType(PositionTypeEnum.ALL_IN.getType());
            userPosition.setLever(OrderUtil.getLeverage(currencyPair, gear));
        }
        userPosition.setGear(gear);

        // 设置开仓保证金
        if (userPosition.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            final GearRate gearRate = this.currencyPairService.getOpenMarginRate(contract.getPairCode(),
                    gear, userPosition.getLever());
            BigDecimal newOpenMargin =
                    BigDecimalUtil.multiply(userPosition.getSize(), gearRate.getEntryRate());

            //逐仓需要加上负的未实现盈亏
            if (userPosition.getType() != PositionTypeEnum.ALL_IN.getType()) {
                /**
                 * 计算最低保证金
                 */
                final MarkIndexPriceDTO markIndexPriceDTO = this.marketService.getMarkIndex(contract);
                BigDecimal openMargin = BigDecimalUtil.multiply(userPosition.getSize(),
                        OrderUtil.getOpenMarginRate(currencyPair, userPosition.getSize()));
                final BigDecimal diffValue = FormulaUtil.calcProfit(userPosition, markIndexPriceDTO.getMarkPrice());
                if (diffValue.compareTo(BigDecimal.ZERO) < 0) {
                    /**
                     * 保证金需要+-未实现盈亏=真实保证金
                     */
                    openMargin = openMargin.subtract(diffValue);
                }
                newOpenMargin = newOpenMargin.max(openMargin).max(BigDecimalUtil.multiply(userPosition.getSize(),
                        BigDecimalUtil.divide(BigDecimal.ONE, userPosition.getLever())));
            }

            final BigDecimal diffMargin = newOpenMargin.subtract(userPosition.getOpenMargin());
            if (diffMargin.compareTo(BigDecimal.ZERO) > 0 || (diffMargin.compareTo(BigDecimal.ZERO) < 0
                    && userPosition.getType() == PositionTypeEnum.ALL_IN.getType())) {
                if (userBalance.getAvailableBalance().compareTo(diffMargin) < 0) {
                    log.error("调整失败-3,余额不足, userId={}", userId);
                    throw new BizException(BizErrorCodeEnum.LEVER_UPDATE_ILLEGAL);
                }
                userBalance.setAvailableBalance(userBalance.getAvailableBalance().subtract(diffMargin));
                userBalance.setPositionMargin(userBalance.getPositionMargin().add(diffMargin));
                userPosition.setOpenMargin(userPosition.getOpenMargin().add(diffMargin));
            }
        }

        final ContractCodeUserIdBrokerIdBean dto = ContractCodeUserIdBrokerIdBean.builder()
                .userId(userId).brokerId(brokerId).contractCode(contractCode).build();
        final boolean result = this.orderShardingService.checkOrderMarginWithBlance(userBalance,
                userPosition, dto, currencyPair, contract, false);
        if (!result) {
            log.error("调整失败-4, userId={}", userId);
            throw new BizException(BizErrorCodeEnum.LEVER_UPDATE_ILLEGAL);
        }
        return userPosition;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOpenMargin(final Long userId, final Integer brokerId, final String contractCode,
                                final String margin) {
        final Contract contract = this.contractService.getContract(contractCode);
        if (contract == null) {
            throw new BizException(BizErrorCodeEnum.NO_CONTRACT);
        }
        final CurrencyPair currencyPair = this.currencyPairService.getByPairCode(contract.getPairCode());
        if (currencyPair == null) {
            log.error("not found currencyPair, contractCode : {}", contractCode);
            throw new BizException(BizErrorCodeEnum.NO_CURRENCY_PAIR);
        }
        final UserBalance userBalance =
                this.userBalanceService.getForUpdate(userId, brokerId, contract.getBase());
        final UserPosition userPosition = this.getUserPosition(userId, brokerId, contractCode);
        if (userPosition.getType() == PositionTypeEnum.ALL_IN.getType()) {
            log.error("全仓不能修改保证金");
            throw new BizException(BizErrorCodeEnum.ALLIN_UPDATE_MARGIN_ERROR);
        }
        //无仓位不能转钱
        if (userPosition.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            log.error("无仓位不能修改保证金");
            throw new BizException(BizErrorCodeEnum.NO_AMOUNT_UPDATE_MARGIN_ERROR);
        }
        /**
         * 转出保证金,爆仓和强平都不能转出 转入保证金,只判断爆仓不能转入
         */
        if (new BigDecimal(margin).compareTo(BigDecimal.ZERO) < 0) {
            if (userBalance.getStatus().equals(UserBalanceStatusEnum.LIQUIDATE.getCode())
                    || userBalance.getStatus().equals(UserBalanceStatusEnum.EXPLOSION.getCode())) {
                log.error("转出保证金,爆仓和强平都不能转出");
                throw new BizException(BizErrorCodeEnum.SUBMARGIN_IN_LIQUIDATE_EXPLOSION);
            }
        } else {
            if (userBalance.getStatus().equals(UserBalanceStatusEnum.EXPLOSION.getCode())) {
                log.error("转入保证金,爆仓不能转入");
                throw new BizException(BizErrorCodeEnum.ADDMARGIN_IN_LIQUIDATE_EXPLOSION);
            }
        }
        /**
         * 计算可用余额,排除未实现盈亏的余额
         */
        final List<UserPosition> userPositionList = this.getUserPositionWithoutIdByBase(userPosition.getId(), userBalance.getUserId(),
                userBalance.getBrokerId(), contract.getBase());
        final Map<String, MarkIndexPriceDTO> allMarkIndexPriceMap = this.marketService.allMarkIndexPrice();
        final BigDecimal totalBalance = FormulaUtil.countAvailableBalance(userBalance, allMarkIndexPriceMap, userPositionList);
        final BigDecimal availableBalance = BigDecimalUtil.subtract(totalBalance, new BigDecimal(margin));
        /**
         * 判断挂单:修改杠杆和档位后,可用余额是否足够
         */
        if (availableBalance.compareTo(BigDecimal.ZERO) < 0) {
            log.error(
                    "updateOpenMargin 保证金不足, userId={},availableBalance={},openMargin={}", userId,
                    availableBalance, userPosition.getOpenMargin());
            throw new BizException(BizErrorCodeEnum.AVAILABLE_BALANCE_NOT_ENOUGH);
        }

        userPosition.setOpenMargin(userPosition.getOpenMargin().add(new BigDecimal(margin)));

        final List<Contract> contractList = Arrays.asList(contract);
        final List<MarkIndexPriceDTO> markIndexPriceDTOList = this.marketService.getMarkIndexList(contractList);

        final Map<String, MarkIndexPriceDTO> markIndexPriceMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(markIndexPriceDTOList)) {
            for (final MarkIndexPriceDTO markIndexPriceDTO : markIndexPriceDTOList) {
                markIndexPriceMap.put(markIndexPriceDTO.getContractCode(), markIndexPriceDTO);
            }
        }

        /**
         * 计算保证金够不够
         */
        final MarkIndexPriceDTO markIndexPriceDTO = markIndexPriceMap.get(contract.getContractCode());
        BigDecimal openMargin = BigDecimalUtil.multiply(userPosition.getSize(),
                OrderUtil.getOpenMarginRate(currencyPair, userPosition.getSize()));
        final BigDecimal diffValue = FormulaUtil.calcProfit(userPosition, markIndexPriceDTO.getMarkPrice());
        if (diffValue.compareTo(BigDecimal.ZERO) < 0) {
            /**
             * 保证金需要+-未实现盈亏=真实保证金
             */
            openMargin = openMargin.subtract(diffValue);
        }
        openMargin = openMargin.max(BigDecimalUtil.multiply(userPosition.getSize(),
                BigDecimalUtil.divide(BigDecimal.ONE, userPosition.getLever())));
        if (openMargin.compareTo(userPosition.getOpenMargin()) > 0) {
            final String msg;
            if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
                msg = openMargin.setScale(contract.getMinQuoteDigit(), BigDecimal.ROUND_CEILING).stripTrailingZeros().toPlainString();
            } else {
                msg = openMargin.setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_CEILING).stripTrailingZeros().toPlainString();
            }
            throw new BizException(BizErrorCodeEnum.ADDMARGIN_ERROR, msg);
        }

        /**
         * 重新计算强平,预强平价和破产价
         */
        FormulaUtil.calculationBrokerForcedLiquidationPrice(contractList, markIndexPriceMap,
                userPosition, Arrays.asList(userPosition), userBalance);
        userBalance.setAvailableBalance(availableBalance);
        userBalance.setPositionMargin(userBalance.getPositionMargin().add(new BigDecimal(margin)));
        this.editById(userPosition);
        this.userBalanceService.editById(userBalance);

        final String channel = (PerpetualConstants.PERPETUAL + "_" + contract.getPairCode()).toUpperCase();
        // push balance
        final List<UserBalance> pushBalanceList = Arrays.asList(userBalance);
        this.cacheService.convertAndSend(
                channel,
                JSON.toJSONString(
                        PushData.builder().biz(PerpetualConstants.PERPETUAL).type(PushTypeEnum.ASSET.name())
                                .zip(false).data(PushDataUtil.dealUserBalance(pushBalanceList, contract)).build()));
        userPositionList.add(userPosition);
        // push position
        this.cacheService.convertAndSend(
                channel,
                JSON.toJSONString(PushData.builder().biz(PerpetualConstants.PERPETUAL)
                        .type(PushTypeEnum.POSITION.name()).contractCode(contract.getContractCode()).zip(false)
                        .data(PushDataUtil.dealUserPositions(userPositionList, contract)).build()));
        return 1;
    }

    @Override
    public BigDecimal lowestOpenMargin(final Long userId, final Integer brokerId, final String contractCode) {

        final Contract contract = this.contractService.getContract(contractCode);
        if (contract == null) {
            throw new BizException(BizErrorCodeEnum.NO_CONTRACT);
        }

        final CurrencyPair currencyPair = this.currencyPairService.getByPairCode(contract.getPairCode());
        if (currencyPair == null) {
            log.error("not found currencyPair, contractCode : {}", contractCode);
            throw new BizException(BizErrorCodeEnum.NO_CURRENCY_PAIR);
        }

        final UserPosition userPosition = this.getUserPosition(userId, brokerId, contractCode);
        if (userPosition.getType() == PositionTypeEnum.ALL_IN.getType()) {
            log.error("全仓不能修改保证金");
            throw new BizException(BizErrorCodeEnum.ALLIN_UPDATE_MARGIN_ERROR);
        }
        /**
         * 计算保证金
         */
        final MarkIndexPriceDTO markIndexPriceDTO = this.marketService.getMarkIndex(contract);
        BigDecimal openMargin = BigDecimalUtil.multiply(userPosition.getSize(),
                OrderUtil.getOpenMarginRate(currencyPair, userPosition.getSize()));
        final BigDecimal diffValue = FormulaUtil.calcProfit(userPosition, markIndexPriceDTO.getMarkPrice());
        if (diffValue.compareTo(BigDecimal.ZERO) < 0) {
            openMargin = openMargin.subtract(diffValue);
        }
        openMargin = openMargin.max(BigDecimalUtil.multiply(userPosition.getSize(),
                BigDecimalUtil.divide(BigDecimal.ONE, userPosition.getLever())));
        return openMargin;
    }

    @Override
    public List<UserPosition> selectBatchPosition(final String contractCode, final Integer brokerId,
                                                  final Set<Long> userIds) {
        return this.userPositionRepository.selectBatchPosition(contractCode, brokerId, userIds);
    }

    @Override
    public List<UserPosition> selectBatchPositionByBase(final String currencyCode,
                                                        final Integer brokerId, final Set<Long> userIds) {
        final UserPositionExample userPositionExample = new UserPositionExample();
        final UserPositionExample.Criteria criteria = userPositionExample.createCriteria();
        if (StringUtils.isNotBlank(currencyCode)) {
            criteria.andBaseEqualTo(currencyCode);
        }
        if (brokerId != null) {
            criteria.andBrokerIdEqualTo(brokerId);
        }
        if (CollectionUtils.isNotEmpty(userIds)) {
            criteria.andUserIdIn(new ArrayList<>(userIds));
        }
        return this.getByExample(userPositionExample);
    }

    /**
     * 排名头部用户
     * <p>
     * 破产价格 b 标记价格 p 平均开仓价 a
     * <p>
     * 盈利: abs(盈利百分比)*有效杠杆 亏损:abs(盈利百分比)/有效杠杆
     * <p>
     * 盈利百分比 (标记价值 - 平均开仓价值)/abs(平均开仓价值)
     * <p>
     * 有效杠杆 abs(标记价值)/(标记价值-破产价值)
     * <p>
     * <p>
     * 多仓: 盈利: (b(a-p))/(p(b-p)) 亏损: ((a-p)(b-p))/pb 空仓: 盈利: (b(p-a))/(p/(p-b)) 亏损: ((p-b)(p-a))/(pb)
     *
     * @param contractCode
     */
    @Override
    public void sortUserPosition(final String contractCode) {
        final Contract contract = this.contractService.getContract(contractCode);

        final MarkIndexPriceDTO markIndexPriceDTO = this.marketService.getMarkIndex(contract);
        if (markIndexPriceDTO == null) {
            log.error("market price is null");
            return;
        }

        final UserPositionExample userPositionExample = new UserPositionExample();
        userPositionExample.createCriteria().andContractCodeEqualTo(contractCode);
        final List<UserPosition> list = this.getByExample(userPositionExample);
        if (CollectionUtils.isEmpty(list)) {
            log.error("user position list is empty");
            return;
        }
        final BigDecimal marketPrice = markIndexPriceDTO.getMarkPrice();
        final List<UserPosition> longPosition = new LinkedList<>();
        final List<UserPosition> shortPositoin = new LinkedList<>();
        BigDecimal totalPosition = BigDecimal.ZERO;
        for (final UserPosition u : list) {
            // 用户没有持仓 不参与排名
            if (u.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }
            totalPosition = totalPosition.add(u.getAmount());
            if (OrderSideEnum.LONG.getSide().equalsIgnoreCase(u.getSide())
                    && marketPrice.compareTo(u.getBrokerPrice()) > 0) {
                longPosition.add(u);
            }
            if (OrderSideEnum.SHORT.getSide().equalsIgnoreCase(u.getSide())
                    && marketPrice.compareTo(u.getBrokerPrice()) < 0) {
                shortPositoin.add(u);
            }
        }
        final JSONObject totalPositionJson = new JSONObject();
        totalPositionJson.put("totalPosition", totalPosition);
        this.pushService.pushData(contract, PushTypeEnum.TOTAL_POSITION, JSON.toJSONString(totalPositionJson), true,
                false, false);

        List<UserRank> longRank = new LinkedList<>();
        // 多仓
        BigDecimal longMin = null;
        BigDecimal longMax = null;
        for (final UserPosition userPosition : longPosition) {
            final BigDecimal score;
            final BigDecimal a = userPosition.getPrice();
            final BigDecimal b = userPosition.getBrokerPrice();
            final BigDecimal p = marketPrice;
            // 亏损 ((a-p)(b-p))/pb
            if (userPosition.getPrice().compareTo(marketPrice) > 0) {
                score = BigDecimalUtil.divide(BigDecimalUtil.multiply(a.subtract(p), b.subtract(p)),
                        BigDecimalUtil.multiply(p, b));
            } else {
                // (b(a-p))/(p(b-p))
                score = BigDecimalUtil.divide(BigDecimalUtil.multiply(b, a.subtract(p)),
                        BigDecimalUtil.multiply(p, b.subtract(p)));
            }
            longMin = longMin == null ? score : longMin.min(score);
            longMax = longMax == null ? score : longMax.max(score);
            longRank.add(new UserRank(userPosition.getId(), userPosition.getUserId(),
                    userPosition.getBrokerId(), contractCode, userPosition.getAmount(), score));
        }
        String rankKey = PerpetualCacheKeys.getLongUserRankKey(contractCode);
        longRank = longRank.stream().sorted(Comparator.comparing(UserRank::getScore).reversed())
                .collect(Collectors.toList());
        this.cacheService.setCacheValueExpireTime(rankKey, JSON.toJSONString(longRank), 1,
                TimeUnit.HOURS);
        List<UserRank> shortRank = new LinkedList<>();
        // 空仓
        BigDecimal shortMin = null;
        BigDecimal shortMax = null;
        for (final UserPosition userPosition : shortPositoin) {
            final BigDecimal score;
            final BigDecimal a = userPosition.getPrice();
            final BigDecimal b = userPosition.getBrokerPrice();
            final BigDecimal p = marketPrice;
            // 亏损 ((p-b)(p-a))/(pb)
            if (userPosition.getPrice().compareTo(marketPrice) < 0) {
                score = BigDecimalUtil.divide(BigDecimalUtil.multiply(p.subtract(b), p.subtract(a)),
                        BigDecimalUtil.multiply(p, b));
            } else {
                // (b(p-a))/(p/(p-b))
                score = BigDecimalUtil.divide(BigDecimalUtil.multiply(b, p.subtract(a)),
                        BigDecimalUtil.multiply(p, p.subtract(b)));
            }
            shortMin = shortMin == null ? score : shortMin.min(score);
            shortMax = shortMax == null ? score : shortMax.max(score);
            shortRank.add(new UserRank(userPosition.getId(), userPosition.getUserId(),
                    userPosition.getBrokerId(), contractCode, userPosition.getAmount(), score));
        }

        final List<Map<String, Object>> pushDateList =
                this.buildPushRankData(longMax, longMin, longRank);
        shortRank = shortRank.stream().sorted(Comparator.comparing(UserRank::getScore).reversed())
                .collect(Collectors.toList());
        pushDateList.addAll(this.buildPushRankData(shortMax, shortMin, shortRank));

        rankKey = PerpetualCacheKeys.getShortUserRankKey(contractCode);

        this.cacheService.setCacheValueExpireTime(rankKey, JSON.toJSONString(shortRank), 1,
                TimeUnit.HOURS);
        this.pushService.pushData(contract, PushTypeEnum.RANK, JSON.toJSONString(pushDateList), true,
                false, false);
    }

    @Override
    public List<UserRank> getUserRank(final String contractCode, final OrderSideEnum sideEnum) {
        final String rankKey;
        if (sideEnum == OrderSideEnum.LONG) {
            rankKey = PerpetualCacheKeys.getLongUserRankKey(contractCode);
        } else {
            rankKey = PerpetualCacheKeys.getShortUserRankKey(contractCode);
        }
        return Optional.ofNullable(this.cacheService.getCacheList(rankKey, UserRank.class))
                .orElse(new ArrayList<>());
    }

    @Override
    public List<CurrentPosition> getUserPositionByType(final Long userId, final Integer brokerId,
                                                       final String contractCode) {
        final UserPositionExample example = new UserPositionExample();
        final UserPositionExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId).andBrokerIdEqualTo(brokerId);
        if (StringUtils.isNotEmpty(contractCode)) {
            criteria.andContractCodeEqualTo(contractCode);
        }
        final List<UserPosition> userPositionList = this.getByExample(example);
        final List<CurrentPosition> positionList = Lists.newArrayList();
        for (final UserPosition userPosition : userPositionList) {
            final Contract contract = this.contractService.getContract(userPosition.getContractCode());
            final MarkIndexPriceDTO markIndexPriceDTO = this.marketService.getMarkIndex(contract);
            final CurrentPosition currentPosition = CurrentPosition.builder().id(userPosition.getId())
                    .contractCode(userPosition.getContractCode())
                    .base(userPosition.getBase()).quote(userPosition.getQuote()).type(userPosition.getType())
                    .minTradeDigit(contract.getMinTradeDigit()).minQuoteDigit(contract.getMinQuoteDigit())
                    .side(userPosition.getSide()).closingAmount(this.decimalToString(userPosition.getClosingAmount()))
                    .realizedSurplus(this.decimalToString(userPosition.getRealizedSurplus().setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN)))
                    .price(this.decimalToString(userPosition.getPrice()))
                    .amount(this.decimalToString(userPosition.getAmount())).fee(this.decimalToString(userPosition.getFee().setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN)))
                    .openMargin(this.decimalToString(userPosition.getOpenMargin().setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN)))
                    .maintenanceMargin(this.decimalToString(userPosition.getMaintenanceMargin().setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN)))
                    .size(this.decimalToString(userPosition.getSize().setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN)))
                    .markPrice(this.decimalToString(Objects.isNull(markIndexPriceDTO) ? BigDecimal.ZERO : markIndexPriceDTO.getMarkPrice()))
                    .createdDate(userPosition.getCreatedDate()).liqudatePrice(this.decimalToString(userPosition.getLiqudatePrice()))
                    .preLiqudatePrice(this.decimalToString(userPosition.getPreLiqudatePrice())).lever(this.decimalToString(userPosition.getLever()))
                    .gear(this.decimalToString(userPosition.getGear())).build();
            positionList.add(currentPosition);
        }
        return positionList;
    }

    private String decimalToString(final BigDecimal value) {
        if (value == null) {
            return null;
        }
        return value.stripTrailingZeros().toPlainString();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initAccount(final String contractCode, final Long userId, final Integer brokerId) {
        final Contract contract = this.contractService.getContract(contractCode);
        final List<UserBalance> userBalanceList = this.userBalanceService.get(userId, brokerId);

        boolean hasCurrencyCode = false, hasDK = false;
        final String currencyCode = DirectionEnum.POSITIVE.getDirection() == contract.getDirection() ? contract.getQuote() : contract.getBase();
        for (final UserBalance userBalance : userBalanceList) {
            if (userBalance.getCurrencyCode().equals(currencyCode)) {
                hasCurrencyCode = true;
            } else if (userBalance.getCurrencyCode().equals(this.perpetualConfig.getDkCurrency())) {
                hasDK = true;
            }
        }

        UserPosition position = this.getUserPosition(userId, brokerId, contractCode);

        try {
            if (!hasCurrencyCode) {
                final UserBalance userBalance = UserBalance.builder().userId(userId)
                        .env(CurrencyEnum.parseName(contract.getBase()).isContractFakeCurrency() ? 1 : 0)
                        .currencyCode(currencyCode).frozenStatus(0).availableBalance(BigDecimal.ZERO)
                        .frozenBalance(BigDecimal.ZERO).orderMargin(BigDecimal.ZERO).orderFee(BigDecimal.ZERO)
                        .brokerId(brokerId).status(UserBalanceStatusEnum.NORMAL.getCode())
                        .createdDate(new Date()).modifyDate(new Date()).build();
                this.userBalanceService.add(userBalance);
            }
        } catch (final Exception e) {
            log.error("initAccount error1: ", e);
        }

        try {
            if (!hasDK) {
                final UserBalance dkUserBalance = UserBalance.builder().userId(userId).env(0)
                        .currencyCode(this.perpetualConfig.getDkCurrency()).frozenStatus(0)
                        .availableBalance(BigDecimal.ZERO).frozenBalance(BigDecimal.ZERO)
                        .orderMargin(BigDecimal.ZERO).orderFee(BigDecimal.ZERO).brokerId(brokerId)
                        .status(UserBalanceStatusEnum.NORMAL.getCode()).createdDate(new Date())
                        .modifyDate(new Date()).build();
                this.userBalanceService.add(dkUserBalance);
            }
        } catch (final Exception e) {
            log.error("initAccount error2: ", e);
        }

        try {
            if (ObjectUtils.isEmpty(position)) {
                final CurrencyPair currencyPair =
                        this.currencyPairService.getByPairCode(contract.getPairCode());
                position = UserPosition.builder().userId(userId).brokerId(brokerId).base(contract.getBase())
                        .quote(contract.getQuote()).contractCode(contract.getContractCode()).type(0)
                        .side(OrderSideEnum.LONG.getSide()).amount(BigDecimal.ZERO)
                        .closingAmount(BigDecimal.ZERO).openMargin(BigDecimal.ZERO)
                        .orderLongAmount(BigDecimal.ZERO).orderLongSize(BigDecimal.ZERO)
                        .orderShortAmount(BigDecimal.ZERO).orderShortSize(BigDecimal.ZERO)
                        .lever(BigDecimal.ONE.divide(currencyPair.getMaintainRate().add(currencyPair.getMaintainRate())
                                .setScale(2, BigDecimal.ROUND_DOWN)))
                        .gear(currencyPair.getMinGear())
                        .maintenanceMargin(BigDecimal.ZERO).price(BigDecimal.ZERO).size(BigDecimal.ZERO)
                        .preLiqudatePrice(BigDecimal.ZERO).liqudatePrice(BigDecimal.ZERO)
                        .brokerPrice(BigDecimal.ZERO).realizedSurplus(BigDecimal.ZERO).createdDate(new Date())
                        .modifyDate(new Date()).build();
                this.add(position);
            }
        } catch (final Exception e) {
            log.error("initAccount error3: ", e);
        }
    }

    @Override
    public PositionPubConfig getPositionConfig(final Long userId, final Integer brokerId, final String contractCode) {
        final Contract contract = this.contractService.getContract(contractCode);
        final MarkIndexPriceDTO markIndexPriceDTO = this.marketService.getMarkIndex(contract);
        final CurrencyPair currencyPair = this.currencyPairService.getByPairCode(contract.getPairCode());
        UserPosition userPosition = this.getUserPosition(userId, brokerId, contractCode);

        if (Objects.isNull(userPosition)) {
            this.initAccount(contractCode, userId, brokerId);
            userPosition = this.getUserPosition(userId, brokerId, contractCode);
        }
        final PositionPubConfig config = PositionPubConfig.builder().contractCode(contractCode)
                .indexPrice(this.decimalToString(markIndexPriceDTO.getIndexPrice())).markPrice(this.decimalToString(markIndexPriceDTO.getMarkPrice())).build();
        final BigDecimal fundRate = this.assetsFeeRateService.takeFeeRate(contractCode);

        /**
         * 获取手续费
         */
        final BigDecimal makerFee = this.feesService.getFeeRate(userId,
                contract.getPairCode(), MakerEnum.MAKER, brokerId).abs();
        final BigDecimal takerFee = this.feesService.getFeeRate(userId,
                contract.getPairCode(), MakerEnum.TAKER, brokerId).abs();
        final BigDecimal maxFeeRate = makerFee.max(takerFee);

        config.setFundRate(this.decimalToString(fundRate.abs()));
        config.setFeeRate(this.decimalToString(maxFeeRate));

        config.setDiffGear(this.decimalToString(currencyPair.getDiffGear()));
        config.setMinGear(this.decimalToString(currencyPair.getMinGear()));
        config.setMaxGear(this.decimalToString(currencyPair.getMaxGear()));
        config.setMaintainRate(this.decimalToString(currencyPair.getMaintainRate()));
        config.setUnitAmount(this.decimalToString(currencyPair.getUnitAmount()));

        config.setType(userPosition.getType());
        config.setLever(this.decimalToString(userPosition.getLever()));
        config.setGear(this.decimalToString(userPosition.getGear()));

        return config;
    }

    @Override
    public UserPosition getUserPosition(final Long userId, final Integer brokerId,
                                        final String contractCode) {
        final UserPositionExample example = new UserPositionExample();
        example.createCriteria().andUserIdEqualTo(userId).andBrokerIdEqualTo(brokerId)
                .andContractCodeEqualTo(contractCode);
        return this.getOneByExample(example);
    }

    @Override
    public List<UserPosition> getUserPosition(final Long userId, final Integer brokerId,
                                              final List<String> contractCode) {
        final UserPositionExample example = new UserPositionExample();
        example.createCriteria().andUserIdEqualTo(userId).andBrokerIdEqualTo(brokerId)
                .andContractCodeIn(contractCode);
        return this.getByExample(example);
    }

    @Override
    public List<UserPosition> getUserPositionByBase(final Long userId, final Integer brokerId,
                                                    final String base) {
        final UserPositionExample example = new UserPositionExample();
        example.createCriteria().andUserIdEqualTo(userId).andBrokerIdEqualTo(brokerId)
                .andBaseEqualTo(base);
        return this.getByExample(example);
    }

    @Override
    public List<UserPosition> getUserPositionWithoutIdByBase(final Long id, final Long userId,
                                                             final Integer brokerId, final String base) {
        final UserPositionExample example = new UserPositionExample();
        example.createCriteria().andIdNotEqualTo(id).andUserIdEqualTo(userId)
                .andBrokerIdEqualTo(brokerId).andBaseEqualTo(base);
        return this.getByExample(example);
    }

    @Override
    public List<UserPosition> getHighRiskUserPosition(final long index, final int size,
                                                      final BigDecimal price, final String pairCode) {

        final UserPositionExample example = new UserPositionExample();
        // 开多方向的条件
        final UserPositionExample.Criteria longCriteria = example.createCriteria();
        longCriteria.andIdGreaterThan(index);
        longCriteria.andContractCodeEqualTo(pairCode);
        longCriteria.andAmountGreaterThan(BigDecimal.ZERO);
        longCriteria.andSideEqualTo(OrderSideEnum.LONG.getSide());
        longCriteria.andPreLiqudatePriceGreaterThanOrEqualTo(price);

        // 开空方向的条件
        final UserPositionExample.Criteria shortCriteria = example.createCriteria();
        shortCriteria.andIdGreaterThan(index);
        shortCriteria.andContractCodeEqualTo(pairCode);
        shortCriteria.andAmountGreaterThan(BigDecimal.ZERO);
        shortCriteria.andSideEqualTo(OrderSideEnum.SHORT.getSide());
        shortCriteria.andPreLiqudatePriceLessThanOrEqualTo(price);

        example.or(shortCriteria);

        // 按照 id 分页
        final PageInfo pageInfo = new PageInfo();
        pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);
        pageInfo.setSortItem("id");
        pageInfo.setStartIndex(0);
        pageInfo.setPageSize(size);

        return this.getByPage(pageInfo, example);
    }

    @Override
    public void transfer(final Contract contract, final boolean isContraTrade,
                         final Map<String, MarkIndexPriceDTO> markIndexPriceMap, final List<Contract> contractList,
                         final ShortOrder takerShortOrder, final Order takerOrder, final ShortOrder makerShortOrder,
                         final Order makerOrder, final Map<BrokerUserBean, UserBalance> userBalanceMap,
                         final Map<BrokerUserBean, Map<String, UserPosition>> userPositionMap,
                         final BigDecimal pointsCardPrice, final Map<BrokerUserBean, UserBalance> pointsCardBalanceMap,
                         final List<UserBill> userBillList, final List<SystemBill> systemBillList) {
        final BrokerUserBean takerBrokerUserBean = BrokerUserBean.builder()
                .brokerId(takerShortOrder.getBrokerId()).userId(takerShortOrder.getUserId()).build();
        final BrokerUserBean makerBrokerUserBean = BrokerUserBean.builder()
                .brokerId(makerShortOrder.getBrokerId()).userId(makerShortOrder.getUserId()).build();
        final UserBalance takerUserBalance = userBalanceMap.get(takerBrokerUserBean);
        final Map<String, UserPosition> takerUserPositionMap = userPositionMap.get(takerBrokerUserBean);
        final UserBalance makerUserBalance = userBalanceMap.get(makerBrokerUserBean);
        final Map<String, UserPosition> makerUserPositionMap = userPositionMap.get(makerBrokerUserBean);
        final BigDecimal takerFeeRate = this.feesService.getFeeRate(takerOrder.getUserId(),
                contract.getPairCode(), MakerEnum.TAKER, takerOrder.getBrokerId());
        final BigDecimal makerFeeRate = this.feesService.getFeeRate(makerOrder.getUserId(),
                contract.getPairCode(), MakerEnum.MAKER, makerOrder.getBrokerId());

        final String uId = UUID.randomUUID().toString();
        // 处理taker成交
        final FeeBean takerFeeBean = this.dealTransfer(contract, isContraTrade, contractList,
                markIndexPriceMap, uId, takerShortOrder, takerOrder, takerFeeRate.abs(), takerUserBalance,
                takerUserPositionMap, MakerEnum.TAKER, userBillList, systemBillList);
        final UserBill takerUserBill = userBillList.get(userBillList.size() - 1);
        final SystemBill takerSystemBill = systemBillList.get(systemBillList.size() - 1);
        // 处理maker成交
        final FeeBean makerFeeBean = this.dealTransfer(contract, isContraTrade, contractList,
                markIndexPriceMap, uId, makerShortOrder, makerOrder, makerFeeRate.abs(), makerUserBalance,
                makerUserPositionMap, MakerEnum.MAKER, userBillList, systemBillList);
        final UserBill makerUserBill = userBillList.get(userBillList.size() - 1);
        final SystemBill makerSystemBill = systemBillList.get(systemBillList.size() - 1);

        final UserBalance takerPointsCardBalance = pointsCardBalanceMap.get(takerBrokerUserBean);
        final UserBalance makerPointsCardBalance = pointsCardBalanceMap.get(makerBrokerUserBean);
        final UserPosition takerUserPosition = takerUserPositionMap.get(contract.getContractCode());
        final UserPosition makerUserPosition = makerUserPositionMap.get(contract.getContractCode());
        // 处理手续费
        this.dealFeeTransfer(takerFeeRate, makerFeeRate, takerFeeBean, makerFeeBean,
                takerShortOrder.getCurrentSize(), takerUserPosition, makerUserPosition, takerUserBalance,
                makerUserBalance, userBalanceMap, pointsCardPrice, takerPointsCardBalance,
                makerPointsCardBalance, pointsCardBalanceMap, takerOrder, makerOrder,
                takerUserBill, takerSystemBill, userBillList, makerUserBill, makerSystemBill, systemBillList);
    }

    @Override
    public List<Long> getUserIdGroupByBase(final String base, final Integer brokerId,
                                           final int count) {
        return this.userPositionRepository.getUserIdGroupByBase(base, brokerId, count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refreshLiquidatePrice(final Long userId, final Integer brokerId, final String base,
                                      final List<Contract> contractList) {

        if (CollectionUtils.isEmpty(contractList)) {
            return;
        }

        final UserBalance userBalance = this.userBalanceService.getForUpdate(userId, brokerId, base);
        if (userBalance == null) {
            log.error(
                    "not found user balance, userId : {}, brokerId : {}, currencyCode : {}", userId, brokerId,
                    base);
            throw new BizException(BizErrorCodeEnum.USER_NOT_EXIST);
        }

        final List<String> contractCodes =
                contractList.stream().map(Contract::getContractCode).collect(Collectors.toList());
        final Map<String, MarkIndexPriceDTO> priceDTOMap = this.marketService.allMarkIndexPrice();
        final List<UserPosition> userPositions = this.getUserPosition(userId, brokerId, contractCodes);

        if (CollectionUtils.isEmpty(userPositions)) {
            log.warn(
                    "user position list is empty, userId : {}, brokerId : {}, contractCodes : {}", userId,
                    brokerId, JSON.toJSONString(contractCodes));
            return;
        }

        for (final UserPosition u : userPositions) {
            u.setModifyDate(new Date());
            FormulaUtil.calculationBrokerForcedLiquidationPrice(contractList, priceDTOMap, u,
                    userPositions, userBalance);
        }

        this.userBalanceService.editById(userBalance);
        this.batchEdit(userPositions);
    }

    @Override
    public List<UserPosition> getInById(final List<Long> id) {
        if (CollectionUtils.isEmpty(id)) {
            return new ArrayList<>();
        }
        final UserPositionExample example = new UserPositionExample();
        // 开多方向的条件
        final UserPositionExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(id);

        return this.getByExample(example);
    }

    /**
     * 处理成交，计算出来手续费
     */
    private FeeBean dealTransfer(final Contract contract, final boolean isContraTrade,
                                 final List<Contract> contractList, final Map<String, MarkIndexPriceDTO> markIndexPriceMap,
                                 final String uId, final ShortOrder shortOrder, final Order order, final BigDecimal feeRate,
                                 final UserBalance userBalance, final Map<String, UserPosition> userPositionMap,
                                 final MakerEnum makerEnum, final List<UserBill> userBillList,
                                 final List<SystemBill> systemBillList) {
        final UserPosition userPosition = userPositionMap.get(contract.getContractCode());
        final UserBill userBill = UserPositionService.buildUserBill(userBalance,
                order.getContractCode(), uId,
                OrderSystemTypeEnum.CONTRA_TRADE_TARGET.getSystemType() == shortOrder.getSystemType() ?
                        BillTypeEnum.CONTRA_TRADE
                        : OrderSideEnum.LONG.getSide().equals(shortOrder.getSide()) ? BillTypeEnum.LONG
                        : BillTypeEnum.SHORT,
                order.getDetailSide(), shortOrder.getCurrentAmount(), shortOrder.getCurrentSize(),
                shortOrder.getCurrentPrice(), "", BigDecimal.ZERO, BigDecimal.ZERO,
                PositionSideEnum.LONG.getSide().equals(userPosition.getSide()) ? userPosition.getAmount()
                        : userPosition.getAmount().negate(),
                BigDecimal.ZERO, makerEnum, order.getOrderId());
        if (OrderSystemTypeEnum.FORCED_LIQUIDATION.getSystemType() == shortOrder.getSystemType()) {
            userBill.setType(BillTypeEnum.LIQUIDATE.getBillType());
        } else if (OrderSystemTypeEnum.EXPLOSION.getSystemType() == shortOrder.getSystemType() ||
                OrderSystemTypeEnum.CONTRA_TRADE_SOURCE.getSystemType() == shortOrder.getSystemType()) {
            userBill.setType(BillTypeEnum.EXPLOSION.getBillType());
        }
        userBillList.add(userBill);
        final SystemBill systemBill = UserPositionService.buildSystemBill(userBalance, "",
                BigDecimal.ZERO, BigDecimal.ZERO, userBill.getUId());
        systemBillList.add(systemBill);
        // 该交易需要交的手续费
        BigDecimal fee;
        final BigDecimal feeRealizedSurplus;
        BigDecimal feePercent = BigDecimal.ONE;
        // 没有仓位或者仓位方向和订单方向相同
        if (BigDecimal.ZERO.compareTo(userPosition.getAmount()) == 0
                || order.getSide().equals(userPosition.getSide())) {
            userPosition.setSide(order.getSide());
            userBalance.setPositionSize(userBalance.getPositionSize().add(
                    PositionSideEnum.LONG.getSide().equals(userPosition.getSide()) ?
                            shortOrder.getCurrentSize() : shortOrder.getCurrentSize().negate()));
            final BigDecimal amount = userPosition.getAmount().add(shortOrder.getCurrentAmount());
            userPosition.setAmount(amount);
            // size只能加减，不能乘除
            userPosition.setSize(userPosition.getSize().add(shortOrder.getCurrentSize()));
            // 防止精度问题导致价格不一致
            if (BigDecimal.ZERO.compareTo(userPosition.getAmount()) == 0
                    || userPosition.getPrice().equals(shortOrder.getCurrentPrice())) {
                userPosition.setPrice(shortOrder.getCurrentPrice());
            } else {
                if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
                    userPosition.setPrice(BigDecimalUtil.divide(userPosition.getSize(),
                            BigDecimalUtil.multiply(amount, contract.getUnitAmount()), contract.getMinQuoteDigit()));
                } else {
                    userPosition.setPrice(BigDecimalUtil.divide(BigDecimalUtil.multiply(amount, contract.getUnitAmount()),
                            userPosition.getSize(), contract.getMinQuoteDigit()));
                }
            }
            // 新增保证金
            final BigDecimal addMarginDecimal =
                    BigDecimalUtil.multiply(order.getAvgMargin(), shortOrder.getCurrentAmount());
            userPosition.setOpenMargin(userPosition.getOpenMargin().add(addMarginDecimal));
            userBalance.setPositionMargin(userBalance.getPositionMargin().add(addMarginDecimal));
            if (userPosition.getOrderMargin().compareTo(addMarginDecimal) < 0) {
                throw new BizException("userBalance orderMargin " + userBalance.getOrderMargin()
                        + " less than " + addMarginDecimal);
            }
            userPosition.setOrderMargin(userPosition.getOrderMargin().subtract(addMarginDecimal));
            userBalance.setOrderMargin(userBalance.getOrderMargin().subtract(addMarginDecimal));
            // 新增fee
            final BigDecimal currentFee = BigDecimalUtil.multiply(feeRate, shortOrder.getCurrentSize());
            fee = currentFee.min(BigDecimalUtil.divide(userPosition.getOrderFee(), new BigDecimal(2)));
            feeRealizedSurplus = fee;
            userPosition.setOrderFee(userPosition.getOrderFee().subtract(fee).subtract(fee));
            userBalance.setOrderFee(userBalance.getOrderFee().subtract(fee).subtract(fee));
            userPosition.setFee(userPosition.getFee().add(fee));
            userBalance.setPositionFee(userBalance.getPositionFee().add(fee));
        } else {
            userPosition.setClosingAmount(userPosition.getClosingAmount()
                    .subtract(userPosition.getClosingAmount().min(shortOrder.getCurrentAmount())));
            // 计算收益和需要释放的保证金
            BigDecimal profit;
            final BigDecimal releaseSize;
            if (userPosition.getAmount().compareTo(shortOrder.getCurrentAmount()) >= 0) {
                final BigDecimal percent =
                        BigDecimalUtil.divide(shortOrder.getCurrentAmount(), userPosition.getAmount());
                userPosition.setAmount(userPosition.getAmount().subtract(shortOrder.getCurrentAmount()));
                final BigDecimal percentSize = BigDecimalUtil.multiply(percent, userPosition.getSize());
                userBalance.setPositionSize(userBalance.getPositionSize().subtract(
                        PositionSideEnum.LONG.getSide().equals(userPosition.getSide()) ?
                                percentSize : percentSize.negate()));
                // 计算收益
                profit = percentSize.subtract(shortOrder.getCurrentSize());
                profit = PositionSideEnum.LONG.getSide().equals(userPosition.getSide()) ? profit
                        : profit.negate();
                profit = profit.subtract(
                        shortOrder.getLostSize() == null ? BigDecimal.ZERO : shortOrder.getLostSize());
                order.setProfit(order.getProfit() == null ? profit : profit.add(order.getProfit()));
                // 需要释放的保证金
                final BigDecimal releaseMargin;
                if (userPosition.getType() == PositionTypeEnum.PART_IN.getType()) {
                    releaseMargin = profit.negate();
                    if (releaseMargin.compareTo(userPosition.getOpenMargin()) > 0) {
                        throw new BizException("userPosition openMargin is less than releaseMargin "
                                + userPosition.getOpenMargin() + " " + releaseMargin);
                    }
                } else {
                    releaseMargin = BigDecimalUtil.multiply(percent, userPosition.getOpenMargin());
                }
                releaseSize = releaseMargin.add(profit);
                // 需要释放的fee
                fee = BigDecimalUtil.multiply(percent, userPosition.getFee());
                // 成交需要的fee
                final BigDecimal currentFee = BigDecimalUtil.multiply(feeRate, shortOrder.getCurrentSize());
                if (fee.compareTo(currentFee) > 0) {
                    final BigDecimal diffFee = fee.subtract(currentFee);
                    userPosition.setFee(userPosition.getFee().subtract(diffFee));
                    userBalance.setPositionFee(userBalance.getPositionFee().subtract(diffFee));
                    userBalance.setAvailableBalance(userBalance.getAvailableBalance().add(diffFee));
                    fee = currentFee;
                }
                feeRealizedSurplus = fee;
                // 仓位平完恢复初始数据
                if (userPosition.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                    userPosition.setClosingAmount(BigDecimal.ZERO);
                    userPosition.setPrice(BigDecimal.ZERO);
                    userPosition.setSize(BigDecimal.ZERO);
                    userPosition.setMaintenanceMargin(BigDecimal.ZERO);
                    userPosition.setFee(BigDecimal.ZERO);
                    userPosition.setOpenMargin(BigDecimal.ZERO);
                    userPosition.setPreLiqudatePrice(BigDecimal.ZERO);
                    userPosition.setLiqudatePrice(BigDecimal.ZERO);
                    userPosition.setBrokerPrice(BigDecimal.ZERO);
                    userPosition.setRealizedSurplus(BigDecimal.ZERO);
                } else {
                    userPosition
                            .setSize(userPosition.getSize().subtract(percentSize));
                    userPosition.setRealizedSurplus(userPosition.getRealizedSurplus().add(profit));
                    userPosition.setFee(userPosition.getFee().subtract(fee));
                    userPosition.setOpenMargin(userPosition.getOpenMargin().subtract(releaseMargin));
                }
                userBalance.setPositionMargin(userBalance.getPositionMargin().subtract(releaseMargin));
                userBalance.setPositionFee(userBalance.getPositionFee().subtract(fee));
            } else {
                final BigDecimal percent =
                        BigDecimalUtil.divide(userPosition.getAmount(), shortOrder.getCurrentAmount());
                feePercent = feePercent.subtract(percent);
                final BigDecimal percentSize = BigDecimalUtil.multiply(percent, shortOrder.getCurrentSize());
                // 计算收益
                profit = userPosition.getSize().subtract(percentSize);
                profit = PositionSideEnum.LONG.getSide().equals(userPosition.getSide()) ? profit
                        : profit.negate();
                profit = profit.subtract(
                        shortOrder.getLostSize() == null ? BigDecimal.ZERO : shortOrder.getLostSize());
                order.setProfit(order.getProfit() == null ? profit : profit.add(order.getProfit()));
                // 需要释放的保证金
                final BigDecimal releaseMargin = userPosition.getOpenMargin();
                releaseSize = releaseMargin.add(profit);
                fee = userPosition.getFee();
                // 成交需要的fee
                final BigDecimal currentDealFee =
                        BigDecimalUtil.multiply(feeRate, shortOrder.getCurrentSize());
                if (fee.compareTo(currentDealFee) > 0) {
                    final BigDecimal diffFee = fee.subtract(currentDealFee);
                    userPosition.setFee(userPosition.getFee().subtract(diffFee));
                    userBalance.setPositionFee(userBalance.getPositionFee().subtract(diffFee));
                    userBalance.setAvailableBalance(userBalance.getAvailableBalance().add(diffFee));
                    fee = currentDealFee;
                }
                userPosition.setSide(order.getSide());
                final BigDecimal newSize = shortOrder.getCurrentSize().subtract(percentSize);
                userBalance.setPositionSize(userBalance.getPositionSize().add(
                        PositionSideEnum.LONG.getSide().equals(userPosition.getSide()) ?
                                newSize.add(userPosition.getSize()) : userPosition.getSize().add(newSize).negate()));
                userPosition.setAmount(shortOrder.getCurrentAmount().subtract(userPosition.getAmount()));
                userPosition.setPrice(shortOrder.getCurrentPrice());
                userPosition.setSize(newSize);
                // 需要新加的保证金
                final BigDecimal addMarginDecimal =
                        BigDecimalUtil.multiply(userPosition.getAmount(), order.getAvgMargin());
                userBalance.setPositionMargin(userBalance.getPositionMargin()
                        .subtract(userPosition.getOpenMargin()).add(addMarginDecimal));
                userPosition.setOpenMargin(addMarginDecimal);
                if (userPosition.getOrderMargin().compareTo(addMarginDecimal) < 0) {
                    throw new BizException("userBalance orderMargin " + userBalance.getOrderMargin()
                            + " less than " + addMarginDecimal);
                }
                userPosition.setOrderMargin(userPosition.getOrderMargin().subtract(addMarginDecimal));
                userBalance.setOrderMargin(userBalance.getOrderMargin().subtract(addMarginDecimal));
                // 超出仓位部分手续费
                BigDecimal currentFee = BigDecimalUtil.multiply(feeRate, BigDecimal.ONE.subtract(percent), userPosition.getSize());
                currentFee =
                        currentFee.min(BigDecimalUtil.divide(userPosition.getOrderFee(), new BigDecimal(2)));
                userPosition
                        .setOrderFee(userPosition.getOrderFee().subtract(currentFee).subtract(currentFee));
                userBalance
                        .setOrderFee(userBalance.getOrderFee().subtract(currentFee).subtract(currentFee));
                userBalance.setPositionFee(userBalance.getPositionFee().subtract(fee).add(currentFee));
                fee = fee.add(currentFee);
                userPosition.setFee(currentFee);
                feeRealizedSurplus = currentFee;
                // reset
                userPosition.setRealizedSurplus(BigDecimal.ZERO);
            }
            userBill.setProfit(profit);
            systemBill.setProfit(profit);
            if (userPosition.getType() == PositionTypeEnum.PART_IN.getType()
                    && releaseSize.compareTo(BigDecimal.ZERO) < 0) {
                throw new BizException("userPosition is part in and releaseSize "
                        + userBalance.getAvailableBalance() + " is negtive");
            }
            if (userBalance.getAvailableBalance().add(releaseSize).compareTo(BigDecimal.ZERO) < 0) {
                throw new BizException("userBalance availableBalance " + userBalance.getAvailableBalance()
                        + " less than " + releaseSize);
            }
            userBalance.setAvailableBalance(userBalance.getAvailableBalance().add(releaseSize));
        }
        userBill.setAfterPosition(
                PositionSideEnum.LONG.getSide().equals(userPosition.getSide()) ? userPosition.getAmount()
                        : userPosition.getAmount().negate());

        // 设置维持保证金
        if (userPosition.getAmount().compareTo(BigDecimal.ZERO) != 0) {
            final GearRate gearRate = this.currencyPairService.getOpenMarginRate(contract.getPairCode(),
                    userPosition.getSize(), userPosition.getLever());
            userPosition.setMaintenanceMargin(
                    BigDecimalUtil.multiply(userPosition.getSize(), gearRate.getMaintainRate()));
        }

        // 重新设置强平价格、预强平价格和破产价格
        final int total = FormulaUtil.calculationBrokerForcedLiquidationPrice(contractList,
                markIndexPriceMap, userPosition, new ArrayList<>(userPositionMap.values()), userBalance);
        // 释放仓位保证金
        if (total == 0 && (userBalance.getPositionMargin().compareTo(BigDecimal.ZERO) != 0
                || userBalance.getPositionFee().compareTo(BigDecimal.ZERO) != 0)) {
            userBalance.setAvailableBalance(userBalance.getAvailableBalance()
                    .add(userBalance.getPositionMargin().add(userBalance.getPositionFee())));
            userBalance.setPositionMargin(BigDecimal.ZERO);
            userBalance.setPositionFee(BigDecimal.ZERO);
        }

        // 对敲不处理order统计
        if (!isContraTrade) {
            // 更新订单总数量和总金额
            if (OrderSideEnum.LONG.getSide().equals(shortOrder.getSide())) {
                userPosition.setOrderLongAmount(
                        userPosition.getOrderLongAmount().subtract(shortOrder.getCurrentAmount()));
                if (userPosition.getOrderLongAmount().compareTo(BigDecimal.ZERO) == 0) {
                    userPosition.setOrderLongSize(BigDecimal.ZERO);
                } else {
                    userPosition.setOrderLongSize(userPosition.getOrderLongSize().subtract(
                            BigDecimalUtil.divide(shortOrder.getCurrentAmount(), shortOrder.getPrice())));
                }
            } else {
                userPosition.setOrderShortAmount(
                        userPosition.getOrderShortAmount().subtract(shortOrder.getCurrentAmount()));
                if (userPosition.getOrderShortAmount().compareTo(BigDecimal.ZERO) == 0) {
                    userPosition.setOrderShortSize(BigDecimal.ZERO);
                } else {
                    userPosition.setOrderShortSize(userPosition.getOrderShortSize().subtract(
                            BigDecimalUtil.divide(shortOrder.getCurrentAmount(), shortOrder.getPrice())));
                }
            }
            // 如果订单保证金或者订单手续费存在渣子，直接返还用户
            if (userPosition.getOrderLongAmount().compareTo(BigDecimal.ZERO) == 0
                    && userPosition.getOrderShortAmount().compareTo(BigDecimal.ZERO) == 0
                    && (userPosition.getOrderMargin().compareTo(BigDecimal.ZERO) != 0
                    || userPosition.getOrderFee().compareTo(BigDecimal.ZERO) != 0)) {
                userBalance.setOrderFee(userBalance.getOrderFee().subtract(userPosition.getOrderFee()));
                userBalance
                        .setOrderMargin(userBalance.getOrderMargin().subtract(userPosition.getOrderMargin()));
                userBalance.setAvailableBalance(userBalance.getAvailableBalance()
                        .add(userPosition.getOrderMargin().add(userPosition.getOrderFee())));
                userPosition.setOrderMargin(BigDecimal.ZERO);
                userPosition.setOrderFee(BigDecimal.ZERO);
            }
        }
        return FeeBean.builder().fee(fee).feeRealizedSurplus(feeRealizedSurplus).feePercent(feePercent).build();
    }

    @Override
    public List<UserPosition> allPosition(final Integer brokerId, final Long userId,
                                          final List<Contract> allContract, final Contract excludeContract) {

        if (allContract == null) {
            return new ArrayList<>(0);
        }

        final List<String> contractCodeList = new ArrayList<>();
        for (final Contract c : allContract) {
            if (c.getBase().equalsIgnoreCase(excludeContract.getBase())
                    && !c.getQuote().equalsIgnoreCase(excludeContract.getQuote())) {
                contractCodeList.add(c.getContractCode());
            }
        }
        if (CollectionUtils.isEmpty(contractCodeList)) {
            return new ArrayList<>(0);
        }
        return this.getUserPosition(userId, brokerId, contractCodeList);
    }

    @Override
    public BigDecimal sumTotalUserPosition(final String contractCode) {
        return this.userPositionRepository.sumUserPositionAmount(contractCode);
    }

    private <T> void batchHelper(final List<T> list, final Consumer<List<T>> consumer) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        int fromIndex = 0;
        final int size = list.size();
        final Class clazz = list.get(0).getClass();
        while (true) {
            if (fromIndex >= size) {
                break;
            }
            final int toIndex = Math.min(fromIndex + UserPositionServiceImpl.MAX_LEN, size);
            log.info(
                    "batch helper, class : {}, fromIndex : {}, toIndex : {}, size : {}", clazz.getName(),
                    fromIndex, toIndex, size);
            final List<T> sub = list.subList(fromIndex, toIndex);
            consumer.accept(sub);
            fromIndex = toIndex;
        }
    }

    @Override
    public Map<Long, List<UserPosition>> positionMap(final Integer brokerId,
                                                     final List<Contract> allContract, final Contract excludeContract) {
        return this.positionMap(brokerId, null, allContract, excludeContract);
    }

    @Override
    public Map<Long, List<UserPosition>> positionMap(final Integer brokerId, final List<Long> userIds, final List<Contract> allContract, final Contract excludeContract) {
        final UserPositionExample userPositionExample = new UserPositionExample();
        final UserPositionExample.Criteria criteria = userPositionExample.createCriteria();
        criteria.andBrokerIdEqualTo(brokerId);
        final List<String> contractCodeList = new ArrayList<>();
        if (CollectionUtils.isEmpty(allContract)) {
            return new HashMap<>();
        }
        for (final Contract c : allContract) {
            if (excludeContract == null || (c.getBase().equalsIgnoreCase(excludeContract.getBase())
                    && !c.getQuote().equalsIgnoreCase(excludeContract.getQuote()))) {
                contractCodeList.add(c.getContractCode());
            }
        }
        if (CollectionUtils.isNotEmpty(userIds)) {
            criteria.andUserIdIn(userIds);
        }
        if (CollectionUtils.isEmpty(contractCodeList)) {
            return new HashMap<>();
        }
        criteria.andContractCodeIn(contractCodeList);
        final List<UserPosition> userPositions =
                this.userPositionRepository.selectBaseInfoForClearPosition(userPositionExample);
        return userPositions.stream().collect(Collectors.groupingBy(UserPosition::getUserId));
    }

    /**
     * 记录总手续费和需要计算盈利的百分比
     *
     * @author xionghui
     * @date 2018/11/23
     */
    @Data
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class FeeBean {
        private BigDecimal fee;
        private BigDecimal feeRealizedSurplus;
        private BigDecimal feePercent;
    }
}
