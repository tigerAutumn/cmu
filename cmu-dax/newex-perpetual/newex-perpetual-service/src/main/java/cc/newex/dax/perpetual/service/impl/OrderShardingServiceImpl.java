package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.AbstractCrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.config.PerpetualConfig;
import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.BizErrorCodeEnum;
import cc.newex.dax.perpetual.common.enums.ConditionOrderTypeEnum;
import cc.newex.dax.perpetual.common.enums.OrderClazzEnum;
import cc.newex.dax.perpetual.common.enums.OrderDetailSideEnum;
import cc.newex.dax.perpetual.common.enums.OrderDirectionEnum;
import cc.newex.dax.perpetual.common.enums.OrderSideEnum;
import cc.newex.dax.perpetual.common.enums.OrderSystemTypeEnum;
import cc.newex.dax.perpetual.common.enums.PositionSideEnum;
import cc.newex.dax.perpetual.common.enums.PositionTypeEnum;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.common.push.PushData;
import cc.newex.dax.perpetual.criteria.OrderAllExample;
import cc.newex.dax.perpetual.criteria.OrderShardingExample;
import cc.newex.dax.perpetual.data.OrderAllRepository;
import cc.newex.dax.perpetual.data.OrderShardingRepository;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.Deal;
import cc.newex.dax.perpetual.domain.LatestTicker;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.OrderAll;
import cc.newex.dax.perpetual.domain.OrderCondition;
import cc.newex.dax.perpetual.domain.OrderMarginFee;
import cc.newex.dax.perpetual.domain.TmpOrder;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.MarkIndexReasonablePriceDTO;
import cc.newex.dax.perpetual.dto.OrderDTO;
import cc.newex.dax.perpetual.dto.enums.ContractStatusEnum;
import cc.newex.dax.perpetual.dto.enums.MakerEnum;
import cc.newex.dax.perpetual.dto.enums.OrderConditionEnum;
import cc.newex.dax.perpetual.dto.enums.OrderFromEnum;
import cc.newex.dax.perpetual.dto.enums.OrderStatusEnum;
import cc.newex.dax.perpetual.dto.enums.PushTypeEnum;
import cc.newex.dax.perpetual.dto.enums.UserBalanceStatusEnum;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import cc.newex.dax.perpetual.service.FeesService;
import cc.newex.dax.perpetual.service.LatestTickerService;
import cc.newex.dax.perpetual.service.OrderConditionService;
import cc.newex.dax.perpetual.service.OrderShardingService;
import cc.newex.dax.perpetual.service.UserBalanceService;
import cc.newex.dax.perpetual.service.UserPositionService;
import cc.newex.dax.perpetual.service.cache.CacheService;
import cc.newex.dax.perpetual.service.common.IdGeneratorService;
import cc.newex.dax.perpetual.service.common.MarketService;
import cc.newex.dax.perpetual.service.common.PushService;
import cc.newex.dax.perpetual.util.BigDecimalUtil;
import cc.newex.dax.perpetual.util.ContractSizeUtil;
import cc.newex.dax.perpetual.util.FormulaUtil;
import cc.newex.dax.perpetual.util.ObjectCopyUtil;
import cc.newex.dax.perpetual.util.OrderUtil;
import cc.newex.dax.perpetual.util.PushDataUtil;
import cc.newex.dax.perpetual.util.ShardingUtil;
import cc.newex.dax.users.client.UsersClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 订单表 服务实现
 *
 * @author newex-team
 * @date 2018-10-30 18:49:48
 */
