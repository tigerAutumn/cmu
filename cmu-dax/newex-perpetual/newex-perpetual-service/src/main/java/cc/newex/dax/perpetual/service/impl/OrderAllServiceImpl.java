package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.common.enums.OrderClazzEnum;
import cc.newex.dax.perpetual.criteria.OrderAllExample;
import cc.newex.dax.perpetual.data.OrderAllRepository;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.OrderAll;
import cc.newex.dax.perpetual.dto.enums.OrderStatusEnum;
import cc.newex.dax.perpetual.service.OrderAllService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全部订单表 服务实现
 *
 * @author newex-team
 * @date 2018-11-01 09:29:33
 */
@Slf4j
@Service
public class OrderAllServiceImpl
        extends AbstractCrudService<OrderAllRepository, OrderAll, OrderAllExample, Long>
        implements OrderAllService {
    @Autowired
    private OrderAllRepository orderAllRepository;

    @Override
    protected OrderAllExample getPageExample(final String fieldName, final String keyword) {
        final OrderAllExample example = new OrderAllExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int batchInsertOrderAllDealOnDuplicate(final List<Order> orderList) {
        return this.orderAllRepository.batchInsertOrderAllDealOnDuplicate(orderList);
    }

    @Override
    public int deleteByOrderIds(final List<Order> orderList) {
        return this.orderAllRepository.deleteByOrderIds(orderList);
    }

    @Override
    public List<OrderAll> queryOrderTypeList(final Long userId, final Integer brokerId, final String type) {
        final OrderAllExample example = new OrderAllExample();
        example.createCriteria().andUserIdEqualTo(userId).andBrokerIdEqualTo(brokerId)
                .andClazzEqualTo(OrderClazzEnum.TRADE.getClazz()).andStatusNotEqualTo(OrderStatusEnum.CANCELED.getCode());

        final List<OrderAll> ordersList = this.orderAllRepository.selectByExample(example);
        if (StringUtils.isNotEmpty(type)) {
            ordersList.stream().filter(order -> order.getSide().equalsIgnoreCase(type)).collect(Collectors.toList());
        }
        return ordersList;
    }

    @Override
    public List<OrderAll> queryOrderTypeList(final List<Long> userId, final Integer brokerId, final List<Contract> contract) {
        final OrderAllExample example = new OrderAllExample();
        final OrderAllExample.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isNotEmpty(userId)) {
            criteria.andUserIdIn(userId);
        }
        if (brokerId != null) {
            criteria.andBrokerIdEqualTo(brokerId);
        }
        if (CollectionUtils.isNotEmpty(contract)) {
            criteria.andContractCodeIn(contract.stream().map(Contract::getContractCode).collect(Collectors.toList()));
        }

        return this.orderAllRepository.selectByExample(example);
    }

    @Override
    public List<OrderAll> getInByOrderId(final List<Long> orderIds) {
        if (CollectionUtils.isEmpty(orderIds)) {
            return new ArrayList<>();
        }
        final OrderAllExample example = new OrderAllExample();
        example.createCriteria().andOrderIdIn(orderIds);
        return this.getByExample(example);
    }

}
