package cc.newex.dax.perpetual.service.common.impl;

import cc.newex.dax.perpetual.common.enums.OrderDetailSideEnum;
import cc.newex.dax.perpetual.common.enums.OrderSideEnum;
import cc.newex.dax.perpetual.common.enums.OrderSystemTypeEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.HistoryLiquidate;
import cc.newex.dax.perpetual.domain.Liquidate;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.OrderDTO;
import cc.newex.dax.perpetual.dto.enums.OrderFromEnum;
import cc.newex.dax.perpetual.dto.enums.PushTypeEnum;
import cc.newex.dax.perpetual.dto.enums.UserBalanceStatusEnum;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import cc.newex.dax.perpetual.service.HistoryLiquidateService;
import cc.newex.dax.perpetual.service.LiquidateService;
import cc.newex.dax.perpetual.service.UserPositionService;
import cc.newex.dax.perpetual.service.common.CloseLiquidateService;
import cc.newex.dax.perpetual.util.ContractSizeUtil;
import cc.newex.dax.perpetual.util.PushDataUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service("liquidateCloseService")
public class LiquidateCloseService extends CloseLiquidateService<Liquidate> {

    @Autowired
    private LiquidateService liquidateService;
    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private HistoryLiquidateService historyLiquidateService;


    @Override
    protected List<Long> takePositionId(final List<Liquidate> record) {
        if (CollectionUtils.isEmpty(record)) {
            return new ArrayList<>();
        }
        return record.stream().map(Liquidate::getId).collect(Collectors.toList());
    }

    @Override
    protected Map<Long, Liquidate> toRecordList(final List<Liquidate> record) {
        return record.stream().collect(Collectors.toMap(Liquidate::getId, Function.identity(), (x, y) -> x));
    }

    @Override
    protected Long takePositionId(final Liquidate record) {
        return record.getId();
    }

    @Override
    protected List<Long> takeCancelOrderId(final Liquidate record) {
        if (StringUtils.isBlank(record.getCancelOrderId())) {
            return new ArrayList<>();
        }
        return JSON.parseArray(record.getCancelOrderId(), Long.class);
    }

    @Override
    protected Long takeCloseOrderId(final Liquidate record) {
        return record.getCloseOrderId();
    }

    @Override
    protected boolean marketPriceIsOutSide(final BigDecimal marketPrice, final UserPosition userPosition) {
        if (OrderSideEnum.LONG.getSide().equals(userPosition.getSide())) {
            return marketPrice.compareTo(userPosition.getLiqudatePrice()) >= 0
                    || marketPrice.compareTo(userPosition.getBrokerPrice()) <= 0;
        } else {
            return marketPrice.compareTo(userPosition.getLiqudatePrice()) <= 0
                    || marketPrice.compareTo(userPosition.getBrokerPrice()) >= 0;
        }
    }

    @Override
    protected void cancelPriceOutSideOrder(final Contract contract, final BigDecimal marketPrice, final List<UserPosition> userPositions, final List<Liquidate> allRecords) {

        final Map<Long, Liquidate> liquidateMap = allRecords.stream()
                .collect(Collectors.toMap(Liquidate::getId, Function.identity(), (x, y) -> x));
        final List<HistoryLiquidate> historyLiquidates = new LinkedList<>();
        final List<Long> orderIds = new LinkedList<>();
        for (final UserPosition userPosition : userPositions) {
            final boolean mark = (OrderSideEnum.LONG.getSide().equals(userPosition.getSide())
                    && marketPrice.compareTo(userPosition.getLiqudatePrice()) >= 0)
                    || (OrderSideEnum.SHORT.getSide().equals(userPosition.getSide())
                    && marketPrice.compareTo(userPosition.getLiqudatePrice()) <= 0);
            final Liquidate record = liquidateMap.get(userPosition.getId());
            if (mark && record.getCloseOrderId() != null) {
                final HistoryLiquidate historyLiquidate = this.takeHistoryLiquidate(userPosition, 0, record.getCloseOrderId(), marketPrice);
                historyLiquidates.add(historyLiquidate);
                orderIds.add(record.getCloseOrderId());
            }
        }
        if (CollectionUtils.isNotEmpty(historyLiquidates)) {
            this.historyLiquidateService.batchAdd(historyLiquidates);
        }

        if (CollectionUtils.isNotEmpty(orderIds)) {
            final List<Order> orders = this.orderShardingService.selectInByOrderId(contract.getContractCode(), orderIds);
            this.orderShardingService.cancelAll(orders, contract.getContractCode(), OrderSystemTypeEnum.FORCED_LIQUIDATION);
            LiquidateCloseService.log.info("cancel close order success, orderId : {}", JSON.toJSONString(orderIds));
        }
    }

    @Override
    protected void removeById(final List<Long> id) {
        this.liquidateService.removeInById(id);
    }

    @Override
    protected void batchEdit(final List<Liquidate> record) {
        this.liquidateService.batchEdit(record);
    }

    @Override
    protected void setCancelOrderId(final Liquidate record, final String cancelOrderId) {
        record.setCancelOrderId(cancelOrderId);
    }

