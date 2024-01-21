package cc.newex.dax.perpetual.scheduler.service.impl;

import cc.newex.dax.perpetual.common.enums.OrderSystemTypeEnum;
import cc.newex.dax.perpetual.common.enums.PositionTypeEnum;
import cc.newex.dax.perpetual.domain.PositionClear;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.scheduler.service.ClearWoker;
import cc.newex.dax.perpetual.service.OrderShardingService;
import cc.newex.dax.perpetual.util.ClearPositionUtils;
import cc.newex.dax.perpetual.util.FormulaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@Slf4j
@Service
@Scope("prototype")
public class ClearSubWorker extends ClearWoker implements Callable<BigDecimal> {

    @Autowired
    private OrderShardingService orderShardingService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal call() throws Exception {
        if (CollectionUtils.isEmpty(this.userIds)) {
            return BigDecimal.ZERO;
        }
        BigDecimal size = BigDecimal.ZERO;
        final List<PositionClear> positionClears = new LinkedList<>();
        final List<UserBalance> editBalances = new LinkedList<>();
        final List<UserPosition> editPositions = new LinkedList<>();
        final Map<Long, PositionClear> longPositionClearMap = this.takeClearRecord();
        final List<UserBalance> userBalances = this.lockUserBalance();
        final MarkIndexPriceDTO markPrice = this.priceMap.get(this.contract.getContractCode());
        for (final UserBalance userBalance : userBalances) {
            final UserPosition userPosition = this.check(userBalance);
            if (userPosition == null) {
                continue;
            }
            PositionClear positionClear = longPositionClearMap.get(userBalance.getUserId());
            if (positionClear != null) {
                size = size.add(positionClear.getSumSize().abs());
                continue;
            }
            final List<UserPosition> positions = this.positionMap.get(userBalance.getUserId());

            // (持仓张数*资金费率)/标记价格
            final BigDecimal totalSize = ClearPositionUtils.takeTotalSize(this.contract, userPosition, markPrice);
            BigDecimal tempSize = totalSize;
            positionClear = this.buildPositionClear(userBalance, markPrice);
            positionClear.setBeforeBalance(this.userBalance(userBalance));
            // 如果是全仓 从 用户余额、订单保证金、订单手续费、仓位保证金中扣除 如果是逐仓 从 仓位保证金中扣除
            if (userPosition.getType().equals(PositionTypeEnum.ALL_IN.getType())) {
                // 全仓 如果 仓位保证金-未实现 > 0 则最大可用 是 available， 否则 available - （仓位保证金-未实现）
                BigDecimal availableBalance = userBalance.getAvailableBalance();
                // 未实现的亏损（仓位保证金-未实现）> 0 则为 0 否则 （仓位保证金-未实现）
                BigDecimal subTotal = BigDecimal.ZERO;
                for (final UserPosition u : positions) {
                    if (u.getType().equals(PositionTypeEnum.PART_IN.getType())) {
                        continue;
                    }
                    subTotal = subTotal.add(this.getSubAvailableBalance(userPosition, markPrice.getMarkPrice()));
                }
                availableBalance = availableBalance.subtract(subTotal);
                // 如果 剩余的 available 不够资金费用 则撤单
                if (availableBalance.compareTo(tempSize) < 0
                        && (userBalance.getOrderMargin().compareTo(BigDecimal.ZERO) > 0
                        || userBalance.getOrderFee().compareTo(BigDecimal.ZERO) > 0)) {
                    subTotal = BigDecimal.ZERO;
                    final String[] contractCodes = this.contractCodes();
                    // 撤销所有订单
                    for (final String contractCode : contractCodes) {
                        this.orderShardingService.cancelAllByContractCode(userPosition.getBrokerId(),
                                userPosition.getUserId(), contractCode, OrderSystemTypeEnum.SETTLEMENT_CANCEL);
                    }

                    // 把订单保证金、订单手续费 挪到 available balance
                    for (final UserPosition u : positions) {
                        userBalance.setAvailableBalance(userBalance.getAvailableBalance().add(u.getOrderMargin()).add(u.getOrderFee()));
                        u.setOrderMargin(BigDecimal.ZERO);
                        u.setOrderFee(BigDecimal.ZERO);
                    }
                    userBalance.setOrderMargin(BigDecimal.ZERO);
                    userBalance.setOrderFee(BigDecimal.ZERO);
                    availableBalance = userBalance.getAvailableBalance();
                    for (final UserPosition u : positions) {
                        if (u.getType().equals(PositionTypeEnum.PART_IN.getType())) {
                            continue;
                        }
                        subTotal = subTotal.add(this.getSubAvailableBalance(userPosition, markPrice.getMarkPrice()));
                    }
                    availableBalance = availableBalance.subtract(subTotal);
                }

                if (availableBalance.compareTo(BigDecimal.ZERO) >= 0) {
                    if (availableBalance.compareTo(tempSize) >= 0) {
                        positionClear.setAvailableBalance(tempSize);
                        userBalance.setAvailableBalance(userBalance.getAvailableBalance().subtract(tempSize));
                        tempSize = BigDecimal.ZERO;
                    } else {
                        positionClear.setAvailableBalance(userBalance.getAvailableBalance().subtract(availableBalance));
                        tempSize = tempSize.subtract(availableBalance);
                        userBalance.setAvailableBalance(userBalance.getAvailableBalance().subtract(availableBalance));

                        final BigDecimal unrealSize = FormulaUtil.calcProfit(userPosition, markPrice.getMarkPrice());

                        final BigDecimal openMargin = unrealSize.compareTo(BigDecimal.ZERO) >= 0 ? userPosition.getOpenMargin() : userPosition.getOpenMargin().subtract(unrealSize.abs());
                        if (openMargin.compareTo(BigDecimal.ZERO) > 0) {
                            if (openMargin.compareTo(tempSize) >= 0) {
                                positionClear.setOpenMargin(positionClear.getOpenMargin().add(tempSize));
                                userPosition.setOpenMargin(userPosition.getOpenMargin().subtract(tempSize));
                                userBalance.setPositionMargin(userBalance.getPositionMargin().subtract(tempSize));
                                tempSize = BigDecimal.ZERO;
                            } else {
                                positionClear.setOpenMargin(positionClear.getOpenMargin().add(openMargin));
                                tempSize = tempSize.subtract(openMargin);
                                userPosition.setOpenMargin(userPosition.getOpenMargin().subtract(openMargin));
                                userBalance.setPositionMargin(userBalance.getPositionMargin().subtract(openMargin));
                            }
                        }

                    }
                }
            } else {
                // 逐仓 开仓保证金-未实现
                BigDecimal availableBalance = userPosition.getOpenMargin();
                final BigDecimal unrealSize = FormulaUtil.calcProfit(userPosition, markPrice.getMarkPrice());
                availableBalance = unrealSize.compareTo(BigDecimal.ZERO) < 0 ? availableBalance.subtract(unrealSize.abs()) : availableBalance;
                if (availableBalance.compareTo(BigDecimal.ZERO) > 0) {
                    if (availableBalance.compareTo(tempSize) >= 0) {
                        positionClear.setOpenMargin(tempSize);
                        userBalance.setPositionMargin(userBalance.getPositionMargin().subtract(tempSize));
                        userPosition.setOpenMargin(userPosition.getOpenMargin().subtract(tempSize));
                        tempSize = BigDecimal.ZERO;
                    } else {
                        positionClear.setOpenMargin(availableBalance);
                        userBalance.setPositionMargin(userBalance.getPositionMargin().subtract(availableBalance));
                        tempSize = tempSize.subtract(availableBalance);
                        userPosition.setOpenMargin(userPosition.getOpenMargin().subtract(availableBalance));
                    }
                }
            }
            size = size.add(totalSize.subtract(tempSize));
            // 重新计算强平价 破产价
            FormulaUtil.calculationBrokerForcedLiquidationPrice(this.contracts, this.priceMap, userPosition, positions, userBalance);
            // 已实现盈亏
            userPosition.setRealizedSurplus(userPosition.getRealizedSurplus().subtract(totalSize.subtract(tempSize)));
            userBalance.setRealizedSurplus(userBalance.getRealizedSurplus().subtract(totalSize.subtract(tempSize)));
            // 更新清算记录
            positionClear.setType(userPosition.getType());
            positionClear.setSide(userPosition.getSide());
            positionClear.setAfterBalance(this.userBalance(userBalance));
            positionClear.setRealizedSurplus(userPosition.getRealizedSurplus());
            positionClear.setAmount(userPosition.getAmount());
            positionClear.setSumSize(tempSize.subtract(totalSize));
            positionClears.add(positionClear);
            editBalances.add(userBalance);
            editPositions.addAll(positions);
        }
        if (CollectionUtils.isNotEmpty(editBalances)) {
            this.userBalanceService.batchEdit(editBalances);
        }
        if (CollectionUtils.isNotEmpty(editPositions)) {
            this.userPositionService.batchEdit(editPositions);
        }
        if (CollectionUtils.isNotEmpty(positionClears)) {
            this.positionClearService.batchAdd(positionClears);
        }
        this.pushBalanceAndPosition(this.contract, editBalances, editPositions);
        return size;
    }

    /**
     * 获取需要从 available 扣除的金额
     *
     * @param userPosition
     * @param price
     * @return 盈利 返回 0， 亏损 返回 |开仓保证金 - 未实现|
     */
    private BigDecimal getSubAvailableBalance(final UserPosition userPosition, final BigDecimal price) {
        final BigDecimal unrealSize = FormulaUtil.calcProfit(userPosition, price);
        return unrealSize.compareTo(BigDecimal.ZERO) >= 0
                ? BigDecimal.ZERO
                : (userPosition.getOpenMargin().compareTo(unrealSize.abs()) >= 0
                ? BigDecimal.ZERO
                : userPosition.getOpenMargin().subtract(unrealSize.abs()).abs());
    }
}
