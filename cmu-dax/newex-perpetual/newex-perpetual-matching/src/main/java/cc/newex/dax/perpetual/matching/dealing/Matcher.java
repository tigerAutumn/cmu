package cc.newex.dax.perpetual.matching.dealing;

import cc.newex.dax.perpetual.common.config.PerpetualConfig;
import cc.newex.dax.perpetual.common.enums.DirectionEnum;
import cc.newex.dax.perpetual.common.enums.OrderClazzEnum;
import cc.newex.dax.perpetual.common.enums.OrderReasonEnum;
import cc.newex.dax.perpetual.common.enums.OrderSideEnum;
import cc.newex.dax.perpetual.common.enums.OrderSystemTypeEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.bean.ShortOrder;
import cc.newex.dax.perpetual.dto.enums.OrderStatusEnum;
import cc.newex.dax.perpetual.matching.bean.ShortOrders;
import cc.newex.dax.perpetual.util.BigDecimalUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

/**
 * 价格匹配逻辑
 *
 * @author xionghui
 * @date 2018/11/14
 */
@Slf4j
public class Matcher {
  // 吃单规则：价格成交最大偏差; 需要用"0.1", 不然double转BigDecimal有精度问题
  private static final BigDecimal MAX_PRICE_DIFF = new BigDecimal("0.1");

  /**
   * 价格匹配撮合
   */
  public static List<ShortOrders> matchMaking(final Contract contract, final ShortOrder shortOrder,
                                              final NavigableMap<BigDecimal, Map<Long, ShortOrder>> depthMap,
                                              final NavigableMap<BigDecimal, Map<Long, ShortOrder>> oppositeDepthMap,
                                              final UserBalance insuranceUserBalance, final PerpetualConfig perpetualConfig) {
    //log.info("Matcher matchMaking begin, orderId: {}", shortOrder.getOrderId());
    final List<ShortOrders> shortOrdersList = new LinkedList<>();
    if (shortOrder.getClazz() == OrderClazzEnum.CANCEL.getClazz()) {
      // 撤单
      cancelOrder(shortOrdersList, shortOrder, depthMap);
    } else {
      // 下单
      match(contract, shortOrdersList, shortOrder, depthMap, oppositeDepthMap, insuranceUserBalance,
          perpetualConfig);
    }
    // log.info("Matcher matchMaking end");
    return shortOrdersList;
  }

  /**
   * 撤销订单
   */
  private static void cancelOrder(final List<ShortOrders> shortOrdersList, final ShortOrder shortOrder,
                                  final Map<BigDecimal, Map<Long, ShortOrder>> depthMap) {
    final Map<Long, ShortOrder> shortOrderMap = depthMap.get(shortOrder.getPrice());
    ShortOrder cancelledOrder = null;
    if (shortOrderMap != null) {
      // 使用relationOrderId，因为取消的订单是新订单，relationOrderId为原订单id
      cancelledOrder = shortOrderMap.remove(shortOrder.getRelationOrderId());
      // help gc
      if (shortOrderMap.size() == 0) {
        depthMap.remove(shortOrder.getPrice());
      }
    }
    if (cancelledOrder == null) {
      log.warn("Matcher cancelOrder cancelledOrder is null");
    } else {
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
      shortOrdersList.add(buildCancelOrder(cancelledOrder, orderReasonEnum));
    }
    shortOrdersList.add(ShortOrders.builder().takerOrCancelledOrder(shortOrder).build());
  }

