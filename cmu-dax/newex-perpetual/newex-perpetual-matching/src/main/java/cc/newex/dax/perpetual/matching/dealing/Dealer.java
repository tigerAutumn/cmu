package cc.newex.dax.perpetual.matching.dealing;

import cc.newex.dax.perpetual.common.config.PerpetualConfig;
import cc.newex.dax.perpetual.common.constant.PerpetualCacheKeys;
import cc.newex.dax.perpetual.common.enums.OrderClazzEnum;
import cc.newex.dax.perpetual.common.enums.OrderReasonEnum;
import cc.newex.dax.perpetual.common.enums.OrderSideEnum;
import cc.newex.dax.perpetual.common.enums.OrderSystemTypeEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Deal;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.Pending;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.bean.ShortOrder;
import cc.newex.dax.perpetual.dto.enums.OrderStatusEnum;
import cc.newex.dax.perpetual.dto.enums.PushTypeEnum;
import cc.newex.dax.perpetual.matching.bean.DepthCache;
import cc.newex.dax.perpetual.matching.bean.DepthCache.Depth;
import cc.newex.dax.perpetual.matching.bean.PushInfo;
import cc.newex.dax.perpetual.matching.bean.ShortOrders;
import cc.newex.dax.perpetual.matching.bean.constant.Constants;
import cc.newex.dax.perpetual.matching.service.TradeService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * 撮合交易处理类
 *
 * @author xionghui
 * @date 2018/10/20
 */
@Slf4j
public class Dealer {
  private final Contract contract;
  private final List<Contract> contractList;
  private final TradeService tradeService;
  private final PerpetualConfig perpetualConfig;
  private final StringRedisTemplate redisTemplate;

  private boolean init = false;
  private final int size = 10000;

  // 买方深度，价格从高到低排序
  private final NavigableMap<BigDecimal, Map<Long, ShortOrder>> buyDepths =
      new TreeMap<>((o1, o2) -> {
        return o2.compareTo(o1);
      });
  // 卖方深度
  private final NavigableMap<BigDecimal, Map<Long, ShortOrder>> sellDepths = new TreeMap<>();

  // 最新成交的数据缓存最大条数
  private static final int RECENT_DEAL_MAX_SIZE = 100;
  // 最新成交的数据
  private final LinkedList<Deal> recentDeal = new LinkedList<>();

  public Dealer(final Contract contract, final List<Contract> contractList,
                final TradeService tradeService, final PerpetualConfig perpetualConfig,
                final StringRedisTemplate redisTemplate) {
    this.contract = contract;
    this.contractList = contractList;
    this.tradeService = tradeService;
    this.perpetualConfig = perpetualConfig;
    this.redisTemplate = redisTemplate;
  }

  public Contract getContract() {
    return this.contract;
  }

  /**
   * reset
   */
  public void resetInit() {
    this.init = false;

    this.recentDeal.clear();
    this.buyDepths.clear();
    this.sellDepths.clear();
  }

  /**
   * deal入口
   */
  public void deal() {
    log.info("Dealer deal begin, Contract: {}", this.contract);
    this.tryInit();
    this.realDeal(0, this.size);
    log.info("Dealer deal end");
  }

  /**
   * 初始化
   */
  private void tryInit() {
    if (this.init) {
      return;
    }
    log.info("Dealer tryInit begin");
    // 加载最新成交
    this.initRecentDeal();
    // 匹配已经扫描过的订单
    this.realDeal(1, null);
    this.init = true;
    log.info("Dealer tryInit end");
  }

  /**
   * 从数据库加载最新成交
   */
  private void initRecentDeal() {
    final List<Pending> pendingList =
            this.tradeService.getRecentDeal(RECENT_DEAL_MAX_SIZE, this.contract);
    for (final Pending pending : pendingList) {
      this.recentDeal.addLast(
              Deal.builder().id(pending.getId()).price(pending.getPrice()).amount(pending.getAmount())
                      .side(pending.getSide()).createdDate(pending.getCreatedDate()).build());
    }
  }

