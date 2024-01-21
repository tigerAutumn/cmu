package cc.newex.dax.perpetual.scheduler.task.order;

import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import cc.newex.dax.perpetual.service.OrderShardingService;
import cc.newex.dax.perpetual.service.OrderShardingService.ContractCodeUserIdBrokerIdBean;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 检查订单保证金不足的订单,撤销订单
 */
@Slf4j
@Component
@JobHandler("ClearOrderJob")
public class ClearOrderJob extends IJobHandler {
    @Autowired
    private OrderShardingService orderShardingService;
    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private ContractService contractService;

    @Override
    public ReturnT<String> execute(final String s) {
        final List<Contract> contractList = this.contractService.getUnExpiredContract();

        if (CollectionUtils.isEmpty(contractList)) {
            ClearOrderJob.log.warn("contract list is empty");
            return ReturnT.SUCCESS;
        }

        for (final Contract contract : contractList) {
            try {
                final List<Order> orderList = this.orderShardingService.queryContractCodeOrderList(contract.getContractCode());
                if (CollectionUtils.isEmpty(orderList)) {
                    continue;
                }
                final CurrencyPair currencyPair = this.currencyPairService.getByPairCode(contract.getPairCode());
                final Set<ContractCodeUserIdBrokerIdBean> contractCodeUserIdBrokerIdBeanMap = new HashSet<>();
                for (final Order order : orderList) {
                    final ContractCodeUserIdBrokerIdBean contractCodeUserIdBrokerIdBean =
                            ContractCodeUserIdBrokerIdBean.builder().contractCode(order.getContractCode())
                                    .userId(order.getUserId()).brokerId(order.getBrokerId()).build();
                    if (!contractCodeUserIdBrokerIdBeanMap.add(contractCodeUserIdBrokerIdBean)) {
                        continue;
                    }
                    this.orderShardingService.checkOrderMargin(contractCodeUserIdBrokerIdBean, currencyPair, contract);
                }
            } catch (final Exception e) {
                ClearOrderJob.log.error("unexpected error", e);
            }
        }

        return ReturnT.SUCCESS;
    }
}
