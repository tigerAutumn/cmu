package cc.newex.dax.perpetual.service.common.impl;

import cc.newex.dax.perpetual.common.config.PerpetualConfig;
import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.OrderDetailSideEnum;
import cc.newex.dax.perpetual.common.enums.OrderSideEnum;
import cc.newex.dax.perpetual.common.enums.OrderSystemTypeEnum;
import cc.newex.dax.perpetual.domain.*;
import cc.newex.dax.perpetual.domain.bean.BrokerUserBean;
import cc.newex.dax.perpetual.domain.bean.ShortOrder;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.OrderDTO;
import cc.newex.dax.perpetual.dto.enums.*;
import cc.newex.dax.perpetual.service.*;
import cc.newex.dax.perpetual.service.common.CloseLiquidateService;
import cc.newex.dax.perpetual.service.common.IdGeneratorService;
import cc.newex.dax.perpetual.service.common.PushService;
import cc.newex.dax.perpetual.util.BigDecimalUtil;
import cc.newex.dax.perpetual.util.PushDataUtil;
import cc.newex.dax.perpetual.util.ShardingUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service("explosionCloseService")
public class ExplosionCloseService extends CloseLiquidateService<Explosion> {

    @Autowired
    private ExplosionService explosionService;
    @Autowired
    private HistoryExplosionExpandService historyExplosionExpandService;
    @Autowired
    private FeesService feesService;
    @Autowired
    private PerpetualConfig perpetualConfig;
    @Autowired
    private UserBillService userBillService;
    @Autowired
    private SystemBillService systemBillService;
    @Autowired
    private PushService pushService;
    @Autowired
    private OrderFinishShardingService orderFinishShardingService;
    @Autowired
    private IdGeneratorService idGeneratorService;

    @Override
    protected List<Long> takePositionId(final List<Explosion> record) {
        if (CollectionUtils.isEmpty(record)) {
            return new ArrayList<>();
        }
        return record.stream().map(Explosion::getId).collect(Collectors.toList());
    }

    @Override
    protected Map<Long, Explosion> toRecordList(final List<Explosion> record) {
        return record.stream().collect(Collectors.toMap(Explosion::getId, Function.identity(), (x, y) -> x));
    }

    @Override
    protected Long takePositionId(final Explosion record) {
        return record.getId();
    }

    @Override
    protected List<Long> takeCancelOrderId(final Explosion record) {
        if (StringUtils.isBlank(record.getCancelOrderId())) {
            return new ArrayList<>();
        }
        return JSON.parseArray(record.getCancelOrderId(), Long.class);
    }

    @Override
    protected Long takeCloseOrderId(final Explosion record) {
        return record.getCloseOrderId();
    }

    @Override
    protected boolean marketPriceIsOutSide(final BigDecimal marketPrice,
                                           final UserPosition userPosition) {
        if (OrderSideEnum.LONG.getSide().equals(userPosition.getSide())) {
            return marketPrice.compareTo(userPosition.getBrokerPrice()) > 0;
        } else {
            return marketPrice.compareTo(userPosition.getBrokerPrice()) < 0;
        }
    }

    @Override
    protected void cancelPriceOutSideOrder(final Contract contract, final BigDecimal marketPrice, final List<UserPosition> userPositions, final List<Explosion> allRecords) {

    }

    @Override
    protected void removeById(final List<Long> id) {
        this.explosionService.removeInById(id);
    }


    @Override
    protected void batchEdit(final List<Explosion> record) {
        this.explosionService.batchEdit(record);
    }

    @Override
    protected void setCancelOrderId(final Explosion record, final String cancelOrderId) {
        record.setCancelOrderId(cancelOrderId);
    }

