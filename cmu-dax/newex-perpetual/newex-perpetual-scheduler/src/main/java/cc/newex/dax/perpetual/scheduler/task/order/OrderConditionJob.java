package cc.newex.dax.perpetual.scheduler.task.order;

import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.OrderConditionStatusEnum;
import cc.newex.dax.perpetual.common.exception.BizException;
import cc.newex.dax.perpetual.common.push.PushData;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.OrderCondition;
import cc.newex.dax.perpetual.dto.enums.PushTypeEnum;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.OrderConditionService;
import cc.newex.dax.perpetual.service.cache.CacheService;
import cc.newex.dax.perpetual.util.PushDataUtil;
import com.alibaba.fastjson.JSON;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 条件订单
 */
@Slf4j
@Component
@JobHandler("ConditionOrderJob")
public class OrderConditionJob extends IJobHandler {
    @Autowired
    private OrderConditionService orderConditionService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private ContractService contractService;

    @Override
    public ReturnT<String> execute(final String param) throws Exception {
        final List<OrderCondition> orderConditionList = this.orderConditionService.checkCondition();
        for (final OrderCondition orderCondition : orderConditionList) {
            final Contract contract = this.contractService.getContract(orderCondition.getContractCode());
            try {
                this.orderConditionService.dealCondition(orderCondition);
            } catch (final BizException e) {
                OrderConditionJob.log.error("deal order condition failed", e);
                orderCondition.setStatus(OrderConditionStatusEnum.CANCEL.getStatus());
                this.orderConditionService.editById(orderCondition);
            }
            this.cacheService.convertAndSend((PerpetualConstants.PERPETUAL + "_" + contract.getPairCode()).toUpperCase(),
                    JSON.toJSONString(PushData.builder().biz(PerpetualConstants.PERPETUAL).type(PushTypeEnum.CONDITION_ORDER.name())
                            .contractCode(contract.getContractCode())
                            .zip(false).data(PushDataUtil.dealConditionOrders(Arrays.asList(orderCondition), contract)).build()));
        }
        return ReturnT.SUCCESS;
    }
}