    @Override
    protected UserBalanceStatusEnum takeUserBalanceStatusEnum() {
        return UserBalanceStatusEnum.LIQUIDATE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void placeCloseOrder(final Contract contract, final BigDecimal marketPrice, final List<UserPosition> positions, final List<Liquidate> allRecords) {
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
                LiquidateCloseService.log.error("not found user balance, brokerId : {}, userId : {}", brokerId, JSON.toJSONString(userPositionMap.keySet()));
                continue;
            }
            userBalanceList.addAll(userBalances);
            for (final UserBalance userBalance : userBalances) {
                final UserPosition userPosition = userPositionMap.get(userBalance.getUserId());
                final int amount = this.takeLiquidateAmount(contract, userBalance, userPosition, marketPrice);
                if (amount == 0) {
                    continue;
                }
                final OrderDTO orderDTO = this.takeOrderDto(userPosition.getBrokerId(), userPosition.getUserId(),
                        userPosition.getContractCode(),
                        OrderSideEnum.LONG.getSide().equalsIgnoreCase(userPosition.getSide())
                                ? OrderSideEnum.LONG
                                : OrderSideEnum.SHORT,
                        userPosition.getBrokerPrice(), amount);
                orderDTOList.add(orderDTO);
            }
        }

        final Map<Long, Liquidate> recordMap = allRecords.stream().collect(Collectors.toMap(Liquidate::getId, Function.identity(), (x, y) -> x));
        final List<Order> orders = this.orderShardingService.batchPlaceOrder(positions, OrderFromEnum.API_ORDER, orderDTOList, contract);
        final List<Liquidate> updateLiquidate = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(orders)) {
            final Date date = new Date();
            final List<HistoryLiquidate> liquidates = new LinkedList<>();
            for (final Order order : orders) {
                final UserPosition userPosition = positionMap.get(order.getBrokerId()).get(order.getUserId());
                final HistoryLiquidate historyLiquidate = this.takeHistoryLiquidate(
                        userPosition,
                        order.getAmount().intValue(),
                        order.getOrderId(),
                        marketPrice);
                liquidates.add(historyLiquidate);
                final Liquidate liquidate = recordMap.get(userPosition.getId());
                liquidate.setModifyTime(date);
                liquidate.setCloseOrderId(order.getOrderId());
                updateLiquidate.add(liquidate);
            }
            this.liquidateService.batchEdit(updateLiquidate);
            this.historyLiquidateService.batchAdd(liquidates);
            this.pushService.pushData(contract, PushTypeEnum.ORDER, PushDataUtil.dealOrders(orders, contract).toJSONString(),
                    true, false, false);
        }


        this.userPositionService.batchEdit(positions);

        this.pushService.pushData(contract, PushTypeEnum.ASSET, JSON.toJSONString(PushDataUtil.dealUserBalance(userBalanceList, contract)),
                true, false, false);

        this.pushService.pushData(contract, PushTypeEnum.POSITION, JSON.toJSONString(PushDataUtil.dealUserPositions(positions, contract)),
                true, false, false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean contraTrade(final Contract contract, final List<Contract> contractList, final Liquidate record, final Long header) {
        return true;
    }

    @Override
    public boolean contraTrade(final Contract contract, final List<Contract> contractList, final List<Liquidate> record, final List<UserPositionService.UserRank> header, final Map<String, MarkIndexPriceDTO> priceMap) {
        return true;
    }

    /**
     * 获取平仓数量
     *
     * @param contract
     * @param userBalance
     * @param userPosition
     * @return
     */
    private int takeLiquidateAmount(final Contract contract, final UserBalance userBalance,
                                    final UserPosition userPosition, final BigDecimal marketPrice) {

        final CurrencyPair currencyPair =
                this.currencyPairService.getByPairCode(contract.getPairCode());
        if (currencyPair == null) {
            LiquidateCloseService.log.error("not found currencyPair : {}", currencyPair.getPairCode());
            return 0;
        }

        final BigDecimal maxGear = currencyPair.getMaxGear();
        final BigDecimal diffGear = currencyPair.getDiffGear();

        final BigDecimal totalSize = ContractSizeUtil.countSize(contract, userPosition.getAmount(), userPosition.getPrice());
        BigDecimal tempSize = maxGear;
        while (totalSize.compareTo(tempSize) <= 0 && tempSize.compareTo(BigDecimal.ZERO) >= 0) {
            tempSize = tempSize.subtract(diffGear);
        }

        final BigDecimal result = ContractSizeUtil.countAmount(contract, totalSize.subtract(tempSize), userPosition.getPrice());

        return result.setScale(0, BigDecimal.ROUND_UP).intValue();
    }

    private HistoryLiquidate takeHistoryLiquidate(final UserPosition userPosition,
                                                  final int close, final Long orderId, final BigDecimal marketPrice) {
        final Date date = new Date();
        return HistoryLiquidate.builder().userId(userPosition.getUserId())
                .afterPositionQuantity(userPosition.getAmount().subtract(new BigDecimal(close)))
                .beforePositionQuantity(userPosition.getAmount()).brokerId(userPosition.getBrokerId())
                .brokerPrice(userPosition.getBrokerPrice()).closePositionQuantity(new BigDecimal(close))
                .createdDate(date).liqudatePrice(userPosition.getLiqudatePrice()).marketPrice(marketPrice)
                .modifyDate(date).orderId(orderId).contractCode(userPosition.getContractCode())
                .preLiqudatePrice(userPosition.getPreLiqudatePrice()).side(userPosition.getSide()).build();
    }

    private OrderDTO takeOrderDto(final Integer brokerId, final Long userId,
                                  final String contractCode, final OrderSideEnum sideEnum, final BigDecimal price,
                                  final int amount) {
        return OrderDTO.builder().brokerId(brokerId).userId(userId).contractCode(contractCode)
                .side(OrderSideEnum.LONG.getSide().equals(sideEnum.getSide())
                        ? OrderDetailSideEnum.CLOSE_LONG.getSide()
                        : OrderDetailSideEnum.CLOSE_SHORT.getSide())
                .type(OrderSystemTypeEnum.FORCED_LIQUIDATION.getSystemType()).price(price)
                .amount(new BigDecimal(amount)).build();
    }
}