  /**
   * 成交
   */
  private void realDeal(final int match, final Integer limit) {
    // 查询订单
    final List<ShortOrder> shortOrderList = this.fetchOrder(match, limit);
    if (shortOrderList.size() == 0) {
      return;
    }
    // 撮合的订单对
    final List<ShortOrders> shortOrdersList = new LinkedList<>();
    // 取消新查的数据
    final Set<Long> handledOrderIdSet =
            this.dealCancelNewOrder(shortOrderList, shortOrdersList);
    // 取消单-->爆仓单/强平单/市价单/限价单
    final LinkedList<ShortOrder> dealShortOrderList = new LinkedList<>();
    // 是否有爆仓单
    boolean checkExplosion = false;
    // 是否有强平单或者爆仓单
    boolean checkInsurance = false;
    for (final ShortOrder shortOrder : shortOrderList) {
      // 已经取消的order不处理
      if (handledOrderIdSet.contains(shortOrder.getOrderId())) {
        continue;
      }
      if (shortOrder.getClazz() == OrderClazzEnum.CANCEL.getClazz()) {
        dealShortOrderList.addFirst(shortOrder);
      } else {
        dealShortOrderList.addLast(shortOrder);
        if (shortOrder.getSystemType() == OrderSystemTypeEnum.EXPLOSION.getSystemType()) {
          checkExplosion = true;
          checkInsurance = true;
        } else if (shortOrder.getSystemType() == OrderSystemTypeEnum.FORCED_LIQUIDATION
                .getSystemType()) {
          checkInsurance = true;
        }
      }
    }
    // 爆仓单需要检查保险金账号
    UserBalance insuranceUserBalance = null;
    if (checkExplosion) {
      insuranceUserBalance = this.tradeService.getInsuranceBalance(this.contract);
    }
    this.dealOrders(dealShortOrderList, insuranceUserBalance, checkInsurance, shortOrdersList);
  }

  /**
   * 根据match查询订单
   */
  private List<ShortOrder> fetchOrder(final int match, final Integer limit) {
    log.info("Dealer fetchOrder begin: {}", match);
    final List<Order> orderList =
            this.tradeService.getOrderByMatchStatus(this.contract, match, limit);
    final List<ShortOrder> shortOrderList = new LinkedList<>();
    for (final Order order : orderList) {
      shortOrderList.add(ShortOrder.builder().id(order.getId()).orderId(order.getOrderId())
              .userId(order.getUserId()).clazz(order.getClazz()).mustMaker(order.getMustMaker())
              .side(order.getSide()).amount(order.getAmount()).price(order.getPrice())
              .dealAmount(order.getDealAmount()).brokerSize(order.getBrokerSize()).status(order.getStatus())
              .systemType(order.getSystemType()).relationOrderId(order.getRelationOrderId())
              .brokerId(order.getBrokerId()).build());
    }
    log.info("Dealer fetchOrder end: {}", shortOrderList.size());
    if (match == 0 && orderList.size() > 0) {
      final List<Long> idList = new ArrayList<>();
      for (final Order order : orderList) {
        idList.add(order.getId());
      }
      this.tradeService.editOrderMatchStatus(this.contract, idList, 1);
    }
    return shortOrderList;
  }

