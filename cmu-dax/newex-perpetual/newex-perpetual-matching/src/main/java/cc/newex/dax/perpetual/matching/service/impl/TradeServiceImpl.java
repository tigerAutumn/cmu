package cc.newex.dax.perpetual.matching.service.impl;

import cc.newex.dax.perpetual.common.config.PerpetualConfig;
import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.BillTypeEnum;
import cc.newex.dax.perpetual.common.enums.DirectionEnum;
import cc.newex.dax.perpetual.common.enums.OrderClazzEnum;
import cc.newex.dax.perpetual.common.enums.OrderDetailSideEnum;
import cc.newex.dax.perpetual.common.enums.OrderSideEnum;
import cc.newex.dax.perpetual.common.enums.OrderSystemTypeEnum;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.criteria.OrderShardingExample;
import cc.newex.dax.perpetual.criteria.UserBalanceExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.LatestTicker;
import cc.newex.dax.perpetual.domain.MatchingCache;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.OrderMarginFee;
import cc.newex.dax.perpetual.domain.Pending;
import cc.newex.dax.perpetual.domain.SystemBill;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserBill;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.domain.bean.BrokerUserBean;
import cc.newex.dax.perpetual.domain.bean.ShortOrder;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.enums.MakerEnum;
import cc.newex.dax.perpetual.dto.enums.OrderStatusEnum;
import cc.newex.dax.perpetual.dto.enums.PushTypeEnum;
import cc.newex.dax.perpetual.matching.bean.PushInfo;
import cc.newex.dax.perpetual.matching.bean.ShortOrders;
import cc.newex.dax.perpetual.matching.bean.constant.Constants;
import cc.newex.dax.perpetual.matching.service.TradeService;
import cc.newex.dax.perpetual.service.FeesService;
import cc.newex.dax.perpetual.service.LatestTickerService;
import cc.newex.dax.perpetual.service.MatchingCacheService;
import cc.newex.dax.perpetual.service.OrderAllService;
import cc.newex.dax.perpetual.service.OrderFinishShardingService;
import cc.newex.dax.perpetual.service.OrderShardingService;
import cc.newex.dax.perpetual.service.PendingShardingService;
import cc.newex.dax.perpetual.service.SystemBillService;
import cc.newex.dax.perpetual.service.UserBalanceService;
import cc.newex.dax.perpetual.service.UserBillService;
import cc.newex.dax.perpetual.service.UserPositionService;
import cc.newex.dax.perpetual.service.common.MarketService;
import cc.newex.dax.perpetual.util.BigDecimalUtil;
import cc.newex.dax.perpetual.util.FormulaUtil;
import cc.newex.dax.perpetual.util.PushDataUtil;
import cc.newex.dax.perpetual.util.ShardingUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

/**
 * 交易服务类
 *
 * @author xionghui
 * @date 2018/10/20
 */