    @Override
    protected UserBalanceStatusEnum takeUserBalanceStatusEnum() {
        return UserBalanceStatusEnum.EXPLOSION;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void placeCloseOrder(final Contract contract, final BigDecimal marketPrice, final List<UserPosition> positions, final List<Explosion> allRecords) {
        if (CollectionUtils.isEmpty(positions)) {
            return;
        }
        final Map<Integer, Map<Long, UserPosition>> positionMap = positions.stream()
                .collect(Collectors
                        .groupingBy(UserPosition::getBrokerId,
                                Collectors.toMap(UserPosition::getUserId, Function.identity(), (x, y) -> x)));
        final Set<Map.Entry<Integer, Map<Long, UserPosition>>> entries = positionMap.entrySet();
        final List<OrderDTO> orderDTOList = new LinkedList<>();
        final List<UserBalance> userBalanceList = new LinkedList<>();
        for (final Map.Entry<Integer, Map<Long, UserPosition>> entry : entries) {
            final Integer brokerId = entry.getKey();
            final Map<Long, UserPosition> userPositionMap = entry.getValue();

            final List<UserBalance> userBalances = this.userBalanceService
                    .selectBatchForUpdate(contract.getBase(), brokerId, userPositionMap.keySet());
            if (CollectionUtils.isEmpty(userBalances)) {
                ExplosionCloseService.log.error("not found user balance, brokerId : {}, userId : {}", brokerId, JSON.toJSONString(userPositionMap.keySet()));
                continue;
            }
            userBalanceList.addAll(userBalances);
            for (final UserBalance userBalance : userBalances) {
                final UserPosition userPosition = userPositionMap.get(userBalance.getUserId());
                final OrderDTO orderDTO = this.takeOrderDto(userPosition);
                orderDTOList.add(orderDTO);
            }
        }

        final Map<Long, Explosion> recordMap = allRecords.stream().collect(Collectors.toMap(Explosion::getId, Function.identity(), (x, y) -> x));
        final List<Order> orders = this.orderShardingService.batchPlaceOrder(positions, OrderFromEnum.API_ORDER, orderDTOList, contract);
        final List<Explosion> updateExplosionList = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(orders)) {
            final List<HistoryExplosionExpand> historyExplosionExpands = new ArrayList<>(orders.size());
            for (final Order order : orders) {
                final UserPosition userPosition = positionMap.get(order.getBrokerId()).get(order.getUserId());
                final Explosion explosion = recordMap.get(userPosition.getId());
                final HistoryExplosionExpand historyExplosionExpand =
                        this.takeHistoryExplosionExpand(order.getAmount(), order.getAmount(),
                                explosion.getHistoryExplosionId(), order.getOrderId(), HistoryExplosionExpandEnum.ORDER);
                historyExplosionExpands.add(historyExplosionExpand);
                explosion.setCloseOrderId(order.getOrderId());
                updateExplosionList.add(explosion);
            }
            this.historyExplosionExpandService.batchAdd(historyExplosionExpands);
            this.explosionService.batchEdit(updateExplosionList);
            this.pushService.pushData(contract, PushTypeEnum.ORDER, PushDataUtil.dealOrders(orders, contract).toJSONString(),
                    true, false, false);
        }
        this.userPositionService.batchEdit(positions);
        this.pushService.pushData(contract, PushTypeEnum.ASSET, JSON.toJSONString(PushDataUtil.dealUserBalance(userBalanceList, contract)),
                false, false, false);

        this.pushService.pushData(contract, PushTypeEnum.POSITION, JSON.toJSONString(PushDataUtil.dealUserPositions(positions, contract)),
                true, false, false);
    }

