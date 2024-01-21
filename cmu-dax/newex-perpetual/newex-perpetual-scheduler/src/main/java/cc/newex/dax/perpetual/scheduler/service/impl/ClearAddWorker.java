package cc.newex.dax.perpetual.scheduler.service.impl;

import cc.newex.dax.perpetual.common.enums.PositionTypeEnum;
import cc.newex.dax.perpetual.domain.PositionClear;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.scheduler.service.ClearWoker;
import cc.newex.dax.perpetual.util.FormulaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
public class ClearAddWorker extends ClearWoker implements Callable<BigDecimal> {

    private BigDecimal size;
    private Map<Long, BigDecimal> expectSizeMap;

    public BigDecimal getSize() {
        return this.size;
    }

    public void setSize(final BigDecimal size) {
        this.size = size;
    }

    public Map<Long, BigDecimal> getExpectSizeMap() {
        return this.expectSizeMap;
    }

    public void setExpectSizeMap(final Map<Long, BigDecimal> expectSizeMap) {
        this.expectSizeMap = expectSizeMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal call() throws Exception {
        if (CollectionUtils.isEmpty(this.userIds)) {
            return BigDecimal.ZERO;
        }
        if (this.size.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        final BigDecimal tempSize = this.size;
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
            final List<UserPosition> positions = this.positionMap.get(userBalance.getUserId());
            PositionClear positionClear = longPositionClearMap.get(userBalance.getUserId());
            if (positionClear != null) {
                this.size = this.size.subtract(positionClear.getSumSize().abs());
                continue;
            }
            positionClear = this.buildPositionClear(userBalance, markPrice);
            positionClear.setBeforeBalance(this.userBalance(userBalance));

            BigDecimal totalSize = this.expectSizeMap.get(userBalance.getUserId());
            if (totalSize.compareTo(this.size) >= 0) {
                totalSize = this.size;
                this.size = BigDecimal.ZERO;
            } else {
                this.size = this.size.subtract(totalSize);
            }
            if (PositionTypeEnum.ALL_IN.getType() == userPosition.getType()) {
                positionClear.setAvailableBalance(positionClear.getAvailableBalance().add(totalSize));
                userBalance.setAvailableBalance(userBalance.getAvailableBalance().add(totalSize));
            } else {
                positionClear.setOpenMargin(positionClear.getOpenMargin().add(totalSize));
                userPosition.setOpenMargin(userPosition.getOpenMargin().add(totalSize));
                userBalance.setPositionMargin(userBalance.getPositionMargin().add(totalSize));
            }
            FormulaUtil.calculationBrokerForcedLiquidationPrice(this.contracts, this.priceMap, userPosition, positions, userBalance);
            userPosition.setRealizedSurplus(userPosition.getRealizedSurplus().add(totalSize));
            userBalance.setRealizedSurplus(userBalance.getRealizedSurplus().add(totalSize));
            positionClear.setRealizedSurplus(userPosition.getRealizedSurplus());
            positionClear.setAfterBalance(this.userBalance(userBalance));
            positionClear.setAmount(userPosition.getAmount());
            positionClear.setType(userPosition.getType());
            positionClear.setSide(userPosition.getSide());
            positionClear.setSumSize(positionClear.getOrderMargin().add(positionClear.getAvailableBalance()).add(positionClear.getOrderFee()).add(positionClear.getOpenMargin()));
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
        return tempSize.subtract(this.size);
    }

    public BigDecimal getExpectSize() {
        if (CollectionUtils.isEmpty(this.userIds)) {
            return BigDecimal.ZERO;
        }
        BigDecimal result = BigDecimal.ZERO;
        for (final Long userId : this.userIds) {
            if (this.expectSizeMap.containsKey(userId)) {
                result = result.add(this.expectSizeMap.get(userId));
            } else {
                ClearAddWorker.log.error("not found from expected data : userId : {}", userId);
            }
        }
        return result;
    }
}