@Slf4j
@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private PendingShardingService pendingShardingService;
    @Autowired
    private OrderShardingService orderShardingService;
    @Autowired
    private OrderFinishShardingService orderFinishShardingService;
    @Autowired
    private OrderAllService orderAllService;
    @Autowired
    private UserBalanceService userBalanceService;
    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private UserBillService userBillService;
    @Autowired
    private FeesService feesService;
    @Autowired
    private SystemBillService systemBillService;
    @Autowired
    private PerpetualConfig perpetualConfig;
    @Autowired
    private MarketService marketService;
    @Autowired
    private LatestTickerService latestTickerService;
    @Autowired
    private MatchingCacheService matchingCacheService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public UserBalance getInsuranceBalance(final Contract contract) {
        final UserBalanceExample userBalanceExample = new UserBalanceExample();
        userBalanceExample.createCriteria().andUserIdEqualTo(contract.getInsuranceAccount())
                .andBrokerIdEqualTo(Constants.INSURANCE_BROKERID)
                .andCurrencyCodeEqualTo(contract.getBase());
        return this.userBalanceService.getOneByExample(userBalanceExample);
    }

    @Override
    public List<Pending> getRecentDeal(final int size, final Contract contract) {
        return this.pendingShardingService.getRecentDeal(size, contract.getContractCode(),
                ShardingUtil.buildContractPendingShardTable(contract));
    }

    @Override
    public List<Order> getOrderByMatchStatus(final Contract contract, final int matchStatus, final Integer limit) {
        return this.orderShardingService.getOrderByMatchStatus(contract.getContractCode(), matchStatus, limit,
                ShardingUtil.buildContractOrderShardTable(contract));
    }

    @Override
    public int editOrderMatchStatus(final Contract contract, final List<Long> idList, final int matchStatus) {
        final Order order = Order.builder().matchStatus(matchStatus).build();
        final OrderShardingExample orderShardingExample = new OrderShardingExample();
        orderShardingExample.createCriteria().andIdIn(idList);
        return this.orderShardingService.editByExample(order, orderShardingExample, ShardingUtil.buildContractOrderShardTable(contract));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealOrderPending(final Contract contract, final List<Contract> contractList,
                                 final List<ShortOrders> shortOrdersList, final boolean checkInsurance,
                                 final List<Pending> pendingList) {
        // 记录brokerId,user_id以便加锁: 按brokerId从小到大的顺序锁
        final Map<Integer, Set<Long>> brokerIdUserIdMap = new TreeMap<>();
        shortOrdersList.forEach(shortOrders -> {
            for (final ShortOrder shortOrder : shortOrders) {
                this.fillBrokerIdUserIdMap(brokerIdUserIdMap, shortOrder.getBrokerId(),
                        shortOrder.getUserId());
            }
        });
        // 保险金账号不区分券商，只区分合约币种
        if (checkInsurance) {
            this.fillBrokerIdUserIdMap(brokerIdUserIdMap, Constants.INSURANCE_BROKERID,
                    contract.getInsuranceAccount());
        }
        // 加入券商的手续费账号
        for (final Integer brokerId : brokerIdUserIdMap.keySet()) {
            this.fillBrokerIdUserIdMap(brokerIdUserIdMap, brokerId, PerpetualConstants.FEE_COUNT);
        }
        // 用户资产和点卡资产(包括手续费的资产账户和点卡账户)，需要按顺序锁定
        final Map<BrokerUserBean, UserBalance> balanceMap = new HashMap<>(),
                pointsCardBalanceMap = new HashMap<>();
        // 点卡支持抵扣的价格
        final BigDecimal basePointsCardPrice = this.feesService.getUsdPrice(contract.getPairCode());
        // position查询所有的，即使取消也需要使用position去重新计算订单保证金
        final Map<BrokerUserBean, Map<String, UserPosition>> positionMap = new HashMap<>();
        // 用户所有的订单，后续重新计算订单保证金使用
        final Map<BrokerUserBean, Map<Long, Order>> orderMap = new HashMap<>();
        // 锁定资产: basePointsCardPrice大于0就可能使用点卡
        this.lockBalanceAndLoadPositionOrder(contract, brokerIdUserIdMap,
                basePointsCardPrice.compareTo(BigDecimal.ZERO) > 0, balanceMap, pointsCardBalanceMap,
                positionMap, orderMap);

        // 撤销和已经完全成交的订单,需要删除
        final List<Order> deleteOrderList = new ArrayList<>();
        // 被撤的单和完全成交的订单
        final List<Order> finishOrderList = new ArrayList<>();
        // 待修改订单
        final Map<Long, Order> orderChangeMap = new HashMap<>();
        // 待插入pending
        final List<Pending> dealPendingList = new ArrayList<>();
        // 记录已经成交的orders
        final List<ShortOrders> dealShortOrdersList = new LinkedList<>();
        shortOrdersList.forEach(shortOrders -> {
            // 没有maker时是取消订单
            if (shortOrders.getMakerOrder() == null) {
                final ShortOrder cancelledOrder = shortOrders.getTakerOrCancelledOrder();
                final BrokerUserBean brokerUserBean = BrokerUserBean.builder()
                        .brokerId(cancelledOrder.getBrokerId()).userId(cancelledOrder.getUserId()).build();
                final Order order = orderMap.get(brokerUserBean).get(cancelledOrder.getId());
                deleteOrderList.add(order);
                // 新建的撤单不用插入orderFinish
                if (cancelledOrder.getStatus() == OrderStatusEnum.CANCELED.getCode()) {
                    order.setStatus(cancelledOrder.getStatus());
                    order.setReason(cancelledOrder.getReason());
                    //机器人账号不记订单
                    if (!order.getUserId().equals(this.perpetualConfig.getRobot())) {
                        finishOrderList.add(order);
                    }
                    // 因为数据成交后可能需要删除，比如市场单成交了很多订单，最后因为没有对手单而撤单了
                    orderChangeMap.remove(order.getId());
                }
                return;
            }
            dealShortOrdersList.add(shortOrders);
            for (final ShortOrder shortOrder : shortOrders) {
                final Order order = orderMap.get(BrokerUserBean.builder().brokerId(shortOrder.getBrokerId())
                        .userId(shortOrder.getUserId()).build()).get(shortOrder.getId());
                order.setDealAmount(order.getDealAmount().add(shortOrder.getCurrentAmount()));
                order.setDealSize(order.getDealSize().add(shortOrder.getCurrentSize()));
                if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
                    order.setAvgPrice(BigDecimalUtil.divide(order.getDealSize(),
                            BigDecimalUtil.multiply(order.getDealAmount(), contract.getUnitAmount()), contract.getMinQuoteDigit()));
                } else {
                    order.setAvgPrice(BigDecimalUtil.divide(BigDecimalUtil.multiply(order.getDealAmount(), contract.getUnitAmount()),
                            order.getDealSize(), contract.getMinQuoteDigit()));
                }
                order.setStatus(shortOrder.getStatus());
                if (shortOrder.getStatus() == OrderStatusEnum.COMPLETE.getCode()) {
                    deleteOrderList.add(order);
                    //机器人账号不记订单
                    if (!shortOrder.getUserId().equals(this.perpetualConfig.getRobot())) {
                        finishOrderList.add(order);
                    }
                    // 完全成交的单子不需要更新，但需要删除
                    orderChangeMap.remove(order.getId());
                } else {
                    // 重复的成交订单，新订单可能拆分为多个成交的小order，只需保留最新状态
                    orderChangeMap.put(order.getId(), order);
                }
                final Pending pending = Pending.builder().userId(order.getUserId())
                        .contractCode(contract.getContractCode()).orderId(order.getOrderId())
                        .side(order.getSide()).otherSide(shortOrder.getOtherSide())
                        .amount(shortOrder.getCurrentAmount()).price(shortOrder.getCurrentPrice())
                        .size(shortOrder.getCurrentSize()).status(order.getStatus())
                        .brokerId(order.getBrokerId()).createdDate(new Date()).build();
                // 记录pending
                dealPendingList.add(pending);
                if (pending.getSide().equals(pending.getOtherSide())) {
                    pendingList.add(pending);
                }
            }
        });

        // 标记价格
        final Map<String, MarkIndexPriceDTO> markIndexPriceMap = this.marketService.allMarkIndexPrice();

        // 待插入user账单
        final List<UserBill> userBillList = new ArrayList<>();
        // 待插入系统账单
        final List<SystemBill> systemBillList = new ArrayList<>();
        // 处理成交的order数据
        dealShortOrdersList.forEach(shortOrders -> {
            final ShortOrder takerShortOrder = shortOrders.getTakerOrCancelledOrder();
            final ShortOrder makerShortOrder = shortOrders.getMakerOrder();
            //机器人账号不处理成交
            if (takerShortOrder.getUserId().equals(this.perpetualConfig.getRobot())
                    && makerShortOrder.getUserId().equals(this.perpetualConfig.getRobot())) {
                return;
            }
            final BrokerUserBean takerBrokerUserBean = BrokerUserBean.builder()
                    .brokerId(takerShortOrder.getBrokerId()).userId(takerShortOrder.getUserId()).build();
            final BrokerUserBean makerBrokerUserBean = BrokerUserBean.builder()
                    .brokerId(makerShortOrder.getBrokerId()).userId(makerShortOrder.getUserId()).build();
            final Order takerOrder = orderMap.get(takerBrokerUserBean).get(takerShortOrder.getId());
            final Order makerOrder = orderMap.get(makerBrokerUserBean).get(makerShortOrder.getId());
            // 交易
            this.userPositionService.transfer(contract, false, markIndexPriceMap, contractList,
                    takerShortOrder, takerOrder, makerShortOrder, makerOrder, balanceMap, positionMap,
                    basePointsCardPrice, pointsCardBalanceMap, userBillList, systemBillList);
            // taker可能是(13:强平单 14:爆仓单)，处理保险金; maker可能是(13:强平单)，但是没有保险金
            if (takerOrder.getSystemType() == OrderSystemTypeEnum.FORCED_LIQUIDATION.getSystemType()
                    || takerOrder.getSystemType() == OrderSystemTypeEnum.EXPLOSION.getSystemType()) {
                this.dealExplosionLiqudateTransfer(takerShortOrder, takerOrder,
                        balanceMap.get(BrokerUserBean.builder().brokerId(Constants.INSURANCE_BROKERID)
                                .userId(contract.getInsuranceAccount()).build()),
                        BillTypeEnum.INSURANCE, userBillList, systemBillList);
            }
        });

        final MatchingCache matchingCache = MatchingCache.builder()
                .contractCode(contract.getContractCode())
                .contractInfo(JSON.toJSONString(contract))
                .build();

        // order和orderAll入库
        if (orderChangeMap.size() > 0) {
            final List<Order> orderList = new ArrayList<>(orderChangeMap.values());
            final long start = System.currentTimeMillis();
            this.orderShardingService.batchAddOnDuplicateKey(orderList,
                    ShardingUtil.buildContractOrderShardTable(contract));
            log.info("TradeServiceImpl orderChangeMap cost {}", (System.currentTimeMillis() - start));
            matchingCache.setOrderAllUpdateInfo(JSON.toJSONString(orderList));
        } else {
            matchingCache.setOrderAllUpdateInfo("");
        }

        // pending入库
        if (dealPendingList.size() > 0) {
            matchingCache.setPendingInfo(JSON.toJSONString(dealPendingList));

            final long start = System.currentTimeMillis();

            this.pendingShardingService.batchInsertWithId(dealPendingList,
                    ShardingUtil.buildContractPendingShardTable(contract));

            log.info("TradeServiceImpl dealPendingList cost {}", (System.currentTimeMillis() - start));
        } else {
            matchingCache.setPendingInfo("");
        }

        // 删除的order
        if (deleteOrderList.size() > 0) {
            final long start = System.currentTimeMillis();
            this.orderShardingService.batchDelete(deleteOrderList, ShardingUtil.buildContractOrderShardTable(contract));
            log.info("TradeServiceImpl deleteOrderList cost {}", (System.currentTimeMillis() - start));
            matchingCache.setOrderAllDeleteInfo(JSON.toJSONString(deleteOrderList));
            // 处理position和释放order统计
            for (final Order order : deleteOrderList) {
                if (order.getStatus() == OrderStatusEnum.CANCELED.getCode()) {
                    final UserPosition userPosition =
                            positionMap.get(BrokerUserBean.builder().brokerId(order.getBrokerId())
                                    .userId(order.getUserId()).build()).get(contract.getContractCode());
                    // 取消close单需要释放closingAmount
                    if (OrderDetailSideEnum.isClose(order.getDetailSide())) {
                        userPosition.setClosingAmount(userPosition.getClosingAmount().subtract(userPosition
                                .getClosingAmount().min(order.getAmount().subtract(order.getDealAmount()))));
                    }

                    // 取消订单恢复userPosition的order数据
                    final BigDecimal cancelAmount = order.getAmount().subtract(order.getDealAmount());
                    if (OrderSideEnum.LONG.getSide().equals(order.getSide())) {
                        userPosition
                                .setOrderLongAmount(userPosition.getOrderLongAmount().subtract(cancelAmount));
                        // 机器人账号下单不加orderAmount，可能小于0
                        if (userPosition.getOrderLongAmount().compareTo(BigDecimal.ZERO) < 0) {
                            userPosition.setOrderLongAmount(BigDecimal.ZERO);
                        }
                        if (userPosition.getOrderLongAmount().compareTo(BigDecimal.ZERO) == 0) {
                            userPosition.setOrderLongSize(BigDecimal.ZERO);
                        } else {
                            userPosition.setOrderLongSize(userPosition.getOrderLongSize()
                                    .subtract(BigDecimalUtil.divide(cancelAmount, order.getPrice())));
                        }
                    } else {
                        userPosition
                                .setOrderShortAmount(userPosition.getOrderShortAmount().subtract(cancelAmount));
                        // 机器人账号下单不加orderAmount，可能小于0
                        if (userPosition.getOrderShortAmount().compareTo(BigDecimal.ZERO) < 0) {
                            userPosition.setOrderShortAmount(BigDecimal.ZERO);
                        }
                        if (userPosition.getOrderShortAmount().compareTo(BigDecimal.ZERO) == 0) {
                            userPosition.setOrderShortSize(BigDecimal.ZERO);
                        } else {
                            userPosition.setOrderShortSize(userPosition.getOrderShortSize()
                                    .subtract(BigDecimalUtil.divide(cancelAmount, order.getPrice())));
                        }
                    }
                }
            }
        }

        // finish订单处理
        if (finishOrderList.size() > 0) {
            matchingCache.setOrderFinishInfo(JSON.toJSONString(finishOrderList));
        } else {
            matchingCache.setOrderFinishInfo("");
        }

        // 插入账单
        if (userBillList.size() > 0) {
            matchingCache.setUserBillInfo(JSON.toJSONString(userBillList));
        } else {
            matchingCache.setUserBillInfo("");
        }

        // 插入新对账账单
        if (systemBillList.size() > 0) {
            matchingCache.setSystemBillInfo(JSON.toJSONString(systemBillList));
        } else {
            matchingCache.setSystemBillInfo("");
        }

        final long cacheStart = System.currentTimeMillis();
        this.matchingCacheService.add(matchingCache);
        log.info("TradeServiceImpl matchingCache cost {}", (System.currentTimeMillis() - cacheStart));

        // 取卖一
        final LatestTicker latestTicker = this.latestTickerService.getTickerRedis(contract);
        BigDecimal firstSellPrice = null;
        if (latestTicker != null && latestTicker.getSell().compareTo(BigDecimal.ZERO) > 0) {
            firstSellPrice = latestTicker.getSell();
        }

        // 重新计算用户的订单保证金和fee，比如取消订单需要释放一部分保证金和fee
        for (final Map.Entry<BrokerUserBean, UserBalance> entry : balanceMap.entrySet()) {
            // 手续费和保险金账户没有持仓
            if (entry.getKey().getUserId() == PerpetualConstants.FEE_COUNT
                    || entry.getKey().getUserId().equals(contract.getInsuranceAccount())) {
                continue;
            }
            final UserPosition userPosition =
                    positionMap.get(entry.getKey()).get(contract.getContractCode());
            BigDecimal maxFeeRate = this.feesService.getFeeRate(entry.getKey().getUserId(),
                    contract.getPairCode(), MakerEnum.TAKER, entry.getKey().getBrokerId());
            maxFeeRate = maxFeeRate.max(this.feesService.getFeeRate(entry.getKey().getUserId(),
                    contract.getPairCode(), MakerEnum.MAKER, entry.getKey().getBrokerId()));
            final Map<Long, Order> tmpOrders = orderMap.get(entry.getKey());
            final List<Order> orderList = new ArrayList<>();
            if (tmpOrders != null) {
                for (final Order order : tmpOrders.values()) {
                    // 取消单，完成成交单不需要计算
                    if (order.getClazz() == OrderClazzEnum.CANCEL.getClazz()
                            || order.getStatus() == OrderStatusEnum.CANCELED.getCode()
                            || order.getStatus() == OrderStatusEnum.CANCELED.getCode()) {
                        continue;
                    }
                    orderList.add(order);
                }
            }

            // 求保证金
            final OrderMarginFee orderMarginFee = this.orderShardingService
                    .countOrderMarginAndFee(userPosition, firstSellPrice, maxFeeRate, orderList);
            final UserBalance userBalance = entry.getValue();

            /**
             * 释放多余的保证金(可能有取消的订单)，如果保证金不够不管，会有任务来补充保证金或者取消订单
             */

            // 返回订单保证金
            final BigDecimal diffOrderMargin =
                    userPosition.getOrderMargin().subtract(orderMarginFee.getOrderMargin());
            if (diffOrderMargin.compareTo(BigDecimal.ZERO) > 0) {
                userPosition.setOrderMargin(orderMarginFee.getOrderMargin());
                userBalance.setOrderMargin(userBalance.getOrderMargin().subtract(diffOrderMargin));
                userBalance.setAvailableBalance(userBalance.getAvailableBalance().add(diffOrderMargin));
            }
            // 返回订单手续费
            final BigDecimal diffOrderFee =
                    userPosition.getOrderFee().subtract(orderMarginFee.getOrderFee());
            if (diffOrderFee.compareTo(BigDecimal.ZERO) > 0) {
                userPosition.setOrderFee(orderMarginFee.getOrderFee());
                userBalance.setOrderFee(userBalance.getOrderFee().subtract(diffOrderFee));
                userBalance.setAvailableBalance(userBalance.getAvailableBalance().add(diffOrderFee));
            }
            final Map<String, UserPosition> userPositionMap = positionMap.get(entry.getKey());
            FormulaUtil.calculationBrokerForcedLiquidationPrice(contractList, markIndexPriceMap,
                    userPosition, new ArrayList<>(userPositionMap.values()), userBalance);
        }

        final long start1 = System.currentTimeMillis();
        final List<UserBalance> userBalanceList = new ArrayList<>();
        for (final UserBalance ub : balanceMap.values()) {
            userBalanceList.add(UserBalance.builder()
                    .id(ub.getId()).availableBalance(ub.getAvailableBalance())
                    .positionSize(ub.getPositionSize()).positionMargin(ub.getPositionMargin()).positionFee(ub.getPositionFee())
                    .orderMargin(ub.getOrderMargin()).orderFee(ub.getOrderFee()).realizedSurplus(ub.getRealizedSurplus())
                    .build());
        }
        // 批量修改balance和pointsCardBalance: 注意不使用点卡抵扣则pointsCardBalance为空
        this.userBalanceService.batchEdit(userBalanceList);
        log.info("TradeServiceImpl balance batchEdit cost {}",
                (System.currentTimeMillis() - start1));
        if (pointsCardBalanceMap.size() > 0) {
            final long start2 = System.currentTimeMillis();
            this.userBalanceService
                    .batchEdit(new ArrayList<>(pointsCardBalanceMap.values()));
            log.info("TradeServiceImpl pointsCardBalance batchEdit cost {}",
                    (System.currentTimeMillis() - start2));
        }
        final List<UserPosition> userPositionList = new ArrayList<>();
        for (final Map<String, UserPosition> pMap : positionMap.values()) {
            for (final UserPosition up : pMap.values()) {
                userPositionList.add(UserPosition.builder()
                        .id(up.getId()).side(up.getSide()).amount(up.getAmount()).closingAmount(up.getClosingAmount())
                        .openMargin(up.getOpenMargin()).maintenanceMargin(up.getMaintenanceMargin())
                        .fee(up.getFee()).price(up.getPrice()).size(up.getSize()).preLiqudatePrice(up.getPreLiqudatePrice())
                        .liqudatePrice(up.getLiqudatePrice()).brokerPrice(up.getBrokerPrice()).realizedSurplus(up.getRealizedSurplus())
                        .orderMargin(up.getOrderMargin()).orderFee(up.getOrderFee()).orderLongAmount(up.getOrderLongAmount())
                        .orderShortAmount(up.getOrderShortAmount()).orderLongSize(up.getOrderLongSize()).orderShortSize(up.getOrderShortSize())
                        .build());
            }
        }
        final long start3 = System.currentTimeMillis();
        this.userPositionService.batchEdit(userPositionList);
        log.info("TradeServiceImpl position batchEdit cost {}",
                (System.currentTimeMillis() - start3));

        // 推送资产消息
        for (final Map.Entry<BrokerUserBean, UserBalance> entry : balanceMap.entrySet()) {
            final UserBalance pointsCardBalance = pointsCardBalanceMap.get(entry.getKey());
            final List<UserBalance> pushBalanceList;
            if (pointsCardBalance == null) {
                pushBalanceList = Arrays.asList(entry.getValue());
            } else {
                pushBalanceList = Arrays.asList(entry.getValue(), pointsCardBalance);
            }
            this.pushData(contract, PushTypeEnum.ASSET, PushDataUtil.dealUserBalance(pushBalanceList, contract));
        }
        // 推送持仓消息
        for (final Map.Entry<BrokerUserBean, Map<String, UserPosition>> entry : positionMap
                .entrySet()) {
            final List<UserPosition> pushPositionList = new ArrayList<>(entry.getValue().values());
            this.pushData(contract, PushTypeEnum.POSITION,
                    PushDataUtil.dealUserPositions(pushPositionList, contract));
        }
    }

    /**
     * 记录brokerId下的所有userId
     */
    private void fillBrokerIdUserIdMap(final Map<Integer, Set<Long>> brokerIdUserIdMap,
                                       final int brokerId, final long userId) {
        Set<Long> userIdSet = brokerIdUserIdMap.get(brokerId);
        if (userIdSet == null) {
            userIdSet = new HashSet<>();
            brokerIdUserIdMap.put(brokerId, userIdSet);
        }
        userIdSet.add(userId);
    }

    /**
     * 处理爆仓和强平账户的保险账号，可能加钱也可能减钱
     */
    private void dealExplosionLiqudateTransfer(final ShortOrder shortOrder, final Order order,
                                               final UserBalance insuranceBalance, final BillTypeEnum billTypeEnum,
                                               final List<UserBill> userBillList, final List<SystemBill> systemBillList) {
        if (shortOrder.getLostSize() == null
                || shortOrder.getLostSize().compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        final BigDecimal insuranceAvailableBalance =
                insuranceBalance.getAvailableBalance().add(shortOrder.getLostSize());
        if (insuranceAvailableBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BizException("insurance availableBalance " + insuranceBalance.getAvailableBalance()
                    + " less than " + shortOrder.getLostSize());
        }
        final UserBill userBill = this.buildUserBill(insuranceBalance, order.getContractCode(),
                billTypeEnum, "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, "", BigDecimal.ZERO,
                shortOrder.getLostSize(), BigDecimal.ZERO, BigDecimal.ZERO, MakerEnum.BOTH,
                order.getOrderId());
        userBillList.add(userBill);
        // 计算总收益
        insuranceBalance
                .setRealizedSurplus(insuranceBalance.getRealizedSurplus().add(userBill.getProfit()));
        systemBillList.add(this.buildSystemBill(insuranceBalance, "", BigDecimal.ZERO,
                shortOrder.getLostSize(), userBill.getUId()));
        insuranceBalance.setAvailableBalance(insuranceAvailableBalance);
    }

    /**
     * 先锁定资产: 包括点卡，base；(按currencyId asc和user_id asc排序) <br />
     * 后load持仓和订单
     */
    private void lockBalanceAndLoadPositionOrder(final Contract contract,
                                                 final Map<Integer, Set<Long>> brokerIdUserIdMap, final boolean usePointsCard,
                                                 final Map<BrokerUserBean, UserBalance> balanceMap,
                                                 final Map<BrokerUserBean, UserBalance> pointsCardBalanceMap,
                                                 final Map<BrokerUserBean, Map<String, UserPosition>> positionMap,
                                                 final Map<BrokerUserBean, Map<Long, Order>> orderMap) {
        final String baseCurrency = contract.getBase().toLowerCase(),
                dkCurrency = this.perpetualConfig.getDkCurrency().toLowerCase();
        final String[] currencys = new String[]{baseCurrency, dkCurrency};
        // 锁定用户资产：按currency asc,brokerId asc和user_id asc排序；不排序可能和其它锁的锁定顺序不一致造成死锁
        Arrays.sort(currencys);
        final long start = System.currentTimeMillis();
        for (final String currency : currencys) {
            if (currency.equals(baseCurrency)) {
                this.lockBalanceMap(brokerIdUserIdMap, baseCurrency, balanceMap);
            } else if (usePointsCard) {
                this.lockBalanceMap(brokerIdUserIdMap, dkCurrency, pointsCardBalanceMap);
            }
        }
        log.info("TradeServiceImpl lock {}, {} cost {}", baseCurrency, dkCurrency, (System.currentTimeMillis() - start));
        // load持仓和订单
        this.loadPositionOrder(contract, brokerIdUserIdMap, positionMap, orderMap);
    }

    /**
     * 锁定并缓存资产
     */
    private void lockBalanceMap(final Map<Integer, Set<Long>> brokerIdUserIdMap,
                                final String currency, final Map<BrokerUserBean, UserBalance> balanceMap) {
        for (final Map.Entry<Integer, Set<Long>> entry : brokerIdUserIdMap.entrySet()) {
            final List<UserBalance> balanceList =
                    this.userBalanceService.selectBatchForUpdate(currency, entry.getKey(), entry.getValue());
            for (final UserBalance balance : balanceList) {
                balanceMap.put(BrokerUserBean.builder().brokerId(balance.getBrokerId())
                        .userId(balance.getUserId()).build(), balance);
            }
        }
    }

    /**
     * 加载用户持仓和订单
     */
    private void loadPositionOrder(final Contract contract,
                                   final Map<Integer, Set<Long>> brokerIdUserIdMap,
                                   final Map<BrokerUserBean, Map<String, UserPosition>> positionMap,
                                   final Map<BrokerUserBean, Map<Long, Order>> orderMap) {
        for (final Map.Entry<Integer, Set<Long>> entry : brokerIdUserIdMap.entrySet()) {
            final long start1 = System.currentTimeMillis();
            final List<UserPosition> positionList = this.userPositionService
                    .selectBatchPosition(contract.getContractCode(), entry.getKey(), entry.getValue());
            log.info("TradeServiceImpl selectBatchPosition cost {}", (System.currentTimeMillis() - start1));
            for (final UserPosition position : positionList) {
                // base不相同的过滤掉
                if (!position.getBase().equals(contract.getBase())) {
                    continue;
                }
                final BrokerUserBean brokerUserBean = BrokerUserBean.builder()
                        .brokerId(position.getBrokerId()).userId(position.getUserId()).build();
                Map<String, UserPosition> tmpMap = positionMap.get(brokerUserBean);
                if (tmpMap == null) {
                    tmpMap = new HashMap<>();
                    positionMap.put(brokerUserBean, tmpMap);
                }
                tmpMap.put(position.getContractCode(), position);
            }
            final long start2 = System.currentTimeMillis();
            final List<Order> orderList =
                    this.orderShardingService.selectBatchOrder(contract.getContractCode(), entry.getKey(),
                            entry.getValue(), ShardingUtil.buildContractOrderShardTable(contract));
            log.info("TradeServiceImpl selectBatchOrder cost {}", (System.currentTimeMillis() - start2));
            for (final Order order : orderList) {
                final BrokerUserBean brokerUserBean = BrokerUserBean.builder().brokerId(order.getBrokerId())
                        .userId(order.getUserId()).build();
                Map<Long, Order> tmpOrders = orderMap.get(brokerUserBean);
                if (tmpOrders == null) {
                    tmpOrders = new HashMap<>();
                    orderMap.put(brokerUserBean, tmpOrders);
                }
                tmpOrders.put(order.getId(), order);
            }
        }
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

    /**
     * 推送数据，如果推送失败不回滚(记日志)，因为不影响正常逻辑
     */
    private void pushData(final Contract contract, final PushTypeEnum pushTypeEnum,
                          final JSONArray data) {
        // log.info("TradeServiceImpl pushData: {}, {}", contract, pushTypeEnum);
        try {
            this.redisTemplate.convertAndSend(Constants.buildChannel(contract),
                    PushInfo.builder().biz(Constants.PERPETUAL_BIZ).type(pushTypeEnum.name())
                            .contractCode(PushTypeEnum.ASSET == pushTypeEnum ? null : contract.getContractCode())
                            .zip(false).data(data).build().compress());
        } catch (final Exception e) {
            log.error("TradeServiceImpl pushData error: ", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealDB(final String contractCode) {
        final List<MatchingCache> matchingCacheList = this.matchingCacheService.getBatch(contractCode);
        log.info("TradeServiceImpl dealDB matchingCacheList size: {}", matchingCacheList.size());
        if (matchingCacheList.size() == 0) {
            log.info("TradeServiceImpl dealDB matchingCacheList empty end");
            return;
        }
        // 推送订单消息
        final List<Order> pushOrderList = new ArrayList<>();
        for (final MatchingCache matchingCache : matchingCacheList) {
            final Contract contract = JSON.parseObject(matchingCache.getContractInfo(), Contract.class);
            if (matchingCache.getOrderAllUpdateInfo() != null && matchingCache.getOrderAllUpdateInfo().length() > 0) {
                final List<Order> orderList = JSON.parseArray(matchingCache.getOrderAllUpdateInfo(), Order.class);
                if (orderList.size() > 0) {
                    final long start = System.currentTimeMillis();
                    this.orderAllService.batchInsertOrderAllDealOnDuplicate(orderList);
                    log.info("TradeServiceImpl orderAllService update cost {}", (System.currentTimeMillis() - start));
                    pushOrderList.addAll(orderList);
                }
            }
            if (matchingCache.getOrderAllDeleteInfo() != null && matchingCache.getOrderAllDeleteInfo().length() > 0) {
                final List<Order> orderList = JSON.parseArray(matchingCache.getOrderAllDeleteInfo(), Order.class);
                if (orderList.size() > 0) {
                    final long start = System.currentTimeMillis();
                    this.orderAllService.deleteByOrderIds(orderList);
                    log.info("TradeServiceImpl orderAllService delete cost {}", (System.currentTimeMillis() - start));
                }
            }
            if (matchingCache.getOrderFinishInfo() != null && matchingCache.getOrderFinishInfo().length() > 0) {
                final List<Order> orderList = JSON.parseArray(matchingCache.getOrderFinishInfo(), Order.class);
                if (orderList.size() > 0) {
                    final long start = System.currentTimeMillis();
                    this.orderFinishShardingService.batchInsertByOrder(orderList,
                            ShardingUtil.buildContractOrderFinishShardTable(contract));
                    log.info("TradeServiceImpl finishOrderList cost {}", (System.currentTimeMillis() - start));
                    pushOrderList.addAll(orderList);
                }
            }
//      if (matchingCache.getPendingInfo() != null && matchingCache.getPendingInfo().length() > 0) {
//        final List<Pending> pendingList = JSON.parseArray(matchingCache.getPendingInfo(), Pending.class);
//        if (pendingList.size() > 0) {
//          final long start = System.currentTimeMillis();
//          this.pendingShardingService.batchInsertWithId(pendingList,
//                  ShardingUtil.buildContractPendingShardTable(contract));
//          log.info("TradeServiceImpl pendingList cost {}", (System.currentTimeMillis() - start));
//        }
//      }
            if (matchingCache.getUserBillInfo() != null && matchingCache.getUserBillInfo().length() > 0) {
                final List<UserBill> userBillList = JSON.parseArray(matchingCache.getUserBillInfo(), UserBill.class);
                if (userBillList.size() > 0) {
                    final long start = System.currentTimeMillis();
                    this.userBillService.batchAdd(userBillList);
                    log.info("TradeServiceImpl userBillList cost {}", (System.currentTimeMillis() - start));
                }
            }
            if (matchingCache.getSystemBillInfo() != null && matchingCache.getSystemBillInfo().length() > 0) {
                final List<SystemBill> systemBillList = JSON.parseArray(matchingCache.getSystemBillInfo(), SystemBill.class);
                if (systemBillList.size() > 0) {
                    final long start = System.currentTimeMillis();
                    this.systemBillService.batchAdd(systemBillList);
                    log.info("TradeServiceImpl systemBillList cost {}", (System.currentTimeMillis() - start));
                }
            }
            if (pushOrderList.size() > 0) {
                // 推送订单消息
                this.pushData(contract, PushTypeEnum.ORDER, PushDataUtil.dealOrders(pushOrderList, contract));
            }
        }
        final long start = System.currentTimeMillis();
        this.matchingCacheService.removeIn(matchingCacheList);
        log.info("TradeServiceImpl matchingCacheService removeIn cost {}", (System.currentTimeMillis() - start));
    }
}