    @Override
    public boolean contraTrade(final Contract contract, final List<Contract> contractList, final List<Explosion> record,
                               final List<UserPositionService.UserRank> header, final Map<String, MarkIndexPriceDTO> priceMap) {

        if (CollectionUtils.isEmpty(record)) {
            return true;
        }
        final Map<Long, Explosion> recordMap = record.stream().collect(Collectors.toMap(Explosion::getId, Function.identity(), (x, y) -> x));
        final List<Long> positionIds1 = new ArrayList<>(recordMap.keySet());
        final List<Long> positionIds2 = header.stream().map(UserPositionService.UserRank::getPrositionId).collect(Collectors.toList());
        final BigDecimal dfPrice = this.feesService.getUsdPrice(this.perpetualConfig.getDkCurrency());
        final List<Long> allPositionIds = new ArrayList<>();
        allPositionIds.addAll(positionIds1);
        allPositionIds.addAll(positionIds2);

        if (CollectionUtils.isEmpty(allPositionIds)) {
            return true;
        }

        final List<UserPosition> positions = this.userPositionService.getInById(allPositionIds);
        if (CollectionUtils.isEmpty(positions)) {
            return true;
        }

        final Map<Integer, Map<Long, UserBalance>> balanceMap = this.takeBalanceMap(contract, positions);
        final Map<BrokerUserBean, Map<String, UserPosition>> positionMap = this.takeBrokerPositionMap(contract, contractList, positions, record);

        final Map<Long, UserPosition> positionMap1 = positions.stream().filter(x -> positionIds1.contains(x.getId())).collect(Collectors.toMap(UserPosition::getId, Function.identity(), (x, y) -> x));
        final Map<Long, UserPosition> positionMap2 = positions.stream().filter(x -> positionIds2.contains(x.getId())).collect(Collectors.toMap(UserPosition::getId, Function.identity(), (x, y) -> x));

        final Iterator<Long> iterator1 = positionIds1.iterator();
        final Iterator<Long> iterator2 = positionIds2.iterator();

        Long p1 = null;
        Long p2 = null;

        final List<UserBill> billList = new LinkedList<>();
        final List<SystemBill> systemBillList = new LinkedList<>();
        final List<OrderFinish> orderFinishList = new LinkedList<>();
        final List<HistoryExplosionExpand> historyExplosionExpandList = new LinkedList<>();
        while (iterator1.hasNext() && iterator2.hasNext()) {

            if (p1 == null) {
                p1 = iterator1.next();
            }
            if (p2 == null) {
                p2 = iterator2.next();
            }
            final Map<BrokerUserBean, Map<String, UserPosition>> brokerPositionMap = new HashMap<>();
            final Map<BrokerUserBean, UserBalance> brokerBalanceMap = new HashMap<>();

            BigDecimal amount1 = BigDecimal.ZERO;
            final UserPosition userPosition1 = positionMap1.get(p1);
            if (userPosition1 != null) {
                amount1 = userPosition1.getAmount();
                this.regularBrokerPositionMap(userPosition1, brokerPositionMap);
                this.regularBrokerBalanceMap(userPosition1, brokerBalanceMap, balanceMap);
            }

            BigDecimal amount2 = BigDecimal.ZERO;
            final UserPosition userPosition2 = positionMap2.get(p2);
            if (userPosition2 != null) {
                amount2 = userPosition2.getAmount();
                this.regularBrokerPositionMap(userPosition2, brokerPositionMap);
                this.regularBrokerBalanceMap(userPosition2, brokerBalanceMap, balanceMap);
            }

            final BigDecimal minAmount = amount1.min(amount2);
            if (minAmount.compareTo(BigDecimal.ZERO) <= 0) {
                if (amount1.compareTo(minAmount) <= 0) {
                    p1 = null;
                }
                if (amount2.compareTo(minAmount) <= 0) {
                    p2 = null;
                }
                continue;
            }
            final ShortOrder shortOrder1 =
                    this.buildShortOrder(userPosition1, minAmount, userPosition1.getBrokerPrice(),
                            OrderSideEnum.LONG.getSide().equals(userPosition1.getSide()) ? OrderSideEnum.SHORT
                                    : OrderSideEnum.LONG);
            final ShortOrder shortOrder2 =
                    this.buildShortOrder(userPosition2, minAmount, userPosition1.getBrokerPrice(),
                            OrderSideEnum.LONG.getSide().equals(userPosition2.getSide()) ? OrderSideEnum.SHORT
                                    : OrderSideEnum.LONG);
            final Order order1 = this.buildOrder(userPosition1, minAmount, userPosition1.getBrokerPrice(),
                    OrderSideEnum.LONG.getSide().equals(userPosition1.getSide()) ? OrderSideEnum.SHORT
                            : OrderSideEnum.LONG);
            final Order order2 = this.buildOrder(userPosition2, minAmount, userPosition1.getBrokerPrice(),
                    OrderSideEnum.LONG.getSide().equalsIgnoreCase(userPosition2.getSide()) ? OrderSideEnum.SHORT
                            : OrderSideEnum.LONG);
            final BrokerUserBean brokerUserBean1 = this.buildBrokerUserBean(userPosition1);
            final BrokerUserBean brokerUserBean2 = this.buildBrokerUserBean(userPosition2);
            final Map<BrokerUserBean, UserBalance> userBeanUserBalanceMap = this.buildPointCardBalanceMap(
                    this.perpetualConfig.getDkCurrency(), brokerUserBean1, brokerUserBean2);
            final OrderFinish orderFinish1 = this.buildOrderFinish(userPosition1.getBrokerId(), userPosition1.getUserId(), contract,
                    OrderSideEnum.LONG.getSide().equals(userPosition1.getSide())
                            ? OrderDetailSideEnum.CLOSE_LONG
                            : OrderDetailSideEnum.CLOSE_SHORT,
                    minAmount, userPosition1.getBrokerPrice());
            final Long orderId = this.idGeneratorService.generateOrderId();
            orderFinish1.setSystemType(OrderSystemTypeEnum.CONTRA_TRADE_SOURCE.getSystemType());
            orderFinish1.setRelationOrderId(orderId);
            orderFinish1.setReason(0);
            order1.setSystemType(OrderSystemTypeEnum.CONTRA_TRADE_SOURCE.getSystemType());
            shortOrder1.setSystemType(OrderSystemTypeEnum.CONTRA_TRADE_SOURCE.getSystemType());
            final OrderFinish orderFinish2 = this.buildOrderFinish(userPosition2.getBrokerId(), userPosition2.getUserId(), contract,
                    OrderSideEnum.LONG.getSide().equals(userPosition1.getSide())
                            ? OrderDetailSideEnum.CLOSE_SHORT
                            : OrderDetailSideEnum.CLOSE_LONG,
                    minAmount, userPosition1.getBrokerPrice());
            orderFinish2.setSystemType(OrderSystemTypeEnum.CONTRA_TRADE_TARGET.getSystemType());
            orderFinish2.setRelationOrderId(orderId);
            order2.setSystemType(OrderSystemTypeEnum.CONTRA_TRADE_TARGET.getSystemType());
            shortOrder2.setSystemType(OrderSystemTypeEnum.CONTRA_TRADE_TARGET.getSystemType());
            orderFinish2.setReason(0);
            this.userPositionService.transfer(contract, true, priceMap, contractList, shortOrder1,
                    order1, shortOrder2, order2, brokerBalanceMap, positionMap, dfPrice, userBeanUserBalanceMap,
                    billList, systemBillList);
            orderFinishList.add(orderFinish1);
            orderFinishList.add(orderFinish2);

            orderFinish1.setFee(Optional.ofNullable(order1.getFee()).orElse(BigDecimal.ZERO));
            orderFinish1.setProfit(Optional.ofNullable(order1.getProfit()).orElse(BigDecimal.ZERO));
            orderFinish1.setReason(Optional.ofNullable(order1.getReason()).orElse(0));

            orderFinish2.setFee(Optional.ofNullable(order2.getFee()).orElse(BigDecimal.ZERO));
            orderFinish2.setProfit(Optional.ofNullable(order2.getProfit()).orElse(BigDecimal.ZERO));
            orderFinish2.setReason(Optional.ofNullable(order2.getReason()).orElse(0));

            ExplosionCloseService.log.info("contraTrade order1 : {}, order2 : {}", order1, order2);

            final HistoryExplosionExpand historyExplosionExpand =
                    this.takeHistoryExplosionExpand(userPosition1.getAmount().add(minAmount), minAmount,
                            recordMap.get(p1).getHistoryExplosionId(), p2, HistoryExplosionExpandEnum.USER);
            historyExplosionExpandList.add(historyExplosionExpand);

            if (amount1.compareTo(minAmount) <= 0) {
                p1 = null;
            }
            if (amount2.compareTo(minAmount) <= 0) {
                p2 = null;
            }
        }

        final List<UserPosition> userPositionList = new LinkedList<>();
        final List<UserBalance> userBalanceList = new LinkedList<>();

        final Set<Map.Entry<Integer, Map<Long, UserBalance>>> entries1 = balanceMap.entrySet();
        for (final Map.Entry<Integer, Map<Long, UserBalance>> entry : entries1) {
            userBalanceList.addAll(entry.getValue().values());
        }
        final Set<Map.Entry<BrokerUserBean, Map<String, UserPosition>>> entries2 = positionMap.entrySet();
        for (final Map.Entry<BrokerUserBean, Map<String, UserPosition>> entry : entries2) {
            userPositionList.addAll(entry.getValue().values());
        }
        if (CollectionUtils.isNotEmpty(orderFinishList)) {
            this.orderFinishShardingService.batchAdd(orderFinishList, ShardingUtil.buildContractOrderFinishShardTable(contract));
            this.pushService.pushData(contract, PushTypeEnum.ORDER, JSON.toJSONString(PushDataUtil.dealOrderFinishs(orderFinishList, contract)));
        }
        if (CollectionUtils.isNotEmpty(userPositionList)) {
            this.userPositionService.batchEdit(userPositionList);
            this.pushService.pushData(contract, PushTypeEnum.POSITION, JSON.toJSONString(PushDataUtil.dealUserPositions(userPositionList, contract)), true, false, false);
        }
        if (CollectionUtils.isNotEmpty(billList)) {
            this.userBillService.batchAdd(billList);
        }
        if (CollectionUtils.isNotEmpty(systemBillList)) {
            this.systemBillService.batchAdd(systemBillList);
        }
        if (CollectionUtils.isNotEmpty(userBalanceList)) {
            this.userBalanceService.batchEdit(userBalanceList);
            this.pushService.pushData(contract, PushTypeEnum.ASSET, JSON.toJSONString(PushDataUtil.dealUserBalance(userBalanceList, contract)), false, false, false);
        }
        if (CollectionUtils.isNotEmpty(historyExplosionExpandList)) {
            this.historyExplosionExpandService.batchAdd(historyExplosionExpandList);
        }

        return !iterator1.hasNext();
    }