@Slf4j
@Service
public class OrderShardingServiceImpl
        extends AbstractCrudService<OrderShardingRepository, Order, OrderShardingExample, Long>
        implements OrderShardingService {
    @Autowired
    OrderShardingRepository orderShardingRepository;
    @Autowired
    OrderAllRepository orderAllRepository;
    @Resource
    IdGeneratorService idGeneratorService;
    @Resource
    UserPositionService userPositionService;
    @Resource
    CurrencyPairService currencyPairService;
    @Resource
    UsersClient usersClient;
    @Resource
    ContractService contractService;
    @Resource
    UserBalanceService userBalanceService;
    @Resource
    FeesService feesService;
    @Resource
    MarketService marketService;
    @Resource
    CacheService cacheService;
    @Autowired
    LatestTickerService latestTickerService;
    @Autowired
    PerpetualConfig perpetualConfig;
    @Autowired
    OrderConditionService orderConditionService;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    PushService pushService;

    /**
     * 计算每笔订单的开仓保证金和价格偏移保证金
     *
     * @param contract
     * @param orderList
     * @param longEntryRate
     * @param shortEntryRate
     * @param firstSellPrice
     * @param markIndexReasonablePriceDTO
     */
    private boolean getOrderList(final Contract contract, final List<Order> orderList, final BigDecimal longEntryRate,
                                 final BigDecimal shortEntryRate, final BigDecimal firstSellPrice,
                                 final MarkIndexReasonablePriceDTO markIndexReasonablePriceDTO) {
        boolean changed = false;
        for (final Order order : orderList) {
            final boolean isLong = OrderSideEnum.LONG.getSide().equals(order.getSide());
            final BigDecimal entryRate = isLong ? longEntryRate : shortEntryRate;
            BigDecimal openMargin;
            if (isLong && firstSellPrice != null && firstSellPrice.compareTo(order.getPrice()) < 0) {
                openMargin = BigDecimalUtil
                        .multiply(ContractSizeUtil.countSizeWithScale(contract, order.getAmount(), firstSellPrice), entryRate);
            } else {
                openMargin = BigDecimalUtil.multiply(order.getSize(), entryRate);
            }
            BigDecimal extraMargin = BigDecimal.ZERO;
            if (entryRate.compareTo(BigDecimal.ONE) < 0) {
                final BigDecimal reasonablePrice =
                        isLong ? markIndexReasonablePriceDTO.getMaxReasonablePrice()
                                : markIndexReasonablePriceDTO.getMinReasonablePrice();
                if (isLong) {
                    if (order.getPrice().compareTo(reasonablePrice) > 0) {
                        openMargin = openMargin.max(BigDecimalUtil
                                .multiply(ContractSizeUtil.countSizeWithScale(contract, order.getAmount(), reasonablePrice), entryRate));
                        extraMargin = OrderUtil.getPriceDeviationMargin(contract, order.getAmount(), order.getPrice(),
                                reasonablePrice);
                    }
                } else {
                    if (order.getPrice().compareTo(reasonablePrice) < 0) {
                        openMargin = openMargin.max(BigDecimalUtil
                                .multiply(ContractSizeUtil.countSizeWithScale(contract, order.getAmount(), reasonablePrice), entryRate));
                        extraMargin = OrderUtil.getPriceDeviationMargin(contract, order.getAmount(), order.getPrice(),
                                reasonablePrice);
                    }
                }
            }

            // 保证金不变化
            if (openMargin.equals(order.getOpenMargin()) && extraMargin.equals(order.getExtraMargin())) {
                continue;
            }
            changed = true;

            /**
             * 订单保证金
             */
            order.setOpenMargin(openMargin);
            /**
             * 价格偏移保证金
             */
            order.setExtraMargin(extraMargin);
            /**
             * 每张合约摊到的保证金
             */
            order.setAvgMargin(
                    BigDecimalUtil.divide(BigDecimalUtil.add(extraMargin, openMargin), order.getAmount()));
        }
        return changed;
    }

    private void cancelOrder(final Long userId, final Integer brokerId, final String contractCode, final OrderSystemTypeEnum orderSystemTypeEnum,
                             final List<Order> orderList) {
        this.cancelOrder(userId, brokerId, contractCode, orderList, orderSystemTypeEnum, true);
    }

    private void cancelOrder(final Long userId, final Integer brokerId, final String contractCode,
                             final List<Order> orderList, final OrderSystemTypeEnum orderSystemTypeEnum, final boolean checkOwner) {
        if (CollectionUtils.isEmpty(orderList)) {
            return;
        }

        final List<Order> insertList = new ArrayList<>();
        final List<Order> updateList = new ArrayList<>();
        for (final Order o : orderList) {
            if (o == null) {
                continue;
            }
            // 判断用户与订单是否匹配
            if (checkOwner && (!o.getUserId().equals(userId) || !o.getBrokerId().equals(brokerId))) {
                continue;
            }
            if (o.getClazz().equals(OrderClazzEnum.CANCEL.getClazz())) {
                continue;
            }
            if (o.getStatus().equals(OrderStatusEnum.CANCELING.getCode())) {
                continue;
            }
            final Order cancelOrder = new Order();
            BeanUtils.copyProperties(o, cancelOrder);

            cancelOrder.setMatchStatus(0);
            cancelOrder.setStatus(OrderStatusEnum.NOT_DEAL.getCode());
            cancelOrder.setClazz(OrderClazzEnum.CANCEL.getClazz());
            cancelOrder.setId(null);
            if (orderSystemTypeEnum != null) {
                cancelOrder.setSystemType(orderSystemTypeEnum.getSystemType());
            }
            cancelOrder.setOrderId(this.idGeneratorService.generateOrderId());
            cancelOrder.setRelationOrderId(o.getOrderId());
            insertList.add(cancelOrder);

            o.setStatus(OrderStatusEnum.CANCELING.getCode());
            o.setMatchStatus(null);
            o.setContractCode(null);
            o.setUserId(null);
            o.setBrokerId(null);
            o.setOrderId(null);
            updateList.add(o);
        }
        if (CollectionUtils.isNotEmpty(insertList)) {
            final ShardTable shardTable = this.getOrderShardTable(contractCode);
            this.batchAdd(insertList, shardTable);
            this.batchEdit(updateList, shardTable);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkOrderMargin(
            final ContractCodeUserIdBrokerIdBean contractCodeUserIdBrokerIdBean,
            final CurrencyPair currencyPair, final Contract contract) {
        final UserBalance userBalance =
                this.userBalanceService.getForUpdate(contractCodeUserIdBrokerIdBean.getUserId(),
                        contractCodeUserIdBrokerIdBean.getBrokerId(), currencyPair.getBase());
        //强平和爆仓不管
        if (userBalance.getStatus() != UserBalanceStatusEnum.NORMAL.getCode()) {
            return false;
        }

        final UserPosition userPosition = this.userPositionService.getUserPosition(
                contractCodeUserIdBrokerIdBean.getUserId(), contractCodeUserIdBrokerIdBean.getBrokerId(),
                contractCodeUserIdBrokerIdBean.getContractCode());
        return this.checkOrderMarginWithBlance(userBalance, userPosition,
                contractCodeUserIdBrokerIdBean, currencyPair, contract, true);
    }

    /**
     * 检查订单保证金不足的订单,撤销订单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkOrderMarginWithBlance(final UserBalance userBalance,
                                              final UserPosition userPosition,
                                              final ContractCodeUserIdBrokerIdBean contractCodeUserIdBrokerIdBean,
                                              final CurrencyPair currencyPair, final Contract contract, final boolean check) {

        BigDecimal longTotalSize = userPosition.getOrderLongSize();
        if (userPosition.getSide().equals(OrderSideEnum.LONG.getSide())) {
            longTotalSize = longTotalSize.add(userPosition.getSize());
        }
        BigDecimal shortTotalSize = userPosition.getOrderShortSize();
        if (userPosition.getSide().equals(OrderSideEnum.SHORT.getSide())) {
            shortTotalSize = shortTotalSize.add(userPosition.getSize());
        }
        /**
         * 开仓保证金率
         */
        final CurrencyPairService.GearRate longGearRate =
                this.currencyPairService.getOpenMarginRate(currencyPair.getPairCode(),
                        longTotalSize.max(userPosition.getGear()), userPosition.getLever());
        final CurrencyPairService.GearRate shortGearRate =
                this.currencyPairService.getOpenMarginRate(currencyPair.getPairCode(),
                        shortTotalSize.max(userPosition.getGear()), userPosition.getLever());
        /**
         * 获取合理价格
         */
        final MarkIndexReasonablePriceDTO markIndexReasonablePriceDTO = this.marketService
                .getReasonablePrice(contract, BigDecimalUtil.divide(BigDecimal.ONE, userPosition.getLever())
                        .subtract(currencyPair.getMaintainRate()));

        // 订单列表
        final List<Order> orderList = this.queryTradeOrderList(userPosition.getUserId(),
                userPosition.getBrokerId(), userPosition.getContractCode());

        // 取卖一
        final LatestTicker latestTicker = this.latestTickerService.getTickerRedis(contract);
        BigDecimal firstSellPrice = null;
        if (latestTicker != null && latestTicker.getSell().compareTo(BigDecimal.ZERO) > 0) {
            firstSellPrice = latestTicker.getSell();
        }

        /**
         * 计算每笔订单的开仓保证金和价格偏移保证金
         */
        final boolean changed =
                this.getOrderList(contract, orderList, longGearRate.getEntryRate(),
                        shortGearRate.getEntryRate(), firstSellPrice, markIndexReasonablePriceDTO);
        // 调节杠杆不能return
        if (check && !changed) {
            return true;
        }
        /**
         * 计算总的手续费和总订单保证金：手续费变动不管，只会导致系统或者用户多得或者少得手续费
         */
        final OrderMarginFee orderMarginFee =
                this.countOrderMarginAndFee(userPosition, firstSellPrice, BigDecimal.ZERO, orderList);
        /**
         * 全仓追加或者移除保证金，逐仓不动保证金，保证金不够取消订单
         */
        final BigDecimal diffOrderMargin = orderMarginFee.getOrderMargin().subtract(userBalance.getOrderMargin());
        final List<UserPosition> pushPositionList;
        if (userPosition.getType() == PositionTypeEnum.ALL_IN.getType() || !check) {// 全仓或者调整杠杆
            final List<Contract> contractList = this.contractService.getUnExpiredContract();
            final Map<String, MarkIndexPriceDTO> markIndexPriceMap =
                    this.marketService.allMarkIndexPrice();
            // 不使用该position，因为调整杠杆时还没入库
            final List<UserPosition> userPositionList =
                    this.userPositionService.getUserPositionWithoutIdByBase(userPosition.getId(),
                            userBalance.getUserId(), userBalance.getBrokerId(), contract.getBase());
            userPositionList.add(userPosition);
            // 计算可用的保险金
            final BigDecimal totalBalance = FormulaUtil.countAvailableBalance(userBalance,
                    markIndexPriceMap, userPositionList);
            if (diffOrderMargin.compareTo(BigDecimal.ZERO) > 0) {
                if (totalBalance.subtract(diffOrderMargin).compareTo(BigDecimal.ZERO) < 0) {
                    log.warn(
                            "checkOrderMargin auto all cancelOrder,userId={},originOrderMargin={},orderMargin={},diffOrderMargin={}",
                            userBalance.getUserId(), userBalance.getOrderMargin(), orderMarginFee.getOrderMargin(), diffOrderMargin);
                    if (check) {
                        this.cancelMarginAll(userPosition.getBrokerId(), userPosition.getUserId(),
                                userPosition.getContractCode());
                    }
                    return false;
                }
            }
            /**
             * 增加或释放订单保证金
             */
            userPosition.setOrderMargin(orderMarginFee.getOrderMargin());
            userBalance.setAvailableBalance(userBalance.getAvailableBalance().subtract(diffOrderMargin));
            userBalance.setOrderMargin(userBalance.getOrderMargin().add(diffOrderMargin));
            // 重新计算强平价格
            FormulaUtil.calculationBrokerForcedLiquidationPrice(contractList, markIndexPriceMap,
                    userPosition, userPositionList, userBalance);
            this.userPositionService.batchEdit(userPositionList);
            pushPositionList = userPositionList;
        } else {// 逐仓
            if (diffOrderMargin.compareTo(BigDecimal.ZERO) > 0) {
                log.warn(
                        "checkOrderMargin auto part cancelOrder,userId={},originOrderMargin={},orderMargin={},diffOrderMargin={}", userBalance.getUserId(),
                        userBalance.getOrderMargin(), orderMarginFee.getOrderMargin(), diffOrderMargin);
                if (check) {
                    this.cancelMarginAll(userPosition.getBrokerId(), userPosition.getUserId(),
                            userPosition.getContractCode());
                }
                return false;
            }
            this.userPositionService.editById(userPosition);
            pushPositionList = Arrays.asList(userPosition);
        }
        if (orderList.size() > 0) {
            if (userPosition.getAmount().compareTo(BigDecimal.ZERO) > 0 && userBalance.getStatus() == UserBalanceStatusEnum.NORMAL.getCode()) {
                final List<Order> leftOrderList = new ArrayList<>(), cancelOrderList = new ArrayList<>();
                for (final Order order : orderList) {
                    final OrderSideEnum orderSideEnum = OrderDetailSideEnum.getOrderSideEnum(order.getDetailSide());
                    /**
                     * 判断下单价格是否超过破产价
                     */
                    if (!userPosition.getSide().equals(orderSideEnum.getSide())) {
                        if ((PositionSideEnum.LONG.getSide().equals(userPosition.getSide())
                                && order.getPrice().compareTo(userPosition.getBrokerPrice()) < 0) || (PositionSideEnum.SHORT.getSide().equals(userPosition.getSide())
                                && order.getPrice().compareTo(userPosition.getBrokerPrice()) > 0)) {
                            cancelOrderList.add(order);
                            continue;
                        }
                    }
                    leftOrderList.add(order);
                }
                if (cancelOrderList.size() > 0) {
                    this.cancelOrder(userPosition.getUserId(), userPosition.getBrokerId(),
                            userPosition.getContractCode(), OrderSystemTypeEnum.BROKER_PRICE_CANCEL, cancelOrderList);
                }
                if (leftOrderList.size() > 0) {
                    for (final Order order : orderList) {
                        order.setMatchStatus(null);
                    }
                    this.orderShardingRepository.batchUpdate(orderList,
                            ShardingUtil.buildContractOrderShardTable(contract));
                }
            } else {
                for (final Order order : orderList) {
                    order.setMatchStatus(null);
                }
                this.orderShardingRepository.batchUpdate(orderList,
                        ShardingUtil.buildContractOrderShardTable(contract));
            }
        }
        this.userBalanceService.editById(userBalance);

        // push balance
        final List<UserBalance> pushBalanceList = Arrays.asList(userBalance);
        this.cacheService.convertAndSend(
                (PerpetualConstants.PERPETUAL + "_" + contract.getPairCode()).toUpperCase(),
                JSON.toJSONString(
                        PushData.builder().biz(PerpetualConstants.PERPETUAL).type(PushTypeEnum.ASSET.name())
                                .zip(false).data(PushDataUtil.dealUserBalance(pushBalanceList, contract)).build()));
        // push position
        this.cacheService.convertAndSend(
                (PerpetualConstants.PERPETUAL + "_" + contract.getPairCode()).toUpperCase(),
                JSON.toJSONString(PushData.builder().biz(PerpetualConstants.PERPETUAL)
                        .type(PushTypeEnum.POSITION.name()).contractCode(contract.getContractCode()).zip(false)
                        .data(PushDataUtil.dealUserPositions(pushPositionList, contract)).build()));
        return true;
    }

    private void cancelMarginAll(final Integer brokerId, final Long userId, final String... contractCode) {
        if (contractCode == null || contractCode.length == 0) {
            return;
        }
        final OrderShardingServiceImpl shardingService =
                this.applicationContext.getBean(this.getClass());
        for (int i = 0; i < contractCode.length; i++) {
            shardingService.cancelAllByContractCode(brokerId, userId, contractCode[i], OrderSystemTypeEnum.MARGIN_AUTO_CANCEL);
        }
    }

    @Override
    protected OrderShardingExample getPageExample(final String fieldName, final String keyword) {
        final OrderShardingExample example = new OrderShardingExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int createOrderIfNotExists(final ShardTable shardTable) {
        return this.orderShardingRepository.createOrderIfNotExists(shardTable);
    }

    @Override
    public List<Order> getOrderByMatchStatus(final String contractCode, final int matchStatus,
                                             final Integer limit, final ShardTable shardTable) {
        return this.orderShardingRepository.selectByMatchStatus(contractCode, matchStatus, limit, shardTable);
    }

    @Override
    public List<Order> selectBatchOrder(final String contractCode, final Integer brokerId,
                                        final Set<Long> userIds, final ShardTable shardTable) {
        return this.orderShardingRepository.selectBatchOrder(contractCode, brokerId, userIds,
                shardTable);
    }

    @Override
    public ShardTable getOrderShardTable(final String contractCode) {
        final Contract contract = this.contractService.getContract(contractCode);
        return ShardingUtil.buildContractOrderShardTable(contract);
    }

    @Override
    public List<Order> selectInByOrderId(final String contractCode, final List<Long> orderId) {
        if (CollectionUtils.isEmpty(orderId)) {
            return new ArrayList<>();
        }
        final OrderShardingExample example = new OrderShardingExample();
        example.createCriteria().andOrderIdIn(orderId);
        return this.getByExample(example, this.getOrderShardTable(contractCode));
    }

    @Override
    public Order getByOrderId(final String contractCode, final Long orderId) {
        if (StringUtils.isBlank(contractCode) || orderId == null) {
            return null;
        }

        final OrderShardingExample example = new OrderShardingExample();
        example.createCriteria().andOrderIdEqualTo(orderId);

        return this.orderShardingRepository.selectOneByExample(example, this.getOrderShardTable(contractCode));
    }

    @Override
    public OrderMarginFee countOrderMarginAndFee(final UserPosition userPosition,
                                                 final BigDecimal firstSellPrice, final BigDecimal maxFeeRate, final List<Order> orderList) {
        if (orderList == null || orderList.size() == 0) {
            return OrderMarginFee.builder().orderMargin(BigDecimal.ZERO).orderFee(BigDecimal.ZERO)
                    .build();
        }
        List<TmpOrder> tmpOrderList = new ArrayList<>();
        for (final Order order : orderList) {
            BigDecimal price = order.getPrice();
            if (OrderSideEnum.LONG.getSide().equals(order.getSide()) && firstSellPrice != null
                    && firstSellPrice.compareTo(order.getPrice()) < 0) {
                price = firstSellPrice;
            }
            final TmpOrder tmpOrder = TmpOrder.builder().side(order.getSide())
                    .leftAmount(order.getAmount().subtract(order.getDealAmount())).price(price)
                    .avgMargin(order.getAvgMargin()).build();
            tmpOrderList.add(tmpOrder);
        }
        tmpOrderList.sort(Comparator.comparing(TmpOrder::getAvgMargin));
        final BigDecimal orderMargin = this.countOrderMargin(userPosition, tmpOrderList);
        tmpOrderList = new ArrayList<>();
        for (final Order order : orderList) {
            BigDecimal price = order.getPrice();
            if (OrderSideEnum.LONG.getSide().equals(order.getSide()) && firstSellPrice != null
                    && firstSellPrice.compareTo(order.getPrice()) < 0) {
                price = firstSellPrice;
            }
            final TmpOrder tmpOrder = TmpOrder.builder().side(order.getSide())
                    .leftAmount(order.getAmount().subtract(order.getDealAmount())).price(price)
                    .avgMargin(order.getAvgMargin()).build();
            tmpOrderList.add(tmpOrder);
        }
        tmpOrderList.sort(Comparator.comparing(TmpOrder::getPrice).reversed());
        final BigDecimal orderFee = this.countOrderFee(userPosition, maxFeeRate, tmpOrderList);
        return OrderMarginFee.builder().orderMargin(orderMargin).orderFee(orderFee).build();
    }

    /**
     * count OrderMargin
     */
    private BigDecimal countOrderMargin(final UserPosition userPosition,
                                        final List<TmpOrder> tmpOrderList) {
        final LinkedList<TmpOrder> longOrderList = new LinkedList<>();
        final LinkedList<TmpOrder> shortOrderList = new LinkedList<>();
        BigDecimal longSumAmountDecimal = BigDecimal.ZERO;
        BigDecimal shortSumAmountDecimal = BigDecimal.ZERO;
        BigDecimal longSumMarginDecimal = BigDecimal.ZERO;
        BigDecimal shortSumMarginDecimal = BigDecimal.ZERO;
        BigDecimal positionAmount = userPosition.getAmount();
        for (final TmpOrder tmpOrder : tmpOrderList) {
            if (OrderSideEnum.LONG.getSide().equals(tmpOrder.getSide())) {
                if (PositionSideEnum.SHORT.getSide().equals(userPosition.getSide())
                        && positionAmount.compareTo(BigDecimal.ZERO) > 0) {
                    if (positionAmount.compareTo(tmpOrder.getLeftAmount()) >= 0) {
                        positionAmount = positionAmount.subtract(tmpOrder.getLeftAmount());
                    } else {
                        tmpOrder.setLeftAmount(tmpOrder.getLeftAmount().subtract(positionAmount));
                        positionAmount = BigDecimal.ZERO;
                        longOrderList.add(tmpOrder);
                        longSumAmountDecimal = longSumAmountDecimal.add(tmpOrder.getLeftAmount());
                        longSumMarginDecimal = longSumMarginDecimal
                                .add(BigDecimalUtil.multiply(tmpOrder.getAvgMargin(), tmpOrder.getLeftAmount()));
                    }
                    continue;
                }
                longOrderList.add(tmpOrder);
                longSumAmountDecimal = longSumAmountDecimal.add(tmpOrder.getLeftAmount());
                longSumMarginDecimal = longSumMarginDecimal
                        .add(BigDecimalUtil.multiply(tmpOrder.getAvgMargin(), tmpOrder.getLeftAmount()));
            } else {
                if (PositionSideEnum.LONG.getSide().equals(userPosition.getSide())
                        && positionAmount.compareTo(BigDecimal.ZERO) > 0) {
                    if (positionAmount.compareTo(tmpOrder.getLeftAmount()) >= 0) {
                        positionAmount = positionAmount.subtract(tmpOrder.getLeftAmount());
                    } else {
                        tmpOrder.setLeftAmount(tmpOrder.getLeftAmount().subtract(positionAmount));
                        positionAmount = BigDecimal.ZERO;
                        shortOrderList.add(tmpOrder);
                        shortSumAmountDecimal = shortSumAmountDecimal.add(tmpOrder.getLeftAmount());
                        shortSumMarginDecimal = shortSumMarginDecimal
                                .add(BigDecimalUtil.multiply(tmpOrder.getAvgMargin(), tmpOrder.getLeftAmount()));
                    }
                    continue;
                }
                shortOrderList.add(tmpOrder);
                shortSumAmountDecimal = shortSumAmountDecimal.add(tmpOrder.getLeftAmount());
                shortSumMarginDecimal = shortSumMarginDecimal
                        .add(BigDecimalUtil.multiply(tmpOrder.getAvgMargin(), tmpOrder.getLeftAmount()));
            }
        }
        final LinkedList<TmpOrder> moreOrderList;
        BigDecimal lessSumAmountDecimal;
        final BigDecimal moreSumMarginDecimal;
        final BigDecimal lessSumMarginDecimal;
        // 多空挂单的计算总费用
        if (longSumAmountDecimal.compareTo(shortSumAmountDecimal) >= 0) {
            moreOrderList = longOrderList;
            lessSumAmountDecimal = shortSumAmountDecimal;
            moreSumMarginDecimal = longSumMarginDecimal;
            lessSumMarginDecimal = shortSumMarginDecimal;
        } else {
            moreOrderList = shortOrderList;
            lessSumAmountDecimal = longSumAmountDecimal;
            moreSumMarginDecimal = shortSumMarginDecimal;
            lessSumMarginDecimal = longSumMarginDecimal;
        }
        BigDecimal morePartSumMarginDecimal = BigDecimal.ZERO;
        // 抵扣的margin计算
        for (final TmpOrder tmpOrder : moreOrderList) {
            if (lessSumAmountDecimal.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
            if (lessSumAmountDecimal.compareTo(tmpOrder.getLeftAmount()) >= 0) {
                morePartSumMarginDecimal = morePartSumMarginDecimal
                        .add(BigDecimalUtil.multiply(tmpOrder.getLeftAmount(), tmpOrder.getAvgMargin()));
                lessSumAmountDecimal = lessSumAmountDecimal.subtract(tmpOrder.getLeftAmount());
            } else {
                morePartSumMarginDecimal = morePartSumMarginDecimal
                        .add(BigDecimalUtil.multiply(lessSumAmountDecimal, tmpOrder.getAvgMargin()));
                lessSumAmountDecimal = BigDecimal.ZERO;
            }
        }
        final BigDecimal orderMargin = moreSumMarginDecimal.subtract(morePartSumMarginDecimal)
                .add(morePartSumMarginDecimal.max(lessSumMarginDecimal));
        return orderMargin;
    }

    /**
     * count OrderFee
     */
    private BigDecimal countOrderFee(final UserPosition userPosition, final BigDecimal maxFeeRate,
                                     final List<TmpOrder> tmpOrderList) {
        final LinkedList<TmpOrder> longOrderList = new LinkedList<>();
        final LinkedList<TmpOrder> shortOrderList = new LinkedList<>();
        BigDecimal longSumAmountDecimal = BigDecimal.ZERO;
        BigDecimal shortSumAmountDecimal = BigDecimal.ZERO;
        BigDecimal longSumSizeDecimal = BigDecimal.ZERO;
        BigDecimal shortSumSizeDecimal = BigDecimal.ZERO;
        BigDecimal positionAmount = userPosition.getAmount();
        for (final TmpOrder tmpOrder : tmpOrderList) {
            if (OrderSideEnum.LONG.getSide().equals(tmpOrder.getSide())) {
                if (PositionSideEnum.SHORT.getSide().equals(userPosition.getSide())
                        && positionAmount.compareTo(BigDecimal.ZERO) > 0) {
                    if (positionAmount.compareTo(tmpOrder.getLeftAmount()) >= 0) {
                        positionAmount = positionAmount.subtract(tmpOrder.getLeftAmount());
                    } else {
                        tmpOrder.setLeftAmount(tmpOrder.getLeftAmount().subtract(positionAmount));
                        positionAmount = BigDecimal.ZERO;
                        longOrderList.add(tmpOrder);
                        longSumAmountDecimal = longSumAmountDecimal.add(tmpOrder.getLeftAmount());
                        longSumSizeDecimal = longSumSizeDecimal
                                .add(BigDecimalUtil.divide(tmpOrder.getLeftAmount(), tmpOrder.getPrice()));
                    }
                    continue;
                }
                longOrderList.add(tmpOrder);
                longSumAmountDecimal = longSumAmountDecimal.add(tmpOrder.getLeftAmount());
                longSumSizeDecimal = longSumSizeDecimal
                        .add(BigDecimalUtil.divide(tmpOrder.getLeftAmount(), tmpOrder.getPrice()));
            } else {
                if (PositionSideEnum.LONG.getSide().equals(userPosition.getSide())
                        && positionAmount.compareTo(BigDecimal.ZERO) > 0) {
                    if (positionAmount.compareTo(tmpOrder.getLeftAmount()) >= 0) {
                        positionAmount = positionAmount.subtract(tmpOrder.getLeftAmount());
                    } else {
                        tmpOrder.setLeftAmount(tmpOrder.getLeftAmount().subtract(positionAmount));
                        positionAmount = BigDecimal.ZERO;
                        shortOrderList.add(tmpOrder);
                        shortSumAmountDecimal = shortSumAmountDecimal.add(tmpOrder.getLeftAmount());
                        shortSumSizeDecimal = shortSumSizeDecimal
                                .add(BigDecimalUtil.divide(tmpOrder.getLeftAmount(), tmpOrder.getPrice()));
                    }
                    continue;
                }
                shortOrderList.add(tmpOrder);
                shortSumAmountDecimal = shortSumAmountDecimal.add(tmpOrder.getLeftAmount());
                shortSumSizeDecimal = shortSumSizeDecimal
                        .add(BigDecimalUtil.divide(tmpOrder.getLeftAmount(), tmpOrder.getPrice()));
            }
        }
        final LinkedList<TmpOrder> moreOrderList;
        BigDecimal lessSumAmountDecimal;
        final BigDecimal moreSumSizeDecimal;
        final BigDecimal lessSumSizeDecimal;
        // 多空挂单的计算总费用
        if (longSumAmountDecimal.compareTo(shortSumAmountDecimal) >= 0) {
            moreOrderList = longOrderList;
            lessSumAmountDecimal = shortSumAmountDecimal;
            moreSumSizeDecimal = longSumSizeDecimal;
            lessSumSizeDecimal = shortSumSizeDecimal;
        } else {
            moreOrderList = shortOrderList;
            lessSumAmountDecimal = longSumAmountDecimal;
            moreSumSizeDecimal = shortSumSizeDecimal;
            lessSumSizeDecimal = longSumSizeDecimal;
        }
        BigDecimal morePartSumMarginDecimal = BigDecimal.ZERO;
        BigDecimal morePartSumSizeDecimal = BigDecimal.ZERO;
        // 抵扣的margin计算
        for (final TmpOrder tmpOrder : moreOrderList) {
            if (lessSumAmountDecimal.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
            if (lessSumAmountDecimal.compareTo(tmpOrder.getLeftAmount()) >= 0) {
                morePartSumMarginDecimal = morePartSumMarginDecimal
                        .add(BigDecimalUtil.multiply(tmpOrder.getLeftAmount(), tmpOrder.getAvgMargin()));
                morePartSumSizeDecimal = morePartSumSizeDecimal
                        .add(BigDecimalUtil.divide(tmpOrder.getLeftAmount(), tmpOrder.getPrice()));
                lessSumAmountDecimal = lessSumAmountDecimal.subtract(tmpOrder.getLeftAmount());
            } else {
                morePartSumMarginDecimal = morePartSumMarginDecimal
                        .add(BigDecimalUtil.multiply(lessSumAmountDecimal, tmpOrder.getAvgMargin()));
                morePartSumSizeDecimal = morePartSumSizeDecimal
                        .add(BigDecimalUtil.divide(lessSumAmountDecimal, tmpOrder.getPrice()));
                lessSumAmountDecimal = BigDecimal.ZERO;
            }
        }
        final BigDecimal orderFee = BigDecimalUtil.multiply(
                moreSumSizeDecimal.subtract(morePartSumSizeDecimal)
                        .add(morePartSumSizeDecimal.max(lessSumSizeDecimal)),
                maxFeeRate.max(BigDecimal.ZERO), new BigDecimal(2));
        return orderFee;
    }

    @Override
    public void cancelAll(final Integer brokerId, final Long userId, final String... contractCode) {
        if (contractCode == null || contractCode.length == 0) {
            return;
        }
        final OrderShardingServiceImpl shardingService =
                this.applicationContext.getBean(this.getClass());
        for (int i = 0; i < contractCode.length; i++) {
            shardingService.cancelAllByContractCode(brokerId, userId, contractCode[i], null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelAll(final List<Order> list, final String contractCode, final OrderSystemTypeEnum cancelType) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        this.cancelOrder(null, null, contractCode, list, cancelType, false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelAllByContractCode(final Integer brokerId, final Long userId,
                                        final String contractCode, final OrderSystemTypeEnum orderSystemTypeEnum) {
        if (StringUtils.isBlank(contractCode)) {
            return;
        }
        final Contract contract = this.contractService.getContract(contractCode);
        if (contract == null) {
            throw new BizException(BizErrorCodeEnum.NO_CONTRACT);
        }
        final UserBalance userBalance =
                this.userBalanceService.getForUpdate(userId, brokerId, contract.getBase());
        if (userBalance == null) {
            throw new BizException(BizErrorCodeEnum.USER_NOT_EXIST);
        }
        final List<Order> orderList = this.queryTradeOrderList(userId, brokerId, contractCode);
        this.cancelOrder(userId, brokerId, contractCode, orderSystemTypeEnum, orderList);
    }

    @Override
    public void cancelOverPriceOrder(final UserBalance userBalance, final String contractCode,
                                     final BigDecimal min, final BigDecimal max, final OrderSideEnum sideEnum) {

        final OrderShardingExample orderShardingExample = new OrderShardingExample();
        final OrderShardingExample.Criteria criteria = orderShardingExample.createCriteria();
        criteria.andUserIdEqualTo(userBalance.getUserId());
        criteria.andContractCodeEqualTo(contractCode);
        criteria.andSideEqualTo(sideEnum.getSide());
        criteria.andBrokerIdEqualTo(userBalance.getBrokerId());
        if (min != null) {
            criteria.andPriceLessThanOrEqualTo(min);
        }
        if (max != null) {
            criteria.andPriceGreaterThanOrEqualTo(max);
        }
        final List<Order> orderList = this.orderShardingRepository.selectByExample(orderShardingExample,
                this.getOrderShardTable(contractCode));
        this.cancelOrder(userBalance.getUserId(), userBalance.getBrokerId(), contractCode, OrderSystemTypeEnum.BROKER_PRICE_CANCEL, orderList);
    }

    @Override
    public List<Order> queryTradeOrderList(final Long userId, final Integer brokerId,
                                           final String contractCode) {
        final OrderShardingExample example = new OrderShardingExample();
        example.createCriteria().andUserIdEqualTo(userId).andBrokerIdEqualTo(brokerId);
        final List<Order> orderList = this.getByExample(example, this.getOrderShardTable(contractCode));
        final List<Order> result = orderList.stream()
                .filter(o -> o.getClazz().equals(OrderClazzEnum.TRADE.getClazz()) && !o.getStatus().equals(OrderStatusEnum.CANCELED.getCode()))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public List<Order> queryContractCodeOrderList(final String contractCode) {
        return this.orderShardingRepository.selectByContractCode(contractCode,
                this.getOrderShardTable(contractCode));
    }

    @Override
    public List<Order> queryOrder(final String contractCode, final Integer brokerId, final List<Long> userIds) {
        final OrderShardingExample example = new OrderShardingExample();
        example.createCriteria().andUserIdIn(userIds).andBrokerIdEqualTo(brokerId).andClazzEqualTo(OrderClazzEnum.TRADE.getClazz());

        return this.orderShardingRepository.selectByExample(example, this.getOrderShardTable(contractCode));
    }

    @Override
    public List<Order> queryOrderTypeList(final Long userId, final Integer brokerId,
                                          final String contractCode, final String type) {
        final List<Order> ordersList = this.queryTradeOrderList(userId, brokerId, contractCode);
        if (StringUtils.isNotEmpty(type)) {
            ordersList.stream().filter(order -> order.getSide().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }
        return ordersList;
    }

    @Override
    public JSONObject calculateMargin(final OrderDTO orderDTO) {
        final Long userId = orderDTO.getUserId();
        final Integer brokerId = orderDTO.getBrokerId();
        final Contract contract = this.contractService.getContract(orderDTO.getContractCode());
        final CurrencyPair currencyPair =
                this.currencyPairService.getByPairCode(contract.getPairCode());
        if (orderDTO.getPrice() != null) {
            orderDTO.setPrice(orderDTO.getPrice().setScale(currencyPair.getMinQuoteDigit(), BigDecimal.ROUND_DOWN));
        }
        /**
         * 限价单检查价格
         */
        if (orderDTO.getType() == OrderSystemTypeEnum.LIMIT.getSystemType()) {
            if (orderDTO.getPrice() == null || orderDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                log.error("calculateMargin orderDTO price illegal:price={}", orderDTO.getPrice());
                throw new BizException(BizErrorCodeEnum.ORDER_PRICE_TYPE);
            }
        }
        final UserBalance userBalance =
                this.userBalanceService.get(contract.getBase(), userId, brokerId);
        /**
         * 获取用户持仓数据
         */
        final UserPosition position =
                this.userPositionService.getUserPosition(userId, brokerId, orderDTO.getContractCode());

        /**
         * 获取合理价格
         */
        final MarkIndexReasonablePriceDTO markIndexReasonablePriceDTO =
                this.marketService.getReasonablePrice(contract, BigDecimalUtil
                        .divide(BigDecimal.ONE, position.getLever()).subtract(currencyPair.getMaintainRate()));

        /**
         * 计算下单盘口合理价格
         */
        if (orderDTO.getType() == OrderSystemTypeEnum.MARKET.getSystemType()) {
            // 平仓
            if ("close".equals(orderDTO.getSide())) {
                orderDTO.setPrice(position.getBrokerPrice());
            } else {// 开仓
                orderDTO.setPrice(markIndexReasonablePriceDTO.getMaxReasonablePrice());
            }
        }

        // 取卖一
        final LatestTicker latestTicker = this.latestTickerService.getTickerRedis(contract);
        BigDecimal firstSellPrice = null;
        if (latestTicker != null && latestTicker.getSell().compareTo(BigDecimal.ZERO) > 0) {
            firstSellPrice = latestTicker.getSell();
        }

        // 订单列表
        final List<Order> orderList = this.queryTradeOrderList(userBalance.getUserId(),
                userBalance.getBrokerId(), contract.getContractCode());

        final Order order = Order.builder().orderId(1L)
                .userId(userBalance.getUserId()).brokerId(userBalance.getBrokerId())
                .contractCode(contract.getContractCode()).clazz(OrderClazzEnum.TRADE.getClazz())
                .systemType(orderDTO.getType()).amount(orderDTO.getAmount()).price(orderDTO.getPrice())
                .avgPrice(BigDecimal.ZERO).dealAmount(BigDecimal.ZERO).dealSize(BigDecimal.ZERO)
                .size(orderDTO.getPrice() == null ? BigDecimal.ZERO : OrderUtil.getOrderCost(orderDTO.getAmount(), orderDTO.getPrice()))
                .status(OrderStatusEnum.NOT_DEAL.getCode()).detailSide(orderDTO.getSide()).orderFrom(0)
                .side(OrderSideEnum.LONG.getSide()).mustMaker(orderDTO.getBeMaker() == null ? 0 : orderDTO.getBeMaker()).createdDate(new Date()).build();

        orderList.add(order);

        /**
         * 开仓保证金
         */
        final OrderMarginFee longOrderMarginFee = this.getOpenMargin(orderList, position, order, contract,
                firstSellPrice, markIndexReasonablePriceDTO);
        final BigDecimal longOrderMargin =
                longOrderMarginFee.getOrderMargin().subtract(position.getOrderMargin()).max(BigDecimal.ZERO);
        final BigDecimal longOrderFee = longOrderMarginFee.getOrderFee().subtract(position.getOrderFee()).max(BigDecimal.ZERO);
        final JSONObject data = new JSONObject();
        data.put("longOrderMargin", longOrderMargin.add(longOrderFee).setScale(currencyPair.getMinTradeDigit(), BigDecimal.ROUND_UP).stripTrailingZeros().toPlainString());

        order.setSide(OrderSideEnum.SHORT.getSide());
        /**
         * 计算下单盘口合理价格
         */
        if (orderDTO.getType() == OrderSystemTypeEnum.MARKET.getSystemType()) {
            // 平仓
            if ("close".equals(orderDTO.getSide())) {
                order.setPrice(position.getBrokerPrice());
            } else {// 开仓
                order.setPrice(markIndexReasonablePriceDTO.getMinReasonablePrice());
            }
        }

        /**
         * 开仓保证金
         */
        final OrderMarginFee shortOrderMarginFee = this.getOpenMargin(orderList, position, order, contract,
                firstSellPrice, markIndexReasonablePriceDTO);
        final BigDecimal shortOrderMargin =
                shortOrderMarginFee.getOrderMargin().subtract(position.getOrderMargin()).max(BigDecimal.ZERO);
        final BigDecimal shortOrderFee = shortOrderMarginFee.getOrderFee().subtract(position.getOrderFee()).max(BigDecimal.ZERO);
        data.put("shortOrderMargin", shortOrderMargin.add(shortOrderFee).setScale(currencyPair.getMinTradeDigit(), BigDecimal.ROUND_UP).stripTrailingZeros().toPlainString());

        return data;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult dealOrder(final OrderDTO orderDTO, final OrderFromEnum orderFromEnum) {
        final Long userId = orderDTO.getUserId();
        final Integer brokerId = orderDTO.getBrokerId();
        final Contract contract = this.contractService.getContract(orderDTO.getContractCode());
        final CurrencyPair currencyPair =
                this.currencyPairService.getByPairCode(contract.getPairCode());
        if (orderDTO.getPrice() != null) {
            orderDTO.setPrice(orderDTO.getPrice().setScale(currencyPair.getMinQuoteDigit(), BigDecimal.ROUND_DOWN));
        }

        /**
         * 限价单检查价格
         */
        if (orderDTO.getType() == OrderSystemTypeEnum.LIMIT.getSystemType()) {
            if (orderDTO.getPrice() == null || orderDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                log.error("dealOrder orderDTO price illegal:price={}", orderDTO.getPrice());
                throw new BizException(BizErrorCodeEnum.ORDER_PRICE_TYPE);
            }
        }

        /**
         * 验证用户状态和合约状态
         */
        final ResponseResult responseResult = this.checkOrderCondition(userId, contract, currencyPair, orderDTO);
        if (responseResult != null) {
            return responseResult;
        }

        final UserBalance userBalance = this.userBalanceService.getForUpdate(userId, brokerId, contract.getBase());

        if (userBalance.getStatus() != UserBalanceStatusEnum.NORMAL.getCode()) {
            log.error("账户状态异常,userId={},brokerId={}", userId, brokerId);
            throw new BizException(BizErrorCodeEnum.ORDER_IN_LIQUIDATE_EXPLOSION);
        }

        final Contract contractWithNoCache = this.contractService.getContractWithNoCache(orderDTO.getContractCode());

        if (contractWithNoCache.getStatus() != cc.newex.dax.perpetual.common.enums.ContractStatusEnum.NORMAL.getCode()) {
            throw new BizException(BizErrorCodeEnum.CONTRACT_ARE_BEING_SETTLED);
        }

        /**
         * 获取用户持仓数据
         */
        final UserPosition position = this.userPositionService.getUserPosition(userId, brokerId, orderDTO.getContractCode());

        return this.dealOrder(userBalance, position, orderDTO, orderFromEnum, contract, currencyPair);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult dealOrder(final UserBalance userBalance, final UserPosition position,
                                    final OrderDTO orderDTO, final OrderFromEnum orderFromEnum, final Contract contract,
                                    final CurrencyPair currencyPair) {
        final OrderSideEnum orderSideEnum = OrderDetailSideEnum.getOrderSideEnum(orderDTO.getSide());

        /**
         * 获取合理价格
         */
        final MarkIndexReasonablePriceDTO markIndexReasonablePriceDTO =
                this.marketService.getReasonablePrice(contract, BigDecimalUtil
                        .divide(BigDecimal.ONE, position.getLever()).subtract(currencyPair.getMaintainRate()));

        /**
         * 计算下单盘口合理价格
         */
        if (orderDTO.getType() == OrderSystemTypeEnum.MARKET.getSystemType()) {
            // 平仓, 可能平仓后又去平仓
            if (OrderDetailSideEnum.isClose(orderDTO.getSide()) && position.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                orderDTO.setPrice(position.getBrokerPrice());
            } else {// 开仓
                BigDecimal price = BigDecimal.ZERO;

                if (orderSideEnum == OrderSideEnum.LONG) {
                    price = BigDecimalUtil.multiply(markIndexReasonablePriceDTO.getMaxReasonablePrice(), new BigDecimal("1.02"));
                } else {
                    price = BigDecimalUtil.multiply(markIndexReasonablePriceDTO.getMinReasonablePrice(), new BigDecimal("0.98"));
                }

                orderDTO.setPrice(price);

                log.info("open market order: {}", JSON.toJSONString(orderDTO));
            }
        }

        // 取卖一
        final LatestTicker latestTicker = this.latestTickerService.getTickerRedis(contract);
        BigDecimal firstSellPrice = null;
        if (latestTicker != null && latestTicker.getSell().compareTo(BigDecimal.ZERO) > 0) {
            firstSellPrice = latestTicker.getSell();
        }

        /**
         * 计算是否超过风险限额，强平单和爆仓单不检查
         */
        if (orderDTO.getType() == OrderSystemTypeEnum.LIMIT.getSystemType()
                || orderDTO.getType() == OrderSystemTypeEnum.MARKET.getSystemType()) {
            BigDecimal longSize = position.getOrderLongSize();
            BigDecimal shortSize = position.getOrderShortSize();
            if (position.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                if (position.getSide().equals(PositionSideEnum.LONG.getSide())) {
                    longSize = longSize.add(position.getSize());
                } else {
                    shortSize = shortSize.add(position.getSize());
                }
            }
            // 买按卖一或者自己算
            if (orderSideEnum == OrderSideEnum.LONG) {
                longSize = longSize.add((BigDecimalUtil.divide(orderDTO.getAmount(), orderDTO.getPrice()
                        .min(firstSellPrice == null ? orderDTO.getPrice() : firstSellPrice))));
            } else {
                shortSize = shortSize.add(BigDecimalUtil.divide(orderDTO.getAmount(), orderDTO.getPrice()));
            }
            // 大于默认风险限额提示用户调整
            if (!OrderDetailSideEnum.isClose(orderDTO.getSide()) && longSize.max(shortSize).compareTo(position.getGear()) > 0) {
                log.error(
                        "user position gear not enough, userId={}, brokerId={}, maxSize : {}, gear: {}",
                        userBalance.getUserId(), userBalance.getBrokerId(), longSize.max(shortSize),
                        position.getGear());
                throw new BizException(BizErrorCodeEnum.ORDER_SIZE_ILLEGAL);

            }
        }

        /**
         * 判断下单价格是否超过破产价
         */
        if (position.getAmount().compareTo(BigDecimal.ZERO) > 0
                && !position.getSide().equals(orderSideEnum.getSide())) {
            if (PositionSideEnum.LONG.getSide().equals(position.getSide())
                    && orderDTO.getPrice().compareTo(position.getBrokerPrice()) < 0) {
                log.error("价格不能低于破产价格, order: {}, broke price: {}", JSON.toJSONString(orderDTO), position.getBrokerPrice());

                throw new BizException(BizErrorCodeEnum.ORDER_PRICE_SMALL_ILLEGAL);
            } else if (PositionSideEnum.SHORT.getSide().equals(position.getSide())
                    && orderDTO.getPrice().compareTo(position.getBrokerPrice()) > 0) {
                log.error("价格不能高于破产价格, order: {}, broke price: {}", JSON.toJSONString(orderDTO), position.getBrokerPrice());

                throw new BizException(BizErrorCodeEnum.ORDER_PRICE_GREAT_ILLEGAL);
            }
        }

        final Order order = Order.builder().orderId(this.idGeneratorService.generateOrderId())
                .userId(userBalance.getUserId()).brokerId(userBalance.getBrokerId())
                .contractCode(contract.getContractCode()).clazz(OrderClazzEnum.TRADE.getClazz())
                .systemType(orderDTO.getType()).amount(orderDTO.getAmount()).price(orderDTO.getPrice())
                .avgPrice(BigDecimal.ZERO).dealAmount(BigDecimal.ZERO).dealSize(BigDecimal.ZERO)
                .size(orderDTO.getPrice() == null ? BigDecimal.ZERO : OrderUtil.getOrderCost(orderDTO.getAmount(), orderDTO.getPrice()))
                .status(OrderStatusEnum.NOT_DEAL.getCode()).detailSide(orderDTO.getSide()).orderFrom(orderFromEnum.getCode())
                .profit(BigDecimal.ZERO).fee(BigDecimal.ZERO).reason(0)
                .side(orderSideEnum.getSide()).mustMaker(orderDTO.getBeMaker() == null ? 0 : orderDTO.getBeMaker())
                .createdDate(new Date()).build();

        // 订单列表
        final List<Order> originOrderList = this.queryTradeOrderList(userBalance.getUserId(),
                userBalance.getBrokerId(), contract.getContractCode());

        if (userBalance.getStatus() == UserBalanceStatusEnum.NORMAL.getCode()
                && !OrderDetailSideEnum.isClose(order.getDetailSide())
                && originOrderList.size() - currencyPair.getMaxOrders() >= 0) {
            /**
             * 机器人下单不限制下单数
             */
            if (!userBalance.getUserId().equals(this.perpetualConfig.getMarketId())) {
                log.info("不能超过单人最大挂单数, userId: {}, max orders: {}", orderDTO.getUserId(), currencyPair.getMaxOrders());

                throw new BizException(BizErrorCodeEnum.ORDER_AMOUNT_MORE_MAX_ERROR);
            }
        }

        final List<Order> orderList = new ArrayList<>(originOrderList);

        /**
         * 平仓检查仓位
         */
        if (OrderDetailSideEnum.isClose(order.getDetailSide())) {
            if (position.getAmount().compareTo(BigDecimal.ZERO) == 0
                    || position.getSide().equals(order.getSide())) {
                log.info("仓位已经平完或者平仓方向不合法 userId={},", orderDTO.getUserId());
                throw new BizException(BizErrorCodeEnum.ORDER_STRATEGY_AMOUNT);

            }
            position.setClosingAmount(position.getClosingAmount().add(order.getAmount()));
            if (position.getClosingAmount().compareTo(position.getAmount()) > 0) {
                log.info("平仓数量大于仓位数量 userId={},", orderDTO.getUserId());
                throw new BizException(BizErrorCodeEnum.ORDER_STRATEGY_AMOUNT);
            }
        }

        orderList.add(order);

        /**
         * 开仓保证金
         */
        final OrderMarginFee orderMarginFee = this.getOpenMargin(orderList, position, order, contract,
                firstSellPrice, markIndexReasonablePriceDTO);
        final BigDecimal diffOrderMargin =
                orderMarginFee.getOrderMargin().subtract(position.getOrderMargin());

        final Map<String, MarkIndexPriceDTO> markIndexPriceMap = this.marketService.allMarkIndexPrice();

        final List<UserPosition> userPositionList =
                this.userPositionService.getUserPositionWithoutIdByBase(position.getId(),
                        userBalance.getUserId(), userBalance.getBrokerId(), contract.getBase());
        userPositionList.add(position);

        /**
         * 计算可用的保险金
         */
        BigDecimal totalBalance = FormulaUtil.countAvailableBalance(userBalance, markIndexPriceMap, userPositionList);

        /**
         * 增加或扣减订单保证金
         */
        if (diffOrderMargin.compareTo(BigDecimal.ZERO) > 0) {
            if (totalBalance.compareTo(diffOrderMargin) < 0) {
                log.error(
                        "user balance not enough, userId={}, brokerId={}, totalBalance : {}, inc : {}",
                        userBalance.getUserId(), userBalance.getBrokerId(), totalBalance, diffOrderMargin);
                throw new BizException(BizErrorCodeEnum.AVAILABLE_BALANCE_NOT_ENOUGH);
            }
            totalBalance = totalBalance.subtract(diffOrderMargin);
            position.setOrderMargin(orderMarginFee.getOrderMargin());
            userBalance.setAvailableBalance(userBalance.getAvailableBalance().subtract(diffOrderMargin));
            userBalance.setOrderMargin(userBalance.getOrderMargin().add(diffOrderMargin));
        }
        /**
         * 计算总的订单保证金和总手续差额,多退少补原则
         */
        final BigDecimal diffOrderFee = orderMarginFee.getOrderFee().subtract(position.getOrderFee());
        if (diffOrderFee.compareTo(BigDecimal.ZERO) > 0) {
            if (totalBalance.compareTo(diffOrderFee) < 0) {
                log.error(
                        "user balance not enough, userId={}, brokerId={}, totalBalance : {}, inc : {}",
                        userBalance.getUserId(), userBalance.getBrokerId(), totalBalance, diffOrderFee);
                throw new BizException(BizErrorCodeEnum.AVAILABLE_BALANCE_NOT_ENOUGH);
            }
            // totalBalance = totalBalance.subtract(diffOrderFee);
            position.setOrderFee(orderMarginFee.getOrderFee());
            userBalance.setOrderFee(userBalance.getOrderFee().add(diffOrderFee));
            userBalance.setAvailableBalance(userBalance.getAvailableBalance().subtract(diffOrderFee));
        }

        /**
         * 机器人下单不限制下单数
         */
        if (!userBalance.getUserId().equals(this.perpetualConfig.getRobot())) {
            /**
             * 挂多
             */
            if ((OrderSideEnum.LONG.getSide().equals(order.getSide()))) {
                position.setOrderLongAmount(position.getOrderLongAmount().add(order.getAmount()));
                position.setOrderLongSize(position.getOrderLongSize().add(order.getSize()));
            } else {// 挂空
                position.setOrderShortAmount(position.getOrderShortAmount().add(order.getAmount()));
                position.setOrderShortSize(position.getOrderShortSize().add(order.getSize()));
            }
        }
        final List<Contract> contractList = this.contractService.getUnExpiredContract();
        // 重新计算强平价格
        FormulaUtil.calculationBrokerForcedLiquidationPrice(contractList, markIndexPriceMap, position,
                userPositionList, userBalance);

        if (originOrderList.size() > 0) {
            for (final Order originOrder : originOrderList) {
                originOrder.setMatchStatus(null);
            }
            this.orderShardingRepository.batchUpdate(originOrderList,
                    ShardingUtil.buildContractOrderShardTable(contract));
        }

        this.orderShardingRepository.insert(order, ShardingUtil.buildContractOrderShardTable(contract));

        this.orderAllRepository.insert(ObjectCopyUtil.map(order, OrderAll.class));

        this.userPositionService.batchEdit(userPositionList);

        this.userBalanceService.editById(userBalance);

        final String channel = (PerpetualConstants.PERPETUAL + "_" + contract.getPairCode()).toUpperCase();

        // 推送订单消息
        this.cacheService.convertAndSend(channel,
                JSON.toJSONString(PushData.builder().biz(PerpetualConstants.PERPETUAL).type(PushTypeEnum.ORDER.name())
                        .contractCode(contract.getContractCode())
                        .zip(false).data(PushDataUtil.dealOrders(orderList, contract)).build()));
        // push balance
        final List<UserBalance> pushBalanceList = Arrays.asList(userBalance);
        this.cacheService.convertAndSend(
                channel,
                JSON.toJSONString(
                        PushData.builder().biz(PerpetualConstants.PERPETUAL).type(PushTypeEnum.ASSET.name())
                                .zip(false).data(PushDataUtil.dealUserBalance(pushBalanceList, contract)).build()));
        // push position
        this.cacheService.convertAndSend(
                channel,
                JSON.toJSONString(PushData.builder().biz(PerpetualConstants.PERPETUAL)
                        .type(PushTypeEnum.POSITION.name()).contractCode(contract.getContractCode()).zip(false)
                        .data(PushDataUtil.dealUserPositions(userPositionList, contract)).build()));

        final Map resultMap = Maps.newHashMap();
        resultMap.put("id", order.getId());
        resultMap.put("orderId", order.getOrderId());

        return ResultUtils.success(resultMap);
    }

    @Override
    public OrderMarginFee getOpenMargin(final List<Order> orderList, final UserPosition userPosition,
                                        final Order order, final Contract contract, final BigDecimal firstSellPrice,
                                        final MarkIndexReasonablePriceDTO markIndexReasonablePriceDTO) {
        BigDecimal longTotalSize = userPosition.getOrderLongSize().add(
                OrderSideEnum.LONG.getSide().equals(order.getSide()) ? order.getSize() : BigDecimal.ZERO);
        if (userPosition.getSide().equals(OrderSideEnum.LONG.getSide())) {
            longTotalSize = longTotalSize.add(userPosition.getSize());
        }

        BigDecimal shortTotalSize = userPosition.getOrderShortSize().add(
                OrderSideEnum.SHORT.getSide().equals(order.getSide()) ? order.getSize() : BigDecimal.ZERO);
        if (userPosition.getSide().equals(OrderSideEnum.SHORT.getSide())) {
            shortTotalSize = shortTotalSize.add(userPosition.getSize());
        }
        /**
         * 开仓保证金率
         */
        final CurrencyPairService.GearRate longGearRate = this.currencyPairService.getOpenMarginRate(
                contract.getPairCode(), longTotalSize.max(userPosition.getGear()), userPosition.getLever());
        final CurrencyPairService.GearRate shortGearRate =
                this.currencyPairService.getOpenMarginRate(contract.getPairCode(),
                        shortTotalSize.max(userPosition.getGear()), userPosition.getLever());
        /**
         * 计算每笔订单的开仓保证金和价格偏移保证金
         */
        this.getOrderList(contract, orderList, longGearRate.getEntryRate(),
                shortGearRate.getEntryRate(), firstSellPrice, markIndexReasonablePriceDTO);

        /**
         * 获取手续费
         */
        final BigDecimal makerFee = this.feesService.getFeeRate(order.getUserId(),
                contract.getPairCode(), MakerEnum.MAKER, order.getBrokerId());
        final BigDecimal takerFee = this.feesService.getFeeRate(order.getUserId(),
                contract.getPairCode(), MakerEnum.TAKER, order.getBrokerId());
        final BigDecimal maxFeeRate = makerFee.max(takerFee);

        final OrderMarginFee orderMarginFee =
                this.countOrderMarginAndFee(userPosition, firstSellPrice, maxFeeRate, orderList);
        return orderMarginFee;
    }

    /**
     * 验证用户状态和合约状态
     *
     * @param userId
     * @param orderDTO
     * @return
     */
    private ResponseResult checkOrderCondition(final Long userId,
                                               final Contract contract, final CurrencyPair currencyPair, final OrderDTO orderDTO) {
        /**
         * 验证kyc2
         */
        if (!contract.getEnv().equals(1)) {
            final Integer kyclevel = this.usersClient.getUserKycLevel(userId).getData();
            if (kyclevel == null || !kyclevel.equals(2)) {
                log.error("checkOrderCondition user kyc2  not pass,userId={}", userId);
                throw new BizException(BizErrorCodeEnum.ORDER_KYC_LEVEL2_ERROR);
            }
        }
        /**
         * 用户不能下爆仓单
         */
        if (orderDTO.getType().equals(OrderSystemTypeEnum.FORCED_LIQUIDATION.getSystemType())
                || orderDTO.getType().equals(OrderSystemTypeEnum.EXPLOSION.getSystemType())) {
            log.error("用户不能下爆仓单,userId={}", userId);
            throw new BizException(BizErrorCodeEnum.ORDER_STRATEGY_ILLEGAL);
        }

        /**
         *  合约状态是否可以下单
         */
        if (contract.getExpired().intValue() != ContractStatusEnum.IN_USING.getCode()) {
            log.error("checkOrderCondition palce contract expired is error ,status ={},pairCode={},userId={}",
                    contract.getExpired(), contract.getPairCode(), userId);
            throw new BizException(BizErrorCodeEnum.CONTRACT_STATUS_ERROR);

        }
        /**
         * 下单失败，数量不能为空
         */
        if (orderDTO.getAmount() == null || orderDTO.getAmount().compareTo(currencyPair.getMinOrderAmount()) < 0) {
            log.error("checkOrderCondition contrant pairCode={},userId={},amount={}",
                    contract.getPairCode(), userId, orderDTO.getAmount());
            throw new BizException(BizErrorCodeEnum.ORDER_STRATEGY_MIN_AMOUNT, currencyPair.getMinOrderAmount());

        }
        /**
         * 下单数量不能超过单笔最大张数
         */
        if (orderDTO.getAmount().compareTo(currencyPair.getMaxOrderAmount()) > 0) {
            log.error("contrant pairCode={},userId={},amount={}",
                    contract.getPairCode(), userId, orderDTO.getAmount());
            throw new BizException(BizErrorCodeEnum.ORDER_EXCEED_AMOUNT);
        }
        /**
         *  张数是否为整数
         */
        if (!BigDecimalUtil.isInteger(orderDTO.getAmount())) {
            log.error("contract amount is not integer,userId={}", userId);
            throw new BizException(BizErrorCodeEnum.IS_NOT_INTEGER);

        }
        /**
         * 条件单检查是不是限价单和市价单
         */
        if (!orderDTO.getType().equals(OrderSystemTypeEnum.LIMIT.getSystemType()) && !orderDTO.getType().equals(OrderSystemTypeEnum.MARKET.getSystemType())) {
            log.error("checkOrderCondition 条件单检查是不是限价单和市价单 pairCode={},userId={},amount={}", contract.getPairCode(), userId, orderDTO.getAmount());
            throw new BizException(BizErrorCodeEnum.ORDER_STRATEGY_ILLEGAL);
        }
        if (orderDTO.getType() == OrderSystemTypeEnum.LIMIT.getSystemType()) {
            if (orderDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                log.error("checkOrderCondition 下单失败，数量不能为空 pairCode={},userId={},amount={}",
                        contract.getPairCode(), userId, orderDTO.getAmount());
                throw new BizException(BizErrorCodeEnum.ORDER_STRATEGY_MIN_AMOUNT, currencyPair.getMinOrderAmount());
            }
            if (orderDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                log.error("checkOrderCondition 下单失败，价格不能为空 pairCode={},userId={},amount={}",
                        contract.getPairCode(), userId, orderDTO.getAmount());
                throw new BizException(BizErrorCodeEnum.ORDER_PRICE_TYPE);
            }
            if (orderDTO.getPrice().compareTo(PerpetualConstants.MAX_PRICE) > 0) {
                log.error("checkOrderCondition 下单失败，价格超过最大限价 pairCode={},userId={},amount={}",
                        contract.getPairCode(), userId, orderDTO.getAmount());
                throw new BizException(BizErrorCodeEnum.ORDER_PRICE_TYPE);
            }
        } else {
            if (orderDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                log.error("checkOrderCondition 市价下单失败，数量不能为空 pairCode={},userId={},amount={}",
                        contract.getPairCode(), userId, orderDTO.getAmount());
                throw new BizException(BizErrorCodeEnum.ORDER_STRATEGY_MIN_AMOUNT, currencyPair.getMinOrderAmount());
            }
            orderDTO.setPrice(null);
        }
        return null;
    }

    @Override
    public List<OrderAll> getUserOrders(final Long userId, final Integer brokerId,
                                        final List<String> contractCode, final List<Integer> status, final String side) {
        final OrderAllExample orderAllExample = new OrderAllExample();
        final OrderAllExample.Criteria criteria = orderAllExample.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (brokerId != null) {
            criteria.andBrokerIdEqualTo(brokerId);
        }
        if (CollectionUtils.isNotEmpty(contractCode)) {
            criteria.andContractCodeIn(contractCode);
        }
        if (CollectionUtils.isNotEmpty(status)) {
            criteria.andStatusIn(status);
        }
        if (side != null) {
            criteria.andSideEqualTo(side);
        }
        criteria.andClazzEqualTo(OrderClazzEnum.TRADE.getClazz());
        orderAllExample.setOrderByClause(" id ASC ");
        return this.orderAllRepository.selectByExample(orderAllExample);
    }

    @Override
    public List<Order> fetchOrders(final Integer operateStatus, final List<Integer> status,
                                   final String pairCode) {
        final OrderShardingExample example = new OrderShardingExample();
        example.createCriteria().andStatusIn(status);
        example.setOrderByClause(" id ASC ");
        return this.orderShardingRepository.selectByExample(example, this.getOrderShardTable(pairCode));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult cancelOrder(final Long userId, final Integer brokerId, final Long orderId,
                                      final String contractCode) {
        final OrderShardingExample example = new OrderShardingExample();
        example.createCriteria().andUserIdEqualTo(userId).andBrokerIdEqualTo(brokerId)
                .andContractCodeEqualTo(contractCode).andOrderIdEqualTo(orderId)
                .andClazzEqualTo(OrderClazzEnum.TRADE.getClazz());
        final Optional<Order> orderOptional =
                this.getByExample(example, this.getOrderShardTable(contractCode)).stream().findFirst();

        if (!orderOptional.isPresent()) {
            throw new BizException(BizErrorCodeEnum.ORDER_NOT_EXIST);
        }
        final Order order = orderOptional.get();
        if (order.getStatus() == OrderStatusEnum.CANCELING.getCode()) {
            throw new BizException(BizErrorCodeEnum.ORDER_ALREAY_CANCELLED);
        }
        final Order oldOrder = new Order();
        BeanUtils.copyProperties(order, oldOrder);
        /**
         * 用户不能撤销爆仓单
         */
        if (order.getSystemType().equals(OrderSystemTypeEnum.FORCED_LIQUIDATION.getSystemType())
                || order.getSystemType().equals(OrderSystemTypeEnum.EXPLOSION.getSystemType())) {
            log.error("用户不能下爆仓单,userId={}", userId);
            throw new BizException(BizErrorCodeEnum.ORDER_STRATEGY_ILLEGAL);
        }

        if (order.getSystemType().equals(OrderSystemTypeEnum.MARKET.getSystemType())) {
            throw new BizException(BizErrorCodeEnum.NO_LIMIT_PRICE_TYPE);
        }
        // 判断用户与订单是否匹配
        if (!order.getUserId().equals(userId)) {
            throw new BizException(BizErrorCodeEnum.CANCEL_ORDER_USER_NOT_AGREE);
        }
        order.setMatchStatus(0);
        order.setStatus(OrderStatusEnum.NOT_DEAL.getCode());
        order.setClazz(OrderClazzEnum.CANCEL.getClazz());
        order.setId(null);
        order.setOrderId(this.idGeneratorService.generateOrderId());
        order.setRelationOrderId(oldOrder.getOrderId());
        this.orderShardingRepository.insert(order, this.getOrderShardTable(contractCode));

        oldOrder.setStatus(OrderStatusEnum.CANCELING.getCode());
        oldOrder.setMatchStatus(null);
        this.orderShardingRepository.updateById(oldOrder, this.getOrderShardTable(contractCode));

        final OrderAllExample orderAllExample = new OrderAllExample();
        orderAllExample.createCriteria().andUserIdEqualTo(userId).andBrokerIdEqualTo(brokerId)
                .andContractCodeEqualTo(contractCode).andOrderIdEqualTo(orderId)
                .andClazzEqualTo(OrderClazzEnum.TRADE.getClazz());
        final OrderAll orderAll = this.orderAllRepository.selectOneByExample(orderAllExample);
        this.orderAllRepository.updateById(OrderAll.builder().id(orderAll.getId()).status(OrderStatusEnum.CANCELING.getCode()).build());

        return ResultUtils.success();
    }

    /**
     * 计划委托下单流程
     *
     * @param orderDTO 下单dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult dealConditionOrder(final OrderDTO orderDTO,
                                             final OrderFromEnum orderFromEnum) {
        final Long userId = orderDTO.getUserId();
        final Integer brokerId = orderDTO.getBrokerId();
        final Contract contract = this.contractService.getContract(orderDTO.getContractCode());
        final CurrencyPair currencyPair =
                this.currencyPairService.getByPairCode(contract.getPairCode());
        if (orderDTO.getTriggerPrice() != null) {
            orderDTO.setTriggerPrice(orderDTO.getTriggerPrice().setScale(currencyPair.getMinQuoteDigit(), BigDecimal.ROUND_DOWN));
        }
        if (orderDTO.getTriggerPrice() == null
                || orderDTO.getTriggerPrice().compareTo(BigDecimal.ZERO) <= 0
                || orderDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            log.error("orderDTO triggerPrice={} ,price={}",
                    orderDTO.getTriggerPrice(), orderDTO.getPrice());
            throw new BizException(BizErrorCodeEnum.ORDER_PRICE_TYPE);
        }
        /**
         * 验证用户状态和合约状态
         */
        final ResponseResult responseResult = this.checkOrderCondition(userId, contract, currencyPair, orderDTO);
        if (responseResult != null) {
            return responseResult;
        }
        if (orderDTO.getTriggerPrice() == null
                || orderDTO.getTriggerPrice().compareTo(BigDecimal.ZERO) <= 0
                || orderDTO.getTriggerPrice().compareTo(PerpetualConstants.MAX_PRICE) > 0
                || (!ConditionOrderTypeEnum.INDEX.getType().equals(orderDTO.getTriggerBy())
                && !ConditionOrderTypeEnum.MARK.getType().equals(orderDTO.getTriggerBy())
                && !ConditionOrderTypeEnum.LAST.getType().equals(orderDTO.getTriggerBy()))) {
            log.error("params error,userId={}", userId);
            throw new BizException(BizErrorCodeEnum.ILLEGAL_PARAM);
        }
        BigDecimal directionPrice = null;
        if (ConditionOrderTypeEnum.INDEX.getType().equals(orderDTO.getTriggerBy())) {
            final MarkIndexPriceDTO markIndexPriceDTO = this.marketService.getMarkIndex(contract);
            directionPrice = markIndexPriceDTO.getIndexPrice();
        } else if (ConditionOrderTypeEnum.MARK.getType().equals(orderDTO.getTriggerBy())) {
            final MarkIndexPriceDTO markIndexPriceDTO = this.marketService.getMarkIndex(contract);
            directionPrice = markIndexPriceDTO.getMarkPrice();
        } else {
            final List<Deal> dealList = this.marketService.fills(contract);
            if (dealList != null && dealList.size() > 0) {
                directionPrice = dealList.get(0).getPrice();
            }
        }
        if (directionPrice == null) {
            log.error("directionPrice null, triggerPrice: {}",
                    directionPrice, orderDTO.getTriggerPrice());
            throw new BizException(BizErrorCodeEnum.ORDER_CONDITION_PRICE_ERROR);
        }
        if (directionPrice.compareTo(orderDTO.getTriggerPrice()) == 0) {
            log.error("directionPrice error,directionPrice={} triggerPrice: {}",
                    directionPrice, orderDTO.getTriggerPrice());
            throw new BizException(BizErrorCodeEnum.ORDER_CONDITION_PRICE_TYPE);
        }
        final OrderSideEnum orderSideEnum = OrderDetailSideEnum.getOrderSideEnum(orderDTO.getSide());
        final UserPosition position = this.userPositionService.getUserPosition(userId, brokerId, contract.getContractCode());
        if (position != null) {
            // 判断条件单的价格不可以高于或低于破产家
            if (position.getAmount().compareTo(BigDecimal.ZERO) > 0
                    && !position.getSide().equals(orderSideEnum.getSide())) {
                if (PositionSideEnum.LONG.getSide().equals(position.getSide())
                        && orderDTO.getPrice().compareTo(position.getBrokerPrice()) < 0) {
                    log.error("价格不能低于破产价格");
                    throw new BizException(BizErrorCodeEnum.ORDER_PRICE_SMALL_ILLEGAL);
                } else if (PositionSideEnum.SHORT.getSide().equals(position.getSide())
                        && orderDTO.getPrice().compareTo(position.getBrokerPrice()) > 0) {
                    log.error("价格不能高于破产价格");
                    throw new BizException(BizErrorCodeEnum.ORDER_PRICE_GREAT_ILLEGAL);
                }
            }
        }

        final int amount = this.orderConditionService.list(userId, brokerId, contract.getContractCode()).size();
        if (amount - currencyPair.getMaxOrders() >= 0) {
            /**
             * 机器人下单不限制下单数
             */
            if (!userId.equals(this.perpetualConfig.getRobot())) {
                log.info("不能超过单人最大挂单数 userId={},", orderDTO.getUserId());
                throw new BizException(BizErrorCodeEnum.ORDER_AMOUNT_MORE_MAX_ERROR);
            }
        }

        final OrderCondition orderCondition = OrderCondition.builder().type(orderDTO.getTriggerBy())
                .status(OrderConditionEnum.WAINING_FOR_TRADE.getCode())
                .direction(directionPrice.compareTo(orderDTO.getTriggerPrice()) > 0 ?
                        OrderDirectionEnum.LESS.getDirection() : OrderDirectionEnum.GREATER.getDirection())
                .conditionPrice(orderDTO.getTriggerPrice())
                .orderId(this.idGeneratorService.generateOrderId()).userId(orderDTO.getUserId())
                .brokerId(orderDTO.getBrokerId()).contractCode(orderDTO.getContractCode())
                .clazz(OrderClazzEnum.TRADE.getClazz()).systemType(orderDTO.getType())
                .amount(orderDTO.getAmount()).price(orderDTO.getPrice()).dealAmount(BigDecimal.ZERO)
                .dealSize(BigDecimal.ZERO)
                .size(BigDecimal.ZERO)
                .openMargin(BigDecimal.ZERO)
                .extraMargin(BigDecimal.ZERO)
                .avgMargin(BigDecimal.ZERO)
                .detailSide(orderDTO.getSide()).orderFrom(orderFromEnum.getCode())
                .side(orderSideEnum.getSide()).mustMaker(orderDTO.getBeMaker())
                .createdDate(new Date()).build();
        this.orderConditionService.add(orderCondition);

        final List<OrderCondition> orderConditionList = Arrays.asList(orderCondition);
        // 推送订单消息
        this.cacheService.convertAndSend((PerpetualConstants.PERPETUAL + "_" + contract.getPairCode()).toUpperCase(),
                JSON.toJSONString(PushData.builder().biz(PerpetualConstants.PERPETUAL).type(PushTypeEnum.CONDITION_ORDER.name())
                        .contractCode(contract.getContractCode())
                        .zip(false).data(PushDataUtil.dealConditionOrders(orderConditionList, contract)).build()));

        final Map resultMap = Maps.newHashMap();
        resultMap.put("id", orderCondition.getId());
        resultMap.put("orderId", orderCondition.getOrderId());
        return ResultUtils.success(resultMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Order> batchPlaceOrder(final List<UserPosition> userPositions,
                                       final OrderFromEnum orderFromEnum,
                                       final List<OrderDTO> orderDTOS,
                                       final Contract contract) {

        if (CollectionUtils.isEmpty(orderDTOS)) {
            return new ArrayList<>();
        }

        final Map<Integer, Map<Long, UserPosition>> positionMap = userPositions.stream()
                .collect(Collectors.groupingBy(UserPosition::getBrokerId, Collectors.toMap(UserPosition::getUserId, Function.identity(), (x, y) -> x)));

        final List<Order> orders = new ArrayList<>(orderDTOS.size());
        final List<OrderAll> orderAlls = new ArrayList<>(orderDTOS.size());
        for (final OrderDTO orderDTO : orderDTOS) {
            final OrderSideEnum orderSideEnum = OrderDetailSideEnum.getOrderSideEnum(orderDTO.getSide());
            final Order order = Order.builder().orderId(this.idGeneratorService.generateOrderId()).relationOrderId(0L)
                    .userId(orderDTO.getUserId()).brokerId(orderDTO.getBrokerId()).openMargin(BigDecimal.ZERO)
                    .contractCode(contract.getContractCode()).clazz(OrderClazzEnum.TRADE.getClazz()).extraMargin(BigDecimal.ZERO)
                    .systemType(orderDTO.getType()).amount(orderDTO.getAmount()).price(orderDTO.getPrice()).avgMargin(BigDecimal.ZERO)
                    .avgPrice(BigDecimal.ZERO).dealAmount(BigDecimal.ZERO).dealSize(BigDecimal.ZERO).showAmount(BigDecimal.ZERO).matchStatus(0)
                    .size(orderDTO.getPrice() == null ? BigDecimal.ZERO : OrderUtil.getOrderCost(orderDTO.getAmount(), orderDTO.getPrice()))
                    .status(OrderStatusEnum.NOT_DEAL.getCode()).detailSide(orderDTO.getSide()).orderFrom(orderFromEnum.getCode())
                    .profit(BigDecimal.ZERO).fee(BigDecimal.ZERO).reason(0).brokerSize(Optional.ofNullable(orderDTO.getBrokerSize()).orElse(BigDecimal.ZERO))
                    .side(orderSideEnum.getSide()).mustMaker(orderDTO.getBeMaker() == null ? 0 : orderDTO.getBeMaker()).createdDate(new Date()).build();
            orders.add(order);
            orderAlls.add(ObjectCopyUtil.map(order, OrderAll.class));
            final UserPosition position = positionMap.get(order.getBrokerId()).get(order.getUserId());
            if (OrderDetailSideEnum.isClose(order.getDetailSide())) {
                position.setClosingAmount(position.getClosingAmount().add(order.getAmount()));
            }
            if ((OrderSideEnum.LONG.getSide().equals(order.getSide()))) {
                position.setOrderLongSize(position.getOrderLongSize().add(order.getSize()));
                position.setOrderLongAmount(position.getOrderLongAmount().add(order.getAmount()));
            } else {// 挂空
                position.setOrderShortAmount(position.getOrderShortAmount().add(order.getAmount()));
                position.setOrderShortSize(position.getOrderShortSize().add(order.getSize()));
            }
        }
        if (CollectionUtils.isNotEmpty(orders)) {
            this.batchAdd(orders, this.getOrderShardTable(contract.getContractCode()));
            this.orderAllRepository.batchInsert(orderAlls);
            // 推送订单消息
            /*pushService.pushData(contract, PushTypeEnum.ORDER, PushDataUtil.dealOrders(orders, contract).toJSONString(),
                    true, false, false);*/
        }
        return orders;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(final List<Order> orderList, final ShardTable shardTable) {
        this.orderShardingRepository.batchDelete(orderList, shardTable);
    }

    /*
     * For Unittest
     */
    @Override
    public void setUsersClient(final UsersClient usersClient) {
        this.usersClient = usersClient;
    }
}