  /**
   * 取消未加载的order
   */
  private Set<Long> dealCancelNewOrder(final List<ShortOrder> shortOrderList,
                                       final List<ShortOrders> shortOrdersList) {
    final Set<Long> handledOrderIdSet = new HashSet<>();
    final List<ShortOrder> cancelShortOrderList = new LinkedList<>();
    final Map<Long, ShortOrder> shortOrderMap = new HashMap<>();
    for (final ShortOrder shortOrder : shortOrderList) {
      if (shortOrder.getClazz() == OrderClazzEnum.CANCEL.getClazz()) {
        cancelShortOrderList.add(shortOrder);
      } else {
        shortOrderMap.put(shortOrder.getOrderId(), shortOrder);
      }
    }
    for (final ShortOrder shortOrder : cancelShortOrderList) {
      final ShortOrder cancelledOrder = shortOrderMap.remove(shortOrder.getRelationOrderId());
      if (cancelledOrder != null) {
        handledOrderIdSet.add(cancelledOrder.getOrderId());
        handledOrderIdSet.add(shortOrder.getOrderId());

        cancelledOrder.setStatus(OrderStatusEnum.CANCELED.getCode());
        OrderReasonEnum orderReasonEnum = OrderReasonEnum.CANCEL;
        if (shortOrder.getSystemType() == OrderSystemTypeEnum.FORCED_LIQUIDATION.getSystemType()) {
          orderReasonEnum = OrderReasonEnum.FORCED_LIQUIDATION;
        } else if (shortOrder.getSystemType() == OrderSystemTypeEnum.EXPLOSION.getSystemType()) {
          orderReasonEnum = OrderReasonEnum.EXPLOSION;
        } else if (shortOrder.getSystemType() == OrderSystemTypeEnum.MARGIN_AUTO_CANCEL.getSystemType()) {
          orderReasonEnum = OrderReasonEnum.MARGIN_LESS;
        } else if (shortOrder.getSystemType() == OrderSystemTypeEnum.SETTLEMENT_CANCEL.getSystemType()) {
          orderReasonEnum = OrderReasonEnum.SETTLEMENT;
        } else if (shortOrder.getSystemType() == OrderSystemTypeEnum.BROKER_PRICE_CANCEL.getSystemType()) {
          orderReasonEnum = OrderReasonEnum.BROKER_PRICE;
        }
        cancelledOrder.setReason(orderReasonEnum.getReason());
        shortOrdersList.add(ShortOrders.builder().takerOrCancelledOrder(cancelledOrder).build());
        shortOrdersList.add(ShortOrders.builder().takerOrCancelledOrder(shortOrder).build());
      }
    }
    return handledOrderIdSet;
  }

  /**
   * 撮合订单
   */
  private void dealOrders(final List<ShortOrder> dealShortOrderList,
      final UserBalance insuranceUserBalance, final boolean checkInsurance,
      final List<ShortOrders> shortOrdersList) {
    log.info("Dealer dealOrders dealOrderPending begin, size: {}", dealShortOrderList.size());
    final long start = System.currentTimeMillis();
    final List<Pending> pendingList = new LinkedList<>();
    for (final ShortOrder shortOrder : dealShortOrderList) {
      final NavigableMap<BigDecimal, Map<Long, ShortOrder>> depthMap;
      final NavigableMap<BigDecimal, Map<Long, ShortOrder>> oppositeDepthMap;
      if (OrderSideEnum.LONG.getSide().equals(shortOrder.getSide())) {
        depthMap = this.buyDepths;
        oppositeDepthMap = this.sellDepths;
      } else {
        depthMap = this.sellDepths;
        oppositeDepthMap = this.buyDepths;
      }
      shortOrdersList.addAll(Matcher.matchMaking(this.contract, shortOrder, depthMap, oppositeDepthMap,
          insuranceUserBalance, this.perpetualConfig));
      // 批量处理orders，防止一次性处理数据太多
      if (shortOrdersList.size() >= 1024) {
        this.batchDealOrders(shortOrdersList, checkInsurance, pendingList);
      }
    }
    if (shortOrdersList.size() > 0) {
      this.batchDealOrders(shortOrdersList, checkInsurance, pendingList);
    }
    // 缓存和推送
    this.cacheData(pendingList);
    log.info("Dealer dealOrders dealOrderPending end, it cost: {}",
        (System.currentTimeMillis() - start));
  }