    private void regularBrokerPositionMap(final UserPosition userPosition, final Map<BrokerUserBean, Map<String, UserPosition>> map) {

        if (userPosition != null) {
            final BrokerUserBean brokerUserBean = this.buildBrokerUserBean(userPosition);
            if (!map.containsKey(brokerUserBean)) {
                map.put(brokerUserBean, new HashMap<>());
            }
            final Map<String, UserPosition> positionMap = map.get(brokerUserBean);
            positionMap.put(userPosition.getContractCode(), userPosition);
        }
    }

    private void regularBrokerBalanceMap(final UserPosition userPosition, final Map<BrokerUserBean, UserBalance> map, final Map<Integer, Map<Long, UserBalance>> balanceMap) {
        final BrokerUserBean feeAccount = BrokerUserBean.builder().brokerId(userPosition.getBrokerId()).userId(PerpetualConstants.FEE_COUNT).build();
        if (!map.containsKey(feeAccount)) {
            final Map<Long, UserBalance> userBalanceMap = balanceMap.get(userPosition.getBrokerId());
            map.put(feeAccount, userBalanceMap.get(PerpetualConstants.FEE_COUNT));
        }
        if (userPosition != null) {
            final BrokerUserBean brokerUserBean = this.buildBrokerUserBean(userPosition);
            if (map.containsKey(brokerUserBean)) {
                return;
            }
            final Map<Long, UserBalance> userBalanceMap = balanceMap.get(userPosition.getBrokerId());
            if (userBalanceMap.containsKey(userPosition.getUserId())) {
                map.put(brokerUserBean, userBalanceMap.get(userPosition.getUserId()));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public boolean contraTrade(final Contract contract, final List<Contract> contractList,
                               final Explosion record, final Long header) {


        final List<UserBill> userBillList = new LinkedList<>();
        final List<SystemBill> systemBillList = new LinkedList<>();
        final BigDecimal dfPrice = this.feesService.getUsdPrice(this.perpetualConfig.getDkCurrency());

        final Set<Long> userIds = new HashSet<>();
        userIds.add(PerpetualConstants.FEE_COUNT);
        userIds.add(record.getUserId());
        userIds.add(header);
        final List<UserBalance> userBalanceList = this.userBalanceService
                .selectBatchForUpdate(contract.getBase(), record.getBrokerId(), userIds);
        if (CollectionUtils.isEmpty(userBalanceList)) {
            ExplosionCloseService.log.error("not found userBalance, userId : {}",
                    JSON.toJSONString(userIds));
            return false;
        }

        userIds.remove(PerpetualConstants.FEE_COUNT);
        final List<UserPosition> userPositionList = this.userPositionService
                .selectBatchPositionByBase(contract.getBase(), record.getBrokerId(), userIds);
        if (CollectionUtils.isEmpty(userPositionList)) {
            ExplosionCloseService.log.error("not found userBalance, userId : {}",
                    JSON.toJSONString(userIds));
            return false;
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal minAmount = BigDecimal.ZERO;
        UserPosition position1 = null;
        UserPosition position2 = null;
        // 标记价格
        final Map<String, MarkIndexPriceDTO> markIndexPriceMap = this.marketService.allMarkIndexPrice();
        for (final UserPosition u : userPositionList) {
            if (u.getId().equals(record.getId())) {
                totalAmount = u.getAmount();
                position1 = u;
            } else if (u.getContractCode().equalsIgnoreCase(contract.getContractCode())
                    && u.getUserId().equals(header)) {
                minAmount = u.getAmount();
                position2 = u;
            }
        }
        if (totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return true;
        }
        minAmount = minAmount.min(totalAmount);
        if (minAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        final Map<BrokerUserBean, Map<String, UserPosition>> positionMap =
                userPositionList.stream().collect(Collectors.groupingBy(e -> this.buildBrokerUserBean(e),
                        Collectors.toMap(UserPosition::getContractCode, Function.identity(), (x, y) -> x)));

        final Map<BrokerUserBean, UserBalance> balanceMap = userBalanceList.stream().collect(Collectors
                .toMap(balance -> this.buildBrokerUserBean(balance), Function.identity(), (x, y) -> x));

        final ShortOrder shortOrder1 =
                this.buildShortOrder(position1, minAmount, position1.getBrokerPrice(),
                        OrderSideEnum.LONG.getSide().equals(position1.getSide()) ? OrderSideEnum.SHORT
                                : OrderSideEnum.LONG);
        final ShortOrder shortOrder2 =
                this.buildShortOrder(position2, minAmount, position1.getBrokerPrice(),
                        OrderSideEnum.LONG.getSide().equals(position2.getSide()) ? OrderSideEnum.SHORT
                                : OrderSideEnum.LONG);

        final Order order1 = this.buildOrder(position1, minAmount, position1.getBrokerPrice(),
                OrderSideEnum.LONG.getSide().equals(position1.getSide()) ? OrderSideEnum.SHORT
                        : OrderSideEnum.LONG);
        final Order order2 = this.buildOrder(position2, minAmount, position1.getBrokerPrice(),
                OrderSideEnum.LONG.getSide().equalsIgnoreCase(position2.getSide()) ? OrderSideEnum.SHORT
                        : OrderSideEnum.LONG);
        final BrokerUserBean brokerUserBean1 = this.buildBrokerUserBean(position1);
        final BrokerUserBean brokerUserBean2 = this.buildBrokerUserBean(position2);
        final Map<BrokerUserBean, UserBalance> userBeanUserBalanceMap = this.buildPointCardBalanceMap(
                this.perpetualConfig.getDkCurrency(), brokerUserBean1, brokerUserBean2);
        final OrderFinish orderFinish1 = this.buildOrderFinish(position1.getBrokerId(), position1.getUserId(), contract,
                OrderSideEnum.LONG.getSide().equals(position1.getSide())
                        ? OrderDetailSideEnum.CLOSE_LONG
                        : OrderDetailSideEnum.CLOSE_SHORT,
                minAmount, position1.getBrokerPrice());
        final Long orderId = this.idGeneratorService.generateOrderId();
        orderFinish1.setSystemType(OrderSystemTypeEnum.CONTRA_TRADE_SOURCE.getSystemType());
        orderFinish1.setRelationOrderId(orderId);
        final OrderFinish orderFinish2 = this.buildOrderFinish(position2.getBrokerId(), position2.getUserId(), contract,
                OrderSideEnum.LONG.getSide().equals(position1.getSide())
                        ? OrderDetailSideEnum.CLOSE_SHORT
                        : OrderDetailSideEnum.CLOSE_LONG,
                minAmount, position1.getBrokerPrice());
        orderFinish2.setSystemType(OrderSystemTypeEnum.CONTRA_TRADE_TARGET.getSystemType());
        orderFinish2.setRelationOrderId(orderId);
        this.userPositionService.transfer(contract, true, markIndexPriceMap, contractList, shortOrder1,
                order1, shortOrder2, order2, balanceMap, positionMap, dfPrice, userBeanUserBalanceMap,
                userBillList, systemBillList);
        this.orderFinishShardingService.batchAdd(Arrays.asList(orderFinish1, orderFinish2),
                ShardingUtil.buildContractOrderFinishShardTable(contract));
        this.userPositionService.batchEdit(userPositionList);
        this.userBillService.batchAdd(userBillList);
        this.systemBillService.batchAdd(systemBillList);
        this.userBalanceService.batchEdit(userBalanceList);
        this.pushService.pushData(contract, PushTypeEnum.ASSET, JSON.toJSONString(PushDataUtil.dealUserBalance(userBalanceList, contract)), false, false, false);
        this.pushService.pushData(contract, PushTypeEnum.POSITION, JSON.toJSONString(PushDataUtil.dealUserPositions(userPositionList, contract)), true, false, false);
        final HistoryExplosionExpand historyExplosionExpand =
                this.takeHistoryExplosionExpand(position1.getAmount().add(minAmount), minAmount,
                        record.getHistoryExplosionId(), header, HistoryExplosionExpandEnum.USER);
        this.historyExplosionExpandService.add(historyExplosionExpand);
        if (totalAmount.compareTo(minAmount) <= 0) {
            this.removeRecord(contract, Arrays.asList(position1));
            return true;
        }
        return false;
    }

    private OrderDTO takeOrderDto(final UserPosition userPosition) {
        return OrderDTO.builder().brokerId(userPosition.getBrokerId()).userId(userPosition.getUserId())
                .contractCode(userPosition.getContractCode())
                .side(OrderSideEnum.LONG.getSide().equals(userPosition.getSide())
                        ? OrderDetailSideEnum.CLOSE_LONG.getSide()
                        : OrderDetailSideEnum.CLOSE_SHORT.getSide())
                .type(OrderSystemTypeEnum.EXPLOSION.getSystemType()).amount(userPosition.getAmount())
                .price(userPosition.getBrokerPrice()).brokerSize(userPosition.getSize()).build();
    }

    private HistoryExplosionExpand takeHistoryExplosionExpand(final BigDecimal before,
                                                              final BigDecimal close, final Long historyId, final Long referId,
                                                              final HistoryExplosionExpandEnum historyExplosionExpandEnum) {
        final Date date = new Date();
        return HistoryExplosionExpand.builder().beforePositionQuantity(before)
                .afterPositionQuantity(before.subtract(close)).closePositionQuantity(close)
                .createdDate(date).modifyDate(date).historyExplosionId(historyId).referId(referId)
                .type(historyExplosionExpandEnum.getCode()).build();
    }

    private Map<BrokerUserBean, UserBalance> buildPointCardBalanceMap(final String currencyCode,
                                                                      final BrokerUserBean... brokerUserBeans) {

        if (brokerUserBeans == null || brokerUserBeans.length == 0) {
            return new HashMap<>();
        }

        final Map<Integer, List<BrokerUserBean>> mapBean = new HashMap<>();
        for (final BrokerUserBean u : brokerUserBeans) {
            if (!mapBean.containsKey(u.getBrokerId())) {
                mapBean.put(u.getBrokerId(), new LinkedList<>());
            }
            mapBean.get(u.getBrokerId()).add(u);
        }

        final List<Integer> brokerIds = new ArrayList<>(mapBean.keySet());
        brokerIds.sort(Comparator.comparing(Integer::intValue));

        final Map<BrokerUserBean, UserBalance> result = new HashMap<>();
        for (final Integer brokerId : brokerIds) {
            final List<Long> userIds = mapBean.get(brokerId).stream().map(BrokerUserBean::getUserId)
                    .collect(Collectors.toList());
            final List<UserBalance> userBalances = this.userBalanceService
                    .selectBatchForUpdate(currencyCode, brokerId, new HashSet<>(userIds));
            if (CollectionUtils.isNotEmpty(userBalances)) {
                for (final UserBalance userBalance : userBalances) {
                    result.put(BrokerUserBean.builder().brokerId(userBalance.getBrokerId())
                            .userId(userBalance.getUserId()).build(), userBalance);
                }
            }
        }
        return result;
    }

    private ShortOrder buildShortOrder(final UserPosition userPosition, final BigDecimal amount,
                                       final BigDecimal price, final OrderSideEnum orderSideEnum) {
        final int clazz = 0;
        final int mustMaker = 0;
        final int status = 0;
        return ShortOrder.builder().amount(amount).brokerId(userPosition.getBrokerId()).clazz(clazz)
                .dealAmount(BigDecimal.ZERO).mustMaker(mustMaker).price(userPosition.getPrice())
                .side(orderSideEnum.getSide()).status(status)
                .systemType(OrderSystemTypeEnum.EXPLOSION.getSystemType()).currentAmount(amount)
                .userId(userPosition.getUserId()).currentPrice(price).lostSize(BigDecimal.ZERO)
                .currentSize(BigDecimalUtil.divide(amount, price)).otherSide(orderSideEnum.getSide())
                .build();
    }

    private Order buildOrder(final UserPosition userPosition, final BigDecimal amount,
                             final BigDecimal price, final OrderSideEnum sideEnum) {
        final int clazz = 0;
        final int mustMaker = 0;
        final int status = 0;
        final String side = OrderSideEnum.LONG.getSide().equals(userPosition.getSide())
                ? OrderDetailSideEnum.CLOSE_LONG.getSide()
                : OrderDetailSideEnum.CLOSE_SHORT.getSide();
        return Order.builder().amount(amount).brokerId(userPosition.getBrokerId()).clazz(clazz)
                .contractCode(userPosition.getContractCode()).price(price).side(sideEnum.getSide())
                .systemType(OrderSystemTypeEnum.EXPLOSION.getSystemType()).userId(userPosition.getUserId())
                .dealAmount(BigDecimal.ZERO).mustMaker(mustMaker).status(status).avgMargin(BigDecimal.ZERO)
                .size(BigDecimalUtil.divide(amount, price)).detailSide(side).build();
    }

    private BrokerUserBean buildBrokerUserBean(final UserPosition userPosition) {
        return BrokerUserBean.builder().brokerId(userPosition.getBrokerId())
                .userId(userPosition.getUserId()).build();
    }

    private BrokerUserBean buildBrokerUserBean(final UserBalance userPosition) {
        return BrokerUserBean.builder().brokerId(userPosition.getBrokerId())
                .userId(userPosition.getUserId()).build();
    }

    private OrderFinish buildOrderFinish(final Integer brokerId, final Long userId,
                                         final Contract contract, final OrderDetailSideEnum sideEnum,
                                         final BigDecimal amount, final BigDecimal price) {
        final Date date = new Date();
        final Long orderId = this.idGeneratorService.generateOrderId();
        return OrderFinish.builder()
                .amount(amount)
                .avgPrice(price)
                .brokerId(brokerId)
                .userId(userId)
                .clazz(0)
                .contractCode(contract.getContractCode())
                .dealAmount(amount)
                .createdDate(date)
                .dealSize(BigDecimalUtil.divide(amount, price))
                .size(BigDecimalUtil.divide(amount, price))
                .openMargin(BigDecimal.ZERO)
                .detailSide(sideEnum.getSide())
                .orderId(orderId)
                .modifyDate(date)
                .extraMargin(BigDecimal.ZERO)
                .status(OrderStatusEnum.COMPLETE.getCode())
                .side(OrderDetailSideEnum.getOrderSideEnum(sideEnum.getSide()).getSide())
                .price(price)
                .mustMaker(0)
                .orderFrom(OrderFromEnum.API_ORDER.getCode())
                .build();
    }

    private Map<Integer, Map<Long, UserBalance>> takeBalanceMap(final Contract contract, final List<UserPosition> positions) {

        if (CollectionUtils.isEmpty(positions)) {
            return new HashMap<>();
        }

        final Map<Integer, List<UserPosition>> brokerMap = positions.stream().collect(Collectors.groupingBy(UserPosition::getBrokerId));
        final Map<Integer, Map<Long, UserBalance>> result = new HashMap<>();
        final Set<Map.Entry<Integer, List<UserPosition>>> entries = brokerMap.entrySet();
        for (final Map.Entry<Integer, List<UserPosition>> entry : entries) {

            final List<UserPosition> list = entry.getValue();
            if (CollectionUtils.isEmpty(list)) {
                continue;
            }
            final List<Long> userIds = list.stream().map(UserPosition::getUserId).collect(Collectors.toList());
            userIds.add(PerpetualConstants.FEE_COUNT);
            final List<UserBalance> userBalances = this.userBalanceService.selectBatchForUpdate(contract.getBase(), entry.getKey(), new HashSet<>(userIds));
            if (CollectionUtils.isEmpty(userBalances)) {
                continue;
            }
            result.put(entry.getKey(), userBalances.stream().collect(Collectors.toMap(UserBalance::getUserId, Function.identity(), (x, y) -> x)));
        }

        return result;
    }

    private Map<BrokerUserBean, Map<String, UserPosition>> takeBrokerPositionMap(final Contract contract,
                                                                                 final List<Contract> allContract,
                                                                                 final List<UserPosition> positions,
                                                                                 final List<Explosion> explosions) {


        if (CollectionUtils.isEmpty(explosions)) {
            return new HashMap<>();
        }

        final Map<Integer, List<Explosion>> brokerMap = explosions.stream().collect(Collectors.groupingBy(Explosion::getBrokerId));
        final Map<BrokerUserBean, Map<String, UserPosition>> result = new HashMap<>();
        final Set<Map.Entry<Integer, List<Explosion>>> entries = brokerMap.entrySet();

        for (final Map.Entry<Integer, List<Explosion>> entry : entries) {
            final List<Explosion> list = entry.getValue();
            if (CollectionUtils.isEmpty(list)) {
                continue;
            }
            final List<Long> userIds = list.stream().map(Explosion::getUserId).collect(Collectors.toList());
            final Map<Long, List<UserPosition>> positionMap = this.userPositionService.positionMap(entry.getKey(), userIds, allContract, contract);
            if (MapUtils.isEmpty(positionMap)) {
                continue;
            }
            final Set<Map.Entry<Long, List<UserPosition>>> positionEntries = positionMap.entrySet();
            for (final Map.Entry<Long, List<UserPosition>> pEntry : positionEntries) {
                final BrokerUserBean brokerUserBean = BrokerUserBean.builder().userId(pEntry.getKey()).brokerId(entry.getKey()).build();
                if (!result.containsKey(brokerUserBean)) {
                    result.put(brokerUserBean, new HashMap<>());
                }
                result.get(brokerUserBean).putAll(pEntry.getValue().stream().collect(Collectors.toMap(UserPosition::getContractCode, Function.identity(), (x, y) -> x)));
            }
        }

        if (CollectionUtils.isNotEmpty(positions)) {
            for (final UserPosition p : positions) {
                final BrokerUserBean brokerUserBean = BrokerUserBean.builder().userId(p.getUserId()).brokerId(p.getBrokerId()).build();
                if (!result.containsKey(brokerUserBean)) {
                    result.put(brokerUserBean, new HashMap<>());
                }
                result.get(brokerUserBean).put(p.getContractCode(), p);
            }
        }

        return result;
    }
}
