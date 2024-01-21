package cc.newex.dax.perpetual.service.common;

import cc.newex.dax.perpetual.common.enums.OrderClazzEnum;
import cc.newex.dax.perpetual.common.enums.OrderSystemTypeEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.enums.OrderStatusEnum;
import cc.newex.dax.perpetual.dto.enums.UserBalanceStatusEnum;
import cc.newex.dax.perpetual.service.OrderAllService;
import cc.newex.dax.perpetual.service.OrderShardingService;
import cc.newex.dax.perpetual.service.UserBalanceService;
import cc.newex.dax.perpetual.service.UserPositionService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public abstract class CloseLiquidateService<T> {

    @Autowired
    protected MarketService marketService;
    @Autowired
    protected UserBalanceService userBalanceService;
    @Autowired
    protected UserPositionService userPositionService;
    @Autowired
    protected OrderShardingService orderShardingService;
    @Autowired
    protected OrderAllService orderAllService;
    @Autowired
    protected PushService pushService;

    @Transactional(rollbackFor = Exception.class)
    public List<UserPosition> preCheck(final Contract contract, final BigDecimal marketPrice, final List<T> records) {
        if (records == null) {
            CloseLiquidateService.log.warn("record is null");
            return new ArrayList<>();
        }

        // 获取用户持仓记录 id
        final List<Long> recordId = this.takePositionId(records);
        final List<UserPosition> userPositions = this.userPositionService.getInById(recordId);
        if (CollectionUtils.isEmpty(userPositions)) {
            CloseLiquidateService.log.error("not found userPosition, recordId : {}", JSON.toJSONString(recordId));
            return new ArrayList<>();
        }

        final List<UserPosition> removeList = new LinkedList<>();
        final List<UserPosition> cancelList = new LinkedList<>();
        final List<UserPosition> resultList = new LinkedList<>();

        for (final UserPosition userPosition : userPositions) {
            // 用户持仓数量如果 <= 0 则删除
            if (userPosition.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                removeList.add(userPosition);
                continue;
            }

            // 检查标记价格与强平价格或破产价格是否在合理区间
            final boolean outSide = this.marketPriceIsOutSide(marketPrice, userPosition);
            if (outSide) {
                cancelList.add(userPosition);
                removeList.add(userPosition);
                continue;
            }
            resultList.add(userPosition);
        }

        if (CollectionUtils.isNotEmpty(cancelList)) {
            this.cancelPriceOutSideOrder(contract, marketPrice, cancelList, records);
        }

        if (CollectionUtils.isNotEmpty(removeList)) {
            this.removeRecord(contract, removeList);
        }
        return resultList;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<UserPosition> lockBalanceAndCheckStatus(final Contract contract, final List<Contract> allContract, final List<UserPosition> userPositions,
                                                        final List<T> records,
                                                        final Consumer<List<UserPosition>> onUpdateStatus) {

        if (CollectionUtils.isEmpty(userPositions)) {
            return new ArrayList<>();
        }

        final Map<Integer, List<UserPosition>> positionMap = userPositions.stream().collect(Collectors.groupingBy(UserPosition::getBrokerId));
        final Set<Map.Entry<Integer, List<UserPosition>>> entries = positionMap.entrySet();

        // 记录需要第一次被强平、爆仓的持仓
        final List<UserPosition> normalStatusList = new LinkedList<>();
        // 记录需要被下强平单的持仓
        final Map<Long, UserPosition> resultMap = new HashMap<>();
        // 记录需要更新用户状态为强平、爆仓的balance
        final List<UserBalance> updateBalanceList = new LinkedList<>();

        final UserBalanceStatusEnum regularStatus = this.takeUserBalanceStatusEnum();

        final Map<Integer, Map<Long, List<Order>>> tradeOrderMap = new HashMap<>();
        for (final Map.Entry<Integer, List<UserPosition>> entry : entries) {

            final List<Long> userIds = entry.getValue().stream().map(UserPosition::getUserId).collect(Collectors.toList());
            final List<UserBalance> userBalances = this.userBalanceService.selectBatchForUpdate(contract.getBase(), entry.getKey(), new HashSet<>(userIds));
            if (CollectionUtils.isEmpty(userBalances)) {
                CloseLiquidateService.log.error("not found user balance, brokerId : {}, userIds : {}", entry.getKey(), JSON.toJSONString(userIds));
                continue;
            }

            final Map<Long, UserPosition> pMap = entry.getValue().stream().collect(Collectors.toMap(UserPosition::getUserId, Function.identity(), (x, y) -> x));
            for (final UserBalance userBalance : userBalances) {
                // 检查用户的 balance 的状态如果为 NORMAL 则更新状态
                if (UserBalanceStatusEnum.NORMAL.equals(UserBalanceStatusEnum.fromInteger(userBalance.getStatus()))) {
                    userBalance.setStatus(regularStatus.getCode());
                    updateBalanceList.add(userBalance);
                    normalStatusList.add(pMap.get(userBalance.getUserId()));
                }

                // 检查用户状态如当前执行需要的状态是否相同
                if (!regularStatus.equals(UserBalanceStatusEnum.fromInteger(userBalance.getStatus()))) {
                    continue;
                }
                // 如果用户状态为 normal 或 当前任务应该支持的状态（强平任务支持 强平状态、爆仓任务支持爆仓状态）则放到 result 队列
                final UserPosition userPosition = pMap.get(userBalance.getUserId());
                resultMap.put(userPosition.getId(), userPosition);
            }

            // 查找用户的挂单
            if (CollectionUtils.isNotEmpty(normalStatusList)) {
                final List<Order> orders = this.orderShardingService.queryOrder(contract.getContractCode(), entry.getKey(), normalStatusList.stream().map(UserPosition::getUserId).collect(Collectors.toList()));

                final Map<Long, List<Order>> orderMap = orders.stream().filter(o -> {
                    return (!o.getStatus().equals(OrderStatusEnum.CANCELED.getCode()))
                            && (!o.getClazz().equals(OrderClazzEnum.CANCEL.getClazz()));
                }).collect(Collectors.groupingBy(Order::getUserId));
                tradeOrderMap.put(entry.getKey(), orderMap);
            }
        }

        // 检查是否有未完成的 挂单 或 强平单, 如果有未完成的订单需要讲position从 result 队列中异常
        final Map<Long, Long> orderIdMap = new HashMap<>();
        for (final T record : records) {
            final List<Long> cancelOrderId = this.takeCancelOrderId(record);
            final Long closeOrderId = this.takeCloseOrderId(record);
            final List<Long> orderId = new LinkedList<>();
            if (CollectionUtils.isNotEmpty(cancelOrderId)) {
                orderId.addAll(cancelOrderId);
            }
            if (closeOrderId != null) {
                orderId.add(closeOrderId);
            }
            if (CollectionUtils.isEmpty(orderId)) {
                continue;
            }
            for (final Long i : orderId) {
                orderIdMap.put(i, this.takePositionId(record));
            }
        }
        // 从 orderAll 中查找订单, 并从result队列中移除还未完成的数据
        final List<Order> orderAllList = this.orderShardingService.selectInByOrderId(contract.getContractCode(), new ArrayList<>(orderIdMap.keySet()));
        if (CollectionUtils.isNotEmpty(orderAllList)) {
            final List<Long> uncompletedId = orderAllList.stream()
                    .filter(o -> !o.getStatus().equals(OrderStatusEnum.COMPLETE.getCode()))
                    .map(Order::getOrderId).collect(Collectors.toList());
            CloseLiquidateService.log.info("uncompleted order, orderId : {}", JSON.toJSONString(uncompletedId));
            for (final Long o : uncompletedId) {
                resultMap.remove(orderIdMap.get(o));
            }
        }

        if (CollectionUtils.isNotEmpty(updateBalanceList)) {
            this.userBalanceService.batchEdit(updateBalanceList);
        }

        if (CollectionUtils.isNotEmpty(normalStatusList) && onUpdateStatus != null) {
            onUpdateStatus.accept(normalStatusList);
        }

        // 检查用户是否有未完成的普通交易单，如果有则取消
        if (CollectionUtils.isNotEmpty(normalStatusList) && MapUtils.isNotEmpty(tradeOrderMap)) {
            final List<Order> orderall = new LinkedList<>();
            final List<T> updateRecord = new LinkedList<>();
            final Map<Long, T> recordMap = this.toRecordList(records);
            for (final UserPosition p : normalStatusList) {
                if (!tradeOrderMap.containsKey(p.getBrokerId())) {
                    continue;
                }
                if (!tradeOrderMap.get(p.getBrokerId()).containsKey(p.getUserId())) {
                    continue;
                }
                final List<Order> orders = tradeOrderMap.get(p.getBrokerId()).get(p.getUserId());
                resultMap.remove(p.getId());
                orderall.addAll(orders);
                updateRecord.add(recordMap.get(p.getId()));
                this.setCancelOrderId(recordMap.get(p.getId()), JSON.toJSONString(orders.stream().map(Order::getOrderId).collect(Collectors.toList())));
            }
            final OrderSystemTypeEnum cancelType = (regularStatus == UserBalanceStatusEnum.LIQUIDATE)
                    ? OrderSystemTypeEnum.FORCED_LIQUIDATION
                    : OrderSystemTypeEnum.EXPLOSION;
            this.orderShardingService.cancelAll(orderall, contract.getContractCode(), cancelType);
            if (CollectionUtils.isNotEmpty(updateRecord)) {
                this.batchEdit(updateRecord);
            }
        }

        if (MapUtils.isEmpty(resultMap)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(resultMap.values());
    }

    protected void removeRecord(final Contract contract, final List<UserPosition> userPosition) {

        if (CollectionUtils.isEmpty(userPosition)) {
            return;
        }
        final Map<Integer, List<UserPosition>> brokerGroup = userPosition.stream().collect(Collectors.groupingBy(UserPosition::getBrokerId));
        final UserBalanceStatusEnum userBalanceStatusEnum = this.takeUserBalanceStatusEnum();
        final List<UserBalance> updateBalances = new LinkedList<>();
        final Set<Map.Entry<Integer, List<UserPosition>>> entries = brokerGroup.entrySet();
        for (final Map.Entry<Integer, List<UserPosition>> entry : entries) {
            final List<Long> userIds = entry.getValue().stream().map(UserPosition::getUserId).collect(Collectors.toList());
            final List<UserBalance> userBalances = this.userBalanceService.selectBatchForUpdate(contract.getBase(), entry.getKey(), new HashSet<>(userIds));
            for (final UserBalance userBalance : userBalances) {
                if (userBalanceStatusEnum.equals(UserBalanceStatusEnum.fromInteger(userBalance.getStatus()))) {
                    userBalance.setStatus(UserBalanceStatusEnum.NORMAL.getCode());
                    updateBalances.add(userBalance);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(updateBalances)) {
            this.userBalanceService.batchEdit(updateBalances);
        }

        final List<Long> positionIds = userPosition.stream().map(UserPosition::getId).collect(Collectors.toList());
        this.removeById(positionIds);
    }

    /**
     * 获取持仓 id
     *
     * @param record
     * @return
     */
    protected abstract List<Long> takePositionId(List<T> record);

    /**
     * list -> map
     *
     * @param record
     * @return
     */
    protected abstract Map<Long, T> toRecordList(List<T> record);
    /**
     * 获取持仓 id
     *
     * @param record
     * @return
     */
    protected abstract Long takePositionId(T record);

    /**
     * 获取取消订单 id
     *
     * @param record
     * @return
     */
    protected abstract List<Long> takeCancelOrderId(T record);

    /**
     * 获取平仓单 id
     *
     * @param record
     * @return
     */
    protected abstract Long takeCloseOrderId(T record);

    /**
     * 获取爆仓（平仓）范围的最大值
     *
     * @param marketPrice
     * @param userPosition
     * @return
     */
    protected abstract boolean marketPriceIsOutSide(BigDecimal marketPrice,
                                                    UserPosition userPosition);

    /**
     * 取消价格超出范围的订单
     *
     * @param contract
     * @param marketPrice
     * @param userPositions
     * @param allRecords
     */
    protected abstract void cancelPriceOutSideOrder(Contract contract, BigDecimal marketPrice, List<UserPosition> userPositions, List<T> allRecords);

    /**
     * 删除记录
     *
     * @param id
     */
    protected abstract void removeById(List<Long> id);

    /**
     * 更新记录
     *
     * @param record
     */
    protected abstract void batchEdit(List<T> record);

    /**
     * 设置取消单 id
     *
     * @param record
     * @param cancelOrderId
     */
    protected abstract void setCancelOrderId(T record, String cancelOrderId);

    /**
     * 获取爆仓（平仓）枚举
     *
     * @return
     */
    protected abstract UserBalanceStatusEnum takeUserBalanceStatusEnum();

    public abstract void placeCloseOrder(Contract contract, BigDecimal marketPrice, List<UserPosition> positions, List<T> allRecords);

    public abstract boolean contraTrade(Contract contract, List<Contract> contractList, T record, Long header);

    public abstract boolean contraTrade(final Contract contract, final List<Contract> contractList, final List<T> record,
                                        final List<UserPositionService.UserRank> header, final Map<String, MarkIndexPriceDTO> priceMap);

    private String takeCancelOrderId(final List<Long> orderId) {
        if (CollectionUtils.isEmpty(orderId)) {
            return null;
        }
        return JSON.toJSONString(orderId);
    }
}