  /**
   * 批量处理成交的订单
   */
  private void batchDealOrders(final List<ShortOrders> shortOrdersList,
      final boolean checkInsurance, final List<Pending> pendingList) {
    log.info("Dealer batchDealOrders begin, shortOrdersList: {}", shortOrdersList);
    final long start = System.currentTimeMillis();
    this.tradeService.dealOrderPending(this.contract, this.contractList, shortOrdersList,
            checkInsurance, pendingList);
    shortOrdersList.clear();
    log.info("Dealer batchDealOrders end, it cost: {}", (System.currentTimeMillis() - start));
  }

  /**
   * 缓存和push数据
   */
  private void cacheData(final List<Pending> pendingList) {
    // 更新内存中的最新成交
    pendingList.forEach(pending -> {
      if (this.recentDeal.size() == RECENT_DEAL_MAX_SIZE) {
        this.recentDeal.removeLast();
      }
      this.recentDeal.addFirst(
          Deal.builder().id(pending.getId()).price(pending.getPrice()).amount(pending.getAmount())
              .side(pending.getSide()).createdDate(pending.getCreatedDate()).build());
    });

    final String channel = Constants.buildChannel(this.contract);
    final String depthCacheStr = this.resetLastOrderIdAndBuildDepthCache();
    try {
      this.redisTemplate.opsForValue()
          .set(PerpetualCacheKeys.getDepthKey(this.contract.getContractCode()), depthCacheStr);
      this.redisTemplate.convertAndSend(channel,
          PushInfo.builder().biz(Constants.PERPETUAL_BIZ).type(PushTypeEnum.DEPTH.name())
              .contractCode(this.contract.getContractCode()).zip(true).data(depthCacheStr).build()
              .compress());
      // 最新成交有变化才推送
      if (pendingList.size() > 0) {
        final String recentDealStr = JSON.toJSONString(this.recentDeal);
        this.redisTemplate.opsForValue()
            .set(PerpetualCacheKeys.getDealKey(this.contract.getContractCode()), recentDealStr);
        this.redisTemplate.convertAndSend(channel,
            PushInfo.builder().biz(Constants.PERPETUAL_BIZ).type(PushTypeEnum.FILLS.name())
                .contractCode(this.contract.getContractCode()).zip(true).data(recentDealStr).build()
                .compress());
      }
    } catch (final Exception e) {
      log.error("Dealer cacheData error: ", e);
    }
  }

  /**
   * 构建DepthCache
   */
  private String resetLastOrderIdAndBuildDepthCache() {
    final DepthCache depthCache = new DepthCache();
    BigDecimal sumSellTotalAmount = BigDecimal.ZERO;
    for (final Map.Entry<BigDecimal, Map<Long, ShortOrder>> entry : this.sellDepths.entrySet()) {
      BigDecimal totalAmount = BigDecimal.ZERO;
      for (final Map.Entry<Long, ShortOrder> entry1 : entry.getValue().entrySet()) {
        totalAmount = totalAmount
            .add(entry1.getValue().getAmount().subtract(entry1.getValue().getDealAmount()));
      }
      sumSellTotalAmount = sumSellTotalAmount.add(totalAmount);
      depthCache.getAsks().add(new Depth(entry.getKey(), totalAmount, sumSellTotalAmount));
    }
    BigDecimal sumBuyTotalAmount = BigDecimal.ZERO;
    for (final Map.Entry<BigDecimal, Map<Long, ShortOrder>> entry : this.buyDepths.entrySet()) {
      BigDecimal totalAmount = BigDecimal.ZERO;
      for (final Map.Entry<Long, ShortOrder> entry1 : entry.getValue().entrySet()) {
        totalAmount = totalAmount
            .add(entry1.getValue().getAmount().subtract(entry1.getValue().getDealAmount()));
      }
      sumBuyTotalAmount = sumBuyTotalAmount.add(totalAmount);
      depthCache.getBids().add(new Depth(entry.getKey(), totalAmount, sumBuyTotalAmount));
    }
    return JSON.toJSONString(depthCache);
  }
}
