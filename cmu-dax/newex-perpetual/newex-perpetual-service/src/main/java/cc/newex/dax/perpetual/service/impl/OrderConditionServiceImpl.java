package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.ConditionOrderTypeEnum;
import cc.newex.dax.perpetual.common.enums.OrderClazzEnum;
import cc.newex.dax.perpetual.common.enums.OrderConditionStatusEnum;
import cc.newex.dax.perpetual.common.enums.OrderDirectionEnum;
import cc.newex.dax.perpetual.common.push.PushData;
import cc.newex.dax.perpetual.criteria.OrderConditionExample;
import cc.newex.dax.perpetual.data.OrderConditionRepository;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Deal;
import cc.newex.dax.perpetual.domain.OrderCondition;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.OrderDTO;
import cc.newex.dax.perpetual.dto.enums.OrderFromEnum;
import cc.newex.dax.perpetual.dto.enums.PushTypeEnum;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.OrderConditionService;
import cc.newex.dax.perpetual.service.OrderShardingService;
import cc.newex.dax.perpetual.service.cache.CacheService;
import cc.newex.dax.perpetual.service.common.MarketService;
import cc.newex.dax.perpetual.service.common.PushService;
import cc.newex.dax.perpetual.util.PushDataUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 计划委托订单表 服务实现
 *
 * @author newex-team
 * @date 2018-11-06 20:39:03
 */