  /**
   * 订单和对手订单价格匹配逻辑：<br />
   *
   * 1.价格没交叉不撮合 <br />
   * 2.被动委托如果是taker则撤单 <br />
   * 3.爆仓单fok，还需要检查保险金够不够 <br />
   * 4.强平单不fok，支持部分成交 <br />
   * 5.order成交价格偏离盘口价格一定比例(比如10%)需要完全撤单(只撤order，已经处理的对手订单需要还原，强平单和爆仓单不检查)，防止用户失误下单 <br />
   * 6.如果是机器人账号和普通账号成交则撤机器的订单: <br />
   * **1)新订单为机器人订单但是对手单是普通订单，直接撤销新订单<br />
   * **2)对手订单为机器人订单但是新订单是普通订单，撤销对手订单并continue比较下一个对手订单 <br />
   */
  private static void match(final Contract contract, final List<ShortOrders> totalShortOrdersList, final ShortOrder shortOrder,
                            final NavigableMap<BigDecimal, Map<Long, ShortOrder>> depthMap,
                            final NavigableMap<BigDecimal, Map<Long, ShortOrder>> oppositeDepthMap,
                            final UserBalance insuranceUserBalance, final PerpetualConfig perpetualConfig) {
    final List<ShortOrders> shortOrdersList = new LinkedList<>();
    // fok撤单还原
    final ShortOrder originShortOrder = shortOrder.clone();
    final List<ShortOrders> originShortOrdersList = new LinkedList<>();
    // 初始价格，order成交价格离盘口价格大于一定比例需要完全撤单
    final BigDecimal startPrice = oppositeDepthMap.size() > 0 ? oppositeDepthMap.firstKey() : null;
    final Map<BigDecimal, List<Long>> deleteMap = new HashMap<>();
    final Iterator<Map.Entry<BigDecimal, Map<Long, ShortOrder>>> depthIterator =
        oppositeDepthMap.entrySet().iterator();
    label: while (depthIterator.hasNext()) {
      final Map.Entry<BigDecimal, Map<Long, ShortOrder>> depthEntry = depthIterator.next();
      // 非爆仓单判断价格是否有交叉
      if (shortOrder.getSystemType() != OrderSystemTypeEnum.EXPLOSION.getSystemType()
          && OrderSideEnum.getPriceComparator(shortOrder.getSide()).compare(shortOrder.getPrice(),
              depthEntry.getKey()) < 0) {
        break;
      }
      // 被动委托是taker则撤单
      if (shortOrder.getMustMaker() == 1) {
        shortOrdersList.add(buildCancelOrder(shortOrder, OrderReasonEnum.MUSTMAKER_TAKER));
        break;
      }
      // 取maker价格为成交价
      final BigDecimal currentPrice = depthEntry.getKey();
      // 吃单价格检查
      if (shortOrder.getSystemType() != OrderSystemTypeEnum.EXPLOSION.getSystemType()
          && shortOrder.getSystemType() != OrderSystemTypeEnum.FORCED_LIQUIDATION.getSystemType()
          && startPrice.subtract(currentPrice).abs().divide(startPrice, 4, BigDecimal.ROUND_CEILING)
              .compareTo(MAX_PRICE_DIFF) > 0) {
        // 使用originOrder
        resetShortOrders(shortOrdersList, originShortOrdersList,
                buildCancelOrder(originShortOrder, OrderReasonEnum.DEAL_TOO_MUCH));
        totalShortOrdersList.addAll(shortOrdersList);
        return;
      }
      for (final Map.Entry<Long, ShortOrder> entry : depthEntry.getValue().entrySet()) {
        final ShortOrder oppositeShortOrder = entry.getValue();
        // 还原时使用
        originShortOrdersList.add(ShortOrders.builder().takerOrCancelledOrder(oppositeShortOrder)
            .makerOrder(oppositeShortOrder.clone()).build());
        {
          // 新订单为机器人订单但是对手单是普通订单，直接撤销新订单
          if (shortOrder.getUserId().equals(perpetualConfig.getRobot())
              && !oppositeShortOrder.getUserId().equals(perpetualConfig.getRobot())) {
            shortOrdersList.add(buildCancelOrder(shortOrder, OrderReasonEnum.TAKER_ROBOT));
            break label;
          }
          // 对手订单为机器人订单但是新订单是普通订单，撤销对手订单并continue比较下一个对手订单
          if (oppositeShortOrder.getUserId().equals(perpetualConfig.getRobot())
              && !shortOrder.getUserId().equals(perpetualConfig.getRobot())) {
            shortOrdersList.add(buildCancelOrder(oppositeShortOrder, OrderReasonEnum.MAKER_ROBOT));
            addDeleteMap(deleteMap, oppositeShortOrder);
            continue;
          }
        }
        // 对手剩余挂单量
        final BigDecimal oppositeAmount =
            oppositeShortOrder.getAmount().subtract(oppositeShortOrder.getDealAmount());
        // 自己剩余挂单量
        final BigDecimal amount = shortOrder.getAmount().subtract(shortOrder.getDealAmount());
        // 挂单量为0
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
          shortOrdersList.add(buildCancelOrder(shortOrder, OrderReasonEnum.AMOUNT_ZERO));
          break label;
        }
        // 成交数量，以数量小的为准
        final BigDecimal currentAmount;
        if (amount.compareTo(oppositeAmount) >= 0) {
          currentAmount = oppositeAmount;
          oppositeShortOrder.setStatus(OrderStatusEnum.COMPLETE.getCode());
          shortOrder
              .setStatus(amount.compareTo(oppositeAmount) == 0 ? OrderStatusEnum.COMPLETE.getCode()
                  : OrderStatusEnum.PART_DEAL.getCode());
          addDeleteMap(deleteMap, oppositeShortOrder);
        } else {
          currentAmount = amount;
          oppositeShortOrder.setStatus(OrderStatusEnum.PART_DEAL.getCode());
          shortOrder.setStatus(OrderStatusEnum.COMPLETE.getCode());
        }
        fillShortOrder(contract, shortOrder, shortOrder.getSide(), currentAmount, currentPrice);
        fillShortOrder(contract, oppositeShortOrder, shortOrder.getSide(), currentAmount, currentPrice);
        // 爆仓单累加需要扣除或增加保险金，强平单累加需要增加保险金
        if (shortOrder.getSystemType() == OrderSystemTypeEnum.FORCED_LIQUIDATION.getSystemType()
            || shortOrder.getSystemType() == OrderSystemTypeEnum.EXPLOSION.getSystemType()) {
          final BigDecimal totalAmount = BigDecimalUtil.multiply(currentAmount, contract.getUnitAmount());
          // 爆仓和强平 平多为short，平空位long
          if (OrderSideEnum.SHORT.getSide().equals(shortOrder.getSide())) {
            if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
              shortOrder.setLostSize(BigDecimalUtil.multiply(totalAmount, currentPrice)
                      .subtract(BigDecimalUtil.multiply(totalAmount, shortOrder.getPrice())));
            } else {
              shortOrder.setLostSize(BigDecimalUtil.divide(totalAmount, shortOrder.getPrice())
                      .subtract(BigDecimalUtil.divide(totalAmount, currentPrice)));
            }
          } else {
            if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
              shortOrder.setLostSize(BigDecimalUtil.multiply(totalAmount, shortOrder.getPrice())
                      .subtract(BigDecimalUtil.multiply(totalAmount, currentPrice)));
            } else {
              shortOrder.setLostSize(BigDecimalUtil.divide(totalAmount, currentPrice)
                      .subtract(BigDecimalUtil.divide(totalAmount, shortOrder.getPrice())));
            }
          }
        }
        // 生成成交信息
        shortOrdersList.add(ShortOrders.builder().takerOrCancelledOrder(shortOrder.clone())
            .makerOrder(oppositeShortOrder.clone()).build());
        resetShortOrder(shortOrder);
        resetShortOrder(oppositeShortOrder);
        if (shortOrder.getStatus() == OrderStatusEnum.COMPLETE.getCode()) {
          break label;
        }
      }
    }
    // 爆仓单fok检查和保险金检查
    if (shortOrder.getSystemType() == OrderSystemTypeEnum.EXPLOSION.getSystemType()) {
      // fok检查
      if (OrderStatusEnum.isStatusNotFinish(shortOrder.getStatus())) {
        resetShortOrders(shortOrdersList, originShortOrdersList,
                buildCancelOrder(originShortOrder, OrderReasonEnum.EXPLOSION_FOK));
        totalShortOrdersList.addAll(shortOrdersList);
        return;
      }
      // 预扣掉，不然后续重复用了保险金
      BigDecimal totalLostSize = BigDecimal.ZERO;
      // 检查保证金
      for (final ShortOrders shortOrders : shortOrdersList) {
        if (!shortOrders.getTakerOrCancelledOrder().getId().equals(shortOrder.getId())) {
          continue;
        }
        totalLostSize = totalLostSize.add(shortOrders.getTakerOrCancelledOrder().getLostSize());
        if (insuranceUserBalance.getAvailableBalance().compareTo(totalLostSize.negate()) < 0) {
          resetShortOrders(shortOrdersList, originShortOrdersList,
                  buildCancelOrder(originShortOrder, OrderReasonEnum.INSURANCE_LESS));
          totalShortOrdersList.addAll(shortOrdersList);
          return;
        }
      }
      // 保险金不能补贴穿仓损失超过仓位的开仓价值，防止一次补贴太大导致保险金的钱被耗尽
      if (totalLostSize.negate().compareTo(shortOrder.getBrokerSize()) > 0) {
        resetShortOrders(shortOrdersList, originShortOrdersList,
                buildCancelOrder(originShortOrder, OrderReasonEnum.INSURANCE_TOO_MUCH));
        totalShortOrdersList.addAll(shortOrdersList);
        return;
      }
      insuranceUserBalance
          .setAvailableBalance(insuranceUserBalance.getAvailableBalance().add(totalLostSize));
    }
    // 清除数据
    removeDepthMap(deleteMap, oppositeDepthMap);
    // 新订单或者部分成交完成
    if (OrderStatusEnum.isStatusNotFinish(shortOrder.getStatus())) {
      // 深度吃完后市价单需要撤单
      if (shortOrder.getSystemType() == OrderSystemTypeEnum.MARKET.getSystemType()) {
        shortOrdersList.add(buildCancelOrder(shortOrder, OrderReasonEnum.MARKET_DEPTH));
      } else {
        // 增加数据
        addDepthMap(shortOrder, depthMap);
      }
    }
    totalShortOrdersList.addAll(shortOrdersList);
  }

  /**
   * 填充数据
   */
  private static void fillShortOrder(final Contract contract, final ShortOrder shortOrder, final String side, final BigDecimal currentAmount,
                                     final BigDecimal currentPrice) {
    shortOrder.setDealAmount(shortOrder.getDealAmount().add(currentAmount));
    shortOrder.setOtherSide(side);
    shortOrder.setCurrentAmount(currentAmount);
    shortOrder.setCurrentPrice(currentPrice);
    final BigDecimal totalAmount = BigDecimalUtil.multiply(currentAmount, contract.getUnitAmount());
    if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
      shortOrder.setCurrentSize(BigDecimalUtil.multiply(totalAmount, currentPrice));
    } else {
      shortOrder.setCurrentSize(BigDecimalUtil.divide(totalAmount, currentPrice));
    }
  }

  /**
   * 还原订单
   */
  private static void resetShortOrder(final ShortOrder shortOrder) {
    shortOrder.setOtherSide(null);
    shortOrder.setCurrentAmount(null);
    shortOrder.setCurrentPrice(null);
    shortOrder.setCurrentSize(null);
    shortOrder.setLostSize(null);
    shortOrder.setReason(0);
  }

  /**
   * 还原数据
   */
  private static void resetShortOrders(final List<ShortOrders> shortOrdersList,
                                       final List<ShortOrders> originShortOrdersList, final ShortOrders cancelOrders) {
    originShortOrdersList.forEach(orders -> {
      final ShortOrder shortOrder = orders.getTakerOrCancelledOrder();
      final ShortOrder originShortOrder = orders.getMakerOrder();
      shortOrder.setStatus(originShortOrder.getStatus());
      shortOrder.setOtherSide(originShortOrder.getOtherSide());
      shortOrder.setDealAmount(originShortOrder.getDealAmount());
      shortOrder.setCurrentAmount(originShortOrder.getCurrentAmount());
      shortOrder.setCurrentPrice(originShortOrder.getCurrentPrice());
    });
    // 删除旧数据，只取消新订单
    shortOrdersList.clear();
    shortOrdersList.add(cancelOrders);
  }

  /**
   * 清除已经成交或者取消的数据
   */
  private static void removeDepthMap(final Map<BigDecimal, List<Long>> deleteMap,
                                     final Map<BigDecimal, Map<Long, ShortOrder>> depthMap) {
    for (final Map.Entry<BigDecimal, List<Long>> entry : deleteMap.entrySet()) {
      final Map<Long, ShortOrder> orderMap = depthMap.get(entry.getKey());
      if (orderMap != null) {
        for (final Long orderId : entry.getValue()) {
          orderMap.remove(orderId);
        }
        // help gc
        if (orderMap.size() == 0) {
          depthMap.remove(entry.getKey());
        }
      }
    }
  }

  /**
   * 加入未成交完成的数据
   */
  private static void addDepthMap(final ShortOrder shortOrder,
                                  final Map<BigDecimal, Map<Long, ShortOrder>> depthMap) {
    // 插入缓存
    Map<Long, ShortOrder> shortOrderMap = depthMap.get(shortOrder.getPrice());
    if (shortOrderMap == null) {
      // 插入优先
      shortOrderMap = new LinkedHashMap<>();
      depthMap.put(shortOrder.getPrice(), shortOrderMap);
    }
    shortOrderMap.put(shortOrder.getOrderId(), shortOrder);
  }

  /**
   * 需要删除的数据
   */
  private static void addDeleteMap(final Map<BigDecimal, List<Long>> deleteMap, final ShortOrder shortOrder) {
    List<Long> orderIdList = deleteMap.get(shortOrder.getPrice());
    if (orderIdList == null) {
      orderIdList = new LinkedList<>();
      deleteMap.put(shortOrder.getPrice(), orderIdList);
    }
    orderIdList.add(shortOrder.getOrderId());
  }

  /**
   * 构建撤销的订单
   */
  private static ShortOrders buildCancelOrder(final ShortOrder shortOrder, final OrderReasonEnum orderReasonEnum) {
    shortOrder.setStatus(OrderStatusEnum.CANCELED.getCode());
    shortOrder.setReason(orderReasonEnum.getReason());
    return ShortOrders.builder().takerOrCancelledOrder(shortOrder).build();
  }
}
