package cc.newex.dax.perpetual.scheduler.service.impl;

import cc.newex.dax.perpetual.common.enums.OrderSystemTypeEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.CurrencyPairBrokerRelation;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.OrderCondition;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.dto.enums.PushTypeEnum;
import cc.newex.dax.perpetual.scheduler.service.SettlementUserPositionService;
import cc.newex.dax.perpetual.service.CurrencyPairBrokerRelationService;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import cc.newex.dax.perpetual.service.OrderConditionService;
import cc.newex.dax.perpetual.service.OrderShardingService;
import cc.newex.dax.perpetual.service.UserBalanceService;
import cc.newex.dax.perpetual.service.UserPositionService;
import cc.newex.dax.perpetual.service.common.PushService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 定期合约结算
 */
@Slf4j
@Service
public class SettlementUserPositionServiceImpl implements SettlementUserPositionService {

    @Autowired
    private OrderShardingService orderShardingService;
    @Autowired
    private OrderConditionService orderConditionService;
    @Autowired
    private PushService pushService;
    @Autowired
    private UserBalanceService userBalanceService;
    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private CurrencyPairBrokerRelationService currencyPairBrokerRelationService;

    @Override
    public void settlement(final Contract contract, final List<Contract> contracts) {

        final CurrencyPair currencyPair = this.currencyPairService.getByPairCode(contract.getPairCode());

        if (currencyPair == null) {
            SettlementUserPositionServiceImpl.log.error("not found currencyPair by contractCode : {}", contract.getContractCode());
            return;
        }

        final List<CurrencyPairBrokerRelation> relation = this.currencyPairBrokerRelationService.getByPairCode(currencyPair.getPairCode());

        if (org.apache.commons.collections4.CollectionUtils.isEmpty(relation)) {
            SettlementUserPositionServiceImpl.log.error("not found currency pair broker relation, currencyPairId : {}, pairCode : {}",
                    currencyPair.getId(), currencyPair.getPairCode());
            return;
        }

        final Map<Integer, Map<Long, UserBalance>> balanceMap = new HashMap<>();
        final Map<Integer, Map<Long, List<UserPosition>>> positionMap = new HashMap<>();
        for (final CurrencyPairBrokerRelation r : relation) {
            final List<UserBalance> balances = this.userBalanceService.selectAllBaseInfoForUpdate(contract.getContractCode(), r.getBrokerId());
            if (CollectionUtils.isEmpty(balances)) {
                continue;
            }
            final Map<Long, UserBalance> map = balances.stream().collect(Collectors.toMap(UserBalance::getUserId, Function.identity(), (x, y) -> x));
            balanceMap.put(r.getBrokerId(), map);
            final Map<Long, List<UserPosition>> positions = this.userPositionService.positionMap(r.getBrokerId(), contracts, null);
            if (MapUtils.isEmpty(positions)) {
                continue;
            }
            positionMap.put(r.getBrokerId(), positions);
        }

        // 锁住所有用户
        // 1 撤单 (计划委托、订单)
        final List<PushService.PushContext> orderPushContext = this.cancelOrders(contract);
        if (CollectionUtils.isNotEmpty(orderPushContext)) {
            this.pushService.pushData(orderPushContext);
            return;
        }
        // 2 找到爆仓用户 对敲
        this.contraTrade(contract, balanceMap, positionMap);
        // 3 结算 下结算单
    }


    /**
     * 取消 条件单、订单
     *
     * @param contract
     * @return
     */
    private List<PushService.PushContext> cancelOrders(final Contract contract) {
        final List<PushService.PushContext> result = new ArrayList<>();
        final List<OrderCondition> conditions = this.orderConditionService.listAll(contract.getContractCode());
        if (CollectionUtils.isNotEmpty(conditions)) {
            this.orderConditionService.cancelOrder(conditions, contract);
            final PushService.PushContext context = this.buildContractCodeContext(contract, JSON.toJSONString(conditions), PushTypeEnum.CONDITION_ORDER);
            result.add(context);
        }

        final List<Order> orders = this.orderShardingService.queryContractCodeOrderList(contract.getContractCode());
        if (CollectionUtils.isNotEmpty(orders)) {
            this.orderShardingService.cancelAll(orders, contract.getContractCode(), OrderSystemTypeEnum.SETTLEMENT_CANCEL);
            final PushService.PushContext context = this.buildContractCodeContext(contract, JSON.toJSONString(orders), PushTypeEnum.ORDER);
            result.add(context);
        }
        return result;
    }

    private PushService.PushContext buildContractCodeContext(final Contract contract, final String data, final PushTypeEnum type) {
        return PushService.PushContext.builder()
                .base(false)
                .qoute(false)
                .contractCode(true)
                .contract(contract)
                .data(data)
                .pushTypeEnum(type)
                .build();
    }

    private void contraTrade(final Contract contract,
                             final Map<Integer, Map<Long, UserBalance>> balanceMap,
                             final Map<Integer, Map<Long, List<UserPosition>>> positionMap) {
        return;
    }
}
