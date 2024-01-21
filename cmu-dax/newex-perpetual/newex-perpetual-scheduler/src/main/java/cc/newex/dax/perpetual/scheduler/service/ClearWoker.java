package cc.newex.dax.perpetual.scheduler.service;

import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.PositionClear;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.enums.PushTypeEnum;
import cc.newex.dax.perpetual.service.*;
import cc.newex.dax.perpetual.service.common.PushService;
import cc.newex.dax.perpetual.util.PushDataUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public abstract class ClearWoker implements Callable<BigDecimal> {

    protected List<Long> userIds;
    protected Map<String, MarkIndexPriceDTO> priceMap;
    protected Contract contract;
    protected List<Contract> contracts;
    protected BigDecimal feeRate;
    protected Integer brokerId;
    protected Map<Long, List<UserPosition>> positionMap;
    @Autowired
    protected PositionClearService positionClearService;
    @Autowired
    protected UserBalanceService userBalanceService;
    @Autowired
    protected UserBillService userBillService;
    @Autowired
    protected SystemBillService systemBillService;
    @Autowired
    protected UserPositionService userPositionService;
    @Autowired
    protected PushService pushService;

    public void init(final Integer brokerId, final List<Long> userIds, final Map<String, MarkIndexPriceDTO> priceMap, final Contract contract, final List<Contract> contracts, final BigDecimal feeRate) {
        this.brokerId = brokerId;
        this.userIds = userIds;
        this.priceMap = priceMap;
        this.contract = contract;
        this.contracts = contracts;
        this.feeRate = feeRate;
    }

    protected UserPosition check(final UserBalance userBalance) {
        final List<UserPosition> positions = this.positionMap.get(userBalance.getUserId());
        if (this.feeRate.compareTo(BigDecimal.ZERO) == 0) {
            ClearWoker.log.info("fee rate is 0.");
            return null;
        }

        if (CollectionUtils.isEmpty(positions)) {
            return null;
        }

        final MarkIndexPriceDTO markIndexPriceDTO = this.priceMap.get(this.contract.getContractCode());
        if (markIndexPriceDTO == null) {
            ClearWoker.log.error("market price is null, contractCode : {}", this.contract.getContractCode());
            return null;
        }

        final UserPosition userPosition = positions.stream().filter(u -> u.getContractCode().equalsIgnoreCase(this.contract.getContractCode())).findFirst().orElse(null);

        if (userPosition == null) {
            ClearWoker.log.error("user position is null, contractCode : {}, userId : {}, brokerId : {}",
                    this.contract.getContractCode(), userBalance.getUserId(), userBalance.getBrokerId());
            return null;
        }

        if (userPosition.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        if (userPosition.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            ClearWoker.log.error("user position price less then 0, positionId : {}, price : {}", userPosition.getId(), userPosition.getPrice());
            return null;
        }
        return userPosition;
    }

    protected String[] contractCodes() {
        return this.contracts.stream()
                .filter(c -> c.getBase().equalsIgnoreCase(this.contract.getBase()))
                .map(Contract::getContractCode)
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

    protected PositionClear buildPositionClear(final UserBalance userBalance, final MarkIndexPriceDTO price) {
        final Date date = new Date();
        return PositionClear.builder()
                .brokerId(userBalance.getBrokerId())
                .userId(userBalance.getUserId())
                .contractCode(this.contract.getContractCode())
                .contractId(this.contract.getId())
                .createdDate(date)
                .feeRate(this.feeRate)
                .modifyDate(date)
                .status(0)
                .availableBalance(BigDecimal.ZERO)
                .orderFee(BigDecimal.ZERO)
                .orderMargin(BigDecimal.ZERO)
                .openMargin(BigDecimal.ZERO)
                .sumSize(BigDecimal.ZERO)
                .base(userBalance.getCurrencyCode())
                .lastPrice(this.priceMap.get(this.contract.getContractCode()).getLastPrice())
                .beforeBalance(BigDecimal.ZERO)
                .afterBalance(BigDecimal.ZERO)
                .markPrice(price.getMarkPrice())
                .build();
    }

    protected Map<Long, PositionClear> takeClearRecord() {
        final List<PositionClear> recordMap = this.positionClearService.getPositionClear(this.brokerId, this.contract.getId());
        return recordMap.stream().collect(Collectors.toMap(PositionClear::getUserId, Function.identity(), (x, y) -> x));
    }

    protected List<UserBalance> lockUserBalance() {
        if (CollectionUtils.isEmpty(this.userIds)) {
            return new ArrayList<>();
        }
        final List<UserBalance> userBalances = this.userBalanceService.selectBatchForUpdate(this.contract.getBase(), this.brokerId, new HashSet<>(this.userIds));
        final List<UserPosition> positions = this.userPositionService.selectBatchPositionByBase(this.contract.getBase(), this.brokerId, new HashSet<>(this.userIds));
        this.positionMap = positions.stream().collect(Collectors.groupingBy(UserPosition::getUserId));
        return userBalances;
    }

    protected BigDecimal userBalance(final UserBalance userBalance) {
        return userBalance.getAvailableBalance().add(userBalance.getFrozenBalance())
                .add(userBalance.getPositionMargin()).add(userBalance.getPositionFee())
                .add(userBalance.getOrderMargin()).add(userBalance.getOrderFee());
    }

    protected void pushBalanceAndPosition(final Contract contract, final List<UserBalance> userBalances, final List<UserPosition> positions) {
        this.pushService.pushData(this.contract, PushTypeEnum.ASSET, JSON.toJSONString(PushDataUtil.dealUserBalance(userBalances, contract)), false, false, false);
        this.pushService.pushData(this.contract, PushTypeEnum.POSITION, JSON.toJSONString(PushDataUtil.dealUserPositions(positions, contract)), true, false, false);
    }
}