@Slf4j
@Service
public class OrderConditionServiceImpl extends
        AbstractCrudService<OrderConditionRepository, OrderCondition, OrderConditionExample, Long>
        implements OrderConditionService {
    @Autowired
    MarketService marketService;
    @Autowired
    ContractService contractService;
    @Autowired
    OrderShardingService orderShardingService;
    @Autowired
    OrderConditionRepository orderConditionRepository;
    @Resource
    CacheService cacheService;
    @Autowired
    private PushService pushService;

    private static OrderFromEnum getWebFrom(final Integer orderFrom) {
        if (OrderFromEnum.CLIENT_ANDROID_ORDER.getCode() == orderFrom) {
            return OrderFromEnum.CLIENT_ANDROID_ORDER;
        } else if (OrderFromEnum.CLIENT_IOS_ORDER.getCode() == orderFrom) {
            return OrderFromEnum.CLIENT_IOS_ORDER;
        } else {
            return OrderFromEnum.WEB_PAGE_ORDER;
        }
    }


    @Override
    protected OrderConditionExample getPageExample(final String fieldName, final String keyword) {
        final OrderConditionExample example = new OrderConditionExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<OrderCondition> checkCondition() {
        final List<OrderCondition> filterOrderConditionList = new ArrayList<>();
        final OrderConditionExample orderConditionExample = new OrderConditionExample();
        orderConditionExample.createCriteria()
                .andStatusEqualTo(OrderConditionStatusEnum.NODEAL.getStatus());
        final List<OrderCondition> orderConditionList =
                this.orderConditionRepository.selectByExample(orderConditionExample);
        if (orderConditionList.size() == 0) {
            return filterOrderConditionList;
        }
        Map<String, MarkIndexPriceDTO> markIndexPriceDTOMap = null;
        final Map<String, BigDecimal> lastPriceMap = new HashMap<>();
        for (final OrderCondition orderCondition : orderConditionList) {
            final Contract contract = this.contractService.getContract(orderCondition.getContractCode());
            if (ConditionOrderTypeEnum.INDEX.getType().equals(orderCondition.getType())) {
                markIndexPriceDTOMap = this.initMarkIndexPriceDTOMap(markIndexPriceDTOMap);
                final MarkIndexPriceDTO markIndexPriceDTO =
                        markIndexPriceDTOMap.get(contract.getContractCode());
                if (OrderDirectionEnum.getPriceComparator(orderCondition.getDirection())
                        .compare(markIndexPriceDTO.getIndexPrice(), orderCondition.getConditionPrice()) >= 0) {
                        filterOrderConditionList.add(orderCondition);
                }
            } else if (ConditionOrderTypeEnum.MARK.getType().equals(orderCondition.getType())) {
                markIndexPriceDTOMap = this.initMarkIndexPriceDTOMap(markIndexPriceDTOMap);
                final MarkIndexPriceDTO markIndexPriceDTO =
                        markIndexPriceDTOMap.get(contract.getContractCode());
                if (OrderDirectionEnum.getPriceComparator(orderCondition.getDirection())
                        .compare(markIndexPriceDTO.getMarkPrice(), orderCondition.getConditionPrice()) >= 0) {
                        filterOrderConditionList.add(orderCondition);
                }
            } else if (ConditionOrderTypeEnum.LAST.getType().equals(orderCondition.getType())) {
                final BigDecimal lastPrice = this.initLastPriceMap(contract, lastPriceMap);
                if (lastPrice != null && OrderDirectionEnum.getPriceComparator(orderCondition.getDirection())
                        .compare(lastPrice, orderCondition.getConditionPrice()) >= 0) {
                    filterOrderConditionList.add(orderCondition);
                }
            }
        }
        return filterOrderConditionList;
    }

    private Map<String, MarkIndexPriceDTO> initMarkIndexPriceDTOMap(
            final Map<String, MarkIndexPriceDTO> markIndexPriceDTOMap) {
        if (markIndexPriceDTOMap == null) {
            return this.marketService.allMarkIndexPrice();
        }
        return markIndexPriceDTOMap;
    }

    private BigDecimal initLastPriceMap(final Contract contract,
                                        final Map<String, BigDecimal> lastPriceMap) {
        if (!lastPriceMap.containsKey(contract.getContractCode())) {
            final List<Deal> dealList = this.marketService.fills(contract);
            if (dealList != null && dealList.size() > 0) {
                lastPriceMap.put(contract.getContractCode(), dealList.get(0).getPrice());
            }
        }
        return lastPriceMap.get(contract.getContractCode());
    }

    @Override
    public List<OrderCondition> list(final Long userId, final Integer brokerId, final String contractCode) {
        final OrderConditionExample orderConditionExample = new OrderConditionExample();
        orderConditionExample.createCriteria().andUserIdEqualTo(userId).andBrokerIdEqualTo(brokerId)
                .andContractCodeEqualTo(contractCode).andClazzEqualTo(OrderClazzEnum.TRADE.getClazz())
                .andStatusEqualTo(OrderConditionStatusEnum.NODEAL.getStatus());
        final List<OrderCondition> orderConditionList = this.orderConditionRepository.selectByExample(orderConditionExample);
        return orderConditionList;
    }

    @Override
    public List<OrderCondition> listAll(final String contractCode) {
        final OrderConditionExample example = new OrderConditionExample();
        final OrderConditionExample.Criteria criteria = example.createCriteria();
        criteria.andContractCodeEqualTo(contractCode);
        return this.getAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealCondition(final OrderCondition orderCondition) {
        final OrderCondition newOrderCondition =
                this.orderConditionRepository.selectForUpdate(orderCondition.getOrderId());
        if (newOrderCondition.getStatus() != OrderConditionStatusEnum.NODEAL.getStatus()) {
            OrderConditionServiceImpl.log.info("OrderConditionServiceImpl dealCondition fail: {}, {}",
                    orderCondition, newOrderCondition);
            return;
        }
        final OrderFromEnum orderFromEnum =
                OrderConditionServiceImpl.getWebFrom(orderCondition.getOrderFrom());
        final OrderDTO orderDTO = OrderDTO.builder().userId(orderCondition.getUserId())
                .brokerId(orderCondition.getBrokerId()).contractCode(orderCondition.getContractCode())
                .type(orderCondition.getSystemType()).side(orderCondition.getDetailSide())
                .price(orderCondition.getPrice()).amount(orderCondition.getAmount())
                .beMaker(orderCondition.getMustMaker()).build();
        this.orderShardingService.dealOrder(orderDTO, orderFromEnum);
        orderCondition.setStatus(OrderConditionStatusEnum.DEAL.getStatus());
        this.orderConditionRepository.updateById(orderCondition);
    }

    @Override
    public void cancelOrder(final Long userId, final Integer brokerId, final Long id,
                            final String contractCode) {
        final OrderCondition orderCondition = this.orderConditionRepository.selectForUpdate(id);
        if (orderCondition == null
                || orderCondition.getStatus() != OrderConditionStatusEnum.NODEAL.getStatus()) {
            OrderConditionServiceImpl.log.info("OrderConditionServiceImpl cancelOrder fail: {}",
                    orderCondition);
            return;
        }
        orderCondition.setStatus(OrderConditionStatusEnum.CANCEL.getStatus());
        this.orderConditionRepository.updateById(orderCondition);

        final List<OrderCondition> orderConditionList = Arrays.asList(orderCondition);
        final Contract contract = this.contractService.getContract(orderCondition.getContractCode());
        // 推送订单消息
        this.cacheService.convertAndSend((PerpetualConstants.PERPETUAL + "_" + contract.getPairCode()).toUpperCase(),
                JSON.toJSONString(PushData.builder().biz(PerpetualConstants.PERPETUAL).type(PushTypeEnum.CONDITION_ORDER.name())
                        .contractCode(contract.getContractCode())
                        .zip(false).data(PushDataUtil.dealConditionOrders(orderConditionList, contract)).build()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(final List<OrderCondition> list, final Contract contract) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        final List<Long> orderIds = list.stream().map(OrderCondition::getOrderId).collect(Collectors.toList());
        final List<OrderCondition> conditions = this.orderConditionRepository.batchSelectForUpdate(orderIds);
        if (CollectionUtils.isEmpty(conditions)) {
            return;
        }

        final List<OrderCondition> normalList = conditions.stream().filter(x -> x.getStatus()
                .equals(OrderConditionStatusEnum.NODEAL.getStatus())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(normalList)) {
            return;
        }

        final Date nowTime = new Date();
        normalList.forEach(x -> {
            x.setStatus(OrderConditionStatusEnum.CANCEL.getStatus());
            x.setModifyDate(nowTime);
        });

        this.batchEdit(normalList);
    }
}
